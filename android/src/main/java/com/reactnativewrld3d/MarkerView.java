package com.reactnativewrld3d;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.react.uimanager.ThemedReactContext;

public class MarkerView extends LinearLayout {
    public MarkerView(ThemedReactContext context) {
        super(context);

        this.setBackgroundColor(Color.parseColor("#5FD3F3"));
    }

}
