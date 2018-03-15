package com.springapp.mvc.web;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * @author songkejun
 * @create 2018-01-03 13:53
 **/
public class TestWebInitializer  extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{};//TestRootConfig.class
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{TestWebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");

        TraceLogFilter traceLogFilter = new TraceLogFilter();
        return new Filter[]{encodingFilter, traceLogFilter};
    }

}
