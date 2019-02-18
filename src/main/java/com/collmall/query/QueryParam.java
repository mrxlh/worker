package com.collmall.query;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 通用的查询参数对象<br>
 * @author xulihui 2019年01月24日
 */
public class QueryParam implements Serializable {

    private static final long serialVersionUID = 2926723122405744749L;

    private int startRecord;
    private int pageSize = 1000;
    private int page = 1;
    private String sortOrder;
    /**
     * 数据表中的列名
     */
    private String columnName;
    /**
     * 数据表中的列对应的数据集
     */
    private List<?> columnValueList;

    public int getStartRecord () {
        return startRecord;
    }

    public void setStartRecord (int startRecord) {
        this.startRecord = this.pageSize * (this.page - 1);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        this.setStartRecord(0);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
        this.setStartRecord(0);
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public List<?> getColumnValueList() {
        return columnValueList;
    }

    public void setColumnValueList(List<?> columnValueList) {
        this.columnValueList = columnValueList;
    }
}
