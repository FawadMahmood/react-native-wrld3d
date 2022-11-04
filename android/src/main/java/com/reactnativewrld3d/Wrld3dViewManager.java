// replace with your package
package com.reactnativewrld3d;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Looper;
import android.util.Log;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.eegeo.mapapi.EegeoMap;
import com.eegeo.mapapi.MapView;
import com.eegeo.mapapi.buildings.BuildingContour;
import com.eegeo.mapapi.buildings.BuildingDimensions;
import com.eegeo.mapapi.buildings.BuildingHighlight;
import com.eegeo.mapapi.buildings.BuildingHighlightOptions;
import com.eegeo.mapapi.buildings.BuildingInformation;
import com.eegeo.mapapi.buildings.OnBuildingInformationReceivedListener;
import com.eegeo.mapapi.camera.CameraPosition;
import com.eegeo.mapapi.camera.CameraUpdateFactory;
import com.eegeo.mapapi.geometry.ElevationMode;
import com.eegeo.mapapi.geometry.LatLng;
import com.eegeo.mapapi.map.OnMapReadyCallback;
import com.eegeo.mapapi.markers.MarkerOptions;
import com.eegeo.mapapi.polylines.PolylineOptions;
import com.eegeo.mapapi.positioner.OnPositionerChangedListener;
import com.eegeo.mapapi.positioner.Positioner;
import com.eegeo.mapapi.positioner.PositionerOptions;
import com.eegeo.ui.util.ViewAnchor;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.ThemedReactContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Wrld3dViewManager extends ViewGroupManager<FrameLayout> {
    ViewGroup parent;
    private OnPositionerChangedListener m_positionerChangedListener = null;
    List<Positioner> _positioners = new ArrayList<Positioner>();
    public static final String REACT_CLASS = "Wrld3dView";
    public final int COMMAND_CREATE = 1;
    private int propWidth;
    private int propHeight;
    private double latitude=37.7952;
    private double longitude=-122.4028;
    private int zoomLevel=10;
    private ReadableMap initialCenter;
    private int viewId=0;

    private EegeoMap m_eegeoMap = null;
    private MapView map;


    ReactApplicationContext reactContext;

    private WrldMapFragment wrldMapFragment;
    private List<View> addedView = new ArrayList<View>();


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
        return new FrameLayout(this.reactContext);
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
                if(viewId != 0 && viewId != reactNativeViewId){
                    addedView.clear();
                }


                viewId = reactNativeViewId;
                createFragment(root, reactNativeViewId);
                break;
            default: {}
        }
    }

    @Override
    public void addView(FrameLayout parent, View child, int index) {

        if(index ==0 &&  addedView.size()>0){
            addedView.clear();
        }

        addedView.add(child);

        if(wrldMapFragment != null && wrldMapFragment.m_mapView != null){
            UpdateMapCustomViews(index);
        }
    }

    @Override
    public void removeViewAt(FrameLayout parent, int index) {
            super.removeViewAt(parent, index + 1);
    }

    private void UpdateMapCustomViews(int atIndex){
        if(addedView != null) {
            for (int i = atIndex; i < addedView.size(); i++) {
                if (addedView.get(i) != null && addedView.get(i).getParent() == null) {
                    View _addedView = addedView.get(i);
                    if(_addedView instanceof MarkerView){
                        ((MarkerView) _addedView).AddItToView(m_eegeoMap,parent,true);
                    }
                }
            }
        }
    }

    @Override
    public int getChildCount(FrameLayout parent) {
        return super.getChildCount(parent);
    }


    @ReactPropGroup(names = {"width", "height"}, customType = "Style")
    public void setStyle(FrameLayout view, int index, Integer value) {
        Log.w("setStyle","setStyle");

        if (index == 0) {
            propWidth = value;
        }

        if (index == 1) {
            propHeight = value;
        }
    }

    @ReactProp(name = "initialCenter")
    public void setInitialRegion(FrameLayout view, ReadableMap initialCenter) {
        this.initialCenter = initialCenter;
    }

    @ReactProp(name = "zoomLevel", defaultFloat = 10)
    public void setinitialZoom(FrameLayout view, int zoomLevel) {
        Log.w("zoomLevel","zoomLevel");
        this.zoomLevel = zoomLevel;
    }


    private BuildingHighlight m_highlight = null;

    /**
     * Replace your React Native view with a custom fragment
     */
    public void createFragment(FrameLayout root, int reactNativeViewId) {
        parent = (ViewGroup) root.findViewById(reactNativeViewId);


        setupLayout(parent);


        wrldMapFragment = new WrldMapFragment();

//        Fragment fragmentA = fragmentManager.findFragmentByTag("frag1");


        FragmentActivity activity = (FragmentActivity) reactContext.getCurrentActivity();

        Fragment _oldFrag = activity.getSupportFragmentManager().findFragmentByTag(String.valueOf(reactNativeViewId));
        if(_oldFrag != null){
            activity.getSupportFragmentManager().beginTransaction().remove(_oldFrag);
            Log.w("FRAGMENT EXIST ALREADY","FRAGMENT WAS THERE AND REMOVED"+parent.getId());
        }




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
                                    if(_oldFrag != null){
                                        Log.w("FRAGMENT EXIST ALREADY","MAP READY STILL WORKED WITH LIST SIZE: "+ addedView.size());
                                    }

                                    UpdateMapCustomViews(0);

                                    if(initialCenter != null){
                                        latitude = initialCenter.getDouble("latitude");
                                        longitude = initialCenter.getDouble("longitude");
                                    }


                                    CameraPosition position = new CameraPosition.Builder()
                                            .target(latitude, longitude)
                                            .zoom(zoomLevel)
                                            .build();

                                    map.moveCamera(CameraUpdateFactory.newCameraPosition(position));
                                }
                            });
                        }
                    }
                },
                2000);

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


    @Override
    public void onDropViewInstance(@NonNull FrameLayout view) {
        Log.w("View Has Been destroyed","OH");
        super.onDropViewInstance(view);
    }


    @Override
    public void removeAllViews(FrameLayout parent) {
        Log.w("View Has Been destroyed","OH");
        super.removeAllViews(parent);
    }
}

