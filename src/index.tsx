import React, { forwardRef, useEffect, useImperativeHandle, useRef, useState } from 'react';
import {
  Alert,
  findNodeHandle,
  LayoutChangeEvent,
  PixelRatio,
  Platform,
  UIManager,
  View,
} from 'react-native';


import { WrldMap3d, Marker as MarkerView, Wrld3dProps } from './MapViewManager';
import type { MapViewNativeComponentType, Region } from './type';



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


  const moveToRegion = (location: Region, animated: boolean, duration?: number) => {
    if (Platform.OS === "android") {
      processCommand(viewId.current, 'animateToRegion', [location, animated, duration ? duration : 5000]);
    }
  }

  const moveToBuilding = (location: Region, highlight: boolean, zoomLevel: number) => {

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



  //mapevents
  const publicRef = {
    moveToRegion,
    moveToBuilding
  };


  useImperativeHandle(forwardedRef, () => publicRef);

  return (
    <View onLayout={onlayout.bind(null)} style={[props.style, { overflow: "hidden" }]}>
      <WrldMap3d
        style={mapStyles}
        // @ts-ignore
        ref={ref}
        zoomLevel={props.zoomLevel ? props.zoomLevel : 12}
        initialCenter={props.initialCenter ? props.initialCenter : {
          latitude: 24.8620495,
          longitude: 67.070877
        }}
      >
        {props.children}
      </WrldMap3d>
    </View>

  )
}


export const Wrld3dView = React.forwardRef(MapComponent);