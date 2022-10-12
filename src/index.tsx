import React, { useEffect, useRef, useState } from 'react';
import {
  findNodeHandle,
  LayoutChangeEvent,
  PixelRatio,
  Platform,
  UIManager,
  View,
} from 'react-native';


import { WrldMap3d, Marker as MarkerView, Wrld3dProps } from './MapViewManager';



const createFragment = (viewId: number) =>
  UIManager.dispatchViewManagerCommand(
    viewId,
    // we are calling the 'create' command
    // @ts-ignore
    UIManager.Wrld3dView.Commands.create.toString(),
    [viewId]
  );

// type MapTypes = {
//   style?: ViewStyle,
//   children?: Element;
//   zoomLevel?: number
// }


export const Marker = MarkerView;


export const Wrld3dView = (props: Wrld3dProps) => {
  const [dimensions, setDimensions] = useState({ width: 0, height: 0 });
  const ref = useRef(null);

  useEffect(() => {
    if (Platform.OS === "android") {
      const viewId = findNodeHandle(ref.current);
      createFragment(viewId as number);
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

  return (
    <View onLayout={onlayout.bind(null)} style={[props.style, { overflow: "hidden" }]}>
      <WrldMap3d
        style={mapStyles}
        // @ts-ignore
        ref={ref}
        zoomLevel={props.zoomLevel ? props.zoomLevel : 12}

      >
        {props.children}
      </WrldMap3d>
    </View>

  )
}


{/* <View style={{ width: 100, height: 100, backgroundColor: "red", borderRadius: 50 }}></View> */ }