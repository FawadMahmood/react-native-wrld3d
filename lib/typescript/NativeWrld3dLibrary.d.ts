import type { TurboModule } from 'react-native';
import type { BuildingInformationType, Coordinates } from './types';
export interface Wrld3dModuleSpec extends TurboModule {
    findBuildingOnCoordinates: (tag: number, coordinates: Coordinates) => Promise<BuildingInformationType>;
}
declare const _default: Wrld3dModuleSpec;
export default _default;
//# sourceMappingURL=NativeWrld3dLibrary.d.ts.map