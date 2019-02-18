package com.collmall.model;

import java.io.Serializable;

/**
 * @author xulihui
 * @date 2019-01-25
 */
public class ScheduleMonitorDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
    private int backlogCount;
    private int errorCount;
    private int initCount;

    public int getBacklogCount() {
        return backlogCount;
    }

    public void setBacklogCount(int backlogCount) {
        this.backlogCount = backlogCount;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public void setInitCount(int initCount) {
        this.initCount = initCount;
    }

    public int getInitCount() {
        return initCount;
    }
}
