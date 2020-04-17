package com.collmall.service.impl;


import com.alibaba.fastjson.JSON;
import com.collmall.datasources.DataSourceContext;
import com.collmall.mapper.ScheduleTaskMapper;
import com.collmall.model.*;
import com.collmall.query.WorkerQuery;
import com.collmall.service.IScheduleTaskProcess;
import com.collmall.service.ScheduleTaskService;
import com.collmall.util.ScheduleUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 往数据库中提交调度任务
 */
@Service
public class ScheduleTaskServiceImpl implements ScheduleTaskService {

    private static final Logger logger = Logger.getLogger(ScheduleTaskServiceImpl.class);
    private static final Map<String, String> tableFixMap = new ConcurrentHashMap<>();

    @Autowired
    private ScheduleTaskMapper scheduleTaskMapper;

    @Value("${schedule.tableNumber:8}")
    private int tableNumber = 0;

    @Value("${cleanUp.maxBatchSize:500}")
    private int cleanUpMaxBatchSize = 500;

    /*
     * private int getTableNumber() {
     * 
     * if (tableNumber == 0) { synchronized (this) { if (tableNumber == 0) { try
     * (InputStream stream =
     * this.getClass().getResourceAsStream("prop/application.properties")) {
     * Properties properties = new Properties(); properties.load(stream);
     * tableNumber =
     * Integer.parseInt(properties.getProperty("schedule.tableNumber")); } catch
     * (IOException ignored) {
     * 
     * } } } } if (tableNumber == 0) { tableNumber = 8; } return
     * this.tableNumber; }
     */

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public <T> TaskResponse<T> submitTask(TaskRequest<T> request) {
        checkRequest(request);
        ScheduleTask taskDomain = new ScheduleTask();
        taskDomain.setBodyClass(request.getTaskObject().getClass().getName());
        taskDomain.setFingerprint(request.getFingerPrint());
        taskDomain.setRegionNo(ScheduleUtil.getRegionNo());
        taskDomain.setStatus(IScheduleTaskProcess.TaskStatus_Init);
        taskDomain.setTaskKey1(request.getTaskKey1());
        taskDomain.setTaskKey2(request.getTaskKey2());
        taskDomain.setTaskBody(JSON.toJSONString(request.getTaskObject()));
        taskDomain.setTaskType(request.getTaskType());
        taskDomain.setTableFix(this.getTableFix(request.getTaskType()));
        int count = scheduleTaskMapper.insertTask(taskDomain);
        if (count > 0) {
            return this.getTaskResponse(taskDomain, request.getTaskObject());
        } else {
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public <T> void lockTasks(List<TaskResponse<T>> tasks) {
        if (tasks == null || tasks.size() == 0)
            return;
        checkResponse(tasks.get(0));

        Map<String, Object> map = new HashMap<>();
        map.put("status", IScheduleTaskProcess.TaskStatus_executing);

        List<Long> ids = new ArrayList<>();
        for (TaskResponse<T> task : tasks) {
            ids.add(task.getId());
        }
        map.put("ids", ids);
        map.put("tableFix", this.getTableFix(tasks.get(0).getTaskType()));
        this.scheduleTaskMapper.lockTasks(map);
    }

    @Override
    public <T> void lockTask(TaskResponse<T> task) {
        if (task == null)
            return;
        checkResponse(task);
        List<TaskResponse<T>> tasks = new ArrayList<>();
        tasks.add(task);
        this.lockTasks(tasks);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public <T> void doneTask(TaskResponse<T> task) {
        if (task == null)
            return;
        checkResponse(task);
        ScheduleTask taskDomain = new ScheduleTask();
        taskDomain.setId(task.getId());
        taskDomain.setTaskType(task.getTaskType());
        taskDomain.setTableFix(this.getTableFix(task.getTaskType()));
        taskDomain.setStatus(IScheduleTaskProcess.TaskStatus_executed);
        this.scheduleTaskMapper.doneTask(taskDomain);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public <T> void errorTask(TaskResponse<T> task, Throwable e) {
        if (task == null)
            return;
        checkResponse(task);
        Map<String, Object> map = new HashMap<>();
        map.put("id", task.getId());
        map.put("status", IScheduleTaskProcess.TaskStatus_error);
        String remark = e.getMessage();
        if (remark != null && remark.length() > 255) {
            remark = remark.substring(0, 254);
        }
        map.put("remark", remark);
        map.put("tableFix", this.getTableFix(task.getTaskType()));
        this.scheduleTaskMapper.errorTask(map);
    }



    @Override
    public <T> List<TaskResponse<T>> queryExecuteTasks(String taskType, ScheduleParam param, Integer curServer) {
        if (StringUtils.isEmpty(taskType)) {
            throw new RuntimeException("taskType is null");
        }
        List<TaskResponse<T>> responses = new ArrayList<>();
        Integer tableFix = this.getTableFix(taskType);

        Map<String, Object> map = new HashMap<>();
      //  map.put("regions", ScheduleUtil.getTaskRegions(param.getServerCount(), curServer));
        map.put("statusInit", IScheduleTaskProcess.TaskStatus_Init);
        map.put("statusExecuting", IScheduleTaskProcess.TaskStatus_executing);
        map.put("statusError", IScheduleTaskProcess.TaskStatus_error);
        map.put("lastTime", param.getRetryTimeInterval());
        map.put("retryCount", param.getDataRetryCount());// 失败重试的次数3
        map.put("fetchCount", param.getFetchCount());// 一次限制100条
        map.put("taskType", taskType);
        map.put("tableFix", tableFix);
        StringBuilder sb = new StringBuilder(100);
        sb.append("serverCount=").append(param.getServerCount()).append(",curServer=").append(curServer)
                .append(",serverArg=").append(param.getServerArg()).append(",lastTime=").append(map.get("lastTime"))
                .append(",retryCount=").append(map.get("retryCount")).append(",fetchCount=")
                .append(map.get("fetchCount")).append(",taskType=").append(map.get("taskType")).append(",tableFix=")
                .append(map.get("tableFix"));
        logger.info(sb.toString());
        List<ScheduleTask> list = this.scheduleTaskMapper.queryExecuteTasks(map);
        for (ScheduleTask task : list) {
            TaskResponse<T> response = this.getTaskResponse(task);
            response.setRetryCount(param.getDataRetryCount());
            responses.add(response);
        }
        return responses;
    }

    @Override
    public <T> TaskResponse<T> queryTaskByFingerprint(String taskType, String fingerprint) {
        if (StringUtils.isEmpty(taskType)) {
            throw new RuntimeException("taskType is null");
        }
        int tableFix = this.getTableFix(taskType);
        ScheduleTask task = this.scheduleTaskMapper.queryByFingerprint(tableFix, fingerprint);
        return task == null ? null : this.getTaskResponse(task);
    }

    @Override
    public ScheduleMonitorDTO queryMonitorCount(String taskType, int dataRetryCount) {
        if (StringUtils.isEmpty(taskType)) {
            throw new RuntimeException("taskType is null");
        }
        int tableFix = this.getTableFix(taskType);
        ScheduleMonitorDTO dto = this.scheduleTaskMapper.queryMonitorCount(tableFix, dataRetryCount,taskType);
        dto.setInitCount(this.scheduleTaskMapper.queryInitCount(tableFix,taskType));
        return dto;
    }

    @Override
    public void resetTask(String taskType, String fingerPrint, int status, int executeCount) {
        if (StringUtils.isEmpty(taskType)) {
            throw new RuntimeException("taskType is null");
        }
        int tableFix = this.getTableFix(taskType);
        scheduleTaskMapper.updateTaskByfingerPrint(tableFix, fingerPrint, status, executeCount);
    }

    @SuppressWarnings("unchecked")
    private <T> TaskResponse<T> getTaskResponse(ScheduleTask task) {
        T taskObject = null;
        try {
            Class<?> bodyClass = Class.forName(task.getBodyClass());
            taskObject = (T) JSON.parseObject(task.getTaskBody(), bodyClass);
        } catch (Exception e) {
            logger.error("getTaskResponse:" + e.getMessage(), e);
        }
        return this.getTaskResponse(task, taskObject);
    }

    private <T> TaskResponse<T> getTaskResponse(ScheduleTask task, T taskObject) {
        TaskResponse<T> response = new TaskResponse<>();
        response.setId(task.getId());
        response.setFingerPrint(task.getFingerprint());
        response.setTaskKey1(task.getTaskKey1());
        response.setTaskKey2(task.getTaskKey2());
        response.setTaskType(task.getTaskType());
        response.setTaskBody(task.getTaskBody());
        response.setStatus(task.getStatus());
        response.setTaskObject(taskObject);
        return response;
    }

    private synchronized int getTableFix(String taskType) {
        String identity =  DataSourceContext.getContext().getIdentity();
        

        String shardTaskType = identity==null?taskType:identity+"_"+taskType;
        // 先从内存取
        if (tableFixMap.containsKey(shardTaskType)) {
            return Integer.parseInt(tableFixMap.get(shardTaskType));
        }

        List<Map<String, Object>> list = this.scheduleTaskMapper.queryTableFixs();
        if (list != null && list.size() > 0) {
            for (Map<String, Object> row : list) {
                String tmpTaskType = identity==null?(String)row.get("task_type"):identity+"_"+(String)row.get("task_type");
                tableFixMap.put(tmpTaskType, row.get("table_fix").toString());
            }
        }
        // 没有就插入记录
        if (!tableFixMap.containsKey(shardTaskType)) {
            // 最多tableNumber个表
            int tableFix = 0;
            if (tableFixMap.size() >= tableNumber) {
                // 说明所有的表都被使用，就随机选择一个使用就可以了
                tableFix = new Random(System.currentTimeMillis()).nextInt(tableNumber);
            } else {
                // 找一个空闲的表使用
                for (int i = 0; i < tableNumber; i++) {
                    if (!tableFixMap.containsValue(String.valueOf(i))) {
                        tableFix = i;
                        break;
                    }
                }
            }
            
            // 先取出来
            Map<String, Object> map = new HashMap<>();
            map.put("taskType", taskType);
            map.put("tableFix", tableFix);
            this.scheduleTaskMapper.insertTableFix(map);
            tableFixMap.put(shardTaskType, String.valueOf(map.get("tableFix")));
        }
        return Integer.parseInt(tableFixMap.get(shardTaskType));
    }

    @Override
    public <T> TaskResponse<T> queryTask(String taskType, long taskId) {
        int tableFix = this.getTableFix(taskType);
        ScheduleTask task = this.scheduleTaskMapper.queryTask(tableFix, taskId);
        return task == null ? null : this.getTaskResponse(task);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteTask(String taskType, long taskId) {
        int tableFix = this.getTableFix(taskType);
        this.scheduleTaskMapper.deleteTask(tableFix, taskId);
    }

    private <T> void checkRequest(TaskRequest<T> request) {
        if (StringUtils.isEmpty(request.getTaskType())) {
            throw new RuntimeException("TaskType is null!");
        }
        if (StringUtils.isEmpty(request.getFingerPrint())) {
            throw new RuntimeException("fingerprint is null!");
        }
        if (request.getTaskObject() == null) {
            throw new RuntimeException("taskObject is null!");
        }
    }

    private <T> void checkResponse(TaskResponse<T> response) {
        if (StringUtils.isEmpty(response.getTaskType())) {
            throw new RuntimeException("TaskType is null!");
        }
    }

    /* 不清除今天，昨天，前天的数据 */
    public int cleanUp(String taskType, int batchSize) {
        logger.info("start clean up");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -2);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Timestamp lastDate = new Timestamp(calendar.getTimeInMillis());
        int tableFix = -1;
        List<Map<String, Object>> list = this.scheduleTaskMapper.queryTableFixs();

        if (list != null && list.size() > 0) {
            for (Map<String, Object> row : list) {
                if (taskType.equals(row.get("task_type"))) {
                    tableFix = ((Number) row.get("table_fix")).intValue();
                    break;
                }
            }
        }

        if (tableFix == -1) {
            logger.warn("can't find table fix for:" + taskType);
            return 0;
        }
        
        //List msg = this.scheduleTaskMapper.optimizeTable(tableFix);

        if (batchSize > cleanUpMaxBatchSize) {
            batchSize = cleanUpMaxBatchSize;
        }

        List<Integer> ids = this.scheduleTaskMapper.listDoneTask(tableFix, taskType, batchSize, lastDate);
        // while(!ids.isEmpty()){
        // int updated = this.scheduleTaskMapper.cleanUp(tableFix, ids);
        // deletedSize = deletedSize+updated;
        // }
        if (!ids.isEmpty()) {
            int deletedSize = this.scheduleTaskMapper.cleanUp(tableFix, ids);
            return deletedSize;
        } else {
            return 0;
        }
    }

    @Override
    public boolean optimize(String taskType) {
        logger.info("start clean up");

        int tableFix = -1;
        List<Map<String, Object>> list = this.scheduleTaskMapper.queryTableFixs();

        if (list != null && list.size() > 0) {
            for (Map<String, Object> row : list) {
                if (taskType.equals(row.get("task_type"))) {
                    tableFix = ((Number) row.get("table_fix")).intValue();
                    break;
                }
            }
        }

        if (tableFix == -1) {
            logger.warn("can't find table fix for:" + taskType);
            return false;
        }
        
        List msg = this.scheduleTaskMapper.optimizeTable(tableFix);
        
        logger.info("optimize end"+msg.toString());
        
        return true;
    }



    @Override
	public int cleanUp(String taskType, int batchSize, int backupDays) {
        logger.info("start clean up");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -backupDays);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Timestamp lastDate = new Timestamp(calendar.getTimeInMillis());
        int tableFix = -1;
        List<Map<String, Object>> list = this.scheduleTaskMapper.queryTableFixs();

        if (list != null && list.size() > 0) {
            for (Map<String, Object> row : list) {
                if (taskType.equals(row.get("task_type"))) {
                    tableFix = ((Number) row.get("table_fix")).intValue();
                    break;
                }
            }
        }

        if (tableFix == -1) {
            logger.warn("can't find table fix for:" + taskType);
            return 0;
        }
        
        //List msg = this.scheduleTaskMapper.optimizeTable(tableFix);

        if (batchSize > cleanUpMaxBatchSize) {
            batchSize = cleanUpMaxBatchSize;
        }

        List<Integer> ids = this.scheduleTaskMapper.listDoneTask(tableFix, taskType, batchSize, lastDate);

        if (!ids.isEmpty()) {
            int deletedSize = this.scheduleTaskMapper.cleanUp(tableFix, ids);
            return deletedSize;
        } else {
            return 0;
        }
	}


    @Override
    public List<ScheduleTask> queryList(WorkerQuery query) {
        return scheduleTaskMapper.queryList(query);
    }

    @Override
    public int count(WorkerQuery query) {
        return scheduleTaskMapper.count(query);
    }

    @Override
    public void resetTaskByType(int tableFix) {

    }

}
