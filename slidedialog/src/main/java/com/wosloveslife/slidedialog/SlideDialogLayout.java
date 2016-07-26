package com.wosloveslife.slidedialog;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by YesingBeijing on 2016/7/26.
 */
public class SlideDialogLayout extends FrameLayout {
    private static final String TAG = "SlideDialogLayout";

    private static final int SLIDE_SENSITIVITY = 200;

    private static final int STATE_PART = 0;
    private static final int STATE_COMP = 1;
    private static final int STATE_IDLE = 2;

    // view
    private ViewGroup mChildLayout;

    // controller
    private ViewDragHelper mDragHelper;
    private GestureDetector mGestureDetector;

    // variable
    /**  */
    float mVelocityY;
    int mTop;
    private int mStep1;
    private int mHeight;
    private Point mPartPoint = new Point();
    private int mCurrentState = STATE_PART;


    public SlideDialogLayout(Context context) {
        this(context, null);
    }

    public SlideDialogLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideDialogLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        initGesture();

        initDragHelper();
    }

    private void initGesture() {
        mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                mVelocityY = velocityY;
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.w(TAG, "onFinishInflate: ");

        mChildLayout = (ViewGroup) getChildAt(0);
    }

    private void initDragHelper() {
        mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {

            /* 如果第child是第一个子layout才触发滑动 */
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == mChildLayout;
            }

            /** 禁止x轴滑动 */
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return 0;
            }

            /** 规定y轴滑动区域 */
            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                final int topBound = getPaddingTop();
                final int bottomBound = getHeight() - topBound;

                return Math.min(Math.max(top, topBound), bottomBound);
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);

                Log.w(TAG, "onViewReleased: xvel = " + xvel + "; yvel = " + yvel);

                if (releasedChild == mChildLayout) {
                    /* 启用速度模拟,平滑移动到原始位置 */
                    if (mTop < mStep1) {
                        if (mVelocityY < -SLIDE_SENSITIVITY) {
                            mDragHelper.settleCapturedViewAt(mPartPoint.x, 0);
                            mCurrentState = STATE_COMP;
                        } else if (mVelocityY > SLIDE_SENSITIVITY) {
                            mDragHelper.settleCapturedViewAt(mPartPoint.x, mStep1);
                            mCurrentState = STATE_PART;
                        } else if (mTop < mHeight / 2) {
                            mDragHelper.settleCapturedViewAt(mPartPoint.x, 0);
                        } else {
                            mDragHelper.settleCapturedViewAt(mPartPoint.x, mStep1);
                        }
                    } else {
                        if (mVelocityY < -SLIDE_SENSITIVITY) {
                            mDragHelper.settleCapturedViewAt(mPartPoint.x, mStep1);
                        } else if (mVelocityY > SLIDE_SENSITIVITY) {
                            mDragHelper.settleCapturedViewAt(mPartPoint.x, mHeight - 10);
                        } else if (mTop < (mHeight - mStep1) / 2) {
                            mDragHelper.settleCapturedViewAt(mPartPoint.x, mStep1);
                        } else {
                            mDragHelper.settleCapturedViewAt(mPartPoint.x, mHeight - 10);
                        }
                    }
                    invalidate();
                }
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                Log.w(TAG, "onViewPositionChanged: left = " + left + "; top = " + top + "; dx = " + dx + "; dy = " + dy);

                if (changedView == mChildLayout) {
                    mTop = top;
                }
            }
        });
    }

    /** 将拦截判断交给ViewDragHelper处理 */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    /** 将触摸事件传递给ViewDragHelper以及GestureDetector */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);

        mDragHelper.processTouchEvent(event);

        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.w(TAG, "onMeasure: ");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mHeight = h;
        mStep1 = h - h / 3;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mPartPoint.x = mChildLayout.getLeft();
        mPartPoint.y = mChildLayout.getTop();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        if (mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }
}
