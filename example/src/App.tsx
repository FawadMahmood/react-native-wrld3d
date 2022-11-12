import * as React from 'react';

import { StyleSheet, Text, View } from 'react-native';
import { Wrld3dView } from 'react-native-wrld3d';

export default function App() {
  const [ready, setReady] = React.useState(false);
  const [cache] = React.useState(false);


  const onMapReady = () => {
    setReady(true);
  }

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.statusTxt}>{ready ? "Map Ready" : "Map Not Ready"}</Text>
        <Text style={styles.statusTxt}>{cache ? "Cache Completed" : "Cache In Progress"}</Text>
      </View>
      <Wrld3dView onMapReady={onMapReady.bind(null)} style={styles.box} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  box: {
    width: "100%",
    height: "100%",
  },
  header: {
    height: 40,
    justifyContent: "space-between",
    alignItems: "center",
    flexDirection: "row",
    paddingHorizontal: 10
  },
  statusTxt: {
    fontWeight: "bold",
    textTransform: "uppercase",
    color: "black"
  }
});
