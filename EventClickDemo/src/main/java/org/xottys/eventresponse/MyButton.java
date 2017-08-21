/** 本例以Button为例演示了回调响应事件的方式
 *该方式就是用继承系统控件的方式自定义UI控件，然后重写其中的下列方法来处理事件
 * 1）onTouchEvent(MotionEvent event)
 * 2）onKeyDown(int keyCode,KeyEvent event)
 * 3）onKeyUp(int keyCode,KeyEvent event)
 * 4）onKeyLongPress(int keyCode,KeyEvent event)
 * 5）onKeyShortcut(int keyCode,KeyEvent event)
 * 6）onTrackballEvent(MotionEvent event)
 * 7）onFocusChanged(boolean gainFocus, int direction, Rect previously FocusedRect)--仅在view中有效
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

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;

public class MyButton extends Button{
    private TextView tv;

    public void setTv(TextView tv) {
        this.tv = tv;
    }

    public MyButton(Context context, AttributeSet set)
	{
		super(context, set);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		super.onKeyDown(keyCode, event);
        tv.append("the onKeyDown in MyButton\n");
		Log.v("-MyButton-", "the onKeyDown in MyButton");
		//返回true，表明该事件不会向外扩散
		return false;
	}
}
