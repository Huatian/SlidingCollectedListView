package com.huatian.slidingcollectedlistview.adapter;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class UnReUsedAdapter extends BaseAdapter {
	private SparseArray<SoftReference<View>> items;
	private ArrayList<?> dataSource;
	private OnGetViewListener mOnGetViewListener;
	
	public UnReUsedAdapter(ArrayList<?> data) {
		items = new SparseArray<SoftReference<View>>();
		this.dataSource = data;
	}
	
	public void clear(){
		if(dataSource != null && dataSource.size() > 0){
			dataSource.clear();
		}
		
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataSource.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return dataSource.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		mOnGetViewListener.onGetView(position, convertView, parent, items);
		return items.get(position).get();
	}
	
	public interface OnGetViewListener{
		public void onGetView(int position, View convertView, ViewGroup parent,SparseArray<SoftReference<View>> items);
	}
	
	public void setOnGetViewListener(OnGetViewListener listener){
		this.mOnGetViewListener = listener;
	}
}
