package com.collmall.schedule;

import com.collmall.datasources.DataSourceContext;
import com.collmall.monitor.IScheduleCleanUp;
import com.collmall.service.ScheduleTaskService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author xulihui
 * @since  2019-11-15
 */
@Component
public class DefaultScheduleCleanUp implements IScheduleCleanUp {

    @Autowired
    private ScheduleTaskService scheduleTaskManager;
    
    
    @Value("${schedule.backupDays:2}")
    private int backupDays = 2;
    
    private Logger logger = Logger.getLogger(this.getClass());
    
    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int cleanUp(String taskType, Integer batchSize) {
        try{
            return scheduleTaskManager.cleanUp(taskType, batchSize,this.backupDays);
        }
        catch(Throwable t){
            logger.error("error when clean up",t);
            throw t;
        }
    }
    
    
    
    @Override
    public int cleanUp(String taskType, Integer batchSize,Integer backupDays ) {
        try{
            return scheduleTaskManager.cleanUp(taskType, batchSize,backupDays);
        }
        catch(Throwable t){
            logger.error("error when clean up",t);
            throw t;
        }
    }
    
    @Override
    public int cleanUp(String taskType, Integer batchSize,String serverArg) {
        try{
        	DataSourceContext.getContext().setIdentity(serverArg);
            return scheduleTaskManager.cleanUp(taskType, batchSize,this.backupDays);
        }
        catch(Throwable t){
            logger.error("error when clean up " +taskType+" serverArg:"+serverArg,t);
            throw t;
        }
        finally{
        	DataSourceContext.getContext().setIdentity("");
        }
    }
    
    
    @Override
    public int cleanUp(String taskType, Integer batchSize,Integer backupDays,String serverArg) {
        try{
        	DataSourceContext.getContext().setIdentity(serverArg);
            return scheduleTaskManager.cleanUp(taskType, batchSize,backupDays);
        }
        catch(Throwable t){
            logger.error("error when clean up " +taskType+" serverArg:"+serverArg,t);
            throw t;
        }
        finally{
        	DataSourceContext.getContext().setIdentity("");
        }
    }


    @Override
    public boolean optimize(String taskType) {
        return this.scheduleTaskManager.optimize(taskType);        
    }
}
