/**
 * 本例以Button为例演示了监听响应事件的方式：
 * 1）匿名内部类方式
 * 2）直接在布局文件中绑定到标签方式
 * 3）使用监听接口方式（该方式可以分为本类、内部类、外部类三种方式）
 * 主要监听接口有：OnDragListener、OnLongClickListener、OnKeyListener、OnHoverListener、OnFocusChangeListener等
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
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EventClickActivity extends Activity implements Button.OnClickListener,Button.OnLongClickListener,Button.OnKeyListener {
    private static final String TAG = "EventClick";
    private static final int longClcikTimeInterval = 500;
     Button bt1;
    private TextView tv;

    private static int count;
    private static long firClick, secClick;
    private static boolean longclickFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_click);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        bt1 =  findViewById(R.id.bt1);
        Button bt2 =  findViewById(R.id.bt2);
        Button bt3 =  findViewById(R.id.bt3);
        MyButtonOfClick bt4 =  findViewById(R.id.bt4);
        tv =  findViewById(R.id.tv);

        bt1.setBackgroundColor(0xbd292f34);
        bt1.setTextColor(0xFFFFFFFF);
        bt2.setBackgroundColor(0xbd292f34);
        bt2.setTextColor(0xFFFFFFFF);
        bt3.setBackgroundColor(0xbd292f34);
        bt3.setTextColor(0xFFFFFFFF);
        bt4.setBackgroundColor(0xbd292f34);
        bt4.setTextColor(0xFFFFFFFF);

        //onClick实现方式1：使用匿名内部类的方式实现监听事件，适合按钮少的时候用
        //设置单击监听器
        bt1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                v.requestFocus();
                v.requestFocusFromTouch();
                tv.setText("Button1被单击了！");
                Log.i(TAG, "Button1被单击了");
            }
        });

        //设置长按监听器
        bt1.setOnLongClickListener(new Button.OnLongClickListener() {
            public boolean onLongClick(View v) {
                tv.setText("Button1被长按了！");
                Log.i(TAG, "Button1被长按了");
                return true;
            }
        });


        //设置键盘按键监听器
        bt1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View source
                    , int keyCode, KeyEvent event) {
                // 只处理按下键的事件
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    tv.setText("Button1--- onKeyDown:"+keyCode+"\n");
                    Log.i(TAG, "Button1--- onKeyDown");
                }
                // 返回false，表明该事件会向外传播到Activity，true则在此处终止
                return false;
            }
        });


        //onClick实现方式3：使用接口方式实现监听事件(直接使用Activity作为事件监听器)，适合按钮多的时候用
        bt2.setOnClickListener(this);
        /*该方式可以进一步演变为实现方式4，使用单独监听接口实现类的方式实现监听事件,但比较少用
         bt2.setOnClickListener(new (MyOnClicklistener));
         */
        bt2.setOnLongClickListener(this);
        bt2.setOnKeyListener(this);

        //bt3在xml定义中关联了单击事件到click_bt3方法，无需再写关联代码

        //设置自定义双击监听器
        bt4.setOnTouchListener(new onDoubleClickListener());
        bt4.setOnClickListener(this);
        bt4.setOnLongClickListener(this);
        bt4.setTv(tv);


    }

    //onClick实现方式2：使用接口方式实现监听事件
    @Override
    public void onClick(View v) {
        v.setFocusable(true);
        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.requestFocusFromTouch();
        switch (v.getId()) {
            case R.id.bt2:
                tv.setText("Button2被单击了！");
                Log.i(TAG, "Button2被单击了！");
                break;
            case R.id.bt4:
                tv.setText("MyButton被单击了！");
                Log.i(TAG, "MyButton被单击了！");
                break;
        }
    }

    //onClick实现方式2：使用接口方式实现监听事件
    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.bt2:

                tv.setText("Button2被长按了！");
                Log.i(TAG, "Button2被长按了！");
                break;
            case R.id.bt4:
                tv.setText("MyButton被长按了！");
                Log.i(TAG, "MyButton被长按了！");
                break;

        }
        //返回true，表明已完全处理该事件，该事件不再向外扩散
        return true;
    }

    @Override
    public boolean onKey(View source
            , int keyCode, KeyEvent event) {
        // 只处理按下键的事件
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            tv.setText("Button2--- onKeyDown:"+keyCode+"\n");
            Log.i(TAG, "Button2--- onKeyDown");
        }
        // 返回false，表明该事件会向外传播
        return false;
    }

    //重写Activity onKeyDown方法，该方法可监听它所包含的所有组件的按键被按下事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        tv.append("\n Activity--onKeyDown:"+keyCode+"!");
        Log.v(TAG, "Activity--onKeyDown");
        //返回false，表明并未完全处理该事件，该事件依然向外扩散
        return false;
    }

    //onClick实现方式3：直接布局文件中绑定到标签，广泛适用于各种情况
    public void click_bt3(View v) {
        tv.setText("Button3被单击了！");
        Log.i(TAG, "Button2被单击了！");
    }

    //onClick实现方式4：使用单独的监听接口实现类方式实现监听事件
    //多按钮统一监听处理的方式也适用于方式2和方式3
    class MyOnClicklistener implements View.OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt1:
                    tv.setText("Button1被单击了！");
                    break;
                case R.id.bt2:
                    tv.setText("Button2被单击了！");
                    break;
                case R.id.bt3:
                    tv.setText("Button3被单击了！");
                    break;
                default:
                    break;
            }
        }
    }

    //自定义OnTouchListener实现双击事件，其中对单击和长按也做了区分和处理
    private class onDoubleClickListener implements Button.OnTouchListener {
        WaitThread wait;
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (MotionEvent.ACTION_DOWN == event.getAction()) {
                count++;
                System.out.println("dclick1");
                if (count == 1) {
                    //每次都必须New一个新线程，否则Crash
                    wait = new WaitThread(v);

                    System.out.println("dclick2");
                    //手按下时长按标志设为true
                    longclickFlag = true;
                    firClick = System.currentTimeMillis();
                    //启动等待longClcikTimeIntervalms的线程，在其中处理没有第二次点击的情况
                    wait.start();

                } else if (count == 2) {
                    System.out.println("dclick3");
                    //longClcikTimeIntervalms内产生二次点击则终止等待线程
                    wait.interrupt();

                    secClick = System.currentTimeMillis();
                    System.out.println(secClick - firClick);
                    if (secClick - firClick < longClcikTimeInterval) {
                        tv.setText("MyButton被双击了");
                        Log.i(TAG, "MyButton被双击了");
                    }
                    count = 0;
                    firClick = 0;
                    secClick = 0;
                }
            }
            if (MotionEvent.ACTION_UP == event.getAction()) {
                //如果手抬起则长按标志设为false
                longclickFlag = false;
                System.out.println("dclick8");
            }
            return true;
        }
    }


    private  class WaitThread extends Thread {
        private View view;
        private WaitThread(View view){
            this.view=view;
        }
        @Override
        public void run() {
            try {
                Thread.sleep(longClcikTimeInterval);
                System.out.println("dclick4");
                //等待longClcikTimeInterval ms后仍终止的话，则说明是单击事件
                count = 0;
                firClick = 0;
                secClick = 0;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (longclickFlag)
                        {
                            System.out.println("dclick5");
                            //长按事件往外传
                            view.performLongClick();
                        } else {
                            System.out.println("dclick6");
                            //单击事件往外传
                            view.performClick();
                        }
                    }
                });
            } catch (InterruptedException e) {
                System.out.println("dclick7");
                e.printStackTrace();
            }
        }
    }
}
