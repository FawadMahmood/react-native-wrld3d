export * from './Wrld3dViewNativeComponent';
import { CallOutView } from './CalloutView';
import * as React from 'react';
import { ViewStyle } from 'react-native';
import type { BuildingInformationType, Coordinates, MapReadyPayload } from './types';
interface ModuleEvents {
    onMapReady?: (props: MapReadyPayload) => void;
    onCameraMoveEnd?: (props: Coordinates) => void;
    onCameraMoveBegin?: () => void;
    style: ViewStyle;
    children?: Element;
    initialRegion?: Coordinates;
    zoomLevel?: number;
    onClickBuilding?: (props: BuildingInformationType) => void;
}
interface Map3dDirectEvents {
    setBuildingHighlight: (buildingId: string, color: string, buildingCoordinates: Coordinates) => void;
    removeBuildingHighlight: (buildingId: string) => void;
    findBuildingOnCoordinates: (coordinates: Coordinates) => Promise<BuildingInformationType>;
}
export declare type MapDirectEventsType = Map3dDirectEvents;
export declare const Wrld3dView: React.ForwardRefExoticComponent<ModuleEvents & React.RefAttributes<Map3dDirectEvents>>;
export { CallOutView };
//# sourceMappingURL=index.d.ts.map