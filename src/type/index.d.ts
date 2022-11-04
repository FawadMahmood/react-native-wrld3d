
import type { HostComponent } from 'react-native';


export type MapViewNativeComponentType = NativeCommands;


export type Region = {
    latitude: number,
    longitude: number,
}


interface NativeCommands {
    moveToRegion: (region: Region, animated: boolean, duration?: number) => void
    moveToBuilding: (location: Region, highlight: boolean, zoomLevel: number) => void
}