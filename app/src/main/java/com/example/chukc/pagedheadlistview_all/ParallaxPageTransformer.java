package com.example.chukc.pagedheadlistview_all;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 * Created by chukc on 2016/5/13.
 */
public class ParallaxPageTransformer implements ViewPager.PageTransformer {

    private int id;
    private int border = 0;
    private float speed = 0.2f;

    public ParallaxPageTransformer(int id) {
        this.id = id;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void transformPage(View view, float position) {

        View parallaxView = view.findViewById(id);

        if (view == null ) {
            Log.w("ParallaxPager", "There is no view");
        }

        if (parallaxView != null && Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB ) {
            if (position > -1 && position < 1) {
                float width = parallaxView.getWidth();
                parallaxView.setTranslationX(-(position * width * speed));
                float sc = ((float)view.getWidth() - border)/ view.getWidth();
                if (position == 0) {
                    view.setScaleX(1);
                    view.setScaleY(1);
                } else {
                    view.setScaleX(sc);
                    view.setScaleY(sc);
                }
            }
        }
    }

    public void setBorder(int px) {
        border = px;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
