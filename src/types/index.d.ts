import { NativeSyntheticEvent } from 'react-native';

type Coordinates = {
  longitude: number;
  latitude: number;
};

type MapReadyPayload = {
  success: boolean;
};

type BuildingInformationType = {
  buildingId: string;
  buildingHeight: number;
  longitude: string;
  latitude: string;
};

export type onMapCameraChangedType = NativeSyntheticEvent<Coordinates>;

export type onMapReadyType = NativeSyntheticEvent<MapReadyPayload>;

export type BuildingInformationTypeEvent =
  NativeSyntheticEvent<BuildingInformationType>;
