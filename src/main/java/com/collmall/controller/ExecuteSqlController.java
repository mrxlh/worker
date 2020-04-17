package com.collmall.controller;

import com.collmall.result.Result;
import com.collmall.service.ExecuteSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台修数
 * @Author: xulihui
 * @Date: 2019/3/6 11:28
 */
@RestController
public class ExecuteSqlController {

    @Autowired
    private ExecuteSqlService executeSqlService;

    @RequestMapping(value = "/execute", method = RequestMethod.GET)
    public Result<Integer> index(@RequestParam(value = "sql", required = false) String sql) {
        if (StringUtils.isEmpty(sql)) {
            return  new Result<>(0);
        }
        return executeSqlService.execute(sql);
    }


}
