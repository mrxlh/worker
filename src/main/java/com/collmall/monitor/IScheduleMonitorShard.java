package com.collmall.monitor;

import com.collmall.model.ScheduleMonitorDTO;
import com.collmall.model.ScheduleParam;

public interface IScheduleMonitorShard {
    /**
     * 监控指定任务类型的任务数量
     *
     * @param taskType       任务类型
     * @param scheduleParam  参数，包括serverArg，retryCount等
     * @return 监控任务数量
     */
    ScheduleMonitorDTO monitorCount(String taskType, ScheduleParam scheduleParam);
}
