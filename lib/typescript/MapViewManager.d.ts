import { NativeSyntheticEvent, ViewStyle } from 'react-native';
export declare type Wrld3dProps = {
    style?: ViewStyle;
    children?: Element | undefined;
    zoomLevel?: number;
    initialCenter?: {
        latitude: number;
        longitude: number;
    };
    precache?: boolean;
    precacheDistance?: number;
    onMapReady?: () => void;
    onMapCacheCompleted?: (props: NativeSyntheticEvent<{
        success: boolean;
    }>) => void;
    pickBuilding?: boolean;
};
export declare type MarkerProps = {
    style?: ViewStyle;
    children: Element | undefined;
    location: {
        latitude: number;
        longitude: number;
    };
    elevationMode?: "HeightAboveGround" | "HeightAboveSeaLevel";
    elevation?: number;
};
export declare const ComponentName = "Wrld3dView";
export declare const ComponentNameMarker = "MarkerView";
declare const WrldMap3d: import("react-native").HostComponent<Wrld3dProps> | (() => never);
declare const Marker: import("react-native").HostComponent<MarkerProps> | (() => never);
export { WrldMap3d, Marker };
