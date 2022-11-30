package com.wrld3d;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;

import com.eegeo.mapapi.EegeoApi;
import com.eegeo.mapapi.EegeoMap;
import com.eegeo.mapapi.MapView;
import com.eegeo.mapapi.buildings.BuildingDimensions;
import com.eegeo.mapapi.buildings.BuildingHighlight;
import com.eegeo.mapapi.buildings.BuildingHighlightOptions;
import com.eegeo.mapapi.buildings.BuildingInformation;
import com.eegeo.mapapi.buildings.OnBuildingInformationReceivedListener;
import com.eegeo.mapapi.camera.CameraPosition;
import com.eegeo.mapapi.camera.CameraUpdateFactory;
import com.eegeo.mapapi.geometry.LatLng;
import com.eegeo.mapapi.geometry.LatLngAlt;
import com.eegeo.mapapi.geometry.MapFeatureType;
import com.eegeo.mapapi.map.OnMapReadyCallback;
import com.eegeo.mapapi.picking.PickResult;
import com.eegeo.mapapi.positioner.OnPositionerChangedListener;
import com.eegeo.mapapi.positioner.Positioner;
import com.eegeo.mapapi.util.Ready;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.wrld3d.events.MapCameraMoveBeginEvent;
import com.wrld3d.events.MapCameraMoveEvent;
import com.wrld3d.events.MapOnClickBuilding;
import com.wrld3d.events.MapReadyEvent;

import java.util.Hashtable;

public class WrldMapFragment extends Fragment {
  MapView m_mapView;
  Wrld3dView parent;
  EegeoMap eegeoMap;
  private ReadableMap initailRegion;
  private int zoomLevel=10;
  private GestureDetectorCompat m_detector;
  public boolean isReady=false;
  private OnScreenPointChangedListener _screenPointListener;
  private OnMapClickListener _mapClickListener;


  public WrldMapFragment(Wrld3dView parent){
    this.parent = parent;
    WritableMap records = Arguments.createMap();
    records.putDouble("longitude",67.05802695237224);
    records.putDouble("latitude", 67.05802695237224);
    this.initailRegion = records;
  }


