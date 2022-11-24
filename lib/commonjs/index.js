"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
var _exportNames = {
  Wrld3dView: true
};
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
var React = _interopRequireWildcard(require("react"));
var _reactNative = require("react-native");
function _getRequireWildcardCache(nodeInterop) { if (typeof WeakMap !== "function") return null; var cacheBabelInterop = new WeakMap(); var cacheNodeInterop = new WeakMap(); return (_getRequireWildcardCache = function (nodeInterop) { return nodeInterop ? cacheNodeInterop : cacheBabelInterop; })(nodeInterop); }
function _interopRequireWildcard(obj, nodeInterop) { if (!nodeInterop && obj && obj.__esModule) { return obj; } if (obj === null || typeof obj !== "object" && typeof obj !== "function") { return { default: obj }; } var cache = _getRequireWildcardCache(nodeInterop); if (cache && cache.has(obj)) { return cache.get(obj); } var newObj = {}; var hasPropertyDescriptor = Object.defineProperty && Object.getOwnPropertyDescriptor; for (var key in obj) { if (key !== "default" && Object.prototype.hasOwnProperty.call(obj, key)) { var desc = hasPropertyDescriptor ? Object.getOwnPropertyDescriptor(obj, key) : null; if (desc && (desc.get || desc.set)) { Object.defineProperty(newObj, key, desc); } else { newObj[key] = obj[key]; } } } newObj.default = obj; if (cache) { cache.set(obj, newObj); } return newObj; }
function _extends() { _extends = Object.assign ? Object.assign.bind() : function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; }; return _extends.apply(this, arguments); }
// NativeProps &
const Wrld3dView = props => {
  const ref = (0, React.useRef)(null);
  const {
    onCameraMoveEnd: onMove,
    onMapReady: onReady,
    onCameraMoveBegin: onMoveBegin
  } = props;
  React.useEffect(() => {
    if (_reactNative.Platform.OS === 'android') {
      setTimeout(() => {
        createMapViewInstance();
      }, 500);
    }
  });
  const onCameraMoveEnd = (0, React.useCallback)(_ => {
    if (onMove) onMove(_.nativeEvent);
  }, [onMove]);
  const onMapReady = (0, React.useCallback)(_ => {
    if (onReady) onReady(_.nativeEvent);
  }, [onReady]);
  const onCameraMoveBegin = (0, React.useCallback)(() => {
    if (onMoveBegin) onMoveBegin();
  }, [onMoveBegin]);
  const _getHandle = () => {
    return (0, _reactNative.findNodeHandle)(ref.current);
  };
  const createMapViewInstance = () => {
    _Wrld3dViewNativeComponent.Commands.create(ref.current, _getHandle() + '');
  };
  return /*#__PURE__*/React.createElement(_Wrld3dViewNativeComponent.default, _extends({
    ref: ref
  }, props, {
    onCameraMoveEnd: onCameraMoveEnd,
    onMapReady: onMapReady,
    onCameraMoveBegin: onCameraMoveBegin
  }));
};
exports.Wrld3dView = Wrld3dView;
//# sourceMappingURL=index.js.map