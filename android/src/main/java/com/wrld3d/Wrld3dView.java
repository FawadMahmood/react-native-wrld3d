package com.wrld3d;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class Wrld3dView extends FrameLayout {
  View parent;
  ThemedReactContext context;
  WrldMapFragment wrldMapFragment;
  Wrld3dViewManager manager;
  FragmentActivity activity;

  public Wrld3dView(ThemedReactContext context,Wrld3dViewManager manager) {
    super(context);
    this.context = context;
    Log.d("NEW INSTANCE","NEW INSTANCE");
    wrldMapFragment = new WrldMapFragment(this);
    this.manager = manager;
  }

  public Wrld3dView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public Wrld3dView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @UiThread
  public void createFragment(int reactNativeViewId){
    parent = (View) this.findViewById(reactNativeViewId);
    activity = (FragmentActivity) this.context.getCurrentActivity();
    activity.getSupportFragmentManager()
      .beginTransaction()
      .replace(reactNativeViewId, wrldMapFragment, String.valueOf(reactNativeViewId))
      .commit();
  }

  public void onDestroy(){
    activity.getSupportFragmentManager().beginTransaction().remove(wrldMapFragment).commit();
    this.manager = null;
    wrldMapFragment.onDestroy();
  }

  public void pushEvent(Event event) {
   this.manager.pushEvent(this.context,event,this);
  }



  @Override
  public void addView(View child) {
    if(wrldMapFragment.m_mapView != null){
      wrldMapFragment.m_mapView.addView(child);
    }else {
      Runnable runnable;
      Handler handler;

      runnable = new Runnable() {
        public void run() {
          addView(child);
        }
      };
      handler = new android.os.Handler();
      handler.postDelayed(runnable, 1000);
    }
  }

  public void setRegion(ReadableMap region) {
    wrldMapFragment.setInitialRegion(region);
  }

  public void setZoomLevel(int zoomLevel) {
    wrldMapFragment.setZoomLevel(zoomLevel);
  }

  public void setBuildingHighlight(String buildingId,String color, ReadableMap region) {
    wrldMapFragment.setBuildingHighlight(buildingId,color,region);
  }

  public void removeBuildingHighlight(String buildingId) {
    wrldMapFragment.removeBuildingHighlight(buildingId);
  }
}
