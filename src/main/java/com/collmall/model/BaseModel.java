package com.collmall.model;


import java.io.Serializable;

/**
 * 基本数据模型
 * @Author: xulihui
 * @Date: 2019/1/4 18:00
 */
public abstract class BaseModel implements Serializable {

	private static final long serialVersionUID = -5197016274254911057L;

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
