package com.gangtise.gdk.result.dataprocess;

import java.util.List;

/**
 * @author: created by qinsen
 * date: 2021/1/19 20
 * email: qinshen@Gangtise.onaliyun.com
 **/
public class GData {

    private String type;
    private Object gid;
    private List<Object> result;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getGid() {
        return gid;
    }

    public void setGid(Object gid) {
        this.gid = gid;
    }

    public List<Object> getResult() {
        return result;
    }

    public void setResult(List<Object> result) {
        this.result = result;
    }
}
