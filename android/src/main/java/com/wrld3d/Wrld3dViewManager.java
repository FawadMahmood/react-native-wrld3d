package com.wrld3d;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerHelper;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.wrld3d.events.MapReadyEvent;

import java.util.Map;

@ReactModule(name = Wrld3dViewManager.NAME)
public class Wrld3dViewManager extends com.wrld3d.Wrld3dViewManagerSpec<Wrld3dView> {
  //*****************
  //***ALL COMMANDS CONSTANTS
  public final int COMMAND_CREATE = 1;
  public final int ANIMATE_TO_REGION = 2;
  public final int MOVE_TO_BUILDING=3;
  //*****************
  //***ALL COMMANDS CONSTANTS


  //*****************
  //***ALL USAGE VARIABLES
  private int viewId;


  //*****************
  //***ALL USAGE VARIABLES


  public static final String NAME = "Wrld3dView";

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public Wrld3dView createViewInstance(ThemedReactContext context) {
    return new Wrld3dView(context,this);
  }

  @Nullable
  @Override
  public Map<String, Integer> getCommandsMap() {
    return  MapBuilder.of("create", COMMAND_CREATE,"animateToRegion",ANIMATE_TO_REGION,"moveToBuilding",MOVE_TO_BUILDING);
  }

  @Override
  @Nullable
  public Map getExportedCustomDirectEventTypeConstants() {

    Map<String, Map<String, String>> map = MapBuilder.of(
      "onMapReady", MapBuilder.of("registrationName", "onMapReady"),
      "onMapCacheCompleted", MapBuilder.of("registrationName", "onMapCacheCompleted")
    );

    return map;
  }

  public void pushEvent(ThemedReactContext context, String name, WritableMap data,View parent) {
    EventDispatcher dispatcher =context.getNativeModule(UIManagerModule.class).getEventDispatcher(); //UIManagerHelper.getEventDispatcherForReactTag(context,this.viewId);
    Log.w("LLLL",context.getSurfaceId()+"");

    MapReadyEvent ready = new MapReadyEvent(this.viewId);
    dispatcher.dispatchEvent(ready);

//    context.getJSModule(RCTEventEmitter.class).receiveEvent(this.viewId, name, data);
  }

  @Override
  public void receiveCommand(@NonNull Wrld3dView root, String commandId, @Nullable ReadableArray args) {
    super.receiveCommand(root, commandId, args);
    Log.w("create command", args.toString() + "," +  commandId);
    switch (commandId) {
      case "create":
        createFragment(root, root.getId());
      break;
    }
  }

  private void createFragment(Wrld3dView view, int reactNativeViewId){
    this.viewId = view.getId();
    Log.d("RootID",this.viewId+"");
    view.createFragment(view.getId());
  }

  @Override
  public void create(Wrld3dView view,String viewId) {
    Log.w("create",view.getId()+"," + viewId);
  }

  @Override
  public void onDropViewInstance(@NonNull Wrld3dView view) {
    Log.d("REMOVED INSTANCE","onDropViewInstance");
    view.onDestroy();
    super.onDropViewInstance(view);

  }
}
