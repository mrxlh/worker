package com.collmall.model;

import java.math.BigDecimal;

public class OscBalanceDetail extends BaseModel {

    private static final long serialVersionUID = 1L;



    private Integer date;		// 日期
    private Long fCustomer;		// 商户
    private int customerType;		// 商户类型
    private String customerName;		// 商户名称
    private  int docType;		// 单据类型
    private String docNo;		// 单据号
    private Long money;		// 金额
    private Long beginBalance;		// 起初余额
    private Long endBalance;		// 期末余额
    private String remark;		// 备注
    private String batch;		// 批次

    private Integer payerType;
    private String payerTypeName;

    public String getPayerTypeName() {
        return payerTypeName;
    }

    public void setPayerTypeName(String payerTypeName) {
        this.payerTypeName = payerTypeName;
    }


    public Integer getPayerType() {
        return payerType;
    }

    public void setPayerType(Integer payerType) {
        this.payerType = payerType;
    }

    public String getBatch() {
        if(batch==null)
            batch="";
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }


    private int sourceType;		// 来源类型
    private String sourceId;		// 来源id

    public Long getfStoreId() {
        if(fStoreId==null)
            fStoreId=0L;
        return fStoreId;
    }

    public Long getFStoreId() {
        if(fStoreId==null)
            fStoreId=0L;
        return fStoreId;
    }

    public Long getfCustomer() {
        return fCustomer;
    }

    public void setfCustomer(Long fCustomer) {
        this.fCustomer = fCustomer;
    }


    public void setfStoreId(Long fStoreId) {
        this.fStoreId = fStoreId;
    }

    public void setFStoreId(Long fStoreId) {
        this.fStoreId = fStoreId;
    }

    public String getfStoreName() {
        if(fStoreName==null)
            fStoreName="";
        return fStoreName;
    }

    public String getFStoreName() {
        if(fStoreName==null)
            fStoreName="";
        return fStoreName;
    }

    public void setfStoreName(String fStoreName) {
        this.fStoreName = fStoreName;
    }

    public void setFStoreName(String fStoreName) {
        this.fStoreName = fStoreName;
    }


    private Long fStoreId;
    private String fStoreName;

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }


    private String dateString;

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String getDocTypeName() {
        return docTypeName;
    }

    public void setDocTypeName(String docTypeName) {
        this.docTypeName = docTypeName;
    }

    public String getCustomerTypeName() {
        return customerTypeName;
    }

    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
    }

    private String customerTypeName;
    private String docTypeName;


    public Long getFCustomer() {
        return fCustomer;
    }

    public void setFCustomer(Long fCustomer) {
        this.fCustomer = fCustomer;
    }
    public int getCustomerType() {
        return customerType;
    }

    public void setCustomerType(int customerType) {
        this.customerType = customerType;
    }
    public String getCustomerName() {
        if(customerName==null)
            customerName="";
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public int getDocType() {
        return docType;
    }

    public void setDocType(int docType) {
        this.docType = docType;
    }
    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }
    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }
    public Long getBeginBalance() {
        return beginBalance;
    }

    public void setBeginBalance(Long beginBalance) {
        this.beginBalance = beginBalance;
    }
    public Long getEndBalance() {
        return endBalance;
    }

    public void setEndBalance(Long endBalance) {
        this.endBalance = endBalance;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getAdd() {
        if(money>0) {
            add = new BigDecimal(money.toString()).divide(new BigDecimal("100")).toString();
        }else{
            add ="";
        }
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getSub() {
        if(money<0) {
            sub = new BigDecimal(money.toString()).divide(new BigDecimal("-100")).toString();
        }else{
            sub ="";
        }
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }


    private String add;
    private String sub;

    public String getBegin() {
    	if(beginBalance == null)
    		return "";
        begin = new BigDecimal(beginBalance.toString()).divide(new BigDecimal("100")).toString();
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
    	if(endBalance == null)
    		return "";
        end = new BigDecimal(endBalance.toString()).divide(new BigDecimal("100")).toString();
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    private String begin;
    private String end;

    @Override
    public String toString() {
        return "OscBalanceDetail{" +
                "date=" + date +
                ", fCustomer=" + fCustomer +
                ", customerType=" + customerType +
                ", customerName='" + customerName + '\'' +
                ", docType=" + docType +
                ", docNo='" + docNo + '\'' +
                ", money=" + money +
                ", beginBalance=" + beginBalance +
                ", endBalance=" + endBalance +
                ", remark='" + remark + '\'' +
                ", batch='" + batch + '\'' +
                ", payerType=" + payerType +
                ", payerTypeName='" + payerTypeName + '\'' +
                ", sourceType=" + sourceType +
                ", sourceId='" + sourceId + '\'' +
                ", fStoreId=" + fStoreId +
                ", fStoreName='" + fStoreName + '\'' +
                ", dateString='" + dateString + '\'' +
                ", customerTypeName='" + customerTypeName + '\'' +
                ", docTypeName='" + docTypeName + '\'' +
                ", add='" + add + '\'' +
                ", sub='" + sub + '\'' +
                ", begin='" + begin + '\'' +
                ", end='" + end + '\'' +
                '}';
    }
}