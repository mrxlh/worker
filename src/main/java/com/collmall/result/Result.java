package com.collmall.result;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 封装返回结果
 * @param <T>
 * @Author: xulihui
 * @Date: 2019/1/4 18:00
 */
public class Result<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private int errorCode = 0;
	private Error error;

	private T data;
	private int total = 1;
	private int pageSize = 1;
	private int page = 1;

	public Result() {}


	public Result(int errorCode, String message) {
		this.errorCode = errorCode;
		this.error = new Error(message, null);
	}

	public Result(int errorCode, String message, Throwable cause) {
		this.errorCode = errorCode;
		this.error = new Error(message, cause);
	}

	public Result(T t) {
		this.data = t;
	}

	public Result(int total, int pageSize, int page, T t) {
		this.total = total;
		this.pageSize = pageSize;
		this.page = page;
		this.data = t;
	}

	public void setData(T data) {
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public boolean isSuccess() {
		return this.errorCode == 0;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public Error getError() {
		return error;
	}

	public void setError(String message, Throwable cause) {
		this.error = new Error(message, cause);
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalPages() {
		int totalPages = this.total / this.pageSize;
		if (this.total % this.pageSize != 0)
			totalPages++;
		return totalPages;
	}


	public Map<String, ?> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", this.isSuccess());
		map.put("error_code", this.getErrorCode());
		map.put("error", this.getError());
		map.put("data", this.getData());
		map.put("total_records", this.getTotal());
		map.put("recoreds_perpage", this.getPageSize());
		map.put("current_page", this.getPage());
		return map;
	}

	public Map<String, ?> toPagination() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", this.getData());
		map.put("page", this.getPage());
		map.put("per_page", this.getPageSize());
		map.put("total", this.getTotal());
		map.put("total_pages", this.getTotalPages());
		return map;
	}
	
	public Map<String, ?> toPaginationAndSum(String message) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", this.getData());
		map.put("page", this.getPage());
		map.put("per_page", this.getPageSize());
		map.put("total", this.getTotal());
		map.put("total_pages", this.getTotalPages());
		Message msg = new Message();
		msg.setContent(message);
		map.put("message", msg);
		return map;
	}
	
	class Message{
		private String type = "success";
		
		private String content;

		public String getType() {
			return type;
		}

		public String getContent() {
			return content;
		}

		public void setType(String type) {
			this.type = type;
		}

		public void setContent(String content) {
			this.content = content;
		}
	}

}
