package com.springapp.mvc.web;

import com.springapp.mvc.trace.TraceLogInfo;
import com.springapp.mvc.trace.TraceLogInfoThreadLocalManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.async.CallableProcessingInterceptorAdapter;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.DeferredResultProcessingInterceptor;

import java.util.concurrent.Callable;

/**
 * @author songkejun
 * @create 2018-01-03 15:12
 **/
public class TraceAsyncRequestInterceptor extends CallableProcessingInterceptorAdapter
        implements DeferredResultProcessingInterceptor {
    public static final String LOG_INFO_ATTRIBUTE_NAME = "REQUEST_ATTRIBUTE_SPRING_TRACE_ASYNC_TRACE_LOG_INFO";
    private static Logger log = LoggerFactory.getLogger(TraceAsyncRequestInterceptor.class);

    @Override
    public <T> void beforeConcurrentHandling(NativeWebRequest request, Callable<T> task) throws Exception {
        TraceLogInfoThreadLocalManager.clear();
    }

    @Override
    public <T> void preProcess(NativeWebRequest request, Callable<T> task) {
        TraceLogInfo traceLogInfo = (TraceLogInfo) request.getAttribute(LOG_INFO_ATTRIBUTE_NAME, NativeWebRequest.SCOPE_REQUEST);
        TraceLogInfoThreadLocalManager.bindTraceLogInfo(traceLogInfo);
    }

    @Override
    public <T> void postProcess(NativeWebRequest request, Callable<T> task, Object concurrentResult) {
        TraceLogInfoThreadLocalManager.clear();
    }

    @Override
    public <T> Object handleTimeout(NativeWebRequest request, Callable<T> task) throws Exception {
        return RESULT_NONE;
    }

    @Override
    public <T> void afterCompletion(NativeWebRequest request, Callable<T> task) throws Exception {
    }

    @Override
    public <T> void beforeConcurrentHandling(NativeWebRequest request, DeferredResult<T> deferredResult) throws Exception {
    }

    @Override
    public <T> void preProcess(NativeWebRequest request, DeferredResult<T> deferredResult) throws Exception {
        log.debug("preProcess deferredResult");
    }

    @Override
    public <T> void postProcess(NativeWebRequest request, DeferredResult<T> deferredResult, Object concurrentResult) throws Exception {
        log.debug("postProcess deferredResult");
    }

    @Override
    public <T> boolean handleTimeout(NativeWebRequest request, DeferredResult<T> deferredResult) throws Exception {
        return false;
    }

    @Override
    public <T> void afterCompletion(NativeWebRequest request, DeferredResult<T> deferredResult) throws Exception {
    }
}
