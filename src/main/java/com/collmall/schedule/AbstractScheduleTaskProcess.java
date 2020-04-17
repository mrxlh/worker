package com.collmall.schedule;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.collmall.model.ScheduleParam;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.core.util.ShardingUtil;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 抽象 worker 任务切片处理
 * @param <T>
 * @author  xulihui
 * @date  2019-01-25
 */
public abstract class AbstractScheduleTaskProcess<T> extends IJobHandler {

    protected final static Logger logger = Logger.getLogger(AbstractScheduleTaskProcess.class);

    private ExecutorService executor;

    private volatile int lastThreadCount = 0;

    @Override
    public ReturnT<String> execute(String param) {
        try {
            ScheduleParam scheduleParam = new ScheduleParam();
            if (param != null) {
                logger.info("调度系统的参数: "+param);
                scheduleParam = JSON.parseObject(param, new TypeReference<ScheduleParam>(){});
            }
            // 分片参数
            ShardingUtil.ShardingVO sharding = ShardingUtil.getShardingVo();
            List<T> tasks = this.selectTasks(scheduleParam, sharding.getIndex());

            if (logger.isInfoEnabled()) {
                logger.info("获取任务[" + this.getClass().getName() + "][" + sharding.getIndex() + "/" + sharding.getTotal() + "]共"
                        + (tasks == null ? 0 : tasks.size()) + "条");
            }
            if (tasks == null) {
                XxlJobLogger.log("tasks is null");
                return IJobHandler.SUCCESS;
            }

            this.innerExecuteTasks(scheduleParam, tasks);
            if (logger.isInfoEnabled()) {
                logger.info(
                        "执行任务[" + this.getClass().getName() + "][" + sharding.getIndex() + "/" + sharding.getTotal() + "]共" + tasks.size() + "条完成!");
            }
            return IJobHandler.SUCCESS;
        } catch (Exception e) {
            logger.info("执行任务[" + this.getClass().getName() + "] 失败"+ e.getMessage());
            XxlJobLogger.log("执行调度失败",e.toString());
            return IJobHandler.FAIL;
        }
    }

    protected abstract List<T> selectTasks(ScheduleParam param, Integer curServer);

    @Deprecated
    protected void executeTasks(List<T> tasks){

    }

    protected void executeTasks(ScheduleParam param, List<T> tasks) {
        executeTasks(tasks);
    }

    private void innerExecuteTasks(ScheduleParam param, List<T> tasks) {
        if (this.executor == null) {
            this.executor = createCustomExecutorService(param.getClientThreadCount(), "dts.executeTasks");
            this.lastThreadCount = param.getClientThreadCount();
        } else {
            if (this.lastThreadCount != param.getClientThreadCount()) {
                this.executor.shutdown();
                this.executor = createCustomExecutorService(param.getClientThreadCount(), "dts.executeTasks");
                this.lastThreadCount = param.getClientThreadCount();
            }
        }
        List<List<T>> lists = splitList(tasks, param.getExecuteCount());
        final CountDownLatch latch = new CountDownLatch(lists.size());
        for (final List<T> list : lists) {
            this.executor.submit(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    try {

                        executeTasks(param, list);

                        return null;
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                        throw e;
                    } finally {
                        latch.countDown();
                    }
                }
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException("interrupted when processing data access request in concurrency", e);
        }
    }

    private static <T> List<List<T>> splitList(List<T> tasks, int maxLen) {
        if (maxLen <= 0) {
            throw new RuntimeException("maxLen 不能小于等于0!");
        }
        List<List<T>> result = new ArrayList<List<T>>();
        int count = 0;
        if (tasks.size() % maxLen == 0) {
            count = tasks.size() / maxLen;
        } else {
            count = tasks.size() / maxLen + 1;
        }
        for (int i = 0; i < count; i++) {
            int fromIndex = i * maxLen;
            int toIndex = 0;
            if (i == count - 1) {
                toIndex = tasks.size();
            } else {
                toIndex = (i + 1) * maxLen;
            }
            result.add(tasks.subList(fromIndex, toIndex));
        }
        return result;
    }

    private static ExecutorService createCustomExecutorService(int poolSize, final String method) {
        int coreSize = poolSize;
        ThreadFactory tf = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, "custom thread:" + method);
                return t;
            }
        };
        BlockingQueue<Runnable> queueToUse = new LinkedBlockingQueue<Runnable>();
        final ThreadPoolExecutor executor = new ThreadPoolExecutor(coreSize, poolSize, 60, TimeUnit.SECONDS, queueToUse,
                tf, new ThreadPoolExecutor.CallerRunsPolicy());

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    executor.shutdown();
                    executor.awaitTermination(1, TimeUnit.MINUTES);
                } catch (InterruptedException e) {
                    logger.warn("interrupted when shuting down the query executor:\n{}", e);
                }
            }
        });
        return executor;
    }
}
