package com.collmall.service.impl;

import com.collmall.service.InvoiceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xulihui
 * @date 2019/11/5 16:30
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateInvoiceInfo() {
        // todo

    }
}
