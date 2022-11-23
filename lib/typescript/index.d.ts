export * from './Wrld3dViewNativeComponent';
import { ViewStyle } from 'react-native';
interface ModuleEvents {
    onMapReady?: (props: {
        success: boolean;
    }) => void;
    onCameraMove?: (props: {
        longitude: number;
        latitude: number;
    }) => void;
    onCameraMoveBegin?: () => void;
    style: ViewStyle;
    children?: Element;
}
export declare const Wrld3dView: (props: ModuleEvents) => JSX.Element;
//# sourceMappingURL=index.d.ts.map