package com.collmall.service;

import com.collmall.annotation.JProfiler;
import com.collmall.annotation.JProfilerEnum;
import com.collmall.model.ScheduleMonitorDTO;
import com.collmall.monitor.IScheduleMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("workerMonitorService")
public class WorkerMonitorService implements IScheduleMonitor {

    @Autowired
    private ScheduleTaskService scheduleTaskService;

    @Override
    @JProfiler(key = "WorkerMonitorService.getOmsWorker", states = { JProfilerEnum.TP, JProfilerEnum.FunctionError })
    public ScheduleMonitorDTO monitorCount(String taskType, int dataRetryCount) {
        return scheduleTaskService.queryMonitorCount(taskType, dataRetryCount);
    }
}
