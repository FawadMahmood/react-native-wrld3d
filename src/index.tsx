import MapView, { Commands } from './Wrld3dViewNativeComponent';
export * from './Wrld3dViewNativeComponent';
import * as React from 'react';

import { findNodeHandle, Platform, ViewStyle } from 'react-native';
import { useCallback, useRef } from 'react';
import type { onMapCameraChangedType, onMapReadyType } from './types';

interface ModuleEvents {
  onMapReady?: (props: { success: boolean }) => void;
  onCameraMove?: (props: { longitude: number; latitude: number }) => void;
  style: ViewStyle;
  children?: Element;
}

// NativeProps &
export const Wrld3dView = (props: ModuleEvents) => {
  const ref = useRef<any>(null);

  const { onCameraMove: onMove, onMapReady: onReady } = props;

  React.useEffect(() => {
    if (Platform.OS === 'android') {
      setTimeout(() => {
        createMapViewInstance();
      }, 500);
    }
  });

  const onCameraMove = useCallback(
    (_: onMapCameraChangedType) => {
      if (onMove) onMove(_.nativeEvent);
    },
    [onMove]
  );

  const onMapReady = useCallback(
    (_: onMapReadyType) => {
      if (onReady) onReady(_.nativeEvent);
    },
    [onReady]
  );

  const _getHandle = () => {
    return findNodeHandle(ref.current) as number;
  };

  const createMapViewInstance = () => {
    Commands.create(ref.current, _getHandle() + '');
  };

  return (
    <MapView
      ref={ref}
      {...props}
      onCameraMove={onCameraMove}
      onMapReady={onMapReady}
    />
  );
};
