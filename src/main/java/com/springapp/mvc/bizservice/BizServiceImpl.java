package com.springapp.mvc.bizservice;

import com.springapp.mvc.dao.DaoOper;

import javax.annotation.Resource;

/**
 * biz service implement
 *
 * @author songkejun
 * @create 2017-12-19 9:33
 **/
public class BizServiceImpl implements BizService {

    @Resource
    private DaoOper daoOper;

    @Override
    public String getNameValue() {
        return daoOper.getMsgName();
    }
}
