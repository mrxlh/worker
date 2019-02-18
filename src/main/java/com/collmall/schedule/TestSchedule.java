package com.collmall.schedule;

import com.collmall.constant.TaskType;
import com.sprucetec.osc.schedule.MyScheduleTaskProcess;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.stereotype.Component;

/**
 * @Author: xulihui
 * @Date: 2019/1/28 9:50
 */
@JobHandler(value="test")
@Component
public class TestSchedule extends MyScheduleTaskProcess {


    @Override
    public TaskType getTaskType() {
        return TaskType.write_back_sol;
    }

    @Override
    public void executeTask(Object o) {
       System.out.println("#####"+"哈哈" +"#####"+o.toString());
    }
}
