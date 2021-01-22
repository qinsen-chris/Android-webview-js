package com.gangtise.gdk.model;

import com.gangtise.gdk.asynctask.Task2SetParams;
import com.gangtise.gdk.common.TaskObj;
import com.gangtise.gdk.ginterface.GResultCallBack;

/**
 * @author: created by qinsen
 * date: 2021/1/6
 * email: qinshen@Gangtise.onaliyun.com
 **/
public class BasicModel {
    private String id;

    private GResultCallBack onCallback;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BasicModel() {
    }

    /**
     * 执行对象中“设置参数”和“执行函数操作”
     * @param cmd
     */
    public void assembleTask2SetParams(String cmd){
        Task2SetParams task = new Task2SetParams();
        TaskObj obj = new TaskObj();
        obj.requestObj = this;

        obj.cmd = cmd;
        task.execute(obj);
    }

    public GResultCallBack getOnCallback() {
        return onCallback;
    }

    /**
     * 设置回调函数
     * @param onCallback
     */
    public void setOnCallback(GResultCallBack onCallback) {
        this.onCallback = onCallback;
    }

}
