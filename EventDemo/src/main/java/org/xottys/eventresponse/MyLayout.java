/**
 * 自定义Layout，重写事件处理的三个方法：
 * 1）dispatchTouchEvent
 * 2）onInterceptTouchEvent
 * 3）onTouchEvent
 * 事件处理后将响应信息传给Activity，并继续传递该事件
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:EventTransferDemo
 * <br/>Date:Aug，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.eventresponse;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class MyLayout extends LinearLayout {
    private String str;
    private Handler handler;

    public MyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                str = "MyLayout---dispatchTouchEvent---DOWN";
                break;
            case MotionEvent.ACTION_MOVE:
                str = "MyLayout---dispatchTouchEvent---MOVE";
                break;
            case MotionEvent.ACTION_UP:
                str = "MyLayout---dispatchTouchEvent---UP";
                break;
            case MotionEvent.ACTION_CANCEL:
                str = "MyLayout---dispatchTouchEvent---CANCEL";
                break;
            default:
                str = "MyLayout---dispatchTouchEvent---DEFAULT";
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
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                str = "MyLayout---onInterceptTouchEvent---DOWN";
                break;
            case MotionEvent.ACTION_MOVE:
                str = "MyLayout---onInterceptTouchEvent---MOVE";
                break;
            case MotionEvent.ACTION_UP:
                str = "MyLayout---onInterceptTouchEvent---UP";
                break;
            case MotionEvent.ACTION_CANCEL:
                str = "MyLayout---onInterceptTouchEvent---CANCEL";
                break;
            default:
                str = "MyLayout---onInterceptTouchEvent---DEFAULT";
                break;
        }
        System.out.println(str);
        //传递打印信息给Activity
        Message msg = Message.obtain();
        msg.obj = str;
        handler.sendMessage(msg);
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                str = "MyLayout---onTouchEvent---DOWN";
                break;
            case MotionEvent.ACTION_MOVE:
                str = "MyLayout---onTouchEvent---MOVE";
                break;
            case MotionEvent.ACTION_UP:
                str = "MyLayout---onTouchEvent---UP";
                performClick();
                break;
            case MotionEvent.ACTION_CANCEL:
                str = "MyLayout---onTouchEvent---CANCEL";
                break;
            default:
                str = "MyLayout---onTouchEvent---DEFAULT";
                break;
        }
        System.out.println(str);
        //传递打印信息给Activity
        Message msg = Message.obtain();
        msg.obj = str;
        handler.sendMessage(msg);

        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}

