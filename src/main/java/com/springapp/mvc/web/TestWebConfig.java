package com.springapp.mvc.web;

import com.springapp.mvc.trace.EnableTrace;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author songkejun
 * @create 2018-01-03 13:55
 **/
@Configuration
@EnableWebMvc
@EnableTrace(basePackages = "com.springapp.mvc")
@ComponentScan(basePackages = "com.springapp.mvc")
public class TestWebConfig extends WebMvcConfigurerAdapter {
}
