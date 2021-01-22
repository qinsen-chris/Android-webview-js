package com.gangtise.gdk.common;

/**
 * @author: created by qinsen
 * date: 2021/1/8
 * email: qinshen@Gangtise.onaliyun.com
 **/
public class GDKException extends RuntimeException{

    private Integer code;

    private String message;

    public GDKException(Integer code,String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
