package com.collmall.monitor;

/**
 * 优化清理数据
 * 
 * @author xulihui
 *
 */
public interface IScheduleCleanUp {

    /**
     * 根据taskType清理数据
     * 
     * @param taskType
     * @param batchSize 每次事务提交多少数据
     * @param backupDays 保留几天数据      
     * @return
     */
    public int cleanUp(String taskType,Integer batchSize,Integer backupDays);
    
    
    /**
     * 根据taskType清理数据
     * 
     * @param taskType
     * @param batchSize 每次事务提交多少数据
     * @return
     */
    public int cleanUp(String taskType,Integer batchSize);
    
    /**
     * 分库时根据taskType清理数据
     * 
     * @param taskType
     * @param batchSize 每次事务提交多少数据
     * @param serverArg 数据库标识
     * @return
     */
    public int cleanUp(String taskType,Integer batchSize,String serverArg);
    
    
    /**
     * 分库时根据taskType清理数据
     * 
     * @param taskType
     * @param batchSize 每次事务提交多少数据
     * @param backupDays 保留几天数据
     * @param serverArg 数据库标识
     * @return
     */
    public int cleanUp(String taskType,Integer batchSize,Integer backupDays,String serverArg);
    
    /**
     * 根据taskType优化表
     * 
     * @param taskType
     */
    public boolean optimize(String taskType);
}
