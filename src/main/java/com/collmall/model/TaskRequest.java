package com.collmall.model;

import java.io.Serializable;

/**
 *
 * @param <T>
 * @author  xulihui
 * @date 2019-01-25
 */
public class TaskRequest<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	private long id;
	private String taskType;
	private String taskKey1; // 查询使用
	private String taskKey2; // 查询使用
	private T taskObject;
	private String fingerPrint; // 指纹字段，校验唯一性

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getTaskKey1() {
		return taskKey1;
	}

	public void setTaskKey1(String taskKey1) {
		this.taskKey1 = taskKey1;
	}

	public String getTaskKey2() {
		return taskKey2;
	}

	public void setTaskKey2(String taskKey2) {
		this.taskKey2 = taskKey2;
	}

	public T getTaskObject() {
		return taskObject;
	}

	public void setTaskObject(T taskObject) {
		this.taskObject = taskObject;
	}

	public String getFingerPrint() {
		return fingerPrint;
	}

	public void setFingerPrint(String fingerPrint) {
		this.fingerPrint = fingerPrint;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
