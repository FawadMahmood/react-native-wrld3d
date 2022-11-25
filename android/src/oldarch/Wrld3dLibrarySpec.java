package com.wrld3d;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableMap;

abstract class Wrld3dLibrarySpec extends ReactContextBaseJavaModule {
  Wrld3dLibrarySpec(ReactApplicationContext context) {
    super(context);
  }

  public abstract void multiply(double a, double b, Promise promise);

  public abstract void findBuildingOnCoordinates(final int tag,ReadableMap coordinates, Promise promise);
}
