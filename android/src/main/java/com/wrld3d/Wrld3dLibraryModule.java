package com.wrld3d;

import android.util.Log;

import androidx.annotation.NonNull;

import com.eegeo.mapapi.EegeoMap;
import com.eegeo.mapapi.buildings.BuildingDimensions;
import com.eegeo.mapapi.buildings.BuildingHighlight;
import com.eegeo.mapapi.buildings.BuildingHighlightOptions;
import com.eegeo.mapapi.buildings.BuildingInformation;
import com.eegeo.mapapi.buildings.OnBuildingInformationReceivedListener;
import com.eegeo.mapapi.geometry.LatLng;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.UIBlock;
import com.facebook.react.uimanager.UIManagerModule;
import com.wrld3d.events.MapOnClickBuilding;

public class Wrld3dLibraryModule extends com.wrld3d.Wrld3dLibrarySpec {
  ReactApplicationContext reactContext;
  private BuildingHighlight m_highlight = null;
  public static final String NAME = "Wrld3dLibrary";

  Wrld3dLibraryModule(ReactApplicationContext context) {
    super(context);
    this.reactContext = context;
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }


  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  @ReactMethod
  public void multiply(double a, double b, Promise promise) {
    promise.resolve(a * b);
  }

  @ReactMethod
  public void findBuildingOnCoordinates(final int tag,ReadableMap coordinates, Promise promise) {
    double latitude = coordinates.getDouble("latitude");
    double longitude = coordinates.getDouble("longitude");
    UIManagerModule uiManager = reactContext.getNativeModule(UIManagerModule.class);
    uiManager.addUIBlock(new UIBlock()
    {
      @Override
      public void execute(NativeViewHierarchyManager nvhm)
      {
        try{
          Wrld3dViewManager view = (Wrld3dViewManager) nvhm.resolveViewManager(tag);
          if (view == null) {
            promise.reject("EegeoMap not found");
            return;
          }
          if (view.mapChild == null || view.mapChild.wrldMapFragment.eegeoMap == null) {
            promise.reject("EegeoMap.map is not valid");
            return;
          }
          EegeoMap m_eegeoMap =view.mapChild.wrldMapFragment.eegeoMap;
          m_highlight = m_eegeoMap.addBuildingHighlight(new BuildingHighlightOptions()
                          .highlightBuildingAtLocation(new LatLng(latitude, longitude))
                          .informationOnly()
                          .buildingInformationReceivedListener(new OnBuildingInformationReceivedListener() {
                            @Override
                            public void onBuildingInformationReceived(BuildingHighlight buildingHighlight) {
                              BuildingInformation buildingInformation = buildingHighlight.getBuildingInformation();
                              if (buildingInformation == null) {
                                WritableMap event = Arguments.createMap();
                                event.putString("buildingId","");
                                event.putBoolean("buildingAvailable", false);
                                promise.resolve(event);
                                return;
                              }

                              BuildingDimensions buildingDimensions = buildingInformation.buildingDimensions;
                              double buildingHeight = buildingDimensions.topAltitude - buildingDimensions.baseAltitude;
                              String buildingId = buildingInformation.buildingId;
                              WritableMap buildingInfo = Arguments.createMap();
                              buildingInfo.putString("buildingId",buildingId);
                              buildingInfo.putDouble("buildingHeight",buildingHeight);
                              buildingInfo.putDouble("longitude",longitude);
                              buildingInfo.putDouble("latitude",latitude);
                              promise.resolve(buildingInfo);
                            }
                          })
          );

        }catch (Exception e){
          promise.reject(e);
        }
      }
    });
  }
}
