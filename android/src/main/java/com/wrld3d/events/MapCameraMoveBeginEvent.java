package com.wrld3d.events;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.uimanager.events.RCTModernEventEmitter;

public class MapCameraMoveBeginEvent extends Event<MapCameraMoveBeginEvent> {
    int viewId;
    WritableMap data;

    public static String EVENT_NAME= "topOnCameraMoveBegin";

    public MapCameraMoveBeginEvent(int viewId,WritableMap data){
        super(viewId);
        this.viewId = viewId;
        this.data= data;
    }

    public MapCameraMoveBeginEvent(int viewId){
        super(viewId);
        this.viewId = viewId;
    }


    @Override
    public String getEventName() {
        return EVENT_NAME;
    }

    @Override
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(this.viewId,this.getEventName(),this.data);
    }

    @Override
    public void dispatchModern(RCTModernEventEmitter rctEventEmitter) {
        super.dispatchModern(rctEventEmitter);
    }


}
