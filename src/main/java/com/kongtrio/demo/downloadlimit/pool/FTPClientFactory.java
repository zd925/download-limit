package com.kongtrio.demo.downloadlimit.pool;

import com.kongtrio.demo.downloadlimit.entity.FTPConfig;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.io.IOException;

public class FTPClientFactory extends BasePooledObjectFactory<FTPClient> {
	private FTPConfig ftpConfig;

	public FTPClientFactory(FTPConfig ftpConfig) {
		this.ftpConfig = ftpConfig;
	}

	/**
	 * 新建对象
	 */
	@Override
	public FTPClient create() throws Exception {
		FTPClient ftpClient = new FTPClient();
		ftpClient.setConnectTimeout(ftpConfig.getClientTimeout());
		try {
			ftpClient.connect(ftpConfig.getHost(), ftpConfig.getPort());
			int reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				System.out.println("FTPServer 拒绝连接");
				return null;
			}
			boolean result = ftpClient.login(ftpConfig.getUsername(), ftpConfig.getPassword());
			if (!result) {
				System.out.println("ftpClient登陆失败!");
				throw new Exception(
						"ftpClient登陆失败! userName:" + ftpConfig.getUsername() + " ; password:" + ftpConfig.getPassword());
			}
			ftpClient.setFileType(ftpConfig.getTransferFileType());
			ftpClient.setBufferSize(ftpConfig.getBufferSize());
			ftpClient.setControlEncoding(ftpConfig.getEncoding());
			if (ftpConfig.getPassiveMode()) {
				ftpClient.enterLocalPassiveMode();
			}
			ftpClient.changeWorkingDirectory(ftpConfig.getWorkingDirectory());
		} catch (IOException e) {
			System.out.println("FTP连接失败：" + e);
		}
		return ftpClient;
	}

	@Override
	public PooledObject<FTPClient> wrap(FTPClient ftpClient) {
		return new DefaultPooledObject<FTPClient>(ftpClient);
	}

	/**
	 * 销毁对象
	 */
	@Override
	public void destroyObject(PooledObject<FTPClient> p) throws Exception {
		FTPClient ftpClient = p.getObject();
		ftpClient.logout();
		super.destroyObject(p);
	}

	/**
	 * 验证对象
	 */
	@Override
	public boolean validateObject(PooledObject<FTPClient> p) {
		FTPClient ftpClient = p.getObject();
		boolean connect = false;
		try {
			connect = ftpClient.sendNoOp();
			if (connect) {
				ftpClient.changeWorkingDirectory(ftpConfig.getWorkingDirectory());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return connect;
	}
}
