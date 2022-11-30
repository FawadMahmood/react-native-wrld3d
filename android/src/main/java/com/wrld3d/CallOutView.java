package com.wrld3d;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;

import com.eegeo.mapapi.EegeoMap;
import com.eegeo.mapapi.geometry.ElevationMode;
import com.eegeo.mapapi.geometry.LatLng;
import com.eegeo.mapapi.positioner.OnPositionerChangedListener;
import com.eegeo.mapapi.positioner.Positioner;
import com.eegeo.mapapi.positioner.PositionerOptions;
import com.eegeo.ui.util.ViewAnchor;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.ThemedReactContext;

public class CallOutView extends LinearLayout {
    ThemedReactContext context;
    private double longitude=0.0;
    private double latitude=0.0;
    EegeoMap m_eegeoMap;
    OnPositionerChangedListener m_positionerChangedListener=null;
    Positioner positioner;
    ElevationMode elevationMode = ElevationMode.HeightAboveGround;
    double elevation = 10;


    public void setReferences(EegeoMap m_eegeoMap){
            this.m_eegeoMap = m_eegeoMap;
            m_positionerChangedListener = new ViewAnchorAdapter(this, 0.5f, 0.5f);
            m_eegeoMap.addPositionerChangedListener(m_positionerChangedListener);
            positioner =  m_eegeoMap.addPositioner(new PositionerOptions()
                    .position(new LatLng(latitude,longitude)).elevation(this.elevation).elevationMode(this.elevationMode)
            );
    }

    public CallOutView(ThemedReactContext context, CallOutViewManager manager) {
        super(context);
        this.context = context;
        this.setVisibility(View.INVISIBLE);
    }

    public CallOutView(ThemedReactContext context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setVisibility(View.INVISIBLE);
    }

    public CallOutView(ThemedReactContext context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setVisibility(View.INVISIBLE);
    }

    public void setLocation(ReadableMap region) {
        if(this.latitude != 0.0 && this.longitude != 0.0){
            updateLatLong(region);
            if(this.m_eegeoMap != null){
                if(m_positionerChangedListener != null){
                    this.m_eegeoMap.removePositionerChangedListener(m_positionerChangedListener);
                }

                if(positioner != null){
                    this.m_eegeoMap.removePositioner(positioner);
                }

                this.setReferences(this.m_eegeoMap);
            }
        }else{
            updateLatLong(region);
        }

    }

    void updateLatLong(ReadableMap region){
        double latitude = region.getDouble("latitude");
        double longitude = region.getDouble("longitude");
        this.latitude = latitude;
        this.longitude = longitude;
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

    public void setElevationValue(double elevation) {
        this.elevation = elevation;
    }

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
