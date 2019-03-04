package com.collmall.query;

public class WorkerQuery extends QueryParam {

	private static final long serialVersionUID = 1L;

	private String taskType;
	private String fingerprint;
	private Integer status;
	private String startTime;
	private String endTime;
	private String tableFix;

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTableFix() {
		return tableFix;
	}

	public void setTableFix(String tableFix) {
		this.tableFix = tableFix;
	}
}
