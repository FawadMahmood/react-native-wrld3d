package com.wrld3d;


import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

@ReactModule(name = CallOutViewManager.NAME)
public class CallOutViewManager extends com.wrld3d.CallOutViewManagerSpec<CallOutView> {
    public static final String NAME = "Wrld3dCallOutView";


    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public CallOutView createViewInstance(ThemedReactContext context) {
        return new CallOutView(context,this);
    }

    @ReactProp(name = "region")
    public void setLocation(CallOutView view, ReadableMap region) {
        view.setLocation(region);
    }

    @ReactProp(name = "elevation")
    public void setElevation(CallOutView view, double elevation) {
        view.setElevationValue(elevation);
    }
}
