package com.wrld3d;

import android.view.View;

import androidx.annotation.Nullable;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ViewManagerDelegate;
import com.facebook.react.viewmanagers.Wrld3dViewManagerDelegate;
import com.facebook.react.viewmanagers.Wrld3dViewManagerInterface;

public abstract class Wrld3dViewManagerSpec<T extends View> extends SimpleViewManager<T> implements Wrld3dViewManagerInterface<T> {
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
