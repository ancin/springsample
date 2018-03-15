package com.springapp.mvc.controller;

import com.springapp.mvc.bizservice.BizService;
import com.springapp.mvc.dubbox.service.DemoService;
import com.springapp.mvc.dubbox.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/test")
public class HelloController {
    private static Logger logger = LoggerFactory.getLogger(HelloController.class);
    @Resource
    private DemoService demoService;

    @Resource
    private OrderService orderService;


    @Autowired
    private BizService bizService;

    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        model.addAttribute("message", "Hello world!");
        return "hello";
    }

    @RequestMapping("sayhello")
    public String sayHello(ModelMap model) throws Exception {
        String result = demoService.sayHello();
        model.addAttribute("message", result);
        return "sayhello";
    }

    @RequestMapping("hellodb")
    public String helloDB(ModelMap model) throws Exception {
        String result = bizService.getNameValue();
        logger.info("###result=" + result);
        model.addAttribute("message", result);
        StackTraceElement st[] = Thread.currentThread().getStackTrace();
        for (int i = 0; i < st.length; i++) {
            System.out.println("----\t" + st[i]);
        }
        return "sayhello";
    }

    @RequestMapping("order")
    public String helloOder(ModelMap model) throws Exception {
        System.out.println("controller order dubbo service");
        List order = orderService.query();
        model.addAttribute("message", "get order size:" + order.size());
        return "sayhello";
    }


    public void setBizService(BizService bizService) {
        this.bizService = bizService;
    }
}