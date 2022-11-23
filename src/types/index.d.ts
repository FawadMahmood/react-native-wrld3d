import { NativeSyntheticEvent } from 'react-native';

type Coordinates = {
  longitude: number;
  latitude: number;
};

type MapReadyPayload = {
  success: boolean;
};

export type onMapCameraChangedType = NativeSyntheticEvent<Coordinates>;

export type onMapReadyType = NativeSyntheticEvent<MapReadyPayload>;
