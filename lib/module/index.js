import React, { useEffect, useRef, useState } from 'react';
import { findNodeHandle, PixelRatio, Platform, UIManager, View } from 'react-native';
import { WrldMap3d, Marker as MarkerView } from './MapViewManager';

const createFragment = viewId => UIManager.dispatchViewManagerCommand(viewId, // we are calling the 'create' command
// @ts-ignore
UIManager.Wrld3dView.Commands.create.toString(), [viewId]); // type MapTypes = {
//   style?: ViewStyle,
//   children?: Element;
//   zoomLevel?: number
// }


export const Marker = MarkerView;
export const Wrld3dView = props => {
  const [dimensions, setDimensions] = useState({
    width: 0,
    height: 0
  });
  const ref = useRef(null);
  useEffect(() => {
    if (Platform.OS === "android") {
      const viewId = findNodeHandle(ref.current);
      createFragment(viewId);
    }
  }, []);
  const mapStyles = {
    // converts dpi to px, provide desired height
    height: PixelRatio.getPixelSizeForLayoutSize(dimensions.height),
    // converts dpi to px, provide desired width
    width: PixelRatio.getPixelSizeForLayoutSize(dimensions.width)
  };

  const onlayout = event => {
    const {
      width,
      height
    } = event.nativeEvent.layout;
    setDimensions({
      width: width,
      height: height
    });
  };

  return /*#__PURE__*/React.createElement(View, {
    onLayout: onlayout.bind(null),
    style: [props.style, {
      overflow: "hidden"
    }]
  }, /*#__PURE__*/React.createElement(WrldMap3d, {
    style: mapStyles // @ts-ignore
    ,
    ref: ref,
    zoomLevel: props.zoomLevel ? props.zoomLevel : 12,
    initialCenter: props.initialCenter ? props.initialCenter : {
      latitude: 37.7858,
      longitude: -122.401
    }
  }, props.children));
};
{
  /* <View style={{ width: 100, height: 100, backgroundColor: "red", borderRadius: 50 }}></View> */
}
//# sourceMappingURL=index.js.map