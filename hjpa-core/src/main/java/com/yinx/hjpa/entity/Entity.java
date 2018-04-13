package com.yinx.hjpa.entity;

import java.io.Serializable;

/**
 * Created by seany on 2018/3/11.
 */
public abstract interface Entity extends Serializable {
    public abstract Serializable getId();

    public abstract boolean existed();

    public abstract boolean notExisted();
}
