/**
 * 本例以Button为例演示了事件的分发、处理和拦截流程及其传递顺序
 * 1）事件传递顺序：Activity->MyLayout->MyScrollView->MyButton，它们每一个的处理流程如下：
 * 2）dispatchTouchEvent->onInterceptTouchEvent(ViewGroup特有)->外部onTouchListener
 * ->onTouchEvent(此处实际处理时事件)-->外部onClickListenner监听器
 * 3)控件只会触发自身的外部onTouchListener和外部onClickListener，其上层控件的不会被触发
 * 4)上述方法中任一个返回true，事件就不再继续传递，onInterceptTouchEvent返回true则传给自身的onTouchEvent处理
 * 返回false则传给子控件处理。
 * 5）ViewGroup里的onTouchEvent默认返回false；View里的onTouchEvent返回返回true.这样才能执行多次touch事件。
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:EventTransferDemo
 * <br/>Date:Aug，2017
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.eventresponse;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class EventTransferActivity extends Activity {
    private static final String TAG = "EventTransferDemo";
    private TextView tv;

    //给显示信息编号
    private int sNo;
    //编号后在UI上统一显示，
    private final Handler handler = new  Handler() {
        @Override
        public void handleMessage(Message msg) {
            sNo++;
            if (sNo == 1)
                tv.setText(sNo + ")" + msg.obj.toString() + "\n");
            else
                tv.append(sNo + ")" + msg.obj.toString() + "\n");

            Log.i(TAG, tv.getText().toString());
        }
    };

    //显示信息暂存在这里
    private String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_transfer);
        MyButton myButton =  findViewById(R.id.bt);
        MyScrollView myScrollView = findViewById(R.id.scrollview);
        MyLayout myLayout =  findViewById(R.id.mLayout);
        tv = findViewById(R.id.tv);

        myButton.setHandler(handler);
        //启动单击监听器
        myButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                str = "MyButton Clicked!";
                //信息先在终端打印，然后传递给Handler统一显示
                System.out.println(str);
                Message msg = Message.obtain();
                msg.obj = str;
                handler.sendMessage(msg);
            }
        });
        //启动触屏监听器
        myButton.setOnTouchListener(new Button.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        str = "exMyButton---onTouch---DOWN";
                        break;
                    case MotionEvent.ACTION_MOVE:
                        str = "exMyButton---onTouch---MOVE";
                        break;
                    case MotionEvent.ACTION_UP:
                        str = "exMyButton---onTouch---UP";
                        v.performClick();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        str = "exMyButton---onTouch---CANCEL";
                        break;
                    default:
                        str = "exMyButton---onTouch---DEFAULT";
                        break;
                }
                //信息先在终端打印，然后传递给Handler统一显示
                System.out.println(str);
                Message msg = Message.obtain();
                msg.obj = str;
                handler.sendMessage(msg);
                return false;
            }
        });
        myScrollView.setClickable(true);
        myScrollView.setHandler(handler);

        //启动单击监听器，由于ScrollView对其OntouchEvent进行了重写，取消了onClick事件的响应，所以该方法永远不会被执行
        myScrollView.setOnClickListener(new MyScrollView.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = "MyScrollView Clicked!";
                //信息先在终端打印，然后传递给Handler统一显示
                System.out.println(str);
                Message msg = Message.obtain();
                msg.obj = str;
                handler.sendMessage(msg);
            }
        });

        //启动触屏监听器
        myScrollView.setOnTouchListener(new MyScrollView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        str = "exMyScrollView---onTouch---DOWN";
                        break;
                    case MotionEvent.ACTION_MOVE:
                        str = "exMyScrollView---onTouch---MOVE";
                        break;
                    case MotionEvent.ACTION_UP:
                        str = "exMyScrollView---onTouch---UP";
                        v.performClick();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        str = "exMyScrollView---onTouch---CANCEL";
                        break;
                    default:
                        str = "exMyScrollView---onTouch---DEFAULT";
                        break;
                }
                //信息先在终端打印，然后传递给Handler统一显示
                System.out.println(str);
                Message msg = Message.obtain();
                msg.obj = str;
                handler.sendMessage(msg);

                return false;
            }
        });

        myLayout.setHandler(handler);
        //启动单击监听器
        myLayout.setOnClickListener(new MyLayout.OnClickListener() {

            @Override
            public void onClick(View v) {
                str = "MyLayout Clicked!";
                //信息先在终端打印，然后传递给Handler统一显示
                System.out.println(str);
                Message msg = Message.obtain();
                msg.obj = str;
                handler.sendMessage(msg);
            }
        });
        //启动触屏监听器
        myLayout.setOnTouchListener(new MyLayout.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        str = "exMyLayout---onTouch---DOWN";
                        break;
                    case MotionEvent.ACTION_MOVE:
                        str = "exMyLayout---onTouch---MOVE";
                        break;
                    case MotionEvent.ACTION_UP:
                        str = "exMyLayout---onTouch---UP";
                        v.performClick();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        str = "exMyLayout---onTouch---CANCEL";
                        break;
                    default:
                        str = "exMyLayout---onTouch---DEFAULT";
                        break;
                }
                //信息先在终端打印，然后传递给Handler统一显示
                System.out.println(str);
                Message msg = Message.obtain();
                msg.obj = str;
                handler.sendMessage(msg);

                return false;
            }
        });


    }

    //重写Activity的dispatchTouchEvent
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(event.getRawY()>1300) return super.dispatchTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                sNo = 0;
                str = "Activity---dispatchTouchEvent---DOWN";
                break;
            case MotionEvent.ACTION_MOVE:
                str = "Activity---dispatchTouchEvent---MOVE";
                break;
            case MotionEvent.ACTION_UP:
                str = "Activity---dispatchTouchEvent---UP";
                break;
            case MotionEvent.ACTION_CANCEL:
                str = "Activity---dispatchTouchEvent---CANCEL";
                break;
            default:
                break;
        }
        //信息先在终端打印，然后传递给Handler统一显示
        System.out.println(str);
        Message msg = Message.obtain();
        msg.obj = str;
        handler.sendMessage(msg);

        return super.dispatchTouchEvent(event);
    }

    //重写Activity的onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getRawY()>1300) return super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                str = "Activity---onTouchEvent---DOWN";
                break;
            case MotionEvent.ACTION_MOVE:
                str = "Activity---onTouchEvent---MOVE";
                break;
            case MotionEvent.ACTION_UP:
                str = "Activity---onTouchEvent---UP";
                break;
            case MotionEvent.ACTION_CANCEL:
                str = "Activity---onTouchEvent---CANCEL";
                break;
            default:
                break;
        }
        //信息先在终端打印，然后传递给Handler统一显示
        System.out.println(str);
        Message msg = Message.obtain();
        msg.obj = str;
        handler.sendMessage(msg);

        return super.onTouchEvent(event);
    }

   //Activity没有onInterceptTouchEvent(MotionEvent event)
}
