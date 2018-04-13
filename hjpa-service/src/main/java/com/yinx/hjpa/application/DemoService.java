package com.yinx.hjpa.application;

import com.yinx.hjpa.domain.Demo;
import com.yinx.hjpa.utils.Page;

/**
 * Package : com.yinx.hjpa.application
 *
 * @author YixinCapital -- yinx
 *         2018/4/10 15:52
 */
public interface DemoService {
    void add(Demo demo);
    void modify(Demo demo);
    Page<Demo> pageQuery(Demo demo);
    void seachByHQL(Demo demo);
    void searchById(String id);
    void searchBySQL(Demo demo);
    void searchByProperty(Demo demo);
}
