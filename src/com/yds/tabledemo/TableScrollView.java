package com.yds.tabledemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * 表格的ScrollView
 * 
 * @author yds
 * 
 */
public class TableScrollView extends HorizontalScrollView {

	private OnScrollChanged mOnScrollChanged;
	private OnTouchView mOnTouchView;

	public TableScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setCHScrollViewInfo();
	}

	public TableScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TableScrollView(Context context) {
		this(context, null);
	}

	private void setCHScrollViewInfo() {
		// 滚动条
		this.setVerticalScrollBarEnabled(false);
		// 滑动到边缘的蓝色阴影
		this.setFocusable(false);
		this.setFocusableInTouchMode(false);
		this.setHorizontalFadingEdgeEnabled(false);
		this.setOverScrollMode(View.OVER_SCROLL_NEVER);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// 进行触摸赋值
		if (mOnTouchView != null) {
			mOnTouchView.setOnTouchView(this);
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 获取点击触摸事件
	 */
	public void setOnTouchView(OnTouchView mOnTouchView) {
		this.mOnTouchView = mOnTouchView;
	}

	/**
	 * 获取滑动监听事件
	 */
	public void setOnScrollChanged(OnScrollChanged mOnScrollChanged) {
		this.mOnScrollChanged = mOnScrollChanged;
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// 当当前的TableScrollView被触摸时，滑动其它
		if (mOnScrollChanged != null) {
			mOnScrollChanged.setOnScrollChanged(l, t, oldl, oldt);
		}
	}

	public interface OnTouchView {
		public void setOnTouchView(HorizontalScrollView mTouchView);
	}

	public interface OnScrollChanged {
		public void setOnScrollChanged(int l, int t, int oldl, int oldt);
	}
}
