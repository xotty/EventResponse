/**
 * 本例以Button为例演示了事件的分发、处理和拦截流程及其传递顺序
 * 1）事件传递顺序：Activity->MyLayout->MyScrollView->MyButton，它们每一个的处理流程如下：
 * 2）dispatchTouchEvent->onInterceptTouchEvent(ViewGroup特有)->外部onTouchListener
 * ->onTouchEvent-->外部onClickListenner监听器
 * 3) 控件只会触发自身的外部onTouchListener和外部onClickListener，其上层控件的不会被触发
 * 4）上述方法中任一个返回true，事件就不再继续传递
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:HttpDemo
 * <br/>Date:Aug，2017
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.eventresponse;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static org.xottys.eventresponse.R.id.view;

public class MainActivity extends Activity {
    private static final String TAG = "EventTransferDemo";
    private MyButton myButton;
    private MyLayout myLayout;
    private MyScrollView myScrollView;
    private TextView tv;
    private int sNo;

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            sNo++;
            if (sNo == 1)
                tv.setText(sNo + ")" + msg.obj.toString() + "\n");
            else
                tv.append(sNo + ")" + msg.obj.toString() + "\n");
        }
    };

    private String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myButton = (MyButton) findViewById(R.id.bt);
        myScrollView = (MyScrollView) findViewById(view);
        myLayout = (MyLayout) findViewById(R.id.mLayout);

        tv = (TextView) findViewById(R.id.tv);

        myButton.setBackgroundColor(0xbd292f34);
        myButton.setTextColor(0xFFFFFFFF);


        myButton.setHandler(handler);
        //onClick实现方式1：使用匿名内部类的方式实现监听事件，适合按钮少的时候用
        myButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                str = "MyButton Clicked!";
                System.out.println(str);
                Message msg = Message.obtain();
                msg.obj = str;
                handler.sendMessage(msg);
            }
        });

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
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        str = "exMyButton---onTouch---CANCEL";
                        break;
                    default:
                        break;
                }
                System.out.println(str);
                Message msg = Message.obtain();
                msg.obj = str;
                handler.sendMessage(msg);
                return false;
            }
        });
        myScrollView.setHandler(handler);
        myScrollView.setOnClickListener(new MyScrollView.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = "View Clicked!";
                System.out.println(str);
                Message msg = Message.obtain();
                msg.obj = str;
                handler.sendMessage(msg);
            }
        });

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
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        str = "exMyScrollView---onTouch---CANCEL";
                        break;
                    default:
                        break;
                }
                System.out.println(str);
                Message msg = Message.obtain();
                msg.obj = str;
                handler.sendMessage(msg);
                return false;
            }
        });
        myLayout.setHandler(handler);
        myLayout.setOnClickListener(new MyLayout.OnClickListener() {

            @Override
            public void onClick(View v) {
                str = "MyLayout Clicked!";
                System.out.println(str);
                Message msg = Message.obtain();
                msg.obj = str;
                handler.sendMessage(msg);
            }
        });

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
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        str = "exMyLayout---onTouch---CANCEL";
                        break;
                    default:
                        break;
                }
                System.out.println(str);
                Message msg = Message.obtain();
                msg.obj = str;
                handler.sendMessage(msg);
                return false;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
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
        System.out.println(str);
        Message msg = Message.obtain();
        msg.obj = str;
        handler.sendMessage(msg);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
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
        System.out.println(str);
        Message msg = Message.obtain();
        msg.obj = str;
        handler.sendMessage(msg);
        return super.onTouchEvent(event);
    }
}
