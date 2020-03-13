package com.kongtrio.demo.downloadlimit.inputstream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author DH
 * @since 2020-03-10 14:13
 * <p>
 * 字节流限流器
 * 主要思想：
 * 定义一个chunk(数据块)以及允许的最大速率 maxRate(单位 KB/s)。  
            (可以计算在阈值的速率时间多少   ,如果正常时间小于计算时间 正常速率比较慢,反之快了,睡一下)
 * 通过maxRate我们可以算出，在maxRate的速率下，通过chunk大小的字节流所需要的时间 expectByChunkTime
 * 在读取/写入字节时，我们维护已经读取/写入的字节量 bytesWillBySentOrReceive。
 * 当bytesWillBySentOrReceive达到chunk的大小时，检查期间过去的时间(nowNanoTime-lastPieceSentOrReceiveTick)
 * 如果过去的时间小于 expectByChunkTime，说明当前的速率已经超过了 maxRate的速率，这时候就需要休眠一会来限制流量
 * 如果速率没超过或者休眠完后 将bytesWillBySentOrReceive=bytesWillBySentOrReceive-chunk
 * 之后继续检查
 */
public class BandwidthLimiter {

    private static final Logger LOGGER = LoggerFactory.getLogger(BandwidthLimiter.class);
    //KB代表的字节数
    private static final Long KB = 1024L;
    //一个chunk的大小，单位byte
    private static final Long CHUNK_LENGTH = 1024 * 1024L;

    //已经发送/读取的字节数
    private int bytesWillBySentOrReceive = 0;
    //上一次接收到字节流的时间戳——单位纳秒
    private long lastPieceSentOrReceiveTick = System.nanoTime();
    //允许的最大速率，默认为 1024KB/s
    private int maxRate = 1024;
    //在maxRate的速率下，通过chunk大小的字节流要多少时间（纳秒）
    private long expectByChunkTime = (1000000000L * CHUNK_LENGTH) / (this.maxRate * KB);

    public BandwidthLimiter(int maxRate) {
        this.setMaxRate(maxRate);
    }

    //动态调整最大速率
    public void setMaxRate(int maxRate) {
        if (maxRate < 0) {
            throw new IllegalArgumentException("maxRate can not less than 0");
        }
        this.maxRate = maxRate;
        if (maxRate == 0) {
            this.expectByChunkTime = 0;
        } else {
            this.expectByChunkTime = (1000000000L * CHUNK_LENGTH) / (this.maxRate * KB);
        }
    }

    public synchronized void limitNextBytes() {
        this.limitNextBytes(1);
    }

    public synchronized void limitNextBytes(int len) {
        this.bytesWillBySentOrReceive += len;

        while (this.bytesWillBySentOrReceive > CHUNK_LENGTH) {
            long nowTick = System.nanoTime();
            long passTime = nowTick - this.lastPieceSentOrReceiveTick;
            long missedTime = this.expectByChunkTime - passTime;
            if (missedTime > 0) {
                try {
                    Thread.sleep(missedTime / 1000000, (int) (missedTime % 1000000));
                } catch (InterruptedException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
            this.bytesWillBySentOrReceive -= CHUNK_LENGTH;
            this.lastPieceSentOrReceiveTick = nowTick + (missedTime > 0 ? missedTime : 0);
        }
    }
}
