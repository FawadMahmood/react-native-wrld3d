import * as React from 'react';
import { useCallback } from 'react';

import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { Wrld3dView } from 'react-native-wrld3d';
// props: { navigation: any }
export default function App() {
  const [ready, setReady] = React.useState(false);
  const [cache] = React.useState(false);

  const onMapReady = () => {
    setReady(true);
  };

  const dispatchNewScreen = useCallback(() => {
    // props.navigation.push('Home');
  }, []);

  const onCameraMove = useCallback(
    (_: { longitude: number; latitude: number }) => {
      console.log('on camera moved', _);
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
      <View style={styles.mapContainer}>
        <Wrld3dView
          onCameraMove={onCameraMove}
          onMapReady={onMapReady.bind(null)}
          style={styles.box}
        />
      </View>

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
