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
    private EegeoMap m_eegeoMap = null;
    ReactApplicationContext reactContext;

    private WrldMapFragment wrldMapFragment;
    private List<View> addedView = new ArrayList<View>();
    private List<OnPositionerChangedListener> listPotionersListeners = new ArrayList<OnPositionerChangedListener>();


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
                createFragment(root, reactNativeViewId);
                break;
            default: {}
        }
    }

    @Override
    public void addViews(FrameLayout parent, List<View> views) {
        Log.d("VIEWS CHANGED","OPERATION addViews " + views.size());
    }

    @Override
    public void addView(FrameLayout parent, View child, int index) {
        Log.d("VIEWS CHANGED","OPERATION addView " + index + " views size " + addedView.size());
        addedView.add(child);

        if(wrldMapFragment != null && wrldMapFragment.m_mapView != null){
            UpdateMapCustomViews(index);
        }
    }

    @Override
    public void removeViewAt(FrameLayout parent, int index) {
        Log.d("VIEWS CHANGED","OPERATION removeViewAt "+index + " views size " + addedView.size());
        addedView.remove(index);
        m_eegeoMap.removePositioner(_positioners.get(index));
        _positioners.remove(index);
        if(parent.getChildCount() > index+1)  super.removeViewAt(parent, index+1);
//        UpdateMapCustomViews(0);
    }


    //    @Override
//    public void removeViewAt(FrameLayout _parent, int index) {
//        Log.d("VIEWS CHANGED","OPERATION removeViewAt "+index + " views size " + addedView.size());
//        if(wrldMapFragment != null){
//                m_eegeoMap.removePositioner(_positioners.get(index));
//                addedView.remove(index);
//                _positioners.remove(index);
//                parent.removeViewAt(index + 1);
//
//
//            UpdateMapCustomViews(0);
//        }
//
//    }


    private void addViewToMap(View child){
//        View newView = child;
////        newView.setVisibility(View.INVISIBLE);
//        newView.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
////        newView.setBackground();
//        OnPositionerChangedListener m_positionerChangedListener = new ViewAnchorAdapter(newView, 0.5f, 0.5f,_positioners.size());
//        m_eegeoMap.addPositionerChangedListener(m_positionerChangedListener);
//        _positioners.add(
//                m_eegeoMap.addPositioner(new PositionerOptions().position(new LatLng(50.802355,-122.405848)))
//        );
//
////        m_eegeoMap.removePositioner(_positioners.get(_positioners.size()-1));
//        addedView.add(newView);
//        parent.addView(newView);
    }


    private void UpdateMapCustomViews(int atIndex){
        if(addedView != null) {
            for (int i = atIndex; i < addedView.size(); i++) {
                if (addedView.get(i) != null && addedView.get(i).getParent() == null) {
                    View _addedView = addedView.get(i);
                    if(_addedView instanceof MarkerView){
                        ((MarkerView) _addedView).AddItToView(m_eegeoMap,parent);
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
        if (index == 0) {
            propWidth = value;
        }

        if (index == 1) {
            propHeight = value;
        }
    }


    /**
     * Replace your React Native view with a custom fragment
     */
    public void createFragment(FrameLayout root, int reactNativeViewId) {
        parent = (ViewGroup) root.findViewById(reactNativeViewId);
        setupLayout(parent);


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
                                    UpdateMapCustomViews(0);
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


    private class ViewAnchorAdapter implements OnPositionerChangedListener {

        private View m_view;
        private PointF m_anchorUV;
        private int index;

        ViewAnchorAdapter(@NonNull View view, float u, float v,int _index)
        {
            m_view = view;
            m_anchorUV = new PointF(u, v);
            index = _index;
        }

        @UiThread
        public void onPositionerChanged(Positioner positioner) {
            if(_positioners.size() > index){
                Positioner _positioner = _positioners.get(index);
                if(_positioner.isScreenPointProjectionDefined()){
                    m_view.setVisibility(View.VISIBLE);
                    Point screenPoint = _positioner.getScreenPointOrNull();
                    if(screenPoint != null)
                        ViewAnchor.positionView(m_view, screenPoint, m_anchorUV);
                }else{
                    m_view.setVisibility(View.INVISIBLE);
                }
            }else{
                m_view.setVisibility(View.INVISIBLE);
            }

        }
    }
}

