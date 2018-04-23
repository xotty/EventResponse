/**
 * 本例演示GestureDetector的用法
 * 一、事件监听：单击、双击、长按、滚动等
 * 1）定义一个GestureDetector.SimpleOnGestureListener，在其中完成各种点击或滚动事件的处理
 * 2）用上述监听器构造GestureDetector对象
 *    mGestureDetector = new GestureDetector(this, new MySimpleGestureListener());
 *    如有需要可设置其双击监听器
 *    mGestureDetector.setOnDoubleTapListener(new MySimpleGestureListener());
 * 3）通过view.setOnTouchListener关联GestureDetector，在其方法中直接return mGestureDetector.onTouchEvent(event)即可
 * 二、手势的处理和识别
 * 1）View自带的接口view.GestureDetector可以处理常用手势
 * 2）andorid.gesture框架主要用来生成和识别自定义手势
 * 3）自定义手势处理流程：OnGestureListener->OnGesturingListener->OnGesturePerformedListener
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
import android.content.Context;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class GestureActivity extends Activity implements GestureOverlayView.OnGesturingListener {
    private static final String TAG = "GestureDemo";

    private Button  bt1, bt2;
    private TextView tv, tv1;
    private EditText et1;

    private GestureDetector mGestureDetector;

    private GestureOverlayView mGestureView;
    private Gesture mGesture;
    private static GestureLibrary mGestureLib;
    private File mStoreFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);
        Button bt =  findViewById(R.id.bt);
        bt1 =  findViewById(R.id.bt1);
        bt2 =  findViewById(R.id.bt2);

        tv = findViewById(R.id.tv);
        tv1 =  findViewById(R.id.tv1);
        et1 =  findViewById(R.id.et1);
        mGestureView =  findViewById(R.id.gesture);

        bt.setBackgroundColor(0xbd292f34);
        bt.setTextColor(0xFFFFFFFF);

        //定义view常用手势监听器
        mGestureDetector = new GestureDetector(this, new MySimpleGestureListener());
        mGestureDetector.setOnDoubleTapListener(new MySimpleGestureListener());

        //为Button设置简单的单击监听接口
        bt.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                tv.append("Button被单击了！\n");
                Log.i(TAG, "Button clicked!");
            }
        });

        //为Button设置view常用手势监听接口
        bt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });


        //在SDCard中定义自定义手势库文件mStoreFile->mGestureLib
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
            mStoreFile = new File(sdDir.toString() + "/" + "gesturelib");
        } else {
            Log.i(TAG, "SDCard not exist!");
        }
        if (mGestureLib == null) {
            mGestureLib = GestureLibraries.fromFile(mStoreFile);
            if (!mGestureLib.load()) {
                Log.i(TAG, "Gesture file not load");
            } else {
                Log.i(TAG, "Gesture file loaded");
            }
        }

        //设置手势可多笔画绘制，默认情况为单笔画绘制
        mGestureView.setGestureStrokeType(GestureOverlayView.GESTURE_STROKE_TYPE_MULTIPLE);
        //设置手势的粗细
        mGestureView.setGestureStrokeWidth(6);
        //初始化手势区不可用
        mGestureView.setEnabled(false);

        bt1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //设置手势区可用
                mGestureView.setEnabled(true);
                //设置绘制手势不自动消失
                mGestureView.setFadeEnabled(false);
                //设置绘制手势的颜色
               mGestureView.setGestureColor(Color.CYAN);
                if (bt1.getText().equals("Add")) {
                    //将用于识别手势的监听器移除
                    mGestureView.removeAllOnGesturePerformedListeners();
                    //添加新增手势的监听器
                    mGestureView.addOnGestureListener(new MyGestureListener());

                    bt1.setText("Done");
                    bt2.setText("Discard");
                    tv1.setVisibility(View.VISIBLE);
                    et1.setVisibility(View.VISIBLE);
                    et1.setText("");
                    tv.setText("");
                } else if (bt1.getText().equals("Done")) {
                    //将手势添加到手势库文件中
                    addGesture();
                    //立即清除手势图像
                    mGestureView.clear(false);

                    et1.setText("");
                }
            }
        });

        //手势的真正识别工作在perform 监听器中完成
        bt2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //将用于新增手势的监听器移除
                mGestureView.removeAllOnGestureListeners();
                //添加识别手势的监听器
                mGestureView.addOnGesturePerformedListener(new MyGesturePerformedListener());
                //设置绘制手势自动消失
                mGestureView.setFadeEnabled(true);
                //设置绘制手势自动消失时间为1秒，默认值为420毫秒
                //此间隔时间也可以理解为手势绘制完成手指离开屏幕后到调用onGesturePerformed的时间间隔
                mGestureView.setFadeOffset(1000);

                mGestureView.setGestureColor(Color.GREEN);
                mGestureView.clear(false);

                tv1.setVisibility(View.INVISIBLE);
                et1.setVisibility(View.INVISIBLE);
                if (bt2.getText().equals("Recognize")) {
                    mGestureView.setEnabled(true);
                    bt1.setTextColor(0xFFFFFFFF);
                    bt1.setEnabled(false);
                    bt2.setText("Back");
                    tv.setText("");
                } else {
                    bt1.setTextColor(0xFF000000);
                    bt1.setEnabled(true);
                    bt1.setText("Add");
                    bt2.setText("Recognize");
                    mGestureView.setEnabled(false);
                }
            }
        });

       //绑定监听器
       mGestureView.addOnGesturingListener(this);
    }

    //正在绘制手势时调用
    @Override
    public void onGesturingStarted(GestureOverlayView overlay) {
        tv.append("onGesturingStarted\n");
        Log.i(TAG, "onGesturingStarted:正在绘制手势");
    }

    //结束正在绘制手势时调用(手势绘制完成时一般是先调用它再调用onGesturePerformed)
    @Override
    public void onGesturingEnded(GestureOverlayView overlay) {
        tv.append("onGesturingEnded\n");
        Log.i(TAG, "onGesturingEnded: 结束正在绘制手势");
    }

    //向手势库文件中添加手势
    public void addGesture() {
        if (mGesture != null) {
            //首先确保手势名称不能为空
            final CharSequence name = et1.getText();
            if (name.length() == 0) {
                tv.append(getString(R.string.error_missing_name));
                return;
            }
            //然后将手势及其名称一起保存到mGestureLib文件中
            mGestureLib.addGesture(name.toString(), mGesture);
            if (mGestureLib.save()) {
                tv.append(name.toString() + " 手势保存成功" + "\n");
                Log.i(TAG, "addGesture: Succeed!");
            }
            else {
                tv.append(name.toString() + " 手势保存失败" + "\n");
                Log.i(TAG, "addGesture: Failed!");
            }
        } else {
            tv.append(" 手势添加被取消" + "\n");
            Log.i(TAG, "addGesture: Cancelled!");
        }
    }

    //删除手势库文件中的某个手势，若要修改库中的手势则采用先删除后添加的方式
    private void deleteGesture(String name, Gesture gesture) {
        mGestureLib.removeGesture(name, gesture);
        mGestureLib.save();
    }

    //view常用手势监听器
    class MySimpleGestureListener extends GestureDetector.SimpleOnGestureListener {
        public MySimpleGestureListener() {
            super();
        }

        //每按一下屏幕立即触发
        @Override
        public boolean onDown(MotionEvent e) {
            tv.setText("onDown\n");
            Log.e(TAG, "onDown");
            return false;
        }

        //一次单纯的轻击抬手动作时触发
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            tv.append("onSingleTapUp\n");
            Log.e(TAG, "onSingleTapUp");
            return false;
        }

        /*
           * 单击事件。用来判定该次点击是单纯的SingleTap而不是DoubleTap，如果连续点击两次就是DoubleTap手势，
           * 如果只点击一次，系统等待一段时间后没有收到第二次点击则判定该次点击为SingleTap而不是DoubleTap，
           * 然后触发SingleTapConfirmed事件。触发顺序是：OnDown->OnsingleTapUp->OnsingleTapConfirmed
           * 关于onSingleTapConfirmed和onSingleTapUp的一点区别：二者的区别是：onSingleTapUp，只要手抬起就会执行，
           * 而对于onSingleTapConfirmed来说，如果双击的话，则onSingleTapConfirmed不会执行。
           */
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            tv.append("onSingleTapConfirmed\n");
            Log.e(TAG, "onSingleTapConfirmed");
            return false;
        }

        //用户按下屏幕并且没有移动或松开。主要是提供给用户一个可视化的反馈，告诉用户他们的按下操作已经
        //被捕捉到了。如果按下的速度很快只会调用onDown(),按下的速度稍慢一点会先调用onDown()再调用onShowPress().
        @Override
        public void onShowPress(MotionEvent e) {
            tv.append("onShowPress\n");
            Log.e(TAG, "onShowPress");
        }

        //长按。在down操作之后，过一个更长的特定时间触发
        @Override
        public void onLongPress(MotionEvent e) {
            tv.append("onLongPress\n");
            Log.e(TAG, "onLongPress");
        }

        @Override
        public boolean onContextClick(MotionEvent e) {
            tv.append("onContextClick\n");
            Log.e(TAG, "onContextClick");
            return true;
        }

        //双击事件
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            tv.append("onDoubleTap\n");
            Log.e(TAG, "onDoubleTap");
            return true;
        }

        //双击间隔中发生的动作。指触发onDoubleTap以后，在双击之间发生的其它动作，包含down、up和move事件
        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            tv.append("onDoubleTapEvent\n");
            Log.e(TAG, "onDoubleTapEvent");
            return true;
        }

        /*
         *屏幕拖动事件，如果按下的时间过长，调用了onLongPress，再拖动屏幕不会触发onScroll。拖动屏幕会多次触发
         * @param e1 开始拖动的第一次按下down操作,也就是第一个ACTION_DOWN
         * @parem e2 触发当前onScroll方法的ACTION_MOVE
         * @param distanceX 当前的x坐标与最后一次触发scroll方法的x坐标的差值。
         * @param diastancY 当前的y坐标与最后一次触发scroll方法的y坐标的差值。
         */
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            tv.append("onScroll\n");
            Log.e(TAG, "onScroll");
            return true;
        }

        /*
        * 按下屏幕，在屏幕上快速滑动后松开，由一个down,多个move,一个up触发
        * @param e1 开始快速滑动的第一次按下down操作,也就是第一个ACTION_DOWN
        * @parem e2 触发当前onFling方法的move操作,也就是最后一个ACTION_MOVE
        * @param velocityX：X轴上的移动速度，像素/秒
        * @parram velocityY：Y轴上的移动速度，像素/秒
        */
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            tv.append("onFling\n");
            Log.e(TAG, "onFling");
            return true;
        }
    }

    //用于实现自定义手势添加的监听器，从手势绘制开始到结束全程监听
    private class MyGestureListener implements GestureOverlayView.OnGestureListener {
       //手势绘制开始
        @Override
        public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
            bt1.setEnabled(false);
            mGesture = null;
            //取消软键盘
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(et1.getWindowToken(), 0);
            tv.append("onGestureStarted\n");
            Log.i(TAG, "onGestureStarted: ");
        }

        //手势绘制中
        @Override
        public void onGesture(GestureOverlayView overlay, MotionEvent event) {
            //  Log.i(TAG, "onGesture: ");
        }

        //手势绘制结束
        @Override
        public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
            mGesture = overlay.getGesture();
            if (mGesture.getLength() < 120.0f) {
                overlay.clear(false);
            }
            bt1.setEnabled(true);
            tv.append("nGestureEnded\n");
            Log.i(TAG, "onGestureEnded");
        }

        //手势绘制取消
        public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {
            tv.append("onGestureCancelled\n");
            Log.i(TAG, "onGestureCancelled");
        }
    }

    //用于实现自定义手势识别的监听器
    class MyGesturePerformedListener implements OnGesturePerformedListener {
       //手势绘制结束后抵用
        @Override
        public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
            tv.append("onGesturePerformed\n");
            //从手势库中查询匹配的内容，匹配的结果可能包括多个相似的结果，匹配度高的结果放在最前面
            ArrayList predictions = mGestureLib.recognize(gesture);
            if (predictions.size() > 0) {
                Prediction prediction = (Prediction) predictions.get(0);
                //匹配的手势，越匹配score的值越大，最大为10
                if (prediction.score > 1.0) {
                    tv.append("该手势被识别为："+prediction.name+"\n");
                    Log.i(TAG, "onGesturePerformed: " + prediction.name);
                }
            }

        }
    }
}
