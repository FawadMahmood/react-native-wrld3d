function _extends() { _extends = Object.assign ? Object.assign.bind() : function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; }; return _extends.apply(this, arguments); }
import MapView, { Commands } from './Wrld3dViewNativeComponent';
export * from './Wrld3dViewNativeComponent';
import * as React from 'react';
import { findNodeHandle, Platform } from 'react-native';
import { forwardRef, useCallback, useImperativeHandle, useRef } from 'react';
import Wrld3dLibrary from './module/Wrld3dLibrary';
const Wrld3dModule = Wrld3dLibrary;
// NativeProps &
export const Wrld3dView = /*#__PURE__*/forwardRef((props, forwardedRef) => {
  const ref = useRef(null);
  const mapCreated = useRef(false);

  //mapevents
  const publicRef = {
    setBuildingHighlight: (buildingId, color, buildingCoordinates) => {
      createBuildingHighlight(buildingId, color, buildingCoordinates);
    },
    removeBuildingHighlight: buildingId => {
      removeBuildingHighlightComand(buildingId);
    },
    findBuildingOnCoordinates: coordinates => {
      return Wrld3dModule.findBuildingOnCoordinates(_getHandle(), coordinates);
    }
  };
  useImperativeHandle(forwardedRef, () => publicRef);
  const {
    onCameraMoveEnd: onMove,
    onMapReady: onReady,
    onCameraMoveBegin: onMoveBegin,
    onClickBuilding: clickBuildingUp
  } = props;
  React.useEffect(() => {
    if (Platform.OS === 'android') {
      setTimeout(() => {
        if (!mapCreated.current) {
          mapCreated.current = true;
          createMapViewInstance();
        }
      }, 500);
    }
  });
  const onCameraMoveEnd = useCallback(_ => {
    if (onMove) onMove(_.nativeEvent);
  }, [onMove]);
  const onClickBuilding = useCallback(_ => {
    if (clickBuildingUp) clickBuildingUp(_.nativeEvent);
  }, [clickBuildingUp]);
  const onMapReady = useCallback(_ => {
    if (onReady) onReady(_.nativeEvent);
  }, [onReady]);
  const onCameraMoveBegin = useCallback(() => {
    if (onMoveBegin) onMoveBegin();
  }, [onMoveBegin]);
  const _getHandle = () => {
    return findNodeHandle(ref.current);
  };
  const createBuildingHighlight = useCallback((buildingId, color, buildingCoordinates) => {
    console.log('setting building hi command', buildingId, buildingCoordinates);
    Commands.setBuildingHighlight(ref.current, buildingId, color, buildingCoordinates);
  }, []);
  const removeBuildingHighlightComand = useCallback(buildingId => {
    Commands.removeBuildingHighlight(ref.current, buildingId);
  }, []);
  const createMapViewInstance = useCallback(() => {
    Commands.create(ref.current, _getHandle() + '');
  }, []);
  return /*#__PURE__*/React.createElement(MapView, _extends({
    ref: ref
  }, props, {
    onCameraMoveEnd: onCameraMoveEnd,
    onMapReady: onMapReady,
    onCameraMoveBegin: onCameraMoveBegin,
    onClickBuilding: onClickBuilding
  }));
});
//# sourceMappingURL=index.js.map