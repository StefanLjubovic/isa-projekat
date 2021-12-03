<template>
    <div class="content">
        <div class="map-style">
            <div id="map"></div>
        </div><br/>
        <div class="fields">
            <input id="streetID"        type="text"   class="form-control"  v-model="streetName"     placeholder="Street name*"><br/>
            <input id="streetNumID"     type="text"   class="form-control"  v-model="streetNumber"   placeholder="Street number*"><br/>
            <input id="postalcodeID"    type="number" class="form-control"  v-model="postalcode"     placeholder="Postalcode*"><br/>
            <input id="cityID" 	        type="text" class="form-control"    v-model="city"           placeholder="City*"><br/>
            <input id="countryID" 	    type="text"   class="form-control"  v-model="country"        placeholder="Country*"><br/>
            <input id="latitudeID"      type="number" class="form-control"  v-model="latitude"       placeholder="Latitude*"><br/>
            <input id="longitudeID"     type="number" class="form-control"  v-model="longitude"      placeholder="Longitude*"><br/>
        </div>  
    </div>
</template>

<script>
import View from 'ol/View'
import Map from 'ol/Map'
import TileLayer from 'ol/layer/Tile' 
import OSM from 'ol/source/OSM'
import {toLonLat} from 'ol/proj';
import {fromLonLat} from 'ol/proj';
import 'ol/ol.css'

export default {
    data() {
        return{         
            streetName: '',
            streetNumber: '',
            postalcode: '',
            city: '',
            country: '',
            longitude: '',
            latitude: ''               
        }
    },
    mounted() {
      var center = fromLonLat([19.41, 44.82]);

      var mapSearch = new Map({
        target: 'map',
        layers: [
          // adding a background tiled layer
          new TileLayer({
          source: new OSM() // tiles are served by OpenStreetMap       
        }),
        ],
		view : new View({
		center:center,
		zoom: 7,
	}),
      })

      mapSearch.on('click', function (evt) {          
             var coord = toLonLat(evt.coordinate);
              reverseGeocode(coord);
      })
    },
}
    setTimeout(() => { this.map.updateSize(); });

    function reverseGeocode (coords) {
        fetch('http://nominatim.openstreetmap.org/reverse?format=json&lon=' + coords[0] + '&lat=' + coords[1])
            .then(function (response) {
                return response.json();
            }).then(function (json) {
            
            //https://stackoverflow.com/questions/56348513/how-to-change-v-model-value-from-js
            //LONGITUDE
            let elem = document.getElementById("longitudeID");
            elem.value = coords[0].toFixed(2);
            elem.dispatchEvent(new Event('input'));
            
            //LATITUDE
            let el = document.getElementById("latitudeID");
                el.value = coords[1].toFixed(2);
                el.dispatchEvent(new Event('input'));
                
            //STREET
            if (json.address.road) {
                let el = document.getElementById("streetID");
                el.value = json.address.road;
                el.dispatchEvent(new Event('input'));
            } 

            //STREET NUMBER
            if(json.address.streetNumber){
                let el = document.getElementById("streetNumID");
                el.value = json.address.streetNumber;
                el.dispatchEvent(new Event('input'));
            }

            //CITY
            if (json.address.city) {
                let el = document.getElementById("cityID");
                el.value = json.address.city;
                el.dispatchEvent(new Event('input'));
             } else if (json.address.city_district) {
                let el = document.getElementById("cityID");
                el.value = json.address.city_district;
                el.dispatchEvent(new Event('input'));
            }
                
            //POSTALCODE
            if (json.address.postcode) {
                let el = document.getElementById("postalcodeID");
                el.value = json.address.postcode;
                el.dispatchEvent(new Event('input'));
            } 
                
            //COUNTRY
            if (json.address.postcode) {
                let el = document.getElementById("countryID");
                el.value = json.address.country;
                el.dispatchEvent(new Event('input'));
             } 
          });
        }
    
</script>

<style scoped>
    .content{
        display:flex;
        flex-direction: column;
    }

    .map-style{ 
        width: 500px;
        height: 400px;
    }

    #map {
        width: 100%;
        height: 100%;
    }

    .fields{
        width: 600px;
        margin-left: 10%;
    }

    .form-control{
        border-radius:1.5rem;
        width: 400px;
        height: 40px;  
    }
</style>