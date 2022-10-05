package com.reactnativewrld3d;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.eegeo.mapapi.EegeoApi;
import com.eegeo.mapapi.MapView;

public class WrldMapFragment extends Fragment {
    CustomView customView;
    MapView m_mapView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);

        String API_KEY = "YOUR_API_KEY";

        try {
            ApplicationInfo ai = this.getContext().getPackageManager().getApplicationInfo(this.getContext().getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            API_KEY = bundle.getString("com.wrld3d.API_KEY");
        } catch (Exception e) {
            Log.e("NO API KEY", "Dear developer. Don't forget to configure <meta-data android:name=\"my_test_metagadata\" android:value=\"testValue\"/> in your AndroidManifest.xml file.");
        }

        EegeoApi.init(this.getContext(), API_KEY);

        View view = inflater.inflate(R.layout.fragment_map_screen, parent, false);
        m_mapView = (MapView)view.findViewById(R.id.mapView);
        return view;
//        customView = new CustomView(this.getContext());
//        return customView; // this CustomView could be any view that you want to render
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // do any logic that should happen in an `onCreate` method, e.g:
        // customView.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
        // do any logic that should happen in an `onPause` method
        // e.g.: customView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
       // do any logic that should happen in an `onResume` method
       // e.g.: customView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // do any logic that should happen in an `onDestroy` method
        // e.g.: customView.onDestroy();
    }
}