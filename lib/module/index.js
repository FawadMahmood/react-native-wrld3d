function _extends() { _extends = Object.assign ? Object.assign.bind() : function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; }; return _extends.apply(this, arguments); }
import MapView, { Commands } from './Wrld3dViewNativeComponent';
export * from './Wrld3dViewNativeComponent';
import * as React from 'react';
import { findNodeHandle, Platform } from 'react-native';
import { useRef } from 'react';
export const Wrld3dView = props => {
  const mapNativeID = useRef(0);
  const ref = useRef(null);
  React.useEffect(() => {
    if (Platform.OS === 'android') {
      setTimeout(() => {
        createMapViewInstance();
      }, 2000);
    }
  });
  const createMapViewInstance = () => {
    mapNativeID.current = findNodeHandle(ref.current);
    Commands.create(ref.current, mapNativeID.current + '');
  };
  return /*#__PURE__*/React.createElement(MapView, _extends({
    ref: ref
  }, props));
};
//# sourceMappingURL=index.js.map