// replace with your package
package com.reactnativewrld3d;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Looper;
import android.view.Choreographer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.FragmentActivity;

import com.eegeo.mapapi.EegeoMap;
import com.eegeo.mapapi.MapView;
import com.eegeo.mapapi.geometry.LatLng;
import com.eegeo.mapapi.map.OnMapReadyCallback;
import com.eegeo.mapapi.positioner.OnPositionerChangedListener;
import com.eegeo.mapapi.positioner.Positioner;
import com.eegeo.mapapi.positioner.PositionerOptions;
import com.eegeo.ui.util.ViewAnchor;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.ThemedReactContext;

import java.util.List;
import java.util.Map;

public class Wrld3dViewManager extends ViewGroupManager<FrameLayout> {

  public static final String REACT_CLASS = "Wrld3dView";
  public final int COMMAND_CREATE = 1;
  private int propWidth;
  private int propHeight;
  private EegeoMap m_eegeoMap = null;


  ReactApplicationContext reactContext;

  public Wrld3dViewManager(ReactApplicationContext reactContext) {
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  /**
   * Return a FrameLayout which will later hold the Fragment
   */
  @Override
  public FrameLayout createViewInstance(ThemedReactContext reactContext) {
    return new FrameLayout(reactContext);
  }

  /**
   * Map the "create" command to an integer
   */
  @Nullable
  @Override
  public Map<String, Integer> getCommandsMap() {
    return MapBuilder.of("create", COMMAND_CREATE);
  }

  /**
   * Handle "create" command (called from JS) and call createFragment method
   */
  @Override
  public void receiveCommand(
    @NonNull FrameLayout root,
    String commandId,
    @Nullable ReadableArray args
  ) {
    super.receiveCommand(root, commandId, args);
    int commandIdInt = Integer.parseInt(commandId);

    switch (commandIdInt) {
      case COMMAND_CREATE:
        int reactNativeViewId = args.getInt(0);
        createFragment(root, reactNativeViewId);
        break;
      default: {}
    }
  }

//  @Override
//  public void addViews(FrameLayout parent, List<View> views) {
////    super.addViews(parent, views);
//

//  }


  View addedView;

  @Override
  public void addView(FrameLayout parent, View child, int index) {
//    super.addView(parent, child, index);

//    parent.addView(child);
    addedView= child;

    Toast.makeText(parent.getContext(),(String)"added some views?",
            Toast.LENGTH_SHORT).show();
  }




  WrldMapFragment wrldMapFragment;


  @Override
  public void removeView(FrameLayout parent, View view) {
    Toast.makeText(parent.getContext(),(String)"removed some views?",
            Toast.LENGTH_SHORT).show();
//    wrldMapFragment.m_mapView.removeView(view);
  }

  @ReactPropGroup(names = {"width", "height"}, customType = "Style")
  public void setStyle(FrameLayout view, int index, Integer value) {
    if (index == 0) {
      propWidth = value;
    }

    if (index == 1) {
      propHeight = value;
    }
  }

  private OnPositionerChangedListener m_positionerChangedListener = null;


  /**
   * Replace your React Native view with a custom fragment
   */
  public void createFragment(FrameLayout root, int reactNativeViewId) {
    ViewGroup parentView = (ViewGroup) root.findViewById(reactNativeViewId);
    setupLayout(parentView);

    wrldMapFragment = new WrldMapFragment();
    FragmentActivity activity = (FragmentActivity) reactContext.getCurrentActivity();
    activity.getSupportFragmentManager()
            .beginTransaction()
            .replace(reactNativeViewId, wrldMapFragment, String.valueOf(reactNativeViewId))
            .commit();

    new android.os.Handler(Looper.getMainLooper()).postDelayed(
            new Runnable() {
              public void run() {
                if(wrldMapFragment.isAdded() && wrldMapFragment.m_mapView != null){
                  Toast.makeText(wrldMapFragment.getContext(),(String)"fragment added and map added bro?",
                          Toast.LENGTH_SHORT).show();

                  wrldMapFragment.m_mapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(EegeoMap map) {
                      m_eegeoMap = map;

                      if(addedView != null){
                        Toast.makeText(wrldMapFragment.getContext(),(String)"on Map is ready?",
                                Toast.LENGTH_SHORT).show();


                        addedView.setLayoutParams(new ViewGroup.LayoutParams(addedView.getWidth(), addedView.getHeight()));

                        wrldMapFragment.m_mapView.addView(addedView);
                        m_positionerChangedListener = new ViewAnchorAdapter(addedView, 0.5f, 0.5f);
                        m_eegeoMap.addPositionerChangedListener(m_positionerChangedListener);

                        m_eegeoMap.addPositioner(new PositionerOptions()
                                .position(new LatLng(37.802355, -122.405848))
                        );
                      }



                    }
                  });


//                  eegeo:camera_target_latitude="37.7858"
//                  eegeo:camera_target_longitude="-122.398"

//                  wrldMapFragment.customView.addView(addedView);
                }
              }
            },
            2000);

//    parentView.addView(addedView);
  }

  public void setupLayout(View view) {
    Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
      @Override
      public void doFrame(long frameTimeNanos) {
        manuallyLayoutChildren(view);
        view.getViewTreeObserver().dispatchOnGlobalLayout();
        Choreographer.getInstance().postFrameCallback(this);
      }
    });
  }

  /**
   * Layout all children properly
   */
  public void manuallyLayoutChildren(View view) {
      // propWidth and propHeight coming from react-native props
      int width = propWidth;
      int height = propHeight;

      view.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
      view.layout(0, 0, width, height);
  }


  private class ViewAnchorAdapter implements OnPositionerChangedListener {

    private View m_view;
    private PointF m_anchorUV;

    ViewAnchorAdapter(@NonNull View view, float u, float v)
    {
      m_view = view;
      m_anchorUV = new PointF(u, v);
    }

    @UiThread
    public void onPositionerChanged(Positioner positioner) {
      if(positioner.isScreenPointProjectionDefined()) {
        m_view.setVisibility(View.VISIBLE);
        Point screenPoint = positioner.getScreenPointOrNull();
        if(screenPoint != null)
          ViewAnchor.positionView(m_view, screenPoint, m_anchorUV);
      }
      else {
        m_view.setVisibility(View.INVISIBLE);
      }
    }
  }
}