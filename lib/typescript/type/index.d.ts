export declare type MapViewNativeComponentType = NativeCommands;
export declare type Region = {
    latitude: number;
    longitude: number;
};
export declare const Commands: {
    animateToRegion: string;
    moveToBuilding: string;
};
declare type MapCenter = {
    region: Region;
    zoom: number;
};
declare type BuildingInformation = {
    buildingId?: string;
    buildingAvailable: boolean;
};
declare type Success = {
    success: boolean;
};
export interface NativeCommands {
    moveToRegion: (args: {
        location: Region;
        animated: boolean;
        duration?: number;
        zoomLevel?: number;
    }) => void;
    moveToBuilding: (args: {
        location: Region;
        highlight?: boolean;
        zoomLevel?: number;
        animated?: boolean;
        duration?: number;
    }) => void;
    getMapCenter: () => Promise<MapCenter>;
    getBuildingInformation: (args: {
        location: Region;
        animateToBuilding?: boolean;
        duration?: number;
        zoomLevel?: number;
    }) => Promise<BuildingInformation>;
    setBuildingHighlight: (args: {
        location: Region;
    }) => Promise<Success>;
}
export {};
