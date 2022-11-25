package com.wrld3d;

import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.eegeo.mapapi.camera.CameraPosition;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerHelper;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.wrld3d.events.MapCameraMoveBeginEvent;
import com.wrld3d.events.MapCameraMoveEvent;
import com.wrld3d.events.MapOnClickBuilding;
import com.wrld3d.events.MapReadyEvent;

import java.util.Map;

@ReactModule(name = Wrld3dViewManager.NAME)
public class Wrld3dViewManager extends com.wrld3d.Wrld3dViewManagerSpec<Wrld3dView> {
  Wrld3dView mapChild;

  //*****************
  //***ALL COMMANDS CONSTANTS
  public final int COMMAND_CREATE = 1;
  public final int ANIMATE_TO_REGION = 2;
  public final int MOVE_TO_BUILDING=3;
  public final int HIGHLIGHT_BUILDING=4;
  public final int REMOVE_BUILDING_HIGHLIGHT=5;
  //*****************
  //***ALL COMMANDS CONSTANTS


  //*****************
  //***ALL USAGE VARIABLES
  public int viewId;


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

  @ReactProp(name = "initialRegion")
  public void setLocation(Wrld3dView view, ReadableMap region) {
      view.setRegion(region);
  }

  @ReactProp(name = "zoomLevel", defaultFloat = 10)
  public void setLocation(Wrld3dView view, int zoomLevel) {
    view.setZoomLevel(zoomLevel);
  }

  @Nullable
  @Override
  public Map<String, Integer> getCommandsMap() {
    return  MapBuilder.of(
            "create", COMMAND_CREATE,
            "animateToRegion",ANIMATE_TO_REGION,
            "moveToBuilding",MOVE_TO_BUILDING,
            "setBuildingHighlight",HIGHLIGHT_BUILDING,
            "removeBuildingHighlight",REMOVE_BUILDING_HIGHLIGHT
    );
  }

  @Override
  @Nullable
  public Map getExportedCustomDirectEventTypeConstants() {
    Map<String, Map<String, String>> map = MapBuilder.of(
       MapReadyEvent.EVENT_NAME, MapBuilder.of("registrationName", "onMapReady"),
      "onMapCacheCompleted", MapBuilder.of("registrationName", "onMapCacheCompleted"),
       MapCameraMoveEvent.EVENT_NAME, MapBuilder.of("registrationName", "onCameraMoveEnd"),
       MapCameraMoveBeginEvent.EVENT_NAME, MapBuilder.of("registrationName", "onCameraMoveBegin"),
       MapOnClickBuilding.EVENT_NAME, MapBuilder.of("registrationName", "onClickBuilding")
    );

    return map;
  }

  public void pushEvent(ThemedReactContext context, Event event, View parent) {
    EventDispatcher dispatcher = UIManagerHelper.getEventDispatcherForReactTag(context,this.viewId);
    dispatcher.dispatchEvent(event);
  }

  @Override
  public void receiveCommand(@NonNull Wrld3dView root, String commandId, @Nullable ReadableArray args) {
    int duration;
    double lat;
    double lng;
    double lngDelta;
    double latDelta;
    ReadableMap region;
    ReadableMap camera;
    String buildingId;
    String color;




    super.receiveCommand(root, commandId, args);
    Log.d("create command", args.toString() + "," +  commandId);
    switch (commandId) {
      case "create":
        createFragment(root, root.getId());
      break;
      case "setBuildingHighlight":
        buildingId = args.getString(0);
        color = args.getString(1);
        Log.d("color",color);
        region = args.getMap(2);
        root.setBuildingHighlight(buildingId,color,region);
      break;
      case "removeBuildingHighlight":
        buildingId = args.getString(0);
        root.removeBuildingHighlight(buildingId);
      break;
    }
  }

  private void createFragment(Wrld3dView view, int reactNativeViewId){
    this.viewId = view.getId();
    view.createFragment(view.getId());
    mapChild = view;
  }

  @Override
  public void create(Wrld3dView view,String viewId) {
    Log.w("create",view.getId()+"," + viewId);
  }

  @Override
  public void setBuildingHighlight(Wrld3dView view, String buildingId, String color, ReadableMap buildingCoordinates) {
    Log.w("setBuildingHighlight",view.getId()+"," + viewId);
  }

  @Override
  public void removeBuildingHighlight(Wrld3dView view, String buildingId) {
    Log.w("removeBuildingHighlight",view.getId()+"," + viewId);
  }

  @Override
  public void onDropViewInstance(@NonNull Wrld3dView view) {
    Log.d("REMOVED INSTANCE","onDropViewInstance");
    view.onDestroy();
    super.onDropViewInstance(view);
  }

  @Override
  public void addView(Wrld3dView view, View child, int index) {
    view.addView(child);
  }
}
