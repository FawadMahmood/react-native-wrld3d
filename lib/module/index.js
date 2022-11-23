function _extends() { _extends = Object.assign ? Object.assign.bind() : function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; }; return _extends.apply(this, arguments); }
import MapView, { Commands } from './Wrld3dViewNativeComponent';
export * from './Wrld3dViewNativeComponent';
import * as React from 'react';
import { findNodeHandle, Platform } from 'react-native';
import { useCallback, useRef } from 'react';
// NativeProps &
export const Wrld3dView = props => {
  const ref = useRef(null);
  React.useEffect(() => {
    if (Platform.OS === 'android') {
      setTimeout(() => {
        createMapViewInstance();
      }, 500);
    }
  });
  const onCameraMove = useCallback(_ => {
    if (props.onCameraMove) props.onCameraMove(_.nativeEvent);
  }, []);
  const onMapReady = useCallback(_ => {
    if (props.onMapReady) props.onMapReady(_.nativeEvent);
  }, []);
  const _getHandle = () => {
    return findNodeHandle(ref.current);
  };
  const createMapViewInstance = () => {
    Commands.create(ref.current, _getHandle() + '');
  };
  return /*#__PURE__*/React.createElement(MapView, _extends({
    ref: ref
  }, props, {
    onCameraMove: onCameraMove,
    onMapReady: onMapReady
  }));
};
//# sourceMappingURL=index.js.map