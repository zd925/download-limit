package com.kongtrio.demo.downloadlimit.pool;

import com.kongtrio.demo.downloadlimit.entity.FTPConfig;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.InputStream;
import java.util.Properties;

public class FTPClientPool{
    private GenericObjectPool<FTPClient> ftpClientPool;
    public FTPClientPool(InputStream in){
        Properties pro = new Properties();
        try {
            pro.load(in);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        // 初始化对象池配置
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setBlockWhenExhausted(Boolean.parseBoolean(pro.getProperty("ftpClient_blockWhenExhausted")));
        poolConfig.setMaxWaitMillis(Long.parseLong(pro.getProperty("ftpClient_maxWait")));
        poolConfig.setMinIdle(Integer.parseInt(pro.getProperty("ftpClient_minIdle")));
        poolConfig.setMaxIdle(Integer.parseInt(pro.getProperty("ftpClient_maxIdle")));
        poolConfig.setMaxTotal(Integer.parseInt(pro.getProperty("ftpClient_maxTotal")));
        poolConfig.setTestOnBorrow(Boolean.parseBoolean(pro.getProperty("ftpClient_testOnBorrow")));
        poolConfig.setTestOnReturn(Boolean.parseBoolean(pro.getProperty("ftpClient_testOnReturn")));
        poolConfig.setTestOnCreate(Boolean.parseBoolean(pro.getProperty("ftpClient_testOnCreate")));
        poolConfig.setTestWhileIdle(Boolean.parseBoolean(pro.getProperty("ftpClient_testWhileIdle")));
        poolConfig.setLifo(Boolean.parseBoolean(pro.getProperty("ftpClient_lifo")));

        FTPConfig ftpConfig=new FTPConfig();
        ftpConfig.setHost(pro.getProperty("ftpClient_host"));
        ftpConfig.setPort(Integer.parseInt(pro.getProperty("ftpClient_port")));
        ftpConfig.setUsername(pro.getProperty("ftpClient_username"));
        ftpConfig.setPassword(pro.getProperty("ftpClient_pasword"));
        ftpConfig.setClientTimeout(Integer.parseInt(pro.getProperty("ftpClient_clientTimeout")));
        ftpConfig.setEncoding(pro.getProperty("ftpClient_encoding"));
        ftpConfig.setWorkingDirectory(pro.getProperty("ftpClient_workingDirectory"));
        ftpConfig.setPassiveMode(Boolean.parseBoolean(pro.getProperty("ftpClient_passiveMode")));
        ftpConfig.setRenameUploaded(Boolean.parseBoolean(pro.getProperty("ftpClient_renameUploaded")));
        ftpConfig.setRetryTimes(Integer.parseInt(pro.getProperty("ftpClient_retryTimes")));
        ftpConfig.setTransferFileType(Integer.parseInt(pro.getProperty("ftpClient_transferFileType")));
        ftpConfig.setBufferSize(Integer.parseInt(pro.getProperty("ftpClient_bufferSize")));
        // 初始化对象池
        ftpClientPool = new GenericObjectPool<FTPClient>(new FTPClientFactory(ftpConfig), poolConfig);
    }
    public FTPClient borrowObject() throws Exception {
        return ftpClientPool.borrowObject();
    }
    public void returnObject(FTPClient ftpClient) {
        ftpClientPool.returnObject(ftpClient);
    }
}
