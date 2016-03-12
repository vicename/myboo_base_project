package com.myboo.wiwide.myboobase;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ScrollView;

/**
 * Created by Li DaChang on 16/3/12.
 * ---------------------
 */
public class OverScrollView extends ScrollView {
    private static final int MAX_Y_OVERSCROLL_DISTANCE = 300;
    private static final float SCROLL_RATIO = 0.5f;// 阻尼系数
    private boolean isOver;

    private Context mContext;
    private int mMaxYOverscrollDistance;

    public OverScrollView(Context context) {
        super(context);
        mContext = context;
        initBounceListView();
    }

    public OverScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initBounceListView();
    }

    public OverScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initBounceListView();
    }

    private void initBounceListView() {
        //get the density of the screen and do some maths with it on the max overscroll distance
        //variable so that you get similar behaviors no matter what the screen size

        final DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        final float density = metrics.density;

        mMaxYOverscrollDistance = (int) (density * MAX_Y_OVERSCROLL_DISTANCE);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        int newDeltaY = deltaY;
//        if (MAX_Y_OVERSCROLL_DISTANCE)
        float ratio;
        double ratio2;
        if (isOver) {
            int scrolled = scrollY;
            ratio = (float) (MAX_Y_OVERSCROLL_DISTANCE + scrolled) / MAX_Y_OVERSCROLL_DISTANCE;
            ratio2 = Math.sin((double) ratio);
            if (scrolled > 0) {
                ratio2=1.0;
            }
        } else {
            ratio2=1.0;
        }
//        if (scrollY > 0) {
//            ratio2 = 0.5f;
//        }
        newDeltaY = (int) (deltaY * ratio2);
//        if (delta != 0) newDeltaY = delta;
        Log.i("---", "scroll:" + scrollY + ",delta:" + deltaY + ",ratio:" + ratio2+",scrollRangeY:"+scrollRangeY);
        //This is where the magic happens, we have replaced the incoming maxOverScrollY with our own custom variable mMaxYOverscrollDistance;
        return super.overScrollBy(deltaX, newDeltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMaxYOverscrollDistance, isTouchEvent);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        isOver = true;
    }
}
