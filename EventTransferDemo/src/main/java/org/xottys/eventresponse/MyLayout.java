package org.xottys.eventresponse;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by changqing on 2017/8/17.
 */

public class MyLayout extends LinearLayout {
        private String str;
        private Handler handler;
    public void setHandler(Handler handler) {
        this.handler = handler;
    }
        public MyLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    str="MyLayout---dispatchTouchEvent---DOWN";
                    break;
                case MotionEvent.ACTION_MOVE:
                    str="MyLayout---dispatchTouchEvent---MOVE";
                    break;
                case MotionEvent.ACTION_UP:
                    str="MyLayout---dispatchTouchEvent---UP";
                    break;
                case MotionEvent.ACTION_CANCEL:
                    str="MyLayout---dispatchTouchEvent---CANCEL";
                    break;
                default:
                    str="MyLayout---dispatchTouchEvent---DEFAULT";
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
                    str="MyLayout---onInterceptTouchEvent---DOWN";
                    break;
                case MotionEvent.ACTION_MOVE:
                    str="MyLayout---onInterceptTouchEvent---MOVE";
                    break;
                case MotionEvent.ACTION_UP:
                    str="MyLayout---onInterceptTouchEvent---UP";
                    break;
                case MotionEvent.ACTION_CANCEL:
                    str="MyLayout---onInterceptTouchEvent---CANCEL";
                    break;
                default:
                    str="MyLayout---onInterceptTouchEvent---DEFAULT";
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
                    str="MyLayout---onTouchEvent---DOWN";
                    break;
                case MotionEvent.ACTION_MOVE:
                    str="MyLayout---onTouchEvent---MOVE";
                    break;
                case MotionEvent.ACTION_UP:
                    str="MyLayout---onTouchEvent---UP";
                    break;
                case MotionEvent.ACTION_CANCEL:
                    str="MyLayout---onTouchEvent---CANCEL";
                    break;
                default:
                    str="MyLayout---onTouchEvent---DEFAULT";
                    break;
            }
            System.out.println(str);
            Message msg=Message.obtain();
            msg.obj=str;
            handler.sendMessage(msg);
            return super.onTouchEvent(event);
        }
    }

