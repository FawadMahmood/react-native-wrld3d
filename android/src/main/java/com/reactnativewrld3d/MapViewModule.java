package com.reactnativewrld3d;

import androidx.annotation.NonNull;

import com.eegeo.mapapi.EegeoMap;
import com.eegeo.mapapi.camera.CameraPosition;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.UIBlock;
import com.facebook.react.uimanager.UIManagerModule;

public class MapViewModule extends ReactContextBaseJavaModule {
    ReactApplicationContext reactContext;



    public MapViewModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @NonNull
    @Override
    public String getName() {
        return "MapViewModule";
    }

    @ReactMethod
    public void getCameraBounds(final int tag, Promise promise){
        UIManagerModule uiManager = reactContext.getNativeModule(UIManagerModule.class);

        uiManager.addUIBlock(new UIBlock()
        {
            @Override
            public void execute(NativeViewHierarchyManager nvhm)
            {
                try{
                    Wrld3dViewManager view = (Wrld3dViewManager) nvhm.resolveViewManager(tag);

                    if (view == null) {
                        promise.reject("EegeoMap not found");
                        return;
                    }

                    if (view.m_eegeoMap == null) {
                        promise.reject("EegeoMap.map is not valid");
                        return;
                    }
                    EegeoMap map = view.m_eegeoMap;
                    final CameraPosition cameraPosition = map.getCameraPosition();
                    double latitude = (cameraPosition.target != null) ? cameraPosition.target.latitude : 0.0;
                    double longitude = (cameraPosition.target != null) ? cameraPosition.target.longitude : 0.0;
                    WritableMap event = Arguments.createMap();
                    WritableMap region = Arguments.createMap();
                    region.putDouble("latitude", latitude);
                    region.putDouble("longitude", longitude);
                    event.putMap("region",region);
                    event.putInt("zoom",(int)cameraPosition.zoom);
                    promise.resolve(event);
                }catch (Exception e){
                    promise.reject(e);
                }
            }
        });


    }

}
