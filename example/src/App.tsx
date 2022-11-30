import * as React from 'react';
import { useCallback, useMemo, useRef } from 'react';
import {
  Dimensions,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from 'react-native';
import {
  CallOutView,
  MapDirectEventsType,
  Wrld3dView,
} from 'react-native-wrld3d';
import BottomSheet from '@gorhom/bottom-sheet';
import type { BuildingInformationType } from 'src/types';

// props: { navigation: any }
export default function App(props: { navigation: any }) {
  const { navigation } = props;
  const mapRef = React.useRef<MapDirectEventsType>();
  const [ready, setReady] = React.useState(false);
  const [moving, setMoving] = React.useState(false);
  const [building, setBuilding] = React.useState<BuildingInformationType>();
  const [highlight, setHighlight] = React.useState(false);
  const [callouts, setCallouts] = React.useState<any>([]);

  const [cache] = React.useState(false);
  const currentIndex = useRef<number>(0);
  const bottomSheetRef = useRef<BottomSheet>(null);
  const snapPoints = useMemo(() => ['9.5%', '50%'], []);

  const handleSheetChanges = useCallback((index: number) => {
    currentIndex.current = index;
  }, []);

  const toggleSheet = useCallback(() => {
    if (bottomSheetRef.current) {
      bottomSheetRef.current.snapToIndex(currentIndex.current === 0 ? 1 : 0);
    }
  }, []);

  const [location, setLocation] = React.useState({ latitude: 0, longitude: 0 });

  const onMapReady = () => {
    setReady(true);
  };

  const onCameraMoveBegin = useCallback(() => {
    setMoving(true);
  }, []);

  const dispatchNewScreen = useCallback(() => {
    navigation.push('Home');
  }, [navigation]);

  const onClickBuilding = useCallback((_: BuildingInformationType) => {
    setBuilding(_);
  }, []);

  const highlightSelectedBuilding = useCallback(() => {
    if (building && building.latitude && building.longitude && !highlight) {
      mapRef.current?.setBuildingHighlight(
        building?.buildingId as string,
        '#FFFF00',
        {
          latitude: building?.latitude,
          longitude: building?.longitude,
        }
      );
      setHighlight(true);
    }

    if (highlight) {
      mapRef.current?.removeBuildingHighlight(building?.buildingId as string);
      setHighlight(false);
    }
  }, [mapRef, building, highlight]);

  const onCameraMoveEnd = useCallback(
    (_: { longitude: number; latitude: number }) => {
      setMoving(false);
      setLocation({
        latitude: _.latitude,
        longitude: _.longitude,
      });
    },
    []
  );

  const findBuildingAtCoordinates = useCallback(async () => {
    if (location) {
      const builddingInfo = await mapRef.current
        ?.findBuildingOnCoordinates({
          latitude: location.latitude,
          longitude: location.longitude,
        })
        .catch((err) => {
          console.log('buildingInfo Got error', err);
        });

      console.log('buildingInfo Got', builddingInfo);
    }
  }, [location]);

  const addCallouts = useCallback(() => {
    setCallouts([
      {
        region: {
          latitude: 24.88261334778966,
          longitude: 67.05802695237224,
        },
        bg: 'red',
      },
      {
        region: {
          latitude: 25.882613347,
          longitude: 68.05802695,
        },
        bg: 'green',
      },
    ]);
  }, []);

  const removeCallouts = useCallback(() => {
    setCallouts([
      {
        region: {
          latitude: 25.882613347,
          longitude: 68.05802695,
        },
        bg: 'red',
      },
    ]);
  }, []);

  const addMoreCallouts = useCallback(() => {
    setCallouts([
      {
        region: {
          latitude: 25.882613347,
          longitude: 68.05802695,
        },
        bg: 'red',
      },
      {
        region: {
          latitude: 24.88261334778966,
          longitude: 67.05802695237224,
        },
        bg: 'blue',
      },
      {
        region: {
          latitude: 25.88261334778966,
          longitude: 68.05802695237224,
        },
        bg: 'green',
      },
      {
        region: {
          latitude: 28.88261334778966,
          longitude: 62.05802695237224,
        },
        bg: 'purple',
      },
      {
        region: {
          latitude: 28.88261334778966,
          longitude: 70.05802695237224,
        },
        bg: 'orange',
      },
    ]);
  }, []);

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.statusTxt}>
          {ready ? 'Map Ready' : 'Map Not Ready'}
        </Text>
        <Text style={styles.statusTxt}>
          {cache ? 'Cache Completed' : 'Cache In Progress'}
        </Text>
      </View>
      <View style={styles.header}>
        <Text style={styles.statusTxt}>{moving ? 'Moving' : 'Stopped'}</Text>
        <Text style={styles.statusTxt}>
          {'LOCATION ' +
            location.longitude.toFixed(2) +
            ',' +
            location.latitude.toFixed(2)}
        </Text>
      </View>
      <View style={styles.mapContainer}>
        <Wrld3dView
          // @ts-ignore
          ref={mapRef}
          onCameraMoveEnd={onCameraMoveEnd as any}
          onMapReady={onMapReady.bind(null)}
          onCameraMoveBegin={onCameraMoveBegin}
          style={styles.box}
          initialRegion={{
            latitude: 24.88261334778966,
            longitude: 67.05802695237224,
          }}
          zoomLevel={18}
          onClickBuilding={onClickBuilding}
        >
          {callouts &&
            callouts.map((_: any, i: number) => {
              return (
                <CallOutView
                  key={i + 'marker'}
                  region={_.region}
                  style={StyleSheet.flatten([
                    styles.callout,
                    { backgroundColor: _.bg },
                  ])}
                >
                  <View></View>
                </CallOutView>
              );
            })}
        </Wrld3dView>
      </View>

      <BottomSheet
        ref={bottomSheetRef}
        index={0}
        snapPoints={snapPoints}
        onChange={handleSheetChanges}
        enableOverDrag
      >
        <TouchableOpacity
          activeOpacity={0.98}
          onPress={toggleSheet}
          style={[styles.bottomBtn, styles.blackBG]}
        >
          <Text style={[styles.lbl, styles.whiteLbl]}>EXPAND</Text>
        </TouchableOpacity>
        <View style={styles.contentContainer}>
          <TouchableOpacity
            onPress={dispatchNewScreen}
            style={[styles.bottomBtn, styles.blackBG, styles.innerBtn]}
          >
            <Text style={[styles.lbl, styles.whiteLbl]}>NEW SCREEN</Text>
          </TouchableOpacity>

          <TouchableOpacity
            disabled={!building}
            onPress={highlightSelectedBuilding}
            style={[
              styles.bottomBtn,
              styles.blackBG,
              styles.innerBtn,
              !building && styles.disabled,
            ]}
          >
            <Text style={[styles.lbl, styles.whiteLbl]}>
              {highlight ? 'Remove Highlight' : 'Highlight Building'}
            </Text>
          </TouchableOpacity>

          <TouchableOpacity
            onPress={findBuildingAtCoordinates}
            style={[styles.bottomBtn, styles.blackBG, styles.innerBtn]}
          >
            <Text style={[styles.lbl, styles.whiteLbl]}>FIND BUILDING</Text>
          </TouchableOpacity>

          <TouchableOpacity
            onPress={addCallouts}
            style={[styles.bottomBtn, styles.blackBG, styles.innerBtn]}
          >
            <Text style={[styles.lbl, styles.whiteLbl]}>ADD CALLOUTS</Text>
          </TouchableOpacity>

          <TouchableOpacity
            onPress={removeCallouts}
            style={[styles.bottomBtn, styles.blackBG, styles.innerBtn]}
          >
            <Text style={[styles.lbl, styles.whiteLbl]}>REMOVE CALLOUTS</Text>
          </TouchableOpacity>
          <TouchableOpacity
            onPress={addMoreCallouts}
            style={[styles.bottomBtn, styles.blackBG, styles.innerBtn]}
          >
            <Text style={[styles.lbl, styles.whiteLbl]}>ADD MORE CALLOUTS</Text>
          </TouchableOpacity>
        </View>
      </BottomSheet>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  box: {
    width: '100%',
    height: '100%',
  },
  header: {
    height: 40,
    justifyContent: 'space-between',
    alignItems: 'center',
    flexDirection: 'row',
    paddingHorizontal: 10,
    backgroundColor: 'white',
  },
  statusTxt: {
    fontWeight: 'bold',
    textTransform: 'uppercase',
    color: 'black',
  },
  mapContainer: {
    flex: 1,
  },
  bottomBtn: {
    height: 50,
    backgroundColor: 'white',
    justifyContent: 'center',
    alignItems: 'center',
  },
  lbl: {
    color: 'black',
    textAlign: 'center',
  },
  contentContainer: {
    flex: 1,
    flexWrap: 'wrap',
    flexDirection: 'row',
  },
  blackBG: {
    backgroundColor: 'black',
  },
  whiteLbl: {
    color: 'white',
    fontWeight: '600',
  },
  paddingBtn: {
    padding: 0,
  },
  innerBtn: {
    width: Dimensions.get('screen').width / 2.15,
    margin: 5,
    borderRadius: 10,
  },
  disabled: {
    opacity: 0.6,
  },
  callout: { width: 100, height: 100, backgroundColor: 'white' },
});
