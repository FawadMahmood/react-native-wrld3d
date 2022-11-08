// replace with your package
package com.reactnativewrld3d;

import android.app.AlertDialog;
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
import com.eegeo.mapapi.precaching.OnPrecacheOperationCompletedListener;
import com.eegeo.mapapi.precaching.PrecacheOperationResult;
import com.eegeo.ui.util.ViewAnchor;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.UIBlock;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Wrld3dViewManager extends ViewGroupManager<FrameLayout> implements OnPrecacheOperationCompletedListener {
    //*****************
    //***ALL COMMANDS CONSTANTS
    public final int COMMAND_CREATE = 1;
    public final int ANIMATE_TO_REGION = 2;
    public final int MOVE_TO_BUILDING=3;
    //*****************
    //***ALL COMMANDS CONSTANTS





    ViewGroup parent;
    private OnPositionerChangedListener m_positionerChangedListener = null;
    List<Positioner> _positioners = new ArrayList<Positioner>();
    public static final String REACT_CLASS = "Wrld3dView";
    private int propWidth;
    private int propHeight;
    private double latitude=37.7952;
    private double longitude=-122.4028;
    private int zoomLevel=10;
    private ReadableMap initialCenter;
    private int viewId=0;
    private boolean precache=false;
    private double cacheDistance=100.0f;
    private boolean pickBuilding=false;


    public EegeoMap m_eegeoMap = null;


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
        return MapBuilder.of("create", COMMAND_CREATE,"animateToRegion",ANIMATE_TO_REGION,"moveToBuilding",MOVE_TO_BUILDING);
    }


    void bubbleOnMapReadyEvent(){
        WritableMap event = Arguments.createMap();
        event.putString("ready", "true");
        pushEvent(reactContext,viewId,"onMapReady",event);
    }

    @Override
    @Nullable
    public Map getExportedCustomDirectEventTypeConstants() {
        Map<String, Map<String, String>> map = MapBuilder.of(
                "onMapReady", MapBuilder.of("registrationName", "onMapReady"),
                "onMapCacheCompleted", MapBuilder.of("registrationName", "onMapCacheCompleted")
        );

        return map;
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
            case ANIMATE_TO_REGION:
                ReadableMap location = args.getMap(0);
                boolean animated = args.getBoolean(1);
                int duration = args.getInt(2);
                int zoomLevel = args.getInt(3);
                if(zoomLevel ==-1){
                    zoomLevel = this.zoomLevel;
                }
                if(m_eegeoMap != null){
                    moveToRegion(location,animated,duration,zoomLevel);
                }
            break;
            case MOVE_TO_BUILDING:
                ReadableMap _location = args.getMap(0);
                boolean highlight = args.getBoolean(1);
                int _zoomLevel =args.getInt(2) == -1 ? this.zoomLevel:args.getInt(2) ;
                boolean _animated = args.getBoolean(3);
                int _duration = args.getInt(4);


                break;
            default: {}
        }
    }

    void moveToBuilding(ReadableMap region,boolean highlight,int zoomLevel,boolean animate,int duration){
        double latitude = region.getDouble("latitude");
        double longitude = region.getDouble("longitude");

        if(animate){
            CameraPosition position = new CameraPosition.Builder()
                    .target(latitude, longitude)
                    .zoom(zoomLevel)
                    .bearing(270)
                    .build();

            m_eegeoMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), duration);
        }else{
            CameraPosition position = new CameraPosition.Builder()
                    .target(latitude, longitude)
                    .zoom(zoomLevel)
                    .build();
            m_eegeoMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));
        }

        if(highlight){

        }else{

        }
    }


    void moveToRegion(ReadableMap region,boolean animate,int duration,int zoomLevel){
        double latitude = region.getDouble("latitude");
        double longitude = region.getDouble("longitude");

        if(animate){
            CameraPosition position = new CameraPosition.Builder()
                    .target(latitude, longitude)
                    .zoom(zoomLevel)
                    .bearing(270)
                    .build();

            m_eegeoMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), duration);
        }else{
            CameraPosition position = new CameraPosition.Builder()
                    .target(latitude, longitude)
                    .zoom(zoomLevel)
                    .build();
            m_eegeoMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));
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



    @ReactProp(name = "precacheDistance",defaultDouble =100f)
    public void setPrecacheDistance(FrameLayout view, double distance) {
        Log.w("CACHE STARTED","CACHE STATING"+distance);
        this.cacheDistance = distance;
    }

    @ReactProp(name = "precache",defaultBoolean = false)
    public void setPrecache(FrameLayout view, boolean precache) {
        Log.w("CACHE STARTED","CACHE STATING"+precache);
        this.precache = precache;
    }

    @ReactProp(name = "initialCenter")
    public void setInitialRegion(FrameLayout view, ReadableMap initialCenter) {
        Log.w("setInitialRegion",initialCenter.toString());
        try{
            latitude = initialCenter.getDouble("latitude");
            longitude = initialCenter.getDouble("longitude");
        }catch(Exception e){

        }

        this.initialCenter = initialCenter;
    }

    @ReactProp(name = "zoomLevel", defaultFloat = 10)
    public void setinitialZoom(FrameLayout view, int zoomLevel) {
        Log.w("zoomLevel","zoomLevel");
        this.zoomLevel = zoomLevel;
    }

    @ReactProp(name = "pickBuilding", defaultBoolean = false)
    public void setPickBuilding(FrameLayout view, boolean pickBuilding) {
        this.pickBuilding = pickBuilding;
    }

    private BuildingHighlight m_highlight = null;

    /**
     * Replace your React Native view with a custom fragment
     */
    public void createFragment(FrameLayout root, int reactNativeViewId) {
        parent = (ViewGroup) root.findViewById(reactNativeViewId);
        setupLayout(parent);
        wrldMapFragment = new WrldMapFragment();
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
                            wrldMapFragment.m_mapView.getMapAsync(new OnMapReadyCallback() {
                                @Override
                                public void onMapReady(EegeoMap map) {
                                    m_eegeoMap = map;
                                    UpdateMapCustomViews(0);
                                    if(initialCenter != null){
                                        try{
                                            latitude = initialCenter.getDouble("latitude");
                                            longitude = initialCenter.getDouble("longitude");
                                        }catch (Exception e) {

                                        }
                                    }
                                    if(precache){
                                        Log.w("CACHE STARTED","CACHE STATING");
                                        m_eegeoMap.precache(
                                                new LatLng(latitude, longitude),
                                                cacheDistance,
                                                Wrld3dViewManager.this);
                                    }
                                    CameraPosition position = new CameraPosition.Builder()
                                            .target(latitude, longitude)
                                            .zoom(zoomLevel)
                                            .build();
                                    map.moveCamera(CameraUpdateFactory.newCameraPosition(position));
                                    bubbleOnMapReadyEvent();
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
//        super.onDropViewInstance(view);
    }


    @Override
    public void onPrecacheOperationCompleted(PrecacheOperationResult precacheOperationResult) {
        WritableMap event = Arguments.createMap();

        if(precacheOperationResult.succeeded()){
            event.putString("success", "true");
        }else{
            event.putString("success", "false");
        }
        
        pushEvent(reactContext,viewId,"onMapCacheCompleted",event);
    }


    void pushEvent(ReactApplicationContext context, int viewId, String name, WritableMap data) {
        context.getJSModule(RCTEventEmitter.class).receiveEvent(viewId, name, data);
    }

}

