package com.reactnativewrld3d;

import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;

import com.eegeo.mapapi.EegeoMap;
import com.eegeo.mapapi.MapView;
import com.eegeo.mapapi.geometry.LatLng;
import com.eegeo.mapapi.positioner.OnPositionerChangedListener;
import com.eegeo.mapapi.positioner.Positioner;
import com.eegeo.mapapi.positioner.PositionerOptions;
import com.eegeo.ui.util.ViewAnchor;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.view.ReactViewGroup;

public class MarkerView extends LinearLayout {
    EegeoMap m_eegeoMap;
    ViewGroup parent;
    private ReadableMap region;
    OnPositionerChangedListener m_positionerChangedListener=null;
    Positioner positioner;
    public ReadableMap getRegion(){
            return region;
    }


    public MarkerView(ThemedReactContext context) {
        super(context);
        this.setVisibility(View.INVISIBLE);
    }

    public void setLocation(ReadableMap region) {
        if(this.region != null){
            this.region = region;
            update();
        }else{
            this.region = region;
        }
    }

    public void update(){
        this.setVisibility(View.INVISIBLE);
        onViewRemoved(this);
        if(m_eegeoMap != null && parent != null){
            AddItToView(m_eegeoMap,parent,false);
        }
    }

    public void AddItToView(EegeoMap m_eegeoMap, ViewGroup parent,boolean isNew){
       this.m_eegeoMap = m_eegeoMap;
       this.parent = parent;
        if(isNew){
            this.setLayoutParams(new ViewGroup.LayoutParams(300, 300));
        }
        double latitude = region.hasKey("latitude") ? region.getDouble("latitude") : 40.802355;
        double longitude = region.hasKey("longitude") ? region.getDouble("longitude") : -122.405848;
        m_positionerChangedListener = new ViewAnchorAdapter(this, 0.5f, 0.5f);
        m_eegeoMap.addPositionerChangedListener(m_positionerChangedListener);
        positioner =  m_eegeoMap.addPositioner(new PositionerOptions()
                .position(new LatLng(latitude,longitude))
        );
        if(isNew){
            parent.addView(this);
        }
    }

    @Override
    public void onViewRemoved(View child) {
        if(m_positionerChangedListener != null){
            this.m_eegeoMap.removePositionerChangedListener(m_positionerChangedListener);
        }

        if(positioner != null){
            this.m_eegeoMap.removePositioner(positioner);
        }
    }



//    public void viewWillBeRemoved(){
//        Log.d("ON VIEW REMOVED", "CHILD REMOVED YO ViewRemoved");
//
//    }


    private class ViewAnchorAdapter implements OnPositionerChangedListener {

        private View m_view;
        private PointF m_anchorUV;
        private int index;

        ViewAnchorAdapter(@NonNull View view, float u, float v)
        {
            m_view = view;
            m_anchorUV = new PointF(u, v);
        }

        @UiThread
        public void onPositionerChanged(Positioner __positioner) {
                Positioner _positioner =positioner;
                if(_positioner.isScreenPointProjectionDefined()){
                    m_view.setVisibility(View.VISIBLE);
                    Point screenPoint = _positioner.getScreenPointOrNull();
                    if(screenPoint != null)
                        ViewAnchor.positionView(m_view, screenPoint, m_anchorUV);
                }else{
                    m_view.setVisibility(View.INVISIBLE);
                }

        }
    }
}
