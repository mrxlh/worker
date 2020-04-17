package com.collmall.monitor;

import com.collmall.model.ScheduleMonitorDTO;

/**
 * 任务监控接口
 *
 * @author zhufei
 */
public interface IScheduleMonitor {
    
    /**
     * 监控指定任务类型的任务数量
     *
     * @param taskType       任务类型
     * @param dataRetryCount 大于等于该重试次数
     * @return 监控任务数量
     */
    ScheduleMonitorDTO monitorCount(String taskType, int dataRetryCount);
}
