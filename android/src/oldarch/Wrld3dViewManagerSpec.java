package com.wrld3d;

import android.view.View;

import androidx.annotation.Nullable;

import com.facebook.react.uimanager.SimpleViewManager;

public abstract class Wrld3dViewManagerSpec<T extends View> extends SimpleViewManager<T> {
  public abstract void create(T view,String viewId);
}
