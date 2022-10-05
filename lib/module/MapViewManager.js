import { Platform, requireNativeComponent, UIManager } from 'react-native';
const LINKING_ERROR = `The package 'react-native-wrld3d' doesn't seem to be linked. Make sure: \n\n` + Platform.select({
  ios: "- You have run 'pod install'\n",
  default: ''
}) + '- You rebuilt the app after installing the package\n' + '- You are not using Expo managed workflow\n';
export const ComponentName = 'Wrld3dView';
const WrldMap3d = UIManager.getViewManagerConfig(ComponentName) != null ? requireNativeComponent(ComponentName) : () => {
  throw new Error(LINKING_ERROR);
};
export { WrldMap3d };
//# sourceMappingURL=MapViewManager.js.map