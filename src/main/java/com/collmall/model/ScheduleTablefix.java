package com.collmall.model;

/**
 * worker 切库相关的配置信息
 * @author xulihui
 * @since 2019- 01 -14 
 */
public class ScheduleTablefix {
	
	private long id;
	
	private String taskType;
	
	private int tableFix;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public int getTableFix() {
		return tableFix;
	}

	public void setTableFix(int tableFix) {
		this.tableFix = tableFix;
	}
	

}
