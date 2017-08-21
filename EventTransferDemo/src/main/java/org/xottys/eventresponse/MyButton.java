/**
 * 自定义Button，重写事件处理的两个方法：
 * 1）dispatchTouchEvent
 * 2）onTouchEvent
 * 事件处理后将响应信息传给Activity，并继续传递该事件
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:EventTransferDemo
 * <br/>Date:Aug，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.eventresponse;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

public class MyButton extends Button {
    private String str;
    private Handler handler;

    public MyButton(Context context, AttributeSet set) {
        super(context, set);
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                str = "MyButton---dispatchTouchEvent---DOWN";
                break;
            case MotionEvent.ACTION_MOVE:
                str = "MyButton---dispatchTouchEvent---MOVE";
                break;
            case MotionEvent.ACTION_UP:
                str = "MyButton---dispatchTouchEvent---UP";
                break;
            case MotionEvent.ACTION_CANCEL:
                str = "MyButton---dispatchTouchEvent---CANCEL";
                break;
            default:
                str = "MyButton---dispatchTouchEvent---DEFAULT";
                break;
        }
        System.out.println(str);

        //传递打印信息给Activity
        Message msg = Message.obtain();
        msg.obj = str;
        handler.sendMessage(msg);

        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                str = "MyButton---onTouchEvent---DOWN";
                break;
            case MotionEvent.ACTION_MOVE:
                str = "MyButton---onTouchEvent---MOVE";
                break;
            case MotionEvent.ACTION_UP:
                str = "MyButton---onTouchEvent---UP";
                break;
            case MotionEvent.ACTION_CANCEL:
                str = "MyButton---onTouchEvent---CANCEL";
                break;
            default:
                str = "MyButton---onTouchEvent---DEFAULT";
                break;
        }
        System.out.println(str);
        //传递打印信息给Activity
        Message msg = Message.obtain();
        msg.obj = str;
        handler.sendMessage(msg);

        return super.onTouchEvent(event);
    }
}
