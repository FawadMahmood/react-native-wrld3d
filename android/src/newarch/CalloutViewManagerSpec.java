package com.wrld3d;

import android.view.View;

import androidx.annotation.Nullable;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ViewManagerDelegate;
import com.facebook.react.viewmanagers.CallOutViewManagerDelegate;
import com.facebook.react.viewmanagers.CallOutViewManagerInterface;
import com.facebook.react.uimanager.ViewGroupManager;
import android.view.ViewGroup;



public abstract class CallOutViewManagerSpec<T extends ViewGroup> extends ViewGroupManager<T> implements CallOutViewManagerInterface<T> {
  private final ViewManagerDelegate<T> mDelegate;

  public CallOutViewManagerSpec() {
    mDelegate = new CallOutViewManagerDelegate(this);
  }

  @Nullable
  @Override
  protected ViewManagerDelegate<T> getDelegate() {
    return mDelegate;
  }
}
