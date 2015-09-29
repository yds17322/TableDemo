package com.yds.tabledemo;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TextView;

import com.yds.tabledemo.TableScrollView.OnScrollChanged;
import com.yds.tabledemo.TableScrollView.OnTouchView;

public class TableAdapter extends BaseAdapter {
	private Context mContext;
	private List<TableBean> mDataList;
	private List<TableScrollView> mTableViewList;
	private HorizontalScrollView mTouchView;
	private ListView item_lv;
	private TableScrollView mTableScrollView;

	public TableAdapter(Context mContext, List<TableBean> mDataList, List<TableScrollView> mTableViewList,
			HorizontalScrollView mTouchView, ListView item_lv) {
		this.mContext = mContext;
		this.mDataList = mDataList;
		this.mTableViewList = mTableViewList;
		this.mTouchView = mTouchView;
		this.item_lv = item_lv;
	}

	@Override
	public int getCount() {
		return mDataList.size();
	}

	@Override
	public Object getItem(int position) {
		return mDataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.adapter_table, null);
			holder = new ViewHolder();

			mTableScrollView = addHViews((TableScrollView) convertView.findViewById(R.id.table_tob_sv));
			holder.top_0 = (TextView) convertView.findViewById(R.id.top_0);
			holder.top_1 = (TextView) convertView.findViewById(R.id.top_1);
			holder.top_2 = (TextView) convertView.findViewById(R.id.top_2);
			holder.top_3 = (TextView) convertView.findViewById(R.id.top_3);
			holder.top_4 = (TextView) convertView.findViewById(R.id.top_4);
			holder.top_5 = (TextView) convertView.findViewById(R.id.top_5);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		TableBean tableBean = mDataList.get(position);
		holder.top_0.setText(tableBean.getName());
		holder.top_1.setText(tableBean.getA());
		holder.top_2.setText(tableBean.getB());
		holder.top_3.setText(tableBean.getC());
		holder.top_4.setText(tableBean.getD());
		holder.top_5.setText(tableBean.getE());

		// 当子元素滚动的时候 响应父元素
		mTableScrollView.setOnTouchView(new OnTouchView() {

			@Override
			public void setOnTouchView(HorizontalScrollView touchView) {
				mTouchView = touchView;
			}
		});

		mTableScrollView.setOnScrollChanged(new OnScrollChanged() {

			@Override
			public void setOnScrollChanged(int l, int t, int oldl, int oldt) {
				for (TableScrollView scrollView : mTableViewList) {
					// 防止重复滑动
					if (mTouchView != scrollView)
						scrollView.smoothScrollTo(l, t);
				}
			}
		});

		return convertView;
	}

	public TableScrollView addHViews(final TableScrollView hScrollView) {
		if (!mTableViewList.isEmpty()) {
			int size = mTableViewList.size();
			TableScrollView scrollView = mTableViewList.get(size - 1);
			final int scrollX = scrollView.getScrollX();
			// 第一次满屏后，向下滑动，有一条数据在开始时未加入
			if (scrollX != 0) {
				item_lv.post(new Runnable() {
					@Override
					public void run() {
						// 当listView刷新完成之后，把该条移动到最终位置
						hScrollView.scrollTo(scrollX, 0);
					}
				});
			}
		}
		mTableViewList.add(hScrollView);
		return hScrollView;
	}

	class ViewHolder {
		TextView top_0;
		TextView top_1;
		TextView top_2;
		TextView top_3;
		TextView top_4;
		TextView top_5;
	}
}
