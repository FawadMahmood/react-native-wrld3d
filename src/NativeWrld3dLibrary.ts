import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';
import type { BuildingInformationType, Coordinates } from './types';

export interface Wrld3dModuleSpec extends TurboModule {
  findBuildingOnCoordinates: (
    tag: number,
    coordinates: Coordinates
  ) => Promise<BuildingInformationType>;
}

export default TurboModuleRegistry.getEnforcing<Wrld3dModuleSpec>(
  'Wrld3dLibrary'
);
