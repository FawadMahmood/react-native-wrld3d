import MapView, { Commands } from './Wrld3dViewNativeComponent';
export * from './Wrld3dViewNativeComponent';
import * as React from 'react';

import { findNodeHandle, Platform, ViewStyle } from 'react-native';
import { forwardRef, useCallback, useImperativeHandle, useRef } from 'react';
import type {
  BuildingInformationType,
  BuildingInformationTypeEvent,
  Coordinates,
  MapReadyPayload,
  onMapCameraChangedType,
  onMapReadyType,
} from './types';

interface ModuleEvents {
  onMapReady?: (props: MapReadyPayload) => void;
  onCameraMoveEnd?: (props: Coordinates) => void;
  onCameraMoveBegin?: () => void;
  style: ViewStyle;
  children?: Element;
  initialRegion?: Coordinates;
  zoomLevel?: number;
  onClickBuilding?: (props: BuildingInformationType) => void;
}

export interface Map3dDirectEvents {}

// NativeProps &
export const Wrld3dView = forwardRef(
  (props: ModuleEvents, forwardedRef: React.Ref<Map3dDirectEvents>) => {
    const ref = useRef<any>(null);
    const mapCreated = useRef<boolean>(false);

    //mapevents
    const publicRef = {};

    useImperativeHandle(forwardedRef, () => publicRef);

    const {
      onCameraMoveEnd: onMove,
      onMapReady: onReady,
      onCameraMoveBegin: onMoveBegin,
      onClickBuilding: clickBuildingUp,
    } = props;

    React.useEffect(() => {
      if (Platform.OS === 'android') {
        setTimeout(() => {
          if (!mapCreated.current) {
            mapCreated.current = true;
            createMapViewInstance();
          }
        }, 500);
      }
    });

    const onCameraMoveEnd = useCallback(
      (_: onMapCameraChangedType) => {
        if (onMove) onMove(_.nativeEvent);
      },
      [onMove]
    );

    const onClickBuilding = useCallback(
      (_: BuildingInformationTypeEvent) => {
        if (clickBuildingUp) clickBuildingUp(_.nativeEvent);
      },
      [clickBuildingUp]
    );

    const onMapReady = useCallback(
      (_: onMapReadyType) => {
        if (onReady) onReady(_.nativeEvent);
      },
      [onReady]
    );

    const onCameraMoveBegin = useCallback(() => {
      if (onMoveBegin) onMoveBegin();
    }, [onMoveBegin]);

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
        onCameraMoveEnd={onCameraMoveEnd as any}
        onMapReady={onMapReady as any}
        onCameraMoveBegin={onCameraMoveBegin as any}
        onClickBuilding={onClickBuilding as any}
      />
    );
  }
);
