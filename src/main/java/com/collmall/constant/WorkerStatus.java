package com.collmall.constant;

/**
 * worker 状态枚举
 * @author  xulihui
 * @date 2019-01-25
 */
public enum WorkerStatus  implements  EnumMessage {

	CANCEL(-1, "取消"), INITIAL(0, "初始"), RUNING(1, "执行中"), FINISHED(2, "完成"), FAILED(10, "执行失败");

	private int code;
	private String name;

	private WorkerStatus(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static WorkerStatus getByCode(int code) {
		WorkerStatus[] values = WorkerStatus.values();
		for (WorkerStatus type : values) {
			if (type.getCode() == code)
				return type;
		}
		return null;
	}

	public static WorkerStatus getByName(String name) {
		WorkerStatus[] values = WorkerStatus.values();
		for (WorkerStatus type : values) {
			if (type.getName().equals(name))
				return type;
		}
		return null;
	}
}