  private void registerMapKey(){
    String API_KEY = "YOUR_API_KEY";

    try {
      ApplicationInfo ai = this.getContext().getPackageManager().getApplicationInfo(this.getContext().getPackageName(), PackageManager.GET_META_DATA);
      Bundle bundle = ai.metaData;
      API_KEY = bundle.getString("com.wrld3d.API_KEY");
    } catch (Exception e) {
      Log.e("NO API KEY", "Dear developer. Don't forget to configure <meta-data android:name=\"my_test_metagadata\" android:value=\"testValue\"/> in your AndroidManifest.xml file.");
    }

    EegeoApi.init(this.getContext(), API_KEY);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    registerMapKey();

    View view = inflater.inflate(R.layout.fragment_map_screen, container, false);
    m_mapView = (MapView)view.findViewById(R.id.mapView);
    m_detector = new GestureDetectorCompat(this.parent.context, new TouchTapListener());

    m_mapView.getMapAsync(new OnMapReadyCallback() {
      @Override
      public void onMapReady(EegeoMap map) {
        eegeoMap = map;
        emitMapReady();
        _screenPointListener = new OnScreenPointChangedListener();
        _mapClickListener = new OnMapClickListener();
        map.addOnCameraMoveListener(_screenPointListener);
        map.addOnMapClickListener(_mapClickListener);
        isReady = true;
        if(initailRegion != null){
          double latitude = initailRegion.getDouble("latitude");
          double longitude = initailRegion.getDouble("longitude");
          moveToRegion(longitude,latitude,false,0);
        }
      }
    });

    m_mapView.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        m_detector.onTouchEvent(event);
        return false;
      }
    });

    view.measure(View.MeasureSpec.makeMeasureSpec(container.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(container.getHeight(), View.MeasureSpec.EXACTLY));
    view.layout(0,0,container.getWidth(),container.getHeight());
    return view;
  }

  private void emitMapReady(){
    WritableMap event = Arguments.createMap();
    event.putString("ready", "true");
    MapReadyEvent ev = new MapReadyEvent(parent.manager.viewId,event);
    parent.pushEvent(ev);
  }

  @Override
  public void onPause() {
    super.onPause();
    // do any logic that should happen in an `onPause` method
    // e.g.: customView.onPause();
  }

  @Override
  public void onResume() {
    super.onResume();
    // do any logic that should happen in an `onResume` method
    // e.g.: customView.onResume();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if(_screenPointListener != null) eegeoMap.removeOnCameraMoveListener(_screenPointListener);
    if(_mapClickListener != null) eegeoMap.removeOnMapClickListener(_mapClickListener);
  }

  private void moveToRegion(double longitude,double latitude,boolean animate,int duration){
    CameraPosition position = new CameraPosition.Builder()
            .target(latitude, longitude)
            .zoom(zoomLevel)
            .build();

    if(animate){
      eegeoMap.animateCamera(CameraUpdateFactory.newCameraPosition(position),duration);
    }else{
      eegeoMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));
    }
  }

  boolean begin=false;

  public void setInitialRegion(ReadableMap region) {
    double latitude = region.getDouble("latitude");
    double longitude = region.getDouble("longitude");
    this.initailRegion = region;
    if(eegeoMap != null){
      moveToRegion(longitude,latitude,true,2000);
    }
  }

  public void setZoomLevel(int zoomLevel) {
    if(eegeoMap != null){
      if(this.initailRegion != null){
        this.zoomLevel = zoomLevel;
        double latitude = this.initailRegion.getDouble("latitude");
        double longitude = this.initailRegion.getDouble("longitude");
        moveToRegion(longitude,latitude,true,2000);
      }
    }else{
      this.zoomLevel = zoomLevel;
    }
  }

  Hashtable<String, BuildingHighlight> highlights = new Hashtable<String, BuildingHighlight>();

  public void setBuildingHighlight(String buildingId,String color, ReadableMap region) {
    double latitude = region.getDouble("latitude");
    double longitude = region.getDouble("longitude");

    final BuildingHighlight highlight = eegeoMap.addBuildingHighlight(new BuildingHighlightOptions()
            .highlightBuildingAtLocation(new LatLng(latitude, longitude))
            .color(ColorUtils.setAlphaComponent(Color.parseColor(color), 128))
    );

    highlights.put(buildingId,highlight);
  }

  public void removeBuildingHighlight(String buildingId) {
      if(highlights.containsKey(buildingId)){
        final BuildingHighlight highlight = highlights.get(buildingId);
        eegeoMap.removeBuildingHighlight(highlight);
      }
  }


  private class TouchTapListener extends GestureDetector.SimpleOnGestureListener {
    private Handler m_timerHandler = new Handler();

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
      if (eegeoMap == null) {
        return false;
      }

      final Point screenPoint = new Point((int) e.getX(), (int) e.getY());
      eegeoMap.pickFeatureAtScreenPoint(screenPoint)
              .then(new Ready<PickResult>() {
                @UiThread
                @Override
                public void ready(PickResult pickResult) {
                  switch(pickResult.mapFeatureType){
                    case Building:
                      OnBuildingInformationListerner listener =  new OnBuildingInformationListerner(pickResult.intersectionPoint.longitude,pickResult.intersectionPoint.latitude);
                      final BuildingHighlight highlight =  eegeoMap.addBuildingHighlight(new BuildingHighlightOptions()
                              .highlightBuildingAtScreenPoint(screenPoint).informationOnly().buildingInformationReceivedListener(listener)
                      );
                    break;
                  }
                }
              });

      return false;
    }
  }

  private class OnBuildingInformationListerner implements  OnBuildingInformationReceivedListener{
    double longitude;
    double latitude;


    public OnBuildingInformationListerner(double longitude,double latitude){
        this.longitude =longitude;
        this.latitude = latitude;
    }

    @Override
    public void onBuildingInformationReceived(BuildingHighlight buildingHighlight) {
      eegeoMap.removeBuildingHighlight(buildingHighlight);
      BuildingInformation buildingInformation = buildingHighlight.getBuildingInformation();
      if (buildingHighlight == null) {
        return;
      }

      BuildingDimensions buildingDimensions = buildingInformation.buildingDimensions;
      double buildingHeight = buildingDimensions.topAltitude - buildingDimensions.baseAltitude;
      String buildingId = buildingInformation.buildingId;
      WritableMap buildingInfo = Arguments.createMap();
      buildingInfo.putString("buildingId",buildingId);
      buildingInfo.putDouble("buildingHeight",buildingHeight);
      buildingInfo.putDouble("longitude",this.longitude);
      buildingInfo.putDouble("latitude",this.latitude);
      MapOnClickBuilding ev = new MapOnClickBuilding(parent.manager.viewId,buildingInfo);
      parent.pushEvent(ev);

    }
  }
  private class OnMapClickListener implements EegeoMap.OnMapClickListener {
    @Override
    public void onMapClick(LatLngAlt point) {
//      final Point screenPoint = new Point((int) point., (int) event.getY());
//      eegeoMap.pickFeatureAtScreenPoint(point)
    }
  }

  private class OnScreenPointChangedListener implements EegeoMap.OnCameraMoveListener {
    Runnable runnable;
    Handler handler;

    @UiThread
    public void onCameraMove() {

      if(begin == false){
        begin = true;
        WritableMap data = Arguments.createMap();
        MapCameraMoveBeginEvent event = new MapCameraMoveBeginEvent(parent.manager.viewId,data);
        parent.pushEvent(event);
      }


      if(handler != null && runnable != null){
        handler.removeCallbacks(runnable);
      }

      runnable = new Runnable() {
        public void run() {
          final CameraPosition cameraPosition = eegeoMap.getCameraPosition();
          double latitude = (cameraPosition.target != null) ? cameraPosition.target.latitude : 0.0;
          double longitude = (cameraPosition.target != null) ? cameraPosition.target.longitude : 0.0;
          WritableMap data = Arguments.createMap();
          data.putDouble("longitude",longitude);
          data.putDouble("latitude",latitude);
          data.putDouble("zoomLevel",cameraPosition.zoom);

          MapCameraMoveEvent event = new MapCameraMoveEvent(parent.manager.viewId,data);
          parent.pushEvent(event);
          begin = false;
        }
      };

      handler = new android.os.Handler();
      handler.postDelayed(runnable, 100);
    }
  }

}
