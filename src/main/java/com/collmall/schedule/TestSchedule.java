package com.collmall.schedule;

import com.collmall.constant.TaskType;
import com.collmall.model.WriteBackSolData;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.stereotype.Component;

/**
 * @Author: xulihui
 * @Date: 2019/1/28 9:50
 */
@JobHandler(value="test")
@Component
public class TestSchedule extends ScheduleTaskProcess<WriteBackSolData> {


    @Override
    public TaskType getTaskType() {
        return TaskType.write_back_sol;
    }

    @Override
    public void executeTask(WriteBackSolData o)  {
       // System.out.println(1/0);
       System.out.println("#####"+"哈哈" +"#####"+o.toString());
    }
}
