package com.collmall.service;


import com.collmall.query.WorkerQuery;
import com.collmall.result.Result;

import java.util.List;

/**
 * worker
 * @author  xulihui
 * @date  2019-01-24 18:07
 */
public interface WorkerService {

	Result<List> queryList(WorkerQuery query);

	Result<Integer> resetTask(String taskType, String fingerprint, int status, int executeCount);

	Result<Integer> executeTask(String taskType, String fingerprint);

}
