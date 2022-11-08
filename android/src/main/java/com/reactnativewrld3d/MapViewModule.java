package com.reactnativewrld3d;

import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.graphics.ColorUtils;

import com.eegeo.mapapi.EegeoMap;
import com.eegeo.mapapi.buildings.BuildingContour;
import com.eegeo.mapapi.buildings.BuildingDimensions;
import com.eegeo.mapapi.buildings.BuildingHighlight;
import com.eegeo.mapapi.buildings.BuildingHighlightOptions;
import com.eegeo.mapapi.buildings.BuildingInformation;
import com.eegeo.mapapi.buildings.OnBuildingInformationReceivedListener;
import com.eegeo.mapapi.camera.CameraPosition;
import com.eegeo.mapapi.camera.CameraUpdateFactory;
import com.eegeo.mapapi.geometry.ElevationMode;
import com.eegeo.mapapi.geometry.LatLng;
import com.eegeo.mapapi.markers.MarkerOptions;
import com.eegeo.mapapi.polylines.PolylineOptions;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.UIBlock;
import com.facebook.react.uimanager.UIManagerModule;

public class MapViewModule extends ReactContextBaseJavaModule {
    ReactApplicationContext reactContext;
    private BuildingHighlight m_highlight = null;



    public MapViewModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @NonNull
    @Override
    public String getName() {
        return "MapViewModule";
    }


    @ReactMethod
    public void getBuildingInformation(final int tag,double longitude,double latitude,boolean animateToBuilding,int duration,int zoomLevel,boolean highlight, Promise promise){
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

                    if (view.m_eegeoMap == null) {
                        promise.reject("EegeoMap.map is not valid");
                        return;
                    }


                    EegeoMap map = view.m_eegeoMap;

                    m_highlight = map.addBuildingHighlight(new BuildingHighlightOptions()
                            .highlightBuildingAtLocation(new LatLng(longitude, latitude))
                            .informationOnly()
                            .buildingInformationReceivedListener(new OnBuildingInformationReceivedListener() {
                                @Override
                                public void onBuildingInformationReceived(BuildingHighlight buildingHighlight) {
                                    BuildingInformation buildingInformation = buildingHighlight.getBuildingInformation();

                                    if (buildingInformation == null) {
                                        WritableMap event = Arguments.createMap();
                                        event.putBoolean("buildingAvailable", false);
                                        promise.resolve(event);
                                        return;
                                    }

                                    WritableMap event = Arguments.createMap();
                                    event.putString("buildingId", buildingInformation.buildingId);
                                    event.putBoolean("buildingAvailable", true);
                                    promise.resolve(event);
                                }
                            })

                    );


                    if(animateToBuilding){
                        CameraPosition position = new CameraPosition.Builder()
                                .target(latitude, longitude)
                                .zoom(zoomLevel)
                                .bearing(270)
                                .build();

                        map.animateCamera(CameraUpdateFactory.newCameraPosition(position), duration);
                    }

//                    promise.resolve(event);
                }catch (Exception e){
                    promise.reject(e);
                }
            }
        });
    }


    @ReactMethod
    public void addBuildingHighlight(final int tag,double longitude,double latitude, Promise promise){
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

                    if (view.m_eegeoMap == null) {
                        promise.reject("EegeoMap.map is not valid");
                        return;
                    }
                    EegeoMap map = view.m_eegeoMap;

                    map.addBuildingHighlight(new BuildingHighlightOptions()
                            .highlightBuildingAtLocation(new LatLng(latitude, longitude))
                            .color(ColorUtils.setAlphaComponent(Color.YELLOW, 128)));

                    WritableMap event = Arguments.createMap();
                    event.putBoolean("success", true);
                    promise.resolve(event);

                }catch (Exception e){
                    promise.reject(e);
                }
            }
        });
    }

    @ReactMethod
    public void getCameraBounds(final int tag, Promise promise){
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

                    if (view.m_eegeoMap == null) {
                        promise.reject("EegeoMap.map is not valid");
                        return;
                    }
                    EegeoMap map = view.m_eegeoMap;
                    final CameraPosition cameraPosition = map.getCameraPosition();
                    double latitude = (cameraPosition.target != null) ? cameraPosition.target.latitude : 0.0;
                    double longitude = (cameraPosition.target != null) ? cameraPosition.target.longitude : 0.0;
                    WritableMap event = Arguments.createMap();
                    WritableMap region = Arguments.createMap();
                    region.putDouble("latitude", latitude);
                    region.putDouble("longitude", longitude);
                    event.putMap("region",region);
                    event.putInt("zoom",(int)cameraPosition.zoom);
                    promise.resolve(event);
                }catch (Exception e){
                    promise.reject(e);
                }
            }
        });


    }

}
