package com.collmall.schedule;

import com.collmall.constant.TaskType;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.stereotype.Component;

/**
 * @Author: xulihui
 * @Date: 2019/1/28 16:37
 */
@JobHandler(value="payScheduleTask")
@Component
public class PayScheduleTask extends  ScheduleTaskProcess {
    @Override
    public TaskType getTaskType() {
        return null;
    }

    @Override
    public ReturnT<String> execute(String s) throws Exception {

        System.out.println("#####"+"哈哈" +"#####"+"execute"+s);
        return IJobHandler.FAIL;
    }
}
