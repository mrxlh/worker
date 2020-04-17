package com.collmall.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.collmall.constant.TaskType;
import com.collmall.model.TaskRequest;
import com.collmall.model.TaskResponse;
import com.collmall.model.WriteBackSolData;
import com.collmall.service.ScheduleTaskService;
import com.collmall.service.TestWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 测试woker的插入
 * @Author: xulihui
 * @Date: 2019/2/20 17:14
 */
@Service
public class TestWorkerServiceImpl implements TestWorkerService {

    @Autowired
    private ScheduleTaskService scheduleTaskService;

    @Transactional
    @Override
    public void testSaveWorker() {
        WriteBackSolData data = new WriteBackSolData();
        TaskRequest<WriteBackSolData> request = new TaskRequest<>();
        String key = data.getSourceType() + "_" + data.getSourceId() + "_" + data.getBatch();
        request.setTaskType(TaskType.update_customer_banlance.getCode());
        request.setTaskObject(data);
        request.setFingerPrint(key);
        TaskResponse<JSONObject> t = scheduleTaskService
                .queryTaskByFingerprint(TaskType.update_customer_banlance.getCode(), key);
        if (t != null)
            scheduleTaskService.deleteTask(t.getTaskType(), t.getId());
        scheduleTaskService.submitTask(request);
    }
}
