package com.collmall.service;

import com.collmall.annotation.JProfiler;
import com.collmall.annotation.JProfilerEnum;
import com.collmall.model.CallerInfo;
import com.collmall.util.CacheUtil;
import com.collmall.util.ProfilerUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;

@Aspect
@Service
public class JProfilerService {

    @Value("${application.name}")
    private String appName;

    public JProfilerService() {
    }

    @PostConstruct
    public void init() {
        ProfilerUtil.scopeAlive(this.appName, "alive");
        ProfilerUtil.scopeJvm(this.appName, "jvm");
    }

    @Pointcut("@annotation(com.collmall.annotation.JProfiler)")
    public void JProfilerPoint() {
    }

    @Around("JProfilerPoint()")
    public Object execJProfiler(ProceedingJoinPoint point) throws Throwable {
        Method method = this.getMethod(point);
        JProfiler anno = (JProfiler)method.getAnnotation(JProfiler.class);
        CallerInfo callerInfo = null;
        boolean functionError = false;
        if (anno != null) {
            boolean tp = false;
            boolean heartbeat = false;
            JProfilerEnum[] states = anno.states();
            JProfilerEnum[] var9 = states;
            int var10 = states.length;

            for(int var11 = 0; var11 < var10; ++var11) {
                JProfilerEnum state = var9[var11];
                if (state.equals(JProfilerEnum.TP)) {
                    tp = true;
                } else if (state.equals(JProfilerEnum.Heartbeat)) {
                    heartbeat = true;
                } else if (state.equals(JProfilerEnum.FunctionError)) {
                    functionError = true;
                }
            }

            callerInfo = ProfilerUtil.scopeStart(this.appName, anno.key(), heartbeat, tp);
        }

        Object var18;
        try {
            var18 = point.proceed();
        } catch (Throwable var16) {
            if (callerInfo != null && functionError) {
                ProfilerUtil.scopeFunctionError(callerInfo, CacheUtil.getTargetException(var16));
            }

            throw var16;
        } finally {
            if (callerInfo != null) {
                ProfilerUtil.scopeEnd(callerInfo);
            }

        }

        return var18;
    }

    private Method getMethod(JoinPoint point) throws Exception {
        MethodSignature msig = (MethodSignature)point.getSignature();
        return msig.getMethod();
    }
}
