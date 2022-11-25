import { NativeModules, Platform } from 'react-native';
const LINKING_ERROR = `The package 'react-native-wrld3d' doesn't seem to be linked. Make sure: \n\n` + Platform.select({
  ios: "- You have run 'pod install'\n",
  default: ''
}) + '- You rebuilt the app after installing the package\n' + '- You are not using Expo Go\n';

// @ts-expect-error
const isTurboModuleEnabled = global.__turboModuleProxy != null;
const Wrld3dLibraryModule = isTurboModuleEnabled ? require('../NativeWrld3dLibrary').default : NativeModules.Wrld3dLibrary;
export default Wrld3dLibraryModule ? Wrld3dLibraryModule : new Proxy({}, {
  get() {
    throw new Error(LINKING_ERROR);
  }
});
//# sourceMappingURL=Wrld3dLibrary.js.map