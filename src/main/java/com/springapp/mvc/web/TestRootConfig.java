package com.springapp.mvc.web;

import com.springapp.mvc.trace.EnableTrace;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * TestRootConfig
 *
 * @author songkejun
 * @create 2018-01-03 13:55
 **/
@Configuration
@EnableAsync
@EnableTrace(basePackages = "com.springapp.mvc")
@ComponentScan(basePackages = {"com.springapp.mvc.bizservice", "com.springapp.mvc.dao"})
public class TestRootConfig {
}
