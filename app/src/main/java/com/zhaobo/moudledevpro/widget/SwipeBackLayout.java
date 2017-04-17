package com.zhaobo.moudledevpro.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.Loader;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import android.widget.Scroller;

import com.zhaobo.moudledevpro.R;
import com.zhaobo.utilslib.LogUtils;
import com.zhaobo.utilslib.ScreenUtils;


/**
 *
 */
public class SwipeBackLayout extends FrameLayout {
    private static final int INVALID_POINTER_ID = -1;
    private static final int MIN_DISTANCE_FOR_MOVE = 24;
    private static final String TAG = "SwipeBackLayout";

    private boolean isSwipeBackLayoutEnable = true;

    private VelocityTracker mVelocityTracker;
    private int mActivePointId;

    private boolean mIsSliding = false;
    private boolean mIsScrolling=false;
    private boolean mIsFinish = false;
    private int mAlphaBgColor;

    private float mLastPointX, mLastPointY;
    private float mDownX;

    private int mMoveDistance;
    private int distanceWidth;
    private Rect mColorRect = new Rect();

    private Scroller mScroller;

    private View mContentView;

    private int mContentViewWidth;

    private int maximumFlingVelocity, minimumFlingVelocity;

    private Activity mActivity;

    private float mCurrentX=0;


    public SwipeBackLayout(Context context) {
        this(context, null, 0);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        final float density = context.getResources().getDisplayMetrics().density;
        mMoveDistance = (int) (MIN_DISTANCE_FOR_MOVE * density);
        mScroller = new Scroller(context, new MyInterpolator(1.5f));
        int screenWidth = ScreenUtils.getScreenWidth(context);
        distanceWidth = screenWidth / 4;
        maximumFlingVelocity = ViewConfiguration.getMaximumFlingVelocity();
        minimumFlingVelocity = ViewConfiguration.getMinimumFlingVelocity();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mContentViewWidth = w;
    }

    public class MyInterpolator implements Interpolator {
        private float mFactor;

        public MyInterpolator(float mFactor) {
            this.mFactor = mFactor;
        }

        @Override
        public float getInterpolation(float v) {
            float result = v * mFactor;
            if (result > 0.9f) {
                return 1f;
            }
            return result;
        }
    }


