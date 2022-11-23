package com.wrld3d.events;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class MapReadyEvent extends Event<MapReadyEvent> {
    int viewId;
    WritableMap data;

    public static String EVENT_NAME="topOnMapReady";

    public MapReadyEvent(int viewId,WritableMap data){
        super(viewId);
        this.viewId = viewId;
        this.data= data;
    }

    public MapReadyEvent(int viewId){
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
}
