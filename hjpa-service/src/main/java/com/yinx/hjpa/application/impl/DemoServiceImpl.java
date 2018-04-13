package com.yinx.hjpa.application.impl;

import com.yinx.hjpa.application.DemoService;
import com.yinx.hjpa.domain.Demo;
import com.yinx.hjpa.querychannel.QueryChannelService;
import com.yinx.hjpa.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Package : com.yinx.hjpa.application.impl
 *
 * @author YixinCapital -- yinx
 *         2018/4/10 15:52
 */
@Service("demoservice")
public class DemoServiceImpl implements DemoService {
    @Autowired
    private QueryChannelService queryChannelService;

    @Override
    public void add(Demo demo) {
        Demo d= new Demo();
        d.setName("a");
        d.setAge("12");
        d.setSex("fale");
        d.create();

    }

    @Override
    public void modify(Demo demo) {
        demo.update();
    }

    @Override
    public Page<Demo> pageQuery(Demo demo) {
        List<Object> conditions = new ArrayList<>();
        StringBuilder jpql = new StringBuilder("select demo from Demo demo where demo.deleted = false and demo.name=? ");
        conditions.add(demo.getName());
        jpql.append(" and demo.age = ? ");
        conditions.add(demo.getAge());
        Page<Demo> pagedList = null;
        pagedList = queryChannelService.createJpqlQuery(jpql.toString()).setParameters(conditions).setPage(1-1/**当前页减一**/, 5/**每页查询多少条，参数条件需从前台传**/).pagedList();
        return pagedList;
    }

    @Override
    public void seachByHQL(Demo demo) {
        List<Object> conditions = new ArrayList<>();
        StringBuilder jpql = new StringBuilder("select demo from Demo demo where demo.deleted = false and demo.name=? ");
        conditions.add(demo.getName());
        jpql.append(" and demo.age = ? ");
        conditions.add(demo.getAge());
        List<Demo> list = queryChannelService.createJpqlQuery(jpql.toString()).setParameters(conditions).list();
    }

    @Override
    public void searchById(String id) {
        Demo d = Demo.get(Demo.class,id);
    }

    @Override
    public void searchBySQL(Demo demo) {
        List<Object> conditions = new ArrayList<>();
        StringBuilder jpql = new StringBuilder("select name,age from demo where is_deleted = false and name=? ");
        conditions.add(demo.getName());
        jpql.append(" and age = ? ");
        conditions.add(demo.getAge());
        List<Object[]> list = queryChannelService.createSqlQuery(jpql.toString()).setParameters(conditions).list();
    }

    @Override
    public void searchByProperty(Demo demo) {
        Map<String,Object> map = new HashMap<>();
        map.put("name",demo.getName());
        List<Demo> list = Demo.findByProperties(Demo.class,map);
        List<Demo> list1 = Demo.findByProperty(Demo.class,"name",demo.getName());
    }
}
