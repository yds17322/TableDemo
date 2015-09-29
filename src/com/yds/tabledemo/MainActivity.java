package com.yds.tabledemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.ListView;

import com.yds.tabledemo.TableScrollView.OnScrollChanged;
import com.yds.tabledemo.TableScrollView.OnTouchView;

/**
 * 这个触摸回调有点小问题，最近便忙，之后再改 TODO
 * 
 * @author yds
 * 
 */
public class MainActivity extends Activity {

	private TableScrollView table_tob_sv;
	private ListView item_lv;
	private HorizontalScrollView mTouchView;

	// 装入所有的HScrollView
	private List<TableScrollView> mTableViewList = new ArrayList<TableScrollView>();
	// 模拟数据，表头有6列
	private List<TableBean> mDataList = new ArrayList<TableBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		item_lv = (ListView) findViewById(R.id.item_lv);
		table_tob_sv = (TableScrollView) findViewById(R.id.table_tob_sv);

		mTableViewList.add(table_tob_sv);

		for (int i = 0; i < 20; i++) {
			TableBean bean = new TableBean();
			bean.setName("名称_" + i);
			bean.setA("A_" + i);
			bean.setB("B_" + i);
			bean.setC("C_" + i);
			bean.setD("D_" + i);
			bean.setE("E_" + i);
			mDataList.add(bean);
		}

		setListener();

		TableAdapter adapter = new TableAdapter(this, mDataList, mTableViewList, mTouchView, item_lv);
		item_lv.setAdapter(adapter);
	}

	private void setListener() {

		// 当父元素滚动的时候 响应子元素
		table_tob_sv.setOnTouchView(new OnTouchView() {

			@Override
			public void setOnTouchView(HorizontalScrollView touchView) {
				mTouchView = touchView;
			}
		});

		table_tob_sv.setOnScrollChanged(new OnScrollChanged() {

			@Override
			public void setOnScrollChanged(int l, int t, int oldl, int oldt) {
				for (TableScrollView scrollView : mTableViewList) {
					// 防止重复滑动
					if (mTouchView != scrollView)
						scrollView.smoothScrollTo(l, t);
				}
			}
		});
	}
}
