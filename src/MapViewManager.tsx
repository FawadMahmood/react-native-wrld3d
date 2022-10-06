import { Platform, requireNativeComponent, UIManager, ViewStyle } from 'react-native';


const LINKING_ERROR =
    `The package 'react-native-wrld3d' doesn't seem to be linked. Make sure: \n\n` +
    Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
    '- You rebuilt the app after installing the package\n' +
    '- You are not using Expo managed workflow\n';

export type Wrld3dProps = {
    style: ViewStyle;
    children: Element | undefined;
};


export type MarkerProps = {
    style: ViewStyle;
    children: Element | undefined;
};

export const ComponentName = 'Wrld3dView';

export const ComponentNameMarker = 'MarkerView';


const WrldMap3d = UIManager.getViewManagerConfig(ComponentName) != null ? requireNativeComponent<Wrld3dProps>(ComponentName) : () => {
    throw new Error(LINKING_ERROR);
};


const Marker = UIManager.getViewManagerConfig(ComponentNameMarker) != null ? requireNativeComponent<MarkerProps>(ComponentNameMarker) : () => {
    throw new Error(LINKING_ERROR);
};


export { WrldMap3d, Marker }