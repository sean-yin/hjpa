package com.yinx.hjpa.domain;

import com.yinx.hjpa.entity.BZBaseEntiy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Package : com.yinx.hjpa.domain
 *
 * @author YixinCapital -- yinx
 *         2018/4/10 16:05
 */
@Table(name = "hjpa_demo")
@Entity
public class Demo extends BZBaseEntiy {
    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private String age;

    @Column(name = "sex")
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
