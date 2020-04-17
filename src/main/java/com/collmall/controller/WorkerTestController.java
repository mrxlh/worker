package com.collmall.controller;

import com.collmall.service.WorkerTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xulihui
 * @date 2019/11/4 18:06
 */
@RestController
@RequestMapping("/worker")
public class WorkerTestController {

    @Autowired
    private WorkerTestService workerTestService;

    @GetMapping("/saveInvoice")
    public void saveInvoice() {
        workerTestService.saveInvoice();
    }

}
