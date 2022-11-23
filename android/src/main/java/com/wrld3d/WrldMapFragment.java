package com.wrld3d;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;

import com.eegeo.mapapi.EegeoApi;
import com.eegeo.mapapi.EegeoMap;
import com.eegeo.mapapi.MapView;
import com.eegeo.mapapi.camera.CameraPosition;
import com.eegeo.mapapi.map.OnMapReadyCallback;
import com.eegeo.mapapi.positioner.OnPositionerChangedListener;
import com.eegeo.mapapi.positioner.Positioner;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.wrld3d.events.MapCameraMoveEvent;
import com.wrld3d.events.MapReadyEvent;

public class WrldMapFragment extends Fragment {
  MapView m_mapView;
  Wrld3dView parent;
  EegeoMap eegeoMap;

  public WrldMapFragment(Wrld3dView parent){
    this.parent = parent;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    Log.w("VIEW CREATED","onCreateView WrldMapFragment");
    super.onCreateView(inflater, container, savedInstanceState);

    String API_KEY = "YOUR_API_KEY";

    try {
      ApplicationInfo ai = this.getContext().getPackageManager().getApplicationInfo(this.getContext().getPackageName(), PackageManager.GET_META_DATA);
      Bundle bundle = ai.metaData;
      API_KEY = bundle.getString("com.wrld3d.API_KEY");
    } catch (Exception e) {
      Log.e("NO API KEY", "Dear developer. Don't forget to configure <meta-data android:name=\"my_test_metagadata\" android:value=\"testValue\"/> in your AndroidManifest.xml file.");
    }

    EegeoApi.init(this.getContext(), API_KEY);
    View view = inflater.inflate(R.layout.fragment_map_screen, container, false);
    m_mapView = (MapView)view.findViewById(R.id.mapView);
    m_mapView.getMapAsync(new OnMapReadyCallback() {
      @Override
      public void onMapReady(EegeoMap map) {
        eegeoMap = map;
        Log.w("MAOVIEW READY","MAPVIEW READY");
        emitMapReady();
        map.addOnCameraMoveListener(new OnScreenPointChangedListener());
      }
    });
    view.measure(View.MeasureSpec.makeMeasureSpec(container.getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(container.getHeight(), View.MeasureSpec.EXACTLY));
    view.layout(0,0,container.getWidth(),container.getHeight());
    return view;
  }

  private void emitMapReady(){
    WritableMap event = Arguments.createMap();
    event.putString("ready", "true");
    MapReadyEvent _ = new MapReadyEvent(parent.manager.viewId,event);
    parent.pushEvent(_,event);
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
//    m_mapView.onDestroy();
    // do any logic that should happen in an `onDestroy` method
    // e.g.: customView.onDestroy();
  }


  private class OnScreenPointChangedListener implements EegeoMap.OnCameraMoveListener {
    Runnable runnable;
    Handler handler;

    @UiThread
    public void onCameraMove() {
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
          MapCameraMoveEvent event = new MapCameraMoveEvent(parent.manager.viewId,data);
          parent.pushEvent(event,data);
        }
      };

      handler = new android.os.Handler();
      handler.postDelayed(runnable, 100);
    }
  }

}
