package com.ydh.botanic.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class CustomSwapToRefresh extends SwipeRefreshLayout {
    private int mTouchSlop;
    private float mPrevX;

    public CustomSwapToRefresh(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = MotionEvent.obtain(ev).getX();
                break;

            case MotionEvent.ACTION_MOVE:
                final float eventX = ev.getX();
                float xDiff = Math.abs(eventX - mPrevX);

                if (xDiff > mTouchSlop) {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }
}
