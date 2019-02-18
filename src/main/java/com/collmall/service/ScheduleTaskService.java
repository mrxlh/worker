package com.collmall.service;

import com.collmall.model.*;
import com.collmall.query.WorkerQuery;

import java.util.List;

/**
 * 调度任务管理器，提交任务，锁定任务，完成任务，标记错误，查询任务等
 *
 * @author xulihui
 * @date 2019-01-25
 */
public interface ScheduleTaskService {

    <T> TaskResponse<T> queryTask(String taskType, long taskId);

    <T> TaskResponse<T> submitTask(TaskRequest<T> request);

    void deleteTask(String taskType, long taskId);

    <T> void lockTask(TaskResponse<T> task);

    <T> void lockTasks(List<TaskResponse<T>> tasks);

    <T> void doneTask(TaskResponse<T> task);

    <T> void errorTask(TaskResponse<T> task, Throwable e);

    <T> List<TaskResponse<T>> queryExecuteTasks(String taskType, ScheduleParam param, Integer curServer);

    <T> List<TaskResponse<T>> queryExecuteTasks(String taskType);

    <T> TaskResponse<T> queryTaskByFingerprint(String taskType, String fingerprint);

    ScheduleMonitorDTO queryMonitorCount(String taskType, int dataRetryCount);

    void resetTask(String taskType, String fingerPrint, int status, int executeCount);
    
    int cleanUp(String taskType,int batchSize);
    
    int cleanUp(String taskType,int batchSize,int backupDays);
    
    boolean optimize(String taskType);

    List<ScheduleTask> queryList(WorkerQuery query);

    int count (WorkerQuery query);

    void resetTaskByType(int tableFix);
}
