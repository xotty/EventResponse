/**
 * 自定义ScrollView，重写事件处理的三个方法：
 * 1）dispatchTouchEvent
 * 2）onInterceptTouchEvent
 * 3）onTouchEvent
 * 事件处理后完毕将响应信息传给Activity，并继续传递该事件
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
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
    private String str;
    private Handler handler;

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                str = "MyScrollView---dispatchTouchEvent---DOWN";
                break;
            case MotionEvent.ACTION_MOVE:
                str = "MyScrollView---dispatchTouchEvent---MOVE";
                break;
            case MotionEvent.ACTION_UP:
                str = "MyScrollView---dispatchTouchEvent---UP";
                break;
            case MotionEvent.ACTION_CANCEL:
                str = "MyScrollView---dispatchTouchEvent---CANCEL";
                break;
            default:
                str = "MyScrollView---dispatchTouchEven---DEFAULT";
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
                str = "MyScrollView---onInterceptTouchEvent---DOWN";
                break;
            case MotionEvent.ACTION_MOVE:
                str = "MyScrollView---onInterceptTouchEvent---MOVE";
                break;
            case MotionEvent.ACTION_UP:
                str = "MyScrollView---onInterceptTouchEvent---UP";
                break;
            case MotionEvent.ACTION_CANCEL:
                str = "MyScrollView---onInterceptTouchEvent---CANCEL";
                break;
            default:
                str = "MyScrollView---onInterceptTouchEvent---DEFAULT";
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
                str = "MyScrollView---onTouchEvent---DOWN";
                break;
            case MotionEvent.ACTION_MOVE:
                str = "MyScrollView---onTouchEvent---MOVE";
                break;
            case MotionEvent.ACTION_UP:
                str = "MyScrollView---onTouchEvent---UP";
                break;
            case MotionEvent.ACTION_CANCEL:
                str = "MyScrollView---onTouchEvent---CANCEL";
                break;
            default:
                str = "MyScrollView---onTouchEvent---DEFAULT";
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

