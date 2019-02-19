package com.collmall.model;



public class WriteBackSolData {

	private Integer sourceType;
	
	private String sourceId;
	
	private Long money;
	
	private String batch;
	
	private Integer cityId;
	
	private Integer recSource;
	
	private Long customerId;
	
	private int settlementMethod;
	
	private int settlementType;
 
	private String tradeSerialNumber;// 交易流水
	
	public int getSettlementMethod() {
		return settlementMethod;
	}


	public void setSettlementMethod(int settlementMethod) {
		this.settlementMethod = settlementMethod;
	}


	public int getSettlementType() {
		return settlementType;
	}


	public void setSettlementType(int settlementType) {
		this.settlementType = settlementType;
	}


	public Long getCustomerId() {
		return customerId;
	}


	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}


	public Integer getSourceType() {
		return sourceType;
	}


	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}


	public String getSourceId() {
		return sourceId;
	}


	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}


	public Long getMoney() {
		return money;
	}


	public void setMoney(Long money) {
		this.money = money;
	}


	public String getBatch() {
		return batch;
	}


	public void setBatch(String batch) {
		this.batch = batch;
	}


	public Integer getCityId() {
		return cityId;
	}


	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}


	public Integer getRecSource() {
		return recSource;
	}


	public void setRecSource(Integer recSource) {
		this.recSource = recSource;
	}

	public String getTradeSerialNumber() {
		return tradeSerialNumber;
	}

	public void setTradeSerialNumber(String tradeSerialNumber) {
		this.tradeSerialNumber = tradeSerialNumber;
	}


	@Override
	public String toString() {
		return "WriteBackSolData{" +
				"sourceType=" + sourceType +
				", sourceId='" + sourceId + '\'' +
				", money=" + money +
				", batch='" + batch + '\'' +
				", cityId=" + cityId +
				", recSource=" + recSource +
				", customerId=" + customerId +
				", settlementMethod=" + settlementMethod +
				", settlementType=" + settlementType +
				", tradeSerialNumber='" + tradeSerialNumber + '\'' +
				'}';
	}
}
