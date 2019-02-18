package com.collmall.query;

public class WorkerQuery extends QueryParam {

	private static final long serialVersionUID = 1L;

	private String taskType;
	private String fingerprint;
	private Integer status;
	private Integer startTime;
	private Integer endTime;
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

	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	public String getTableFix() {
		return tableFix;
	}

	public void setTableFix(String tableFix) {
		this.tableFix = tableFix;
	}
}
