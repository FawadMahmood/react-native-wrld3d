function _extends() { _extends = Object.assign ? Object.assign.bind() : function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; }; return _extends.apply(this, arguments); }

import React, { useEffect, useImperativeHandle, useRef, useState } from 'react';
import { findNodeHandle, PixelRatio, Platform, UIManager, View } from 'react-native';
import { NativeModules } from 'react-native';
import { WrldMap3d, Marker as MarkerView } from './MapViewManager';
import { Commands } from './type';

const createFragment = viewId => UIManager.dispatchViewManagerCommand(viewId, // we are calling the 'create' command
// @ts-ignore
UIManager.Wrld3dView.Commands.create.toString(), [viewId]);

const processCommand = (viewId, command, args) => UIManager.dispatchViewManagerCommand(viewId, command, args);

export const Marker = MarkerView;

const MapComponent = (props, forwardedRef) => {
  const viewId = useRef(0);
  const isMapReady = useRef();

  const moveToRegion = _ref => {
    let {
      location,
      animated = false,
      duration = 5000,
      zoomLevel = -1
    } = _ref;

    if (Platform.OS === "android") {
      processCommand(viewId.current, Commands.animateToRegion, [location, animated, duration, zoomLevel]);
    }
  };

  const moveToBuilding = _ref2 => {
    let {
      location,
      highlight = false,
      zoomLevel = -1,
      animated = false,
      duration = 3000
    } = _ref2;

    if (Platform.OS === "android") {
      processCommand(viewId.current, Commands.moveToBuilding, [location, highlight, zoomLevel, animated, duration]);
    }
  };

  const [dimensions, setDimensions] = useState({
    width: 0,
    height: 0
  });
  const ref = useRef(null);
  useEffect(() => {
    if (Platform.OS === "android") {
      viewId.current = findNodeHandle(ref.current);
      createFragment(viewId.current);
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

  const getMapCenter = async () => {
    const message = await NativeModules['MapViewModule'].getCameraBounds(viewId.current);
    return message;
  };

  const getBuildingInformation = async _ref3 => {
    let {
      location,
      animateToBuilding = false,
      duration = 2000,
      zoomLevel = 18
    } = _ref3;
    const message = await NativeModules['MapViewModule'].getBuildingInformation(viewId.current, location.longitude, location.latitude, animateToBuilding, duration, zoomLevel);
    return message;
  };

  const setBuildingHighlight = async _ref4 => {
    let {
      location
    } = _ref4;
    const message = await NativeModules['MapViewModule'].addBuildingHighlight(viewId.current, location.longitude, location.latitude);
    return message;
  }; //mapevents


  const publicRef = {
    moveToRegion,
    moveToBuilding,
    getMapCenter,
    getBuildingInformation,
    setBuildingHighlight
  };
  useImperativeHandle(forwardedRef, () => publicRef);
  return /*#__PURE__*/React.createElement(View, {
    onLayout: onlayout.bind(null),
    style: [props.style ? props.style : {
      width: "100%",
      height: "100%"
    }, {
      overflow: "hidden"
    }]
  }, /*#__PURE__*/React.createElement(WrldMap3d, _extends({}, props, {
    style: mapStyles // @ts-ignore
    ,
    ref: ref,
    zoomLevel: props.zoomLevel ? props.zoomLevel : 12,
    initialCenter: props.initialCenter ? props.initialCenter : {
      latitude: 24.8620502,
      longitude: 67.0708794
    },
    onMapReady: () => {
      isMapReady.current = true;

      if (props.onMapReady) {
        props.onMapReady();
      }
    },
    precache: props.precache,
    precacheDistance: props.precacheDistance
  }), props.children));
};

export const Wrld3dView = /*#__PURE__*/React.forwardRef(MapComponent);
//# sourceMappingURL=index.js.map