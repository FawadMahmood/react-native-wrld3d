package com.wrld3d;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ViewGroupManager;

public abstract class Wrld3dViewManagerSpec<T extends ViewGroup> extends ViewGroupManager<T> {
  public abstract void create(T view,String viewId);
  public abstract void setBuildingHighlight(T view, String buildingId, String color, ReadableMap buildingCoordinates);
  public abstract void removeBuildingHighlight(T view, String buildingId);
}
