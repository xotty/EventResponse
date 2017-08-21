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
    public void setHandler(Handler handler) {
        this.handler = handler;
    }
        public MyScrollView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    str="MyScrollView---dispatchTouchEvent---DOWN";
                    break;
                case MotionEvent.ACTION_MOVE:
                    str="MyScrollView---dispatchTouchEvent---MOVE";
                    break;
                case MotionEvent.ACTION_UP:
                    str="MyScrollView---dispatchTouchEvent---UP";
                    break;
                case MotionEvent.ACTION_CANCEL:
                    str="MyScrollView---dispatchTouchEvent---CANCEL";
                    break;
                default:
                    str="MyScrollView---dispatchTouchEven---DEFAULT";
                    break;
            }
            System.out.println(str);
            Message msg=Message.obtain();
            msg.obj=str;
            handler.sendMessage(msg);
            return super.dispatchTouchEvent(event);
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    str="MyScrollView---onInterceptTouchEvent---DOWN";
                    break;
                case MotionEvent.ACTION_MOVE:
                    str="MyScrollView---onInterceptTouchEvent---MOVE";
                    break;
                case MotionEvent.ACTION_UP:
                    str="MyScrollView---onInterceptTouchEvent---UP";
                    break;
                case MotionEvent.ACTION_CANCEL:
                    str="MyScrollView---onInterceptTouchEvent---CANCEL";
                    break;
                default:
                    str="MyScrollView---onInterceptTouchEvent---DEFAULT";
                    break;
            }
            System.out.println(str);
            Message msg=Message.obtain();
            msg.obj=str;
            handler.sendMessage(msg);
            return super.onInterceptTouchEvent(event);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    str="MyScrollView---onTouchEvent---DOWN";
                    break;
                case MotionEvent.ACTION_MOVE:
                    str="MyScrollView---onTouchEvent---MOVE";
                    break;
                case MotionEvent.ACTION_UP:
                    str="MyScrollView---onTouchEvent---UP";
                    break;
                case MotionEvent.ACTION_CANCEL:
                    str="MyScrollView---onTouchEvent---CANCEL";
                    break;
                default:
                    str="MyScrollView---onTouchEvent---DEFAULT";
                    break;
            }
            System.out.println(str);
            Message msg=Message.obtain();
            msg.obj=str;
            handler.sendMessage(msg);
            return super.onTouchEvent(event);
        }
    }

