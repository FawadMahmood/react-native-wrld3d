import * as React from 'react';

import { Image, StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { Wrld3dView, Marker } from 'react-native-wrld3d';
// , 1, 2
export default function App() {
  const [markets, setMarkers] = React.useState([
    {
      location: {
        latitude: 37.802355,
        longitude: -122.405848
      },
      image: "https://i.pinimg.com/564x/5a/6b/16/5a6b16956a2753892d9ee5714f6f112a.jpg"
    },
    {
      location: {
        latitude: 39.802355,
        longitude: -122.405848
      },
      image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXYQ_eIl5X_voFg97k86FOzaNAj2-5ext_Jq-5AhcTDJlCcbJjjhqhHc_Vw4JO_Q8CLTc&usqp=CAU"
    },
    {
      location: {
        latitude: 40.802355,
        longitude: -122.405848
      },
      image: "https://i.pinimg.com/736x/46/46/3f/46463f00c0db960a677c04f072238b82.jpg"
    }
  ]);



  const onAddMarker = () => {
    setMarkers([
      {
        location: {
          latitude: 37.802355,
          longitude: -122.405848
        },
        image: "https://images.unsplash.com/photo-1463453091185-61582044d556?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTl8fHJhbmRvbSUyMHBlb3BsZXxlbnwwfHwwfHw%3D&w=1000&q=80"
      },
      {
        location: {
          latitude: 39.802355,
          longitude: -122.405848
        },
        image: "https://images.unsplash.com/photo-1485206412256-701ccc5b93ca?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTJ8fHJhbmRvbSUyMHBlb3BsZXxlbnwwfHwwfHw%3D&w=1000&q=80"
      },
      {
        location: {
          latitude: 40.802355,
          longitude: -122.405848
        },
        image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTF_2O5ngxoY3B4Vb-d6MRsS_-F0moFSI39SQ&usqp=CAU"
      },
      {
        location: {
          latitude: 35.802355,
          longitude: -122.405848
        },
        image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSrYCNlYpuNV9PfNMzlNT9SAHTvkPLjI_OmRw&usqp=CAU"
      },
      {
        location: {
          latitude: 42.802355,
          longitude: -122.405848
        },
        image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTS9uSS5KRJCiR_cXunDz3hKtCLBzFsQAs3__yg0hkpt7kt4AJAxq1SwGg0250nGqDOLSc&usqp=CAU"
      }
    ])
  }

  const removeMarker = () => {
    // setMarkers([]);
    setMarkers([
      {
        location: {
          latitude: 65.802355,
          longitude: -122.405848
        },
        image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTS9uSS5KRJCiR_cXunDz3hKtCLBzFsQAs3__yg0hkpt7kt4AJAxq1SwGg0250nGqDOLSc&usqp=CAU"

      },
      {
        location: {
          latitude: 62.802355,
          longitude: -122.405848
        },
        image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSrYCNlYpuNV9PfNMzlNT9SAHTvkPLjI_OmRw&usqp=CAU"
      }
    ]);
  }
  //  key={index}
  //  key={index}
  return (
    <View>
      <Wrld3dView
        initialCenter={{
          latitude: 37.7858,
          longitude: -122.401
        }}
        zoomLevel={5}
        key={'Wrld3dView'} style={{ width: "100%", height: "80%" }}>
        {markets.map((marker, index) => {
          if (index > 3) {
            return (
              <Marker elevationMode="HeightAboveGround" elevation={150} key={index} location={marker.location} style={{ width: 90, height: 90 }}>
                <TouchableOpacity style={{ overflow: "hidden", width: "100%", height: "100%", borderRadius: 50, justifyContent: "center", alignItems: "center" }}>
                  <Image resizeMode='cover' source={{ uri: marker.image }} style={{ width: "100%", height: "100%" }} />
                </TouchableOpacity>
              </Marker>
            )
          }


          return (
            <Marker elevationMode="HeightAboveGround" elevation={150} key={index} location={marker.location} style={{ width: 90, height: 90 }}>
              <TouchableOpacity style={{ overflow: "hidden", width: "100%", height: "100%", borderRadius: 50, justifyContent: "center", alignItems: "center" }}>

                <Image resizeMode='cover' source={{ uri: marker.image }} style={{ width: "100%", height: "100%" }} />
              </TouchableOpacity>
            </Marker>
          )
        })}
      </Wrld3dView>

      <TouchableOpacity onPress={onAddMarker.bind(null)} style={{ width: "100%", height: 45, backgroundColor: "blue" }}>
        <Text style={{ fontSize: 30, color: "white" }}>{"Add a marker"}</Text>
      </TouchableOpacity>

      <TouchableOpacity onPress={removeMarker.bind(null)} style={{ width: "100%", height: 45, backgroundColor: "blue" }}>
        <Text style={{ fontSize: 30, color: "white" }}>{"Add a marker"}</Text>
      </TouchableOpacity>
    </View>

  );
}


const styles = StyleSheet.create({
  container: {
    flex: 1
  },
  box: {
    width: 200,
    height: 200,
  },
});
