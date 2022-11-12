package com.wrld3d.events;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class MapReadyEvent extends Event<MapReadyEvent> {
    int viewId;

    public MapReadyEvent(int viewId){
        super(viewId);
        this.viewId = viewId;
    }

    @Override
    public String getEventName() {
        return "onMapReady";
    }

    @Override
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        WritableMap map = Arguments.createMap();
        map.putBoolean("ready",true);
        rctEventEmitter.receiveEvent(this.viewId,this.getEventName(),map);
    }
}
