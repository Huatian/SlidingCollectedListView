package com.huatian.slidingcollectedlistview.view;

import com.huatian.slidingcollectedlistview.R;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class SlideCollectListView extends ListView implements GestureDetector.OnGestureListener{

	private int mFlingHitPos = -1;
	private int[] mTempLoc = new int[2];
	
	private View item;
	public boolean isScrolled = false;
	public GestureDetector mDetector;
	
	public SlideCollectListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mDetector = new GestureDetector(getContext(), this);
	}
	
	private void setShowAnim(View view) {
		//TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 1.0f);
		//mShowAction.setDuration(500);
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.collect_ensure);
		view.startAnimation(animation);
		Message msg = new Message();
		msg.what = 0;
		msg.obj = view;
		mHandler.sendMessageDelayed(msg, 1000);
	}

	private void setHiddeanAnim(View view) {
		Animation mHiddenAction = AnimationUtils.loadAnimation(getContext(), R.anim.collect_cancle);
		view.startAnimation(mHiddenAction);
		
		Message msg = new Message();
		msg.what = 1;
		msg.obj = view;
		mHandler.sendMessageDelayed(msg, 1000);
	}
	
	public void onScrollStopped(){
		TextView cover = (TextView) item.findViewById(R.id.cover);
		if(cover.getVisibility() != View.VISIBLE){
			setHiddeanAnim(cover);
		}else{
			setShowAnim(cover);
		}
	}
	
	private CoverHandler mHandler = new CoverHandler();
	
	public class CoverHandler extends Handler{

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				if(msg.obj != null){
					((TextView)msg.obj).setVisibility(View.GONE);
				}
				break;
			case 1:
				if(msg.obj != null){
					((TextView)msg.obj).setVisibility(View.VISIBLE);
				}
				break;
			default:
				break;
			}
			
		}
	}
	
	@Override
	public boolean onDown(MotionEvent e) {
		mFlingHitPos = startFlingPosition(e);
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		final int x1 = (int) e1.getX();
		final int y1 = (int) e1.getY();
		final int x2 = (int) e2.getX();
		final int y2 = (int) e2.getY();
		if (mFlingHitPos != -1) {
			if ((x1 - x2) > 0 && Math.abs(x2 - x1) > 1 && Math.abs(y2 - y1) < 10) {
				Log.i("SCLV", "onFlding");
				isScrolled = true;
				return true;
			}	
		}
		
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public int startFlingPosition(MotionEvent ev) {
		return viewIdHitPosition(ev);
	}

	public int viewIdHitPosition(MotionEvent ev) {
		Log.i("TestListView", "viewIdHitPosition");
		final int x = (int) ev.getX();
		final int y = (int) ev.getY();

		int touchPos = this.pointToPosition(x, y); // includes headers/footers
		final int numHeaders = this.getHeaderViewsCount();
		final int numFooters = this.getFooterViewsCount();
		final int count = this.getCount();

		if (touchPos != AdapterView.INVALID_POSITION && touchPos >= numHeaders && touchPos < (count - numFooters)) {
			item  = this.getChildAt(touchPos - this.getFirstVisiblePosition());
			final int rawX = (int) ev.getRawX();
			final int rawY = (int) ev.getRawY();

			if (item != null) {
				item.getLocationOnScreen(mTempLoc);

				if (rawX > mTempLoc[0] && rawY > mTempLoc[1] && rawX < mTempLoc[0] + item.getWidth() && rawY < mTempLoc[1] + item.getHeight()) {
					Log.i("TestListView", "position:"+touchPos);
					return touchPos;
				}
			}
		}

		return -1;
	}
	
}
