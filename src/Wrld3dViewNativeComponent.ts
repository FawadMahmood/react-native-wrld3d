import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent';
import type { ViewProps } from 'react-native';
import codegenNativeCommands from 'react-native/Libraries/Utilities/codegenNativeCommands';
import type { HostComponent } from 'react-native';

import type {
  BubblingEventHandler,
  Double,
} from 'react-native/Libraries/Types/CodegenTypes';

export interface NativeProps extends ViewProps {
  onMapReady?: BubblingEventHandler<
    Readonly<{
      success: boolean;
    }>
  >; // onMapReadyType; //(_: onMapReadyType) => void;
  onCameraMoveEnd?: BubblingEventHandler<
    Readonly<{
      longitude: Double;
      latitude: Double;
    }>
  >; // (_: onMapCameraChangedType) => void;
  onCameraMoveBegin: BubblingEventHandler<Readonly<{}>>;
}

export default codegenNativeComponent<NativeProps>('Wrld3dView');

type Wrld3dViewNativeComponentType = HostComponent<NativeProps>;

interface NativeCommands {
  create: (
    viewRef: React.ElementRef<Wrld3dViewNativeComponentType>,
    viewId: string
  ) => void;
}

export const Commands: NativeCommands = codegenNativeCommands<NativeCommands>({
  supportedCommands: ['create'],
});
