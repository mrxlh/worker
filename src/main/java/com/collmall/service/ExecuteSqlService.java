package com.collmall.service;

import com.collmall.result.Result;

/**
 * @Author: xulihui
 * @Date: 2019/3/6 11:30
 */
public interface ExecuteSqlService {

    public Result<Integer> execute(String sql);

}
