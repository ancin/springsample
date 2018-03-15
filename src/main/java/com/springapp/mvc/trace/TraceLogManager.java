package com.springapp.mvc.trace;

/**
 * @author songkejun
 * @create 2018-01-03 13:37
 **/
public interface TraceLogManager {
    void writeStartLog(String message);

    void writeEndLog(String message);

    void writeExceptionLog(String message, Throwable ex);

    void setSlowTime(long slowTime);

    void setException(Throwable ex);

    Throwable getException();

    long getResponseTime();

    void setErrorLogType(ErrorLogType type);
    ErrorLogType getErrorLogType();
}
