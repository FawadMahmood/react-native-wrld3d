package com.wrld3d;

import android.view.View;

import androidx.annotation.Nullable;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ViewManagerDelegate;
import com.facebook.react.viewmanagers.Wrld3dViewManagerDelegate;
import com.facebook.react.viewmanagers.Wrld3dViewManagerInterface;
import com.facebook.react.uimanager.ViewGroupManager;
import android.view.ViewGroup;



public abstract class Wrld3dViewManagerSpec<T extends ViewGroup> extends ViewGroupManager<T> implements Wrld3dViewManagerInterface<T> {
  private final ViewManagerDelegate<T> mDelegate;

  public Wrld3dViewManagerSpec() {
    mDelegate = new Wrld3dViewManagerDelegate(this);
  }

  @Nullable
  @Override
  protected ViewManagerDelegate<T> getDelegate() {
    return mDelegate;
  }
}
