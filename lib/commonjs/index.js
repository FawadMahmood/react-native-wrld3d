"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
var _exportNames = {
  Wrld3dView: true,
  CallOutView: true
};
Object.defineProperty(exports, "CallOutView", {
  enumerable: true,
  get: function () {
    return _CalloutView.CallOutView;
  }
});
exports.Wrld3dView = void 0;
var _Wrld3dViewNativeComponent = _interopRequireWildcard(require("./Wrld3dViewNativeComponent"));
Object.keys(_Wrld3dViewNativeComponent).forEach(function (key) {
  if (key === "default" || key === "__esModule") return;
  if (Object.prototype.hasOwnProperty.call(_exportNames, key)) return;
  if (key in exports && exports[key] === _Wrld3dViewNativeComponent[key]) return;
  Object.defineProperty(exports, key, {
    enumerable: true,
    get: function () {
      return _Wrld3dViewNativeComponent[key];
    }
  });
});
var _CalloutView = require("./CalloutView");
var React = _interopRequireWildcard(require("react"));
var _reactNative = require("react-native");
var _Wrld3dLibrary = _interopRequireDefault(require("./module/Wrld3dLibrary"));
function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
function _getRequireWildcardCache(nodeInterop) { if (typeof WeakMap !== "function") return null; var cacheBabelInterop = new WeakMap(); var cacheNodeInterop = new WeakMap(); return (_getRequireWildcardCache = function (nodeInterop) { return nodeInterop ? cacheNodeInterop : cacheBabelInterop; })(nodeInterop); }
function _interopRequireWildcard(obj, nodeInterop) { if (!nodeInterop && obj && obj.__esModule) { return obj; } if (obj === null || typeof obj !== "object" && typeof obj !== "function") { return { default: obj }; } var cache = _getRequireWildcardCache(nodeInterop); if (cache && cache.has(obj)) { return cache.get(obj); } var newObj = {}; var hasPropertyDescriptor = Object.defineProperty && Object.getOwnPropertyDescriptor; for (var key in obj) { if (key !== "default" && Object.prototype.hasOwnProperty.call(obj, key)) { var desc = hasPropertyDescriptor ? Object.getOwnPropertyDescriptor(obj, key) : null; if (desc && (desc.get || desc.set)) { Object.defineProperty(newObj, key, desc); } else { newObj[key] = obj[key]; } } } newObj.default = obj; if (cache) { cache.set(obj, newObj); } return newObj; }
function _extends() { _extends = Object.assign ? Object.assign.bind() : function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; }; return _extends.apply(this, arguments); }
const Wrld3dModule = _Wrld3dLibrary.default;
// NativeProps &
const Wrld3dView = /*#__PURE__*/(0, React.forwardRef)((props, forwardedRef) => {
  const ref = (0, React.useRef)(null);
  const mapCreated = (0, React.useRef)(false);

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
  (0, React.useImperativeHandle)(forwardedRef, () => publicRef);
  const {
    onCameraMoveEnd: onMove,
    onMapReady: onReady,
    onCameraMoveBegin: onMoveBegin,
    onClickBuilding: clickBuildingUp
  } = props;
  React.useEffect(() => {
    if (_reactNative.Platform.OS === 'android') {
      setTimeout(() => {
        if (!mapCreated.current) {
          mapCreated.current = true;
          createMapViewInstance();
        }
      }, 500);
    }
  });
  const onCameraMoveEnd = (0, React.useCallback)(_ => {
    if (onMove) onMove(_.nativeEvent);
  }, [onMove]);
  const onClickBuilding = (0, React.useCallback)(_ => {
    if (clickBuildingUp) clickBuildingUp(_.nativeEvent);
  }, [clickBuildingUp]);
  const onMapReady = (0, React.useCallback)(_ => {
    if (onReady) onReady(_.nativeEvent);
  }, [onReady]);
  const onCameraMoveBegin = (0, React.useCallback)(() => {
    if (onMoveBegin) onMoveBegin();
  }, [onMoveBegin]);
  const _getHandle = () => {
    return (0, _reactNative.findNodeHandle)(ref.current);
  };
  const createBuildingHighlight = (0, React.useCallback)((buildingId, color, buildingCoordinates) => {
    console.log('setting building hi command', buildingId, buildingCoordinates);
    _Wrld3dViewNativeComponent.Commands.setBuildingHighlight(ref.current, buildingId, color, buildingCoordinates);
  }, []);
  const removeBuildingHighlightComand = (0, React.useCallback)(buildingId => {
    _Wrld3dViewNativeComponent.Commands.removeBuildingHighlight(ref.current, buildingId);
  }, []);
  const createMapViewInstance = (0, React.useCallback)(() => {
    _Wrld3dViewNativeComponent.Commands.create(ref.current, _getHandle() + '');
  }, []);
  return /*#__PURE__*/React.createElement(_Wrld3dViewNativeComponent.default, _extends({
    ref: ref
  }, props, {
    onCameraMoveEnd: onCameraMoveEnd,
    onMapReady: onMapReady,
    onCameraMoveBegin: onCameraMoveBegin,
    onClickBuilding: onClickBuilding
  }));
});
exports.Wrld3dView = Wrld3dView;
//# sourceMappingURL=index.js.map