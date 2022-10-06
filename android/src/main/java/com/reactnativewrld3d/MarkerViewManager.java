package com.reactnativewrld3d;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;

public class MarkerViewManager extends ViewGroupManager<MarkerView> {
    public static final String REACT_CLASS = "MarkerView";

    ReactApplicationContext reactContext;

    public MarkerViewManager(ReactApplicationContext reactContext) {
        this.reactContext = reactContext;
    }

    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    protected MarkerView createViewInstance(@NonNull ThemedReactContext reactContext) {
        return new MarkerView(reactContext);
    }
}