    /**
     * 将activity加入到这个view中
     *
     * @param activity
     */
    public void attachActivity(Activity activity) {
        this.mActivity=activity;
        try {
            Window window = activity.getWindow();
            window.setBackgroundDrawable(new ColorDrawable(0));
            ViewGroup decorViewGroup = (ViewGroup) window.getDecorView();
            ViewGroup mRealContentView = (ViewGroup) decorViewGroup.getChildAt(0);

            decorViewGroup.removeView(mRealContentView);
            mRealContentView.setClickable(true);
            addView(mRealContentView);
            mContentView = (View) mRealContentView.getParent();
            decorViewGroup.addView(this);
        } catch (Exception e) {
            e.printStackTrace();
            isSwipeBackLayoutEnable=false;
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isSwipeBackLayoutEnable) {
            return super.onInterceptTouchEvent(ev);
        }

        if (mIsFinish || mIsScrolling) {
            return super.onInterceptTouchEvent(ev);
        }

        int action = ev.getAction() & MotionEventCompat.ACTION_MASK;
        if(action==MotionEvent.ACTION_CANCEL||action==MotionEvent.ACTION_UP){
            endDrag();
            return super.onInterceptTouchEvent(ev);
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                int index = MotionEventCompat.getActionIndex(ev);
//               ev.getPointerId(index);
                mActivePointId = MotionEventCompat.getPointerId(ev,index);
                if (isInvalidEvent(ev, mActivePointId, index)) {
                    break;
                }
                mLastPointX =  MotionEventCompat.getX(ev, index);
                mLastPointY =  MotionEventCompat.getY(ev, index);
                mDownX = MotionEventCompat.getX(ev, index);
                break;
            case MotionEvent.ACTION_MOVE:
                determineDrag(ev);
                break;


        }

        return mIsSliding;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIsFinish || mIsScrolling) {
            return super.onTouchEvent(event);
        }
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);

        switch (event.getAction() & MotionEventCompat.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                completeScroll();
                int index = MotionEventCompat.getActionIndex(event);
                mActivePointId = MotionEventCompat.getPointerId(event,index);
                if (isInvalidEvent(event, mActivePointId, index)) {
                    break;
                }
                mLastPointX = MotionEventCompat.getX(event,index);
                mLastPointY = MotionEventCompat.getY(event,index);
                mDownX = MotionEventCompat.getX(event,index);
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mIsSliding) {
                    determineDrag(event);
                }
                if (mIsSliding) {
                    int mIndex = getPointerIndex(event, mActivePointId);
                    if (isInvalidEvent(event, mActivePointId, mIndex)) {
                        break;
                    }
                    float dX = MotionEventCompat.getX(event,mIndex);

                    float disX = mLastPointX - dX;
                    mLastPointX = dX;
                    float oldScrollX = getScrollX();

                    float scrollX = oldScrollX + disX;
                    if (scrollX < -mContentViewWidth) {
                        scrollX = -mContentViewWidth;
                    }
                    if (scrollX > 0) {
                        scrollX = 0;
                    }
                    mCurrentX=scrollX;
                    mLastPointX += scrollX - (int) scrollX;
                    mContentView.scrollTo((int) scrollX, getScrollY());

                }
                break;
            case MotionEvent.ACTION_UP:
                VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, maximumFlingVelocity);
                float xVelocityTracker = mVelocityTracker.getXVelocity();

                float diffX = getDiffX(event);

                endDrag();

                if ((int) Math.abs(xVelocityTracker) > minimumFlingVelocity && diffX > distanceWidth) {
                    if(xVelocityTracker>0){
                        mIsFinish=true;
                        scrollToRight();
                    }else{
                        mIsFinish=false;
                        scrollToOriginal();
                    }
                }

                if (mContentView.getScrollX() <= -mContentViewWidth / 2) {
                    mIsFinish = true;
                    scrollToRight();
                } else {
                    mIsFinish = false;
                    scrollToOriginal();
                }


                break;
            case MotionEvent.ACTION_CANCEL:
                releaseVelocityTracker();
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        mColorRect.top=0;
        mColorRect.left=0;
        mColorRect.bottom=mContentView.getBottom();
        mColorRect.right=(int)mCurrentX;

        canvas.clipRect(mColorRect);
        if (mContentViewWidth != 0) {
            mAlphaBgColor = 100 - (int) (( (-mCurrentX) / (float) mContentViewWidth) * 120);
        }

        if (mAlphaBgColor > 100) {
            mAlphaBgColor = 100;
        }

        if (mIsFinish) {
            mAlphaBgColor = 0;
        }

        if (mAlphaBgColor < 0) {
            mAlphaBgColor = 0;
        }

        canvas.drawARGB(mAlphaBgColor, 0, 0, 0);


    }

    @Override
    public void computeScroll() {
        if(!mScroller.isFinished()&&mScroller.computeScrollOffset()){
           int oldX= getScrollX();
            int oldY=getScrollY();
            int curX=mScroller.getCurrX();
            int curY=mScroller.getCurrY();
            if(oldX!=curX||oldY!=curY){
                mContentView.scrollTo(curX,curY);
            }
            invalidate();
        }
        if(mScroller.isFinished()&&mIsFinish){
            mActivity.finish();
            mActivity.overridePendingTransition(0,0);
        }
        if(mScroller.isFinished()){
            completeScroll();
        }
        super.computeScroll();
    }

    private void completeScroll() {
        boolean scrolling=mIsScrolling;
        if(scrolling){
            mScroller.abortAnimation();
            int oldScrollX=getScrollX();
            int oldScrollY=getScrollY();
            int x=mScroller.getCurrX();
            int y=mScroller.getCurrY();
            if(oldScrollX!=x||oldScrollY!=y){
                mContentView.scrollTo(x,y);
            }
        }
        mIsScrolling=false;
    }

    private void scrollToOriginal() {
        mIsScrolling=true;
        int mScrollX=mContentView.getScrollX();
        mScroller.startScroll(mContentView.getScrollX(),0,-mScrollX,0);
        postInvalidate();
    }

    private void scrollToRight() {
        mIsScrolling=true;
        int mScrollX=mContentView.getScrollX()+mContentViewWidth;
        mScroller.startScroll(mContentView.getScrollX(),0,-mScrollX+1,0);
        postInvalidate();
    }

    private float getDiffX(MotionEvent event) {
        int index = getPointerIndex(event, mActivePointId);
        if (isInvalidEvent(event, mActivePointId, index)) {
            return 0f;
        }
        float mX = MotionEventCompat.getX(event,index);
        float diffX = Math.abs(mX - mDownX);
        return diffX;
    }

    private void determineDrag(MotionEvent ev) {
        int mMoveActionId=mActivePointId;
        int mIndex = getPointerIndex(ev, mMoveActionId);
        if (isInvalidEvent(ev, mMoveActionId, mIndex))
            return;
        float sX = MotionEventCompat.getX(ev, mIndex);
        float sY =MotionEventCompat.getY(ev, mIndex);

        float disX = sX - mLastPointX;
        float disY = sY - mLastPointY;

        float xDiff = Math.abs(disX);
        float yDiff = Math.abs(disY);

        if (disX > 0 && xDiff > mMoveDistance && xDiff > yDiff) {
            mIsSliding = true;
            mLastPointX = sX;
            mLastPointY = sY;
        }
    }

    private int getPointerIndex(MotionEvent ev, int mActivePointId) {
        int index = MotionEventCompat.findPointerIndex(ev, mActivePointId);
        if (index == -1) {
            this.mActivePointId = INVALID_POINTER_ID;
        }
        return index;
    }

    private boolean isInvalidEvent(MotionEvent ev, int mActivePointId, int index) {
        if (ev == null) {
            return true;
        }
        if (mActivePointId == INVALID_POINTER_ID || index == INVALID_POINTER_ID) {
            return true;
        }
        if (index >= ev.getPointerCount()) {
            return true;
        }
        return false;
    }

    private void endDrag() {
        mIsSliding = false;
        mActivePointId = INVALID_POINTER_ID;
        releaseVelocityTracker();
    }

    private void releaseVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    public void setSwipeBackLayoutEnable(boolean isEable) {
        this.isSwipeBackLayoutEnable = isEable;
    }
}

