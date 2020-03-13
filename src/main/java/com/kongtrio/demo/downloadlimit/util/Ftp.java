package com.kongtrio.demo.downloadlimit.util;

import com.kongtrio.demo.downloadlimit.pool.FTPClientPool;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Ftp {
	private static FTPClientPool ftpClientPool;

	static {
		//      ftpClientPool=new FTPClientPool(Thread.currentThread().getContextClassLoader().getResourceAsStream("ftpClient.properties"));
		ftpClientPool = new FTPClientPool(Ftp.class.getClassLoader().getResourceAsStream("ftpClient.properties"));
	}



	public static void sendFile() {
		long start = System.currentTimeMillis();
		InputStream inputStream = null;
		FTPClient ftpClient = null;
		try {
			ftpClient = ftpClientPool.borrowObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String path = "D:\\Program Files\\kafkatool2\\error.log";
			File file = new File(path);
			ftpClient.changeWorkingDirectory("/pub/test");

			inputStream = new FileInputStream(file);
			String fileName = System.currentTimeMillis() + ".log";
			boolean flag = ftpClient.storeFile(new String(fileName.getBytes("GBK"), "iso-8859-1"), inputStream);
			long end = System.currentTimeMillis();
			long lo = end - start;
			System.out.println("耗时文件上传：" + lo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		} finally {
			ftpClientPool.returnObject(ftpClient);
		}
	}
}
