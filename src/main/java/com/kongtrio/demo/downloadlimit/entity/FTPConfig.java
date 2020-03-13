package com.kongtrio.demo.downloadlimit.entity;
/**
 * FTP属性相关的配置
 */
public class FTPConfig{
    /**
     *  主机
     */
    private String host;
    /**
     * 端口
     */
    private int port;
    /**
     *  用户名
     */
    private String username;
    /**
     *  用户密码
     */
    private String password;
    /**
     * 连接是否为主动模式
     */
    private boolean passiveMode;
    /**
     * 编码
     */
    private String encoding;
    /**
     * 超时时间
     */
    private int clientTimeout;
    /**
     * 线程数
     */
    private int threadNum;
    /**
     * 文件传送类型
     */
    private int transferFileType;
    /**
     * 是否重命名
     */
    private boolean renameUploaded;
    /**
     * 重新连接时间
     */
    private int retryTimes;
    /**
     * 缓存大小
     */
    private int bufferSize;
    /**
     * 默认进入的路径
     */
    private String workingDirectory;


    public String getWorkingDirectory() {
        return workingDirectory;
    }
    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }
    public int getBufferSize() {
        return bufferSize;
    }
    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean getPassiveMode() {
        return passiveMode;
    }
    public void setPassiveMode(boolean passiveMode) {
        this.passiveMode = passiveMode;
    }
    public String getEncoding() {
        return encoding;
    }
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
    public int getClientTimeout() {
        return clientTimeout;
    }
    public void setClientTimeout(int clientTimeout) {
        this.clientTimeout = clientTimeout;
    }
    public int getThreadNum() {
        return threadNum;
    }
    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }
    public int getTransferFileType() {
        return transferFileType;
    }
    public void setTransferFileType(int transferFileType) {
        this.transferFileType = transferFileType;
    }
    public boolean isRenameUploaded() {
        return renameUploaded;
    }
    public void setRenameUploaded(boolean renameUploaded) {
        this.renameUploaded = renameUploaded;
    }
    public int getRetryTimes() {
        return retryTimes;
    }
    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

}
