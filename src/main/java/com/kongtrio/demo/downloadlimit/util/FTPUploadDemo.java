package com.kongtrio.demo.downloadlimit.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
import java.net.SocketException;

/**
 * @author DH
 * @date 2020-03-09 09:11
 */
public class FTPUploadDemo {
	public static void main(String[] args) throws IOException {

		//创建客户端对象
		FTPClient ftp = new FTPClient();
		InputStream local = null;
		try {
			//连接ftp服务器
			ftp.connect("10.1.51.83", 21);
			//登录
			ftp.login("ftp", "");
			//设置上传路径
			String path = "/pub/test";
			//检查上传路径是否存在 如果不存在返回false
			boolean flag = ftp.changeWorkingDirectory(path);
			if (!flag) {
				//创建上传的路径  该方法只能创建一级目录，在这里如果/home/ftpuser存在则可创建image
				ftp.makeDirectory(path);
			}
			//指定上传路径
			ftp.changeWorkingDirectory(path);
			//指定上传文件的类型  二进制文件
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			//读取本地文件
			String s = "I am super";
			local = new ByteArrayInputStream(s.getBytes());
			//第一个参数是文件名
			ftp.storeFile(System.currentTimeMillis() + ".log", local);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				//关闭文件流
				local.close();
				//退出
				ftp.logout();
				//断开连接
				ftp.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

