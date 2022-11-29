import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent';
import type { ViewProps } from 'react-native';
import codegenNativeCommands from 'react-native/Libraries/Utilities/codegenNativeCommands';
import type { HostComponent } from 'react-native';

export interface NativeProps extends ViewProps {}

export default codegenNativeComponent<NativeProps>(
  'Wrld3dCallOutView'
) as HostComponent<NativeProps>;

export type Wrld3dViewNativeComponentType = HostComponent<NativeProps>;

interface NativeCommands {}

export const Commands: NativeCommands = codegenNativeCommands<NativeCommands>({
  supportedCommands: [],
});
