import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent';
import type { ViewProps } from 'react-native';
import codegenNativeCommands from 'react-native/Libraries/Utilities/codegenNativeCommands';
import type { HostComponent } from 'react-native';
import type { onMapCameraChangedType, onMapReadyType } from './types';

export interface NativeProps extends ViewProps {
  onMapReady?: (_: onMapReadyType) => void;
  onCameraMove?: (_: onMapCameraChangedType) => void;
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
