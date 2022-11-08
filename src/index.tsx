import React, { useEffect, useImperativeHandle, useRef, useState } from 'react';
import {
  findNodeHandle,
  LayoutChangeEvent,
  PixelRatio,
  Platform,
  UIManager,
  View,
} from 'react-native';
import { NativeModules } from 'react-native';


import { WrldMap3d, Marker as MarkerView, Wrld3dProps } from './MapViewManager';
import { Commands, MapViewNativeComponentType, Region } from './type';



const createFragment = (viewId: number) =>
  UIManager.dispatchViewManagerCommand(
    viewId,
    // we are calling the 'create' command
    // @ts-ignore
    UIManager.Wrld3dView.Commands.create.toString(),
    [viewId]
  );

const processCommand = (viewId: number, command: string, args: any[]) => UIManager.dispatchViewManagerCommand(
  viewId,
  command,
  args
);

export const Marker = MarkerView;

export type MapViewRefPropsType = MapViewNativeComponentType;

const MapComponent: React.ForwardRefRenderFunction<MapViewNativeComponentType, Wrld3dProps> = (
  props,
  forwardedRef,
) => {

  const viewId = useRef<number>(0)
  const isMapReady = useRef<boolean>();


  const moveToRegion = ({ location, animated = false, duration = 5000, zoomLevel = -1 }: { location: Region, animated: boolean, duration?: number, zoomLevel?: number }) => {
    if (Platform.OS === "android") {
      processCommand(viewId.current, Commands.animateToRegion, [location, animated, duration, zoomLevel]);
    }
  }

  const moveToBuilding = ({ location, highlight = false, zoomLevel = -1, animated = false, duration = 3000 }: { location: Region, highlight?: boolean, zoomLevel?: number, animated?: boolean, duration?: number }) => {
    if (Platform.OS === "android") {
      processCommand(viewId.current, Commands.moveToBuilding, [location, highlight, zoomLevel, animated, duration]);
    }
  }



  const [dimensions, setDimensions] = useState({ width: 0, height: 0 });
  const ref = useRef<any>(null);

  useEffect(() => {
    if (Platform.OS === "android") {
      viewId.current = findNodeHandle(ref.current) as number;
      createFragment(viewId.current as number);
    }
  }, []);

  const mapStyles = {
    // converts dpi to px, provide desired height
    height: PixelRatio.getPixelSizeForLayoutSize(dimensions.height),
    // converts dpi to px, provide desired width
    width: PixelRatio.getPixelSizeForLayoutSize(dimensions.width),
  };


  const onlayout = (event: LayoutChangeEvent) => {
    const { width, height } = event.nativeEvent.layout;
    setDimensions({ width: width, height: height });
  }


  const getMapCenter = async () => {
    const message = await NativeModules['MapViewModule'].getCameraBounds(viewId.current);
    return message;
  }

  const getBuildingInformation = async ({ location, animateToBuilding = false, duration = 2000, zoomLevel = 18 }: { location: Region, animateToBuilding?: boolean, duration?: number, zoomLevel?: number }) => {
    const message = await NativeModules['MapViewModule'].getBuildingInformation(viewId.current, location.longitude, location.latitude, animateToBuilding, duration, zoomLevel);
    return message;
  }

  const setBuildingHighlight = async ({ location }: { location: Region }) => {
    const message = await NativeModules['MapViewModule'].addBuildingHighlight(viewId.current, location.longitude, location.latitude);
    return message;
  }

  //mapevents
  const publicRef = {
    moveToRegion,
    moveToBuilding,
    getMapCenter,
    getBuildingInformation,
    setBuildingHighlight
  };


  useImperativeHandle(forwardedRef, () => publicRef);

  return (
    <View onLayout={onlayout.bind(null)} style={[props.style ? props.style : { width: "100%", height: "100%" }, { overflow: "hidden" }]}>
      <WrldMap3d
        {...props}
        style={mapStyles}
        // @ts-ignore
        ref={ref}
        zoomLevel={props.zoomLevel ? props.zoomLevel : 12}
        initialCenter={props.initialCenter ? props.initialCenter : {
          latitude: 24.8620502,
          longitude: 67.0708794
        }}
        onMapReady={() => {
          isMapReady.current = true;
          if (props.onMapReady) {
            props.onMapReady();
          }
        }}

        precache={props.precache}
        precacheDistance={props.precacheDistance}
      >
        {props.children}
      </WrldMap3d>
    </View>

  )
}


export const Wrld3dView = React.forwardRef(MapComponent);