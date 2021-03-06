package com.collmall.model;

import java.io.Serializable;

/**
 * schedule service param
 * @author xilihui
 * @date  2019-01-25
 */
public class ScheduleParam implements Serializable {

	private static final long serialVersionUID = 1L;
	// 任务服务器数量
	private int serverCount = 1;
	// 服务器信息
	private String serverArg;
	// 一次限制100条
	private int fetchCount = 100;
	// 执行的数量
	private int executeCount = 20;
	// 创建本地线程数量
	private int clientThreadCount = 5;
    // 失败重试的次数
	private int dataRetryCount = 3;
	// 数据重试间隔时间秒
	private int retryTimeInterval = 120;

	public int getDataRetryCount() {
		return dataRetryCount;
	}

	public void setDataRetryCount(int dataRetryCount) {
		this.dataRetryCount = dataRetryCount;
	}

	public int getRetryTimeInterval() {
		return retryTimeInterval;
	}

	public void setRetryTimeInterval(int retryTimeInterval) {
		this.retryTimeInterval = retryTimeInterval;
	}

	public int getServerCount() {
		return serverCount;
	}

	public void setServerCount(int serverCount) {
		this.serverCount = serverCount;
	}

	public int getClientThreadCount() {
		return clientThreadCount;
	}

	public void setClientThreadCount(int clientThreadCount) {
		this.clientThreadCount = clientThreadCount;
	}

	public String getServerArg() {
		return serverArg;
	}

	public void setServerArg(String serverArg) {
		this.serverArg = serverArg;
	}

	public int getFetchCount() {
		return fetchCount;
	}

	public void setFetchCount(int fetchCount) {
		this.fetchCount = fetchCount;
	}

	public int getExecuteCount() {
		return executeCount;
	}

	public void setExecuteCount(int executeCount) {
		this.executeCount = executeCount;
	}
}