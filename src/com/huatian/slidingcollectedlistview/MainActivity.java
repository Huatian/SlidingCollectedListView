package com.huatian.slidingcollectedlistview;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collections;
import com.huatian.slidingcollectedlistview.adapter.UnReUsedAdapter;
import com.huatian.slidingcollectedlistview.adapter.UnReUsedAdapter.OnGetViewListener;
import com.huatian.slidingcollectedlistview.view.SlideCollectListView;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private SlideCollectListView mListView;
	private String[] strs = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mListView = (SlideCollectListView) findViewById(R.id.list);
		ArrayList<String> tempList = new ArrayList<String>();
		Collections.addAll(tempList, strs);
		final UnReUsedAdapter adapter = new UnReUsedAdapter(tempList);
		adapter.setOnGetViewListener(new OnGetViewListener() {
			
			@Override
			public void onGetView(int position, View convertView, ViewGroup parent, SparseArray<SoftReference<View>> items) {
				if(items.get(position) == null){
					LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
					View view = inflater.inflate(R.layout.item_list, null);
					
					TextView index = (TextView) view.findViewById(R.id.text);
					//TextView cover = (TextView) view.findViewById(R.id.cover);
					
					index.setText((String) adapter.getItem(position));
					items.put(position, new SoftReference<View>(view));
				}
			}
		});
		
		mListView.setAdapter(adapter);
		
		mListView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				boolean b = true;
				b = mListView.mDetector.onTouchEvent(event);
				Log.i("MainActivity", "onTouchEvent:"+event.getAction()+";isScrolled:"+b+","+mListView.isScrolled);
				//mList.onRefreshTouchEvent(event);
				if(b && mListView.isScrolled){
					mListView.onScrollStopped();
					b = true;
				}
				
				return b;
			}
		});
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent it = new Intent(MainActivity.this, EmptyActivity.class);
				startActivity(it);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
