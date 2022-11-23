import type { ViewProps } from 'react-native';
import type { HostComponent } from 'react-native';
import type { onMapCameraChangedType, onMapReadyType } from './types';
export interface NativeProps extends ViewProps {
    onMapReady?: (_: onMapReadyType) => void;
    onCameraMove?: (_: onMapCameraChangedType) => void;
}
declare const _default: import("react-native/Libraries/Utilities/codegenNativeComponent").NativeComponentType<NativeProps>;
export default _default;
declare type Wrld3dViewNativeComponentType = HostComponent<NativeProps>;
interface NativeCommands {
    create: (viewRef: React.ElementRef<Wrld3dViewNativeComponentType>, viewId: string) => void;
}
export declare const Commands: NativeCommands;
//# sourceMappingURL=Wrld3dViewNativeComponent.d.ts.map