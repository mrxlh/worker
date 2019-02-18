package com.collmall.constant;


/**
 * @author meicai
 * worker任务类型
 */
public enum TaskType {
	init_customer_banlance("init_customer_banlance","初始化商户余额"),
	update_customer_banlance("update_customer_banlance","更新商户余额"),
	write_to_fi_ar("write_to_fi_ar","回传财务应收单"),
	write_to_fi_rec("write_to_fi_rec","回传财务收款单"),
	write_back_sol("write_back_sol", "回写台账收款")
	;

	private String code;
	private String name;

	private TaskType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
	public static TaskType getByCode(String code) {
		TaskType[] values = TaskType.values();
		for (TaskType type : values) {
			if (type.getCode() == code)
				return type;
		}
		return null;
	}

	public static TaskType getByName(String name) {
		TaskType[] values = TaskType.values();
		for (TaskType type : values) {
			if (type.getName().equals(name))
				return type;
		}
		return null;
	}
}
