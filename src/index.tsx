import MapView, { NativeProps, Commands } from './Wrld3dViewNativeComponent';
export * from './Wrld3dViewNativeComponent';
import * as React from 'react';
import { findNodeHandle, Platform } from 'react-native';
import { useRef } from 'react';

interface ModuleEvents {
  onMapReady?: (props: { success: boolean }) => void;
}

export const Wrld3dView = (props: NativeProps & ModuleEvents) => {
  const mapNativeID = useRef<number>(0);

  const ref = useRef<any>(null);

  React.useEffect(() => {
    if (Platform.OS === 'android') {
      setTimeout(() => {
        createMapViewInstance();
      }, 2000);
    }
  });

  const createMapViewInstance = () => {
    mapNativeID.current = findNodeHandle(ref.current) as number;
    Commands.create(ref.current, mapNativeID.current + '');
  };

  return <MapView ref={ref} {...props} />;
};
