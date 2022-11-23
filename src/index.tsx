import MapView, { Commands } from './Wrld3dViewNativeComponent';
export * from './Wrld3dViewNativeComponent';
import * as React from 'react';

import {
  findNodeHandle,
  // NativeSyntheticEvent,useCallback
  Platform,
  ViewStyle,
} from 'react-native';
import { useRef } from 'react';

interface ModuleEvents {
  onMapReady?: (props: { success: boolean }) => void;
  onCameraMove?: (props: { longitude: number; latitude: number }) => void;
  style: ViewStyle;
}

// NativeProps &
export const Wrld3dView = (props: ModuleEvents) => {
  const mapNativeID = useRef<number>(0);

  const ref = useRef<any>(null);

  React.useEffect(() => {
    if (Platform.OS === 'android') {
      setTimeout(() => {
        createMapViewInstance();
      }, 500);
    }
  });

  // const onCameraMove = useCallback((_: NativeSyntheticEvent<Coordinates>) => {
  //   console.log(_.nativeEvent);
  // }, []);

  // const onMapReady = useCallback((_: NativeSyntheticEvent<MapReadyPayload>) => {
  //   console.log(_.nativeEvent);
  // }, []);

  const createMapViewInstance = () => {
    mapNativeID.current = findNodeHandle(ref.current) as number;
    Commands.create(ref.current, mapNativeID.current + '');
  };

  return (
    <MapView
      ref={ref}
      {...props}
      // onCameraMove={onCameraMove}
      // onMapReady={onMapReady}
    />
  );
};
