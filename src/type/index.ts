
export type MapViewNativeComponentType = NativeCommands;


export type Region = {
    latitude: number,
    longitude: number,
}


export const Commands = {
    'animateToRegion': '2',
    'moveToBuilding': '3'
}


type MapCenter = {
    region: Region,
    zoom: number,
}

type BuildingInformation = {
    buildingId?: string;
    buildingAvailable: boolean,
}


type Success = {
    success: boolean;
}

// {"region": {"latitude": 24.882613347789693, "longitude": 67.05802695237223}, "zoom": 15}

export interface NativeCommands {
    moveToRegion: (args: { location: Region, animated: boolean, duration?: number, zoomLevel?: number }) => void
    moveToBuilding: (args: { location: Region, highlight?: boolean, zoomLevel?: number, animated?: boolean, duration?: number }) => void
    getMapCenter: () => Promise<MapCenter>,
    getBuildingInformation: (args: { location: Region, animateToBuilding?: boolean, duration?: number, zoomLevel?: number, highlight?: boolean }) => Promise<BuildingInformation>,
    setBuildingHighlight: (args: { location: Region }) => Promise<Success>,

}