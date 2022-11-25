import type { ViewProps } from 'react-native';
import type { HostComponent } from 'react-native';
import type { BubblingEventHandler, Double } from 'react-native/Libraries/Types/CodegenTypes';
import type { Coordinates } from './types';
export interface NativeProps extends ViewProps {
    onMapReady?: BubblingEventHandler<Readonly<{
        success: boolean;
    }>>;
    onCameraMoveEnd?: BubblingEventHandler<Readonly<{
        longitude: Double;
        latitude: Double;
    }>>;
    onCameraMoveBegin?: BubblingEventHandler<Readonly<{}>>;
    onClickBuilding?: BubblingEventHandler<Readonly<{
        buildingId: string;
        buildingHeight: Double;
        longitude: Double;
        latitude: Double;
    }>>;
}
declare const _default: HostComponent<NativeProps>;
export default _default;
declare type Wrld3dViewNativeComponentType = HostComponent<NativeProps>;
interface NativeCommands {
    create: (viewRef: React.ElementRef<Wrld3dViewNativeComponentType>, viewId: string) => void;
    setBuildingHighlight: (viewRef: React.ElementRef<Wrld3dViewNativeComponentType>, buildingId: string, color: string, buildingCoordinates: Coordinates) => void;
    removeBuildingHighlight: (viewRef: React.ElementRef<Wrld3dViewNativeComponentType>, buildingId: string) => void;
}
export declare const Commands: NativeCommands;
//# sourceMappingURL=Wrld3dViewNativeComponent.d.ts.map