import type { ViewProps } from 'react-native';
import type { HostComponent } from 'react-native';
import type { BubblingEventHandler, Double } from 'react-native/Libraries/Types/CodegenTypes';
export interface NativeProps extends ViewProps {
    onMapReady?: BubblingEventHandler<Readonly<{
        success: boolean;
    }>>;
    onCameraMoveEnd?: BubblingEventHandler<Readonly<{
        longitude: Double;
        latitude: Double;
    }>>;
    onCameraMoveBegin: BubblingEventHandler<Readonly<{}>>;
}
declare const _default: import("react-native/Libraries/Utilities/codegenNativeComponent").NativeComponentType<NativeProps>;
export default _default;
declare type Wrld3dViewNativeComponentType = HostComponent<NativeProps>;
interface NativeCommands {
    create: (viewRef: React.ElementRef<Wrld3dViewNativeComponentType>, viewId: string) => void;
}
export declare const Commands: NativeCommands;
//# sourceMappingURL=Wrld3dViewNativeComponent.d.ts.map