import type { ViewProps } from 'react-native';
import type { HostComponent } from 'react-native';
export interface NativeProps extends ViewProps {
}
declare const _default: import("react-native/Libraries/Utilities/codegenNativeComponent").NativeComponentType<NativeProps>;
export default _default;
declare type Wrld3dViewNativeComponentType = HostComponent<NativeProps>;
interface NativeCommands {
    create: (viewRef: React.ElementRef<Wrld3dViewNativeComponentType>, viewId: string) => void;
}
export declare const Commands: NativeCommands;
//# sourceMappingURL=Wrld3dViewNativeComponent.d.ts.map