package com.sachi.lifetweak.fragment;

/**
 * Created by jombay on 29/3/16.
 */

import android.view.View;

public class VerticalDepthPageTransformer extends VerticalBaseTransformer {
    private static final float MIN_SCALE = 0.75f;

    @Override
    protected void onTransform(View view, float position) {
        if (position <= 0f) {
            view.setTranslationY(0f);
            view.setScaleX(1f);
            view.setScaleY(1f);
        } else if (position <= 1f) {
            final float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setAlpha(1 - position);
            view.setPivotX(0.5f * view.getWidth());
            view.setTranslationY(view.getHeight() * -position);
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
        }
    }

    @Override
    protected boolean isPagingEnabled() {
        return true;
    }
}

