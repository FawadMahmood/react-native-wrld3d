package com.reactnativewrld3d;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.eegeo.mapapi.EegeoApi;
import com.eegeo.mapapi.MapView;
import com.eegeo.mapapi.camera.CameraPosition;
import com.eegeo.mapapi.geometry.LatLng;
import com.eegeo.mapapi.map.EegeoMapOptions;
import com.facebook.react.uimanager.ThemedReactContext;

public class CustomView extends RelativeLayout {
  public CustomView(@NonNull ThemedReactContext context) {
    super(context);
    init(context);
  }

  public void init(@NonNull ThemedReactContext context) {
    EegeoMapOptions options = new EegeoMapOptions();
    options.camera(new CameraPosition(new LatLng(37.802355,-122.405848),10,10,10));
    MapView _mapview =  new MapView(this.getContext(),options);
    this.addView(_mapview);
//    inflate(context, R.layout.fragment_map_screen, this);
//    Log.i("Inflated XML", "MAP SCREEN OF RELATIVE LAYOUT");
  }



}