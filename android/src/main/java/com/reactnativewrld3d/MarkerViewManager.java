package com.reactnativewrld3d;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.eegeo.mapapi.geometry.ElevationMode;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;

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


    @ReactProp(name = "elevationMode")
    public void setElevationMode(MarkerView view, String elevationMode) {
        view.setElevationMode(elevationMode == "HeightAboveGround" ? ElevationMode.HeightAboveGround : ElevationMode.HeightAboveSeaLevel);
    }


    @ReactProp(name = "elevation")
    public void setElevation(MarkerView view, double elevation) {
        view.setElevation(elevation);
    }

    @ReactPropGroup(names = {"width", "height"}, customType = "Style")
    public void setStyle(MarkerView view, int index, Integer value) {
        Log.d("STYLE", index == 0 ? "width " : "height " +  String.valueOf(value));

        if (index == 0) {
            view.setWidth(value);
//            propWidth = value;
        }

        if (index == 1) {
            view.setHeight(value);
//            propHeight = value;
        }
    }

    @Override
    public void updateExtraData(MarkerView root, Object extraData) {
//        super.updateExtraData(root, extraData);
         Log.d("EXTRA DATA", String.valueOf(extraData));
    }



}
