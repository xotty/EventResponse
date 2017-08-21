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

public class MainActivity extends Activity implements Button.OnClickListener {
    private static final String TAG = "ClickDemo";
    private static final int longClcikTimeInterval = 500;
    private MyButton bt1;
    private Button  bt2, bt3;
    private TextView tv;

    private int count;
    private long firClick, secClick;
    private boolean longclickFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt1 = (MyButton) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt3 = (Button) findViewById(R.id.bt3);
        tv = (TextView) findViewById(R.id.tv);

        bt1.setBackgroundColor(0xbd292f34);
        bt1.setTextColor(0xFFFFFFFF);
        bt2.setBackgroundColor(0xbd292f34);
        bt2.setTextColor(0xFFFFFFFF);
        bt3.setBackgroundColor(0xbd292f34);
        bt3.setTextColor(0xFFFFFFFF);

        //onClick实现方式1：使用匿名内部类的方式实现监听事件，适合按钮少的时候用
        //设置单击监听器
        bt1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        //设置自定义双击监听器
        bt1.setOnTouchListener(new onDoubleClickListener());

        //设置键盘按键监听器
        bt1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View source
                    , int keyCode, KeyEvent event) {
                // 只处理按下键的事件
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    tv.setText("the onKeyDown in Listener\n");
                    Log.v("-Listener-", "the onKeyDown in Listener");
                }
                // 返回false，表明该事件会向外传播
                return false;
            }
        });

        bt1.setTv(tv);
        //onClick实现方式3：使用接口方式实现监听事件(直接使用Activity作为事件监听器)，适合按钮多的时候用
        bt3.setOnClickListener(this);
        /*该方式可以进一步演变为实现方式4，使用单独监听接口实现类的方式实现监听事件,但比较少用
         bt3.setOnClickListener(new (MyOnClicklistener));
         */
    }

    //onClick实现方式2：直接布局文件中绑定到标签，广泛适用于各种情况
    public void click_bt2(View v) {
        tv.setText("Button2被点击了！");
        Log.i(TAG, "Button2被点击了！");
    }

    //onClick实现方式3：使用接口方式实现监听事件
    @Override
    public void onClick(View v) {
        tv.setText("Button3被点击了！");
        Log.i(TAG, "Button3被点击了！");
    }

    //重写Activity onKeyDown方法，该方法可监听它所包含的所有组件的按键被按下事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        tv.append("the onKeyDown in Activity\n");
        Log.v("-Activity-", "the onKeyDown in Activity");
        //返回false，表明并未完全处理该事件，该事件依然向外扩散
        return false;
    }

    //onClick实现方式4：使用单独的监听接口实现类方式实现监听事件
    //多按钮统一监听处理的方式也适用于方式2和方式3
    class MyOnClicklistener implements View.OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt1:
                    tv.setText("Button1被点击了！");
                    break;
                case R.id.bt2:
                    tv.setText("Button2被点击了！");
                    break;
                case R.id.bt3:
                    tv.setText("Button3被点击了！");
                    break;
                default:
                    break;
            }
        }
    }

    //自定义实现双击事件
    private class onDoubleClickListener implements Button.OnTouchListener {
        Thread wait = new WaitThread();

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (MotionEvent.ACTION_DOWN == event.getAction()) {
                count++;
                System.out.println("dclick1");
                if (count == 1) {
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
                        tv.setText("Button1被双击了");
                        Log.i(TAG, "Button1被双击了");
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

    private class WaitThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(longClcikTimeInterval);
                System.out.println("dclick4");
                //等待longClcikTimeInterval ms后仍违背良心终止的话，则说明是单击事件
                count = 0;
                firClick = 0;
                secClick = 0;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (longclickFlag)
                        {
                            System.out.println("dclick5");
                            bt1.performLongClick();
                        } else {
                            System.out.println("dclick6");
                            bt1.performClick();
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
