import { NativeSyntheticEvent } from 'react-native';
import type { Double } from 'react-native/Libraries/Types/CodegenTypes';

export type Coordinates = {
  longitude: Number;
  latitude: Number;
};

export type MapReadyPayload = {
  success: boolean;
};

export type BuildingInformationType = {
  buildingId: string;
  buildingHeight: number;
  longitude: number;
  latitude: number;
};

export type onMapCameraChangedType = NativeSyntheticEvent<Coordinates>;

export type onMapReadyType = NativeSyntheticEvent<MapReadyPayload>;

export type BuildingInformationTypeEvent =
  NativeSyntheticEvent<BuildingInformationType>;
