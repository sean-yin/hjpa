package com.yinx.hjpa.controller;

import com.yinx.hjpa.application.DemoService;
import com.yinx.hjpa.domain.Demo;
import com.yinx.hjpa.utils.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Package : com.yinx.hjpa.controller
 *
 * @author YixinCapital -- yinx
 *         2018/4/10 16:20
 */
@Controller
@RequestMapping("/demo")
public class DemoController {
    private Logger logger = LoggerFactory.getLogger(DemoController.class);
    @Autowired
    private DemoService demoService;

    @ResponseBody
    @RequestMapping(value="/pageQuery",method = RequestMethod.GET)
    public Page<?> pageQuery(Demo demo) {
        demo.setName("a");
        demo.setAge("12");
        return demoService.pageQuery(demo);
    }
    @ResponseBody
    @RequestMapping(value="/add",method = RequestMethod.GET)
    public void add(Demo s) {
        demoService.add(s);
    }
}
