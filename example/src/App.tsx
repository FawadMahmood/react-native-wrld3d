import * as React from 'react';
import { useCallback } from 'react';

import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { Wrld3dView } from 'react-native-wrld3d';
// props: { navigation: any }
export default function App() {
  const [ready, setReady] = React.useState(false);
  const [moving, setMoving] = React.useState(false);
  const [instance, setInstance] = React.useState(false);

  const [location, setLocation] = React.useState({ latitude: 0, longitude: 0 });

  const [cache] = React.useState(false);

  const onMapReady = () => {
    setReady(true);
  };

  const onCameraMoveBegin = useCallback(() => {
    setMoving(true);
  }, []);

  const dispatchNewScreen = useCallback(() => {
    setInstance(true);
    // props.navigation.push('Home');
  }, []);

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
          onCameraMoveEnd={onCameraMoveEnd as any}
          onMapReady={onMapReady.bind(null)}
          onCameraMoveBegin={onCameraMoveBegin}
          style={styles.box}
        />
      </View>

      {instance && (
        <View style={styles.mapContainer}>
          <Wrld3dView
            onCameraMoveEnd={onCameraMoveEnd as any}
            onMapReady={onMapReady.bind(null)}
            onCameraMoveBegin={onCameraMoveBegin}
            style={styles.box}
          />
        </View>
      )}

      <TouchableOpacity onPress={dispatchNewScreen} style={styles.bottomBtn}>
        <Text style={styles.lbl}>DISPATCH NEW SCREEN</Text>
      </TouchableOpacity>
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
  },
});
