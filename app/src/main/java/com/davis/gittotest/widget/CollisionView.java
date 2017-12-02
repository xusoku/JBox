package com.davis.gittotest.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by xushengfu on 2017/11/23.
 */

public class CollisionView extends FrameLayout {
    private JboxImp jboxImpl;

    public CollisionView(Context context) {
        this(context, null);
    }

    public CollisionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollisionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);//????
        initView();
    }

    private void initView() {
        jboxImpl = new JboxImp(getResources().getDisplayMetrics().density);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        jboxImpl.setWorldSize(w, h);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        jboxImpl.createWorld();

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);

            if (!jboxImpl.isBodyView(view)) {
                jboxImpl.creatBody(view);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        jboxImpl.startWorld();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);

            if (jboxImpl.isBodyView(view)) {
                view.setX(jboxImpl.getViewX(view));
                view.setY(jboxImpl.getViewY(view));
                view.setRotation(jboxImpl.getViewRotaion(view));
            }
        }
        invalidate();

    }

    public void onSensorChanged(float x, float y) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (jboxImpl.isBodyView(view)) {
                jboxImpl.applyLinearImp(x, y, view);
            }
        }
    }
}
