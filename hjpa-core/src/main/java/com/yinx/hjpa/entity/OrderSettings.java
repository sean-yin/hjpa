package com.yinx.hjpa.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by seany on 2018/3/16.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class OrderSettings {
    private List<KeyValue<String, Boolean>> orderBy = new ArrayList();

    public List<KeyValue<String, Boolean>> getOrderBy() {
        return this.orderBy;
    }

    public void asc(String propName) {
        this.orderBy.add(new KeyValue(propName, Boolean.valueOf(true)));
    }

    public void desc(String propName) {
        this.orderBy.add(new KeyValue(propName, Boolean.valueOf(false)));
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof OrderSettings)) {
            return false;
        }
        OrderSettings that = (OrderSettings) other;
        return new EqualsBuilder().append(this.orderBy, that.orderBy).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(this.orderBy).toHashCode();
    }
}
