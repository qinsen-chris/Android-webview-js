package com.gangtise.gdk.asyncThread;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.gangtise.gdk.model.BasicModel;

/**
 * @author: created by qinsen
 * date: 2021/1/14 14
 * email: qinshen@Gangtise.onaliyun.com
 *
 * 异步线程，等待id, 获取到id之后通知主线程
 **/
public class WaitingIdThread implements Runnable {

    private BasicModel model;

    private Handler UIHandle;

    public WaitingIdThread(BasicModel model,Handler UIHandle) {
        this.model = model;
        this.UIHandle = UIHandle;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("WaitingIdThread---","start---");
        while(model.getId() == null){
            System.out.println("WaitingIdThread getId is null" );

            if(model.getId()!= null){
                System.out.println("WaitingIdThread getId :" + model.getId());
                break;
            }
        }

        Message message = new Message();
        message.obj = model;
        //通知主线程handle执行
        boolean result = UIHandle.sendMessage(message);
        System.out.println("WaitingIdThread UIHandle.sendMessage :" + result);
    }
}
