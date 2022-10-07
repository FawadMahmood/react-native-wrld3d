package com.reactnativewrld3d;

import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

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

    @ReactProp(name = "location")
    public void setLocation(MarkerView view, ReadableMap region) {
        view.setLocation(region);
    }


    @Override
    public void updateExtraData(MarkerView root, Object extraData) {
//        super.updateExtraData(root, extraData);
         Log.d("EXTRA DATA", String.valueOf(extraData));
    }
}
