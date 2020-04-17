package com.collmall.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.collmall.constant.TaskType;
import com.collmall.model.OscBalanceDetail;
import com.collmall.model.TaskRequest;
import com.collmall.model.TaskResponse;
import com.collmall.service.ScheduleTaskService;
import com.collmall.service.WorkerService;
import com.collmall.service.WorkerTestService;
import com.collmall.util.Helper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xulihui
 * @date 2019/11/4 18:08
 */
@Slf4j
@Service
public class WorkerTestServiceImpl implements WorkerTestService {

    @Autowired
    private WorkerService workerService;

    @Autowired
    private ScheduleTaskService scheduleTaskService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveInvoice() {
      // todo
        submitTask();
    }


    public void submitTask() {
        OscBalanceDetail bill = new OscBalanceDetail();
        bill.setDocNo("SK201911061659028");
        bill.setSourceId("8989");
        bill.setFCustomer(555L);
        bill.setCustomerType(3);
        bill.setCustomerTypeName("");
        bill.setCustomerName("二哈");
        bill.setSourceType(8888);
        bill.setDocType(2);
        bill.setRemark("这是一个test");
        bill.setMoney(100L);
        bill.setDate(Helper.getCurrentDate());
        bill.setfStoreId(123L);
        bill.setfStoreName("商户名称");
        bill.setPayerType(2);
        String key = "商户收款" + "_" + 22 + "_" +1;

        TaskRequest<OscBalanceDetail> request = new TaskRequest<OscBalanceDetail>();
        request.setTaskType(TaskType.update_customer_banlance.getCode());
        request.setTaskObject(bill);
        request.setFingerPrint(key);

        TaskResponse<JSONObject> t = scheduleTaskService.queryTaskByFingerprint(TaskType.update_customer_banlance.getCode(), "1");
        if (t != null) {
            scheduleTaskService.deleteTask(t.getTaskType(), t.getId());
        }
        scheduleTaskService.submitTask(request);
    }
}
