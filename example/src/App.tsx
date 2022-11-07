import * as React from 'react';

import { Image, Text, TouchableOpacity, View } from 'react-native';
import { Wrld3dView, Marker, MapViewRefPropsType } from 'react-native-wrld3d';
// , 1, 2
export default function App() {

  const ref = React.useRef<MapViewRefPropsType>();


  let markers = [];
  for (let i = 0; i < 100; i++) {

    markers.push(
      {
        location: {
          latitude: 37.802355,
          longitude: -122.405848
        },
        image: "https://github.com/stasgora/round-spot/blob/master/assets/logo.png?raw=true"
      },
    )
  }


  const [markets, setMarkers] = React.useState([
    ...markers,
    {
      location: {
        latitude: 37.802355,
        longitude: -122.405848
      },
      image: "https://github.com/stasgora/round-spot/blob/master/assets/logo.png?raw=true"
    },
    {
      location: {
        latitude: 37.802380,
        longitude: -122.405848
      },
      image: "https://github.com/stasgora/round-spot/blob/master/assets/logo.png?raw=true"
    },
    {
      location: {
        latitude: 37.802395,
        longitude: -122.405848
      },
      image: "https://github.com/stasgora/round-spot/blob/master/assets/logo.png?raw=true"
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


  const animateToRegion = async () => {

    // move to region
    // ref.current?.moveToRegion({
    //   location: {
    //     latitude: 67.0708766,
    //     longitude: 24.8620518
    //   },
    //   animated: true,
    //   zoomLevel: 15,

    // })
    // move to region


    const building = await ref.current?.getBuildingInformation({
      location: {
        latitude: 24.8620518,
        longitude: 67.0708766,
      },
      animateToBuilding: true,
      zoomLevel: 18
    })

    console.log("got map center", building);
  }

  //  key={index}
  //  key={index} 24.882613347789693, 67.05802695237224
  return (
    <View>
      <Wrld3dView
        ref={ref as { current: MapViewRefPropsType }}
        initialCenter={{
          latitude: 24.882613347789693,
          longitude: 67.05802695237224
        }}
        precache
        precacheDistance={5000}
        zoomLevel={15}
        key={'Wrld3dView'}
        style={{ width: "100%", height: "80%" }}
      >
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

      <TouchableOpacity onPress={animateToRegion.bind(null)} style={{ width: "100%", height: 45, backgroundColor: "blue" }}>
        <Text style={{ fontSize: 30, color: "white" }}>{"Animate to region"}</Text>
      </TouchableOpacity>
    </View>

  );
}
