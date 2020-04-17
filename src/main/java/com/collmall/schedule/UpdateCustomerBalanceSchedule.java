package com.collmall.schedule;

import com.collmall.constant.TaskType;
import com.collmall.exception.BusinessException;
import com.collmall.model.OscBalanceDetail;
import com.collmall.service.InvoiceService;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: xulihui
 * @Date: 2019/1/28 9:50
 */
@JobHandler(value="test")
@Component
public class UpdateCustomerBalanceSchedule extends ScheduleTaskProcess<OscBalanceDetail> {

    @Autowired
    private InvoiceService invoiceService;

    @Override
    public TaskType getTaskType() {
        return TaskType.update_customer_banlance;
    }

    @Override
    public void executeTask(OscBalanceDetail o)  {
        // Todo


       System.out.println("#####"+"WriteBackSolData" +"#####"+o.toString());
       invoiceService.updateInvoiceInfo();
        throw new BusinessException("teeweagagaf ");
    }
}
