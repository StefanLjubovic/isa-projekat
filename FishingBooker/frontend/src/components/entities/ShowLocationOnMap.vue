<template>
    <div class="content">
        <p>{{ cottage.address.streetName }} {{ cottage.address.streetNumber }}, {{ cottage.address.postalCode }}</p>
        <p>{{ cottage.address.city }}, {{ cottage.address.country }}</p>
        <br/>
        <div class="map-style">
            <div id="map"></div>
        </div><br/>
    </div>
</template>

<script>
import View from 'ol/View'
import Feature from 'ol/Feature';
import Map from 'ol/Map';
import Point from 'ol/geom/Point'
import TileLayer from 'ol/layer/Tile' 
import VectorLayer from 'ol/layer/Vector'
import OSM from 'ol/source/OSM'
import VectorSource from 'ol/source/Vector'
import {fromLonLat} from 'ol/proj';
import {Circle as CircleStyle, Fill, Stroke, Style} from 'ol/style';
import 'ol/ol.css';
//import server from '../../server';
//import axios from 'axios'


export default {
    props: ['cottage'],
    data() {
        return{ 
            entityId: this.cottage.id,
            address: this.cottage.address,
            cottageId: this.entityId,
            mapSearch: {},
            positionFeature: new Feature(),
            circle: new Style({
            image: new CircleStyle({
                radius: 10,
                fill: new Fill({
                color: '#FF0000'
                }),
                stroke: new Stroke({
                color: '#fff',
                width: 6
                })
            })
            })     
        }
    },

    created(){
        this.positionFeature.setStyle(this.circle)

      let vm = this;      

        vm.$nextTick(function () {      
           console.log(vm.cottage.address);
        });
     
    
    },

    mounted() {
        
      this.positionFeature.setStyle(this.circle)

      let vm = this;      

        vm.$nextTick(function () {      
           console.log(vm.cottage.address);
        });

      var center = fromLonLat([19.84, 45.2]);

     // console.log(this.address)
      
        this.mapSearch = new Map({
        target: 'map',
        layers: [
          new TileLayer({
          source: new OSM()      
        }),
        ],
		view : new View({
		center:center,
		zoom: 7,
	}),
      })

        var vectorLayer = new VectorLayer({
        map: this.mapSearch,
        source: new VectorSource({
            features: [this.positionFeature],
        })
        })

        const point = new Point(center);
        this.positionFeature.setGeometry(              
        center ? point : null                   
        );                                           
        const source = vectorLayer.getSource()    
        source.addFeatures(this.positionFeature)  
    }

}
  
</script>

<style scoped>
    .content{
        display:flex;
        flex-direction: column;
    }

    .map-style{ 
        width: 320px;
        height: 400px;
    }

    #map {
        width: 100%;
       height: 100%;
    }

    p {
    margin-bottom: 0;
    text-align: left;
    }
</style>