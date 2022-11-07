"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.Wrld3dView = exports.Marker = void 0;

var _react = _interopRequireWildcard(require("react"));

var _reactNative = require("react-native");

var _MapViewManager = require("./MapViewManager");

var _type = require("./type");

function _getRequireWildcardCache(nodeInterop) { if (typeof WeakMap !== "function") return null; var cacheBabelInterop = new WeakMap(); var cacheNodeInterop = new WeakMap(); return (_getRequireWildcardCache = function (nodeInterop) { return nodeInterop ? cacheNodeInterop : cacheBabelInterop; })(nodeInterop); }

function _interopRequireWildcard(obj, nodeInterop) { if (!nodeInterop && obj && obj.__esModule) { return obj; } if (obj === null || typeof obj !== "object" && typeof obj !== "function") { return { default: obj }; } var cache = _getRequireWildcardCache(nodeInterop); if (cache && cache.has(obj)) { return cache.get(obj); } var newObj = {}; var hasPropertyDescriptor = Object.defineProperty && Object.getOwnPropertyDescriptor; for (var key in obj) { if (key !== "default" && Object.prototype.hasOwnProperty.call(obj, key)) { var desc = hasPropertyDescriptor ? Object.getOwnPropertyDescriptor(obj, key) : null; if (desc && (desc.get || desc.set)) { Object.defineProperty(newObj, key, desc); } else { newObj[key] = obj[key]; } } } newObj.default = obj; if (cache) { cache.set(obj, newObj); } return newObj; }

function _extends() { _extends = Object.assign ? Object.assign.bind() : function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; }; return _extends.apply(this, arguments); }

const createFragment = viewId => _reactNative.UIManager.dispatchViewManagerCommand(viewId, // we are calling the 'create' command
// @ts-ignore
_reactNative.UIManager.Wrld3dView.Commands.create.toString(), [viewId]);

const processCommand = (viewId, command, args) => _reactNative.UIManager.dispatchViewManagerCommand(viewId, command, args);

const Marker = _MapViewManager.Marker;
exports.Marker = Marker;

const MapComponent = (props, forwardedRef) => {
  const viewId = (0, _react.useRef)(0);
  const isMapReady = (0, _react.useRef)();

  const moveToRegion = _ref => {
    let {
      location,
      animated = false,
      duration = 5000,
      zoomLevel = -1
    } = _ref;

    if (_reactNative.Platform.OS === "android") {
      processCommand(viewId.current, _type.Commands.animateToRegion, [location, animated, duration, zoomLevel]);
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

    if (_reactNative.Platform.OS === "android") {
      processCommand(viewId.current, _type.Commands.moveToBuilding, [location, highlight, zoomLevel, animated, duration]);
    }
  };

  const [dimensions, setDimensions] = (0, _react.useState)({
    width: 0,
    height: 0
  });
  const ref = (0, _react.useRef)(null);
  (0, _react.useEffect)(() => {
    if (_reactNative.Platform.OS === "android") {
      viewId.current = (0, _reactNative.findNodeHandle)(ref.current);
      createFragment(viewId.current);
    }
  }, []);
  const mapStyles = {
    // converts dpi to px, provide desired height
    height: _reactNative.PixelRatio.getPixelSizeForLayoutSize(dimensions.height),
    // converts dpi to px, provide desired width
    width: _reactNative.PixelRatio.getPixelSizeForLayoutSize(dimensions.width)
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
    const message = await _reactNative.NativeModules['MapViewModule'].getCameraBounds(viewId.current);
    return message;
  }; //mapevents


  const publicRef = {
    moveToRegion,
    moveToBuilding,
    getMapCenter
  };
  (0, _react.useImperativeHandle)(forwardedRef, () => publicRef);
  return /*#__PURE__*/_react.default.createElement(_reactNative.View, {
    onLayout: onlayout.bind(null),
    style: [props.style ? props.style : {
      width: "100%",
      height: "100%"
    }, {
      overflow: "hidden"
    }]
  }, /*#__PURE__*/_react.default.createElement(_MapViewManager.WrldMap3d, _extends({}, props, {
    style: mapStyles // @ts-ignore
    ,
    ref: ref,
    zoomLevel: props.zoomLevel ? props.zoomLevel : 12,
    initialCenter: props.initialCenter ? props.initialCenter : {
      latitude: 24.8620495,
      longitude: 67.070877
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

const Wrld3dView = /*#__PURE__*/_react.default.forwardRef(MapComponent);

exports.Wrld3dView = Wrld3dView;
//# sourceMappingURL=index.js.map