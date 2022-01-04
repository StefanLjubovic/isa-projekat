<template>
        <div id="edit-entity-form"> 
        <div class="title"><h1>Edit '{{backupShip.name}}' </h1></div> 
        <div class="content">
            <div class="left-side">
                <input type="text" class="form-control" placeholder="Name*" v-model="ship.name"/>
                <!-- Error Message -->
                <div class="input-errors" v-for="(error, index) of v$.ship.name.$errors" :key="index">
                    <div class="text-danger">{{ error.$message }}</div>
                </div><br/>

                <h6> Description: </h6>
                <textarea class="reason-area" placeholder="Description*" v-model="ship.description" rows="4" cols="65"></textarea>
                <!-- Error Message -->
                <div class="input-errors" v-for="(error, index) of v$.ship.description.$errors" :key="index">
                     <div class="text-danger">{{ error.$message }}</div>
                </div>
                <br/><hr/>
                <!-- Ship properties -->
                <div class="ship-properties">
                    <h6>Capacity: </h6>
                    <input type="number" class="form-control" v-model="ship.capacity"/><br/>
                    <h6>Type: </h6>
                    <input type="text" class="form-control" v-model="ship.type"/><br/>
                    <h6>Length: </h6>
                    <input type="number" class="form-control" v-model="ship.length"/><br/>
                    <h6>Engine number: </h6>
                    <input type="number" class="form-control" v-model="ship.engineNumber"/><br/>
                    <h6>Engine power: </h6>
                    <input type="number" class="form-control" v-model="ship.enginePower"/><br/>
                    <h6>Max speed: </h6>
                    <input type="number" class="form-control" v-model="ship.maxSpeed"/><br/><hr/>
                </div>
                <!-- Navigation equipment-->
                <div class="ship-properties">
                    <h6> Navigation equipment: </h6>
                    <div class="navigation-equipment-inputs">
                        <span>
                            <input type="checkbox" id="gps" value="0" v-model="ship.navigationEquipment">
                            <label for="gps">GPS</label>
                        </span>
                        <span>
                            <input type="checkbox" id="radar" value="1" v-model="ship.navigationEquipment">
                            <label for="radar">Radar</label>
                        </span>
                        <span>
                            <input type="checkbox" id="vhfradio" value="2" v-model="ship.navigationEquipment">
                            <label for="vhfradio">VHF radio</label>
                        </span>
                        <span>
                            <input type="checkbox" id="fishfinder" value="3" v-model="ship.navigationEquipment">
                            <label for="fishfinder">Fishfinder</label>
                        </span>
                    </div>
                </div><hr/>
                <br/>
                <!-- Fishing equipment -->
                <div class="multiple-inputs">
                    <h6> Fishing equipment: </h6>
                    <div class="icons">
                    <span><i class="fas fa-plus-square fa-2x icon"  @click="fishingEquipmentNum += 1"></i></span>
                    <span><i class="fas fa-minus-square fa-2x icon" @click="fishingEquipmentNum -= 1"></i></span>
                    </div>
                </div>
                <ul v-if="fishingEquipmentNum">
                    <li v-for="fe in fishingEquipmentNum" :key="fe">
                        <input type="text" class="form-control" v-model="ship.fishingEquipment[fe-1]"/>
                    </li>
                </ul><hr/>
                <br/>
                <!-- Allowed behavior -->
                <div class="multiple-inputs">
                    <h6> Allowed behavior: </h6>
                    <div class="icons">
                    <span><i class="fas fa-plus-square fa-2x icon"  @click="allowedBehaviorNum += 1"></i></span>
                    <span><i class="fas fa-minus-square fa-2x icon" @click="allowedBehaviorNum -= 1"></i></span>
                    </div>
                </div>
                <ul v-if="allowedBehaviorNum">
                    <li v-for="ab in allowedBehaviorNum" :key="ab">
                        <input type="text" class="form-control" v-model="ship.allowedBehavior[ab-1]"/>
                    </li>
                </ul><hr/>
                <br/>
                <!-- Unallowed behavior -->
                <div class="multiple-inputs">
                    <h6> Unallowed behavior: </h6>
                    <div class="icons">
                    <span><i class="fas fa-plus-square fa-2x icon"  @click="unallowedBehaviorNum += 1"></i></span>
                    <span><i class="fas fa-minus-square fa-2x icon" @click="unallowedBehaviorNum -= 1"></i></span>
                    </div>
                </div>
                <ul v-if="unallowedBehaviorNum">
                    <li v-for="(ub,index) in unallowedBehaviorNum" :key="ub">
                        <input type="text" class="form-control" v-model="ship.unallowedBehavior[index]"/>
                    </li>
                </ul><hr/>
                <br/>
                <!--Pricelist -->
                <div class="multiple-inputs">
                    <h6> Pricelist: </h6>
                    <div class="icons">
                        <span><i class="fas fa-plus-square fa-2x icon"  @click="addPricelistItem()"></i></span>
                        <span><i class="fas fa-minus-square fa-2x icon" @click="removePricelistItem()"></i></span>
                    </div>
                </div>
                <ul v-if="ship.pricelistItems">
                    <li v-for="item in ship.pricelistItems" :key="item.id">
                        <div class="pricelistItem">
                            <input type="text"   class="form-control" v-model="item.service" placeholder="Service*"/>
                            <input type="number" class="form-control" v-model="item.price"   placeholder="Price*"/>
                        </div>
                    </li>
                </ul><hr/>
                <br/>
                 <!--Cancellation percentage -->
                 <label class="percentage-label">Percentage of price we keep in case of reservation cancellation:</label>
                <input type="number" class="form-control" v-model="ship.cancellationPercentage" placeholder="*Percentage of price you keep, in case of reservation cancellation"/><br/><hr/>
            </div>

            <div class="right-side">
                <!--Images upload -->
                <div class="upload-images">
                    <p> Upload images of your ship: </p>
                    <input type="file" class="file-upload" @change="imageAdded"/>
                </div> <br/>
                <div v-if="imagesFrontend" class="images-preview">
                        <div class="display-images" v-for="image in imagesFrontend" :key="image">
                            <img :src="image" />
                            <button class="btn btn-close close" @click="removeImage(image)"></button>
                        </div>
                </div> <hr/><br/>
                <!-- Address -->
                 <div class="fields">
                     <h6> Address: </h6>
                    <input id="streetID"        type="text"   class="form-control"  v-model="ship.address.streetName"     placeholder="Street name*">  <br/>
                    <input id="streetNumID"     type="text"   class="form-control"  v-model="ship.address.streetNumber"   placeholder="Street number*"><br/>
                    <input id="postalcodeID"    type="text"   class="form-control"  v-model="ship.address.postalCode"     placeholder="Postalcode*">   <br/>
                    <input id="cityID" 	        type="text"   class="form-control"  v-model="ship.address.city"           placeholder="City*">         <br/>
                    <input id="countryID" 	    type="text"   class="form-control"  v-model="ship.address.country"        placeholder="Country*">      <br/>
                    <input id="latitudeID"      type="number" class="form-control"  v-model="ship.address.latitude"       placeholder="Latitude*">     <br/>
                    <input id="longitudeID"     type="number" class="form-control"  v-model="ship.address.longitude"      placeholder="Longitude*">    <br/>
                </div>              
                 <!--OpenLayersMap v-if="ship.address" @change-address="changeAddress" :existedAddress ="ship.address" ></OpenLayersMap-->
                 <div class="btn-div">
                     <button class="btn save-button" @click.prevent="submitForm()">Confirm</button> 
                     <button class="btn cancel-button">Cancel</button>
                 </div>
            </div>
        </div>
    </div>
</template>

<script>
    import useValidate from '@vuelidate/core'
    import {required} from '@vuelidate/validators' 
    import server from '../server/index'
    import axios from 'axios'

    export function validName(name) {
        let validNamePattern = new RegExp("^[a-zA-Z]+(?:[-'\\s][a-zA-Z]+)*$");
        if (validNamePattern.test(name))
            return true;
        
        return false;
    }

    export default ({
        components: {
            //OpenLayersMap,
        },
        props:['shipId'],
        setup() {
            return {v$: useValidate()}
        },
        computed:{
             state(){
                return this.$store.getters.getState;
            },
             token(){
                 return this.$store.getters.getToken;
             }
        },
        created(){
            this.getData();
        }, 
        data() {
            return{
                ship: {
                    name: '',
                    description: '',
                    cancellationPercentage: undefined,
                    images: [],
                    allowedBehavior: [],
                    unallowedBehavior: [],
                    address: {
                        streetName: "",
                        streetNumber: "",
                        postalCode: "",
                        city: "",
                        country:  "",
                        longitude: undefined,
                        latitude: undefined
                    },
                    pricelistItems: [
                        {
                            service:'',
                            price: undefined
                        }
                    ],
                    type: '',
                    length: undefined,
                    engineNumber: undefined,
                    enginePower: undefined,
                    maxSpeed: undefined,
                    capacity: undefined,
                    navigationEquipment: [],
                    fishingEquipment: []
                },
                backupShip: {},
                imagesFrontend: [],
                allowedBehaviorNum: 1,
                unallowedBehaviorNum: 1,
                fishingEquipmentNum: 1,
                pricelistItemsNum: 1,
            }
        },
        validations() {
            return {
                ship: {
                    name: { 
                        required, name_validation: {
                            $validator: validName,
                             $message: 'Invalid Name. Valid name only contain letters, dashes (-) and spaces'
                        } 
                    },
                    description: {required}
                },
            }
        },
        methods: {
            submitForm(){
                this.v$.$validate();  

                const editedShip = {
                    id: this.ship.id,
                    name: this.ship.name,
                    description: this.ship.description,
                    cancellationPercentage: this.ship.cancellationPercentage,
                    images: this.ship.images,
                    allowedBehavior: this.ship.allowedBehavior,
                    unallowedBehavior: this.ship.unallowedBehavior,
                    address: this.ship.address,
                    pricelistItems: this.ship.pricelistItems,  
                    type: this.ship.type,
                    length: this.ship.length,
                    engineNumber: this.ship.engineNumber,
                    enginePower: this.ship.enginePower,
                    maxSpeed: this.ship.maxSpeed,
                    capacity: this.ship.capacity,
                    navigationEquipment: this.ship.navigationEquipment,
                    fishingEquipment: this.ship.fishingEquipment       
                }

                const headers = {
                    'Content-Type': 'application/json;charset=UTF-8',
                     Accept: 'application/json',
                    'Authorization': `Bearer ${this.token}`
                }
                
                axios.put(`${server.baseUrl}/ship/update/`, editedShip, {headers: headers})
                .then((response) => {
                    this.$swal({
                        icon: 'success',
                        title: response.data,
                        showConfirmButton: false,
                        timer: 2000
                    })
                })
                .catch(() => {
                  this.$swal('Internal server error!');
                })         
            },
            getData() {
                axios
                .get(`${server.baseUrl}/ship/getOne/` + this.shipId)
                .then(response => {
                    this.ship = response.data;
                    this.imagesFrontend = this.ship.images;
                    this.allowedBehaviorNum = this.ship.allowedBehavior.length;
                    this.unallowedBehaviorNum = this.ship.unallowedBehavior.length;
                    this.pricelistItemNum = this.ship.pricelistItems.length;
                    this.fishingEquipmentNum = this.ship.fishingEquipmentNum.length;
                    this.backupShip = {...this.ship}
                })
            },
            addPricelistItem() {
                this.pricelistItemsNum += 1;
                this.ship.pricelistItems.push({
                    service: '',
                    price: undefined
                })
            },
            removePricelistItem(){
                this.pricelistItemsNum -= 1;
                this.ship.pricelistItems.pop()
            },
            removeImage(image) {
                const index = this.ship.images.indexOf(image);
                if (index > -1) {
                  this.ship.images.splice(index, 1);
                }
            },
            imageAdded(e) {
                const file = e.target.files[0];    
                this.createBase64Image(file);
            },
            createBase64Image(file){
                const reader= new FileReader();
            
                reader.onload = (e) =>{
                    let img = e.target.result;
                    this.cottage.images.push(img);
                }
                reader.readAsDataURL(file);
            },
        },
    })
</script>

<style scoped>
    .title{
        display: flex;
    }
    h1{
        margin-top: 10px;
        margin-left: 10px;
    }
    h6{
        text-align: left;
    }
    #edit-entity-form{
        padding-top: 50px;
        padding-bottom: 50px;
        margin-left: 15%;
        margin-right: 15%;
    }
    .percentage-label{
        text-align: left;
        margin-right: 100px;
        margin-bottom: 10px;
    }
     .content {
        display: flex;
        margin-top: 50px;
    }
    .left-side {
        width: 50%;
    }
    .right-side {
        width: 50%;
    }
    .form-control{
        border-radius:1.5rem;
        width: 90%;
        height: 40px;  
    }
    .room-type .form-control{
        width : 50%;
        margin-right: 10px;
        margin-bottom: 5px;
    }
     ul .form-control{
        width : 80%;
        margin-right: 10px;
        margin-bottom: 10px;
    }
    .reason-area{
        width: 90%;
        align-self: left;
        margin-right: 30%;
        margin-top: 15px;
        background-color: #ffffff;
        border-width: 0.5px solid rgb(248, 244, 244) ;
        font-size: 16px;
        resize: none;
        outline: none;
        -webkit-border-radius: 5px;
        -moz-border-radius: 15px;
        border-radius: 15px;
    }
    .map{
        margin-left: 10px;
        margin-right: 10px;
        width: 80%;
    }
    .left-side hr {
        margin-top: 30px;
        margin-bottom: 17px;
        width: 90%;
    }
    .room-type{
        display: flex;
        margin-left: 5%;
        align-items: center;
    }
    .multiple-inputs{
        display: flex;
        align-items: center;
        margin-bottom: 10px;
        justify-content: space-between;
    }
   .icons, .pricelistItem{
        display: flex;
        margin-right: 15%;
    }
    .icons span{
        margin-left: 10px;
    }
    .upload-images{
        margin-right: 200px;
    }
    .images-preview{
        display: contents;
         width: 40%;
         height: 30%;
     }
    .images-preview img{
        width:50%;
        height: 50%;
    }
    .display-images{
        display: flex;
        margin-left: 10%;
        margin-bottom: 5px;
    }
    p{
        font-size: 20px;
        color: #1c3146;
    }
    .btn-close{ 
        background-color: transparent;
        margin-left: 2px;
        margin-bottom: 25%;
    }
    .cancel-button {
      background-color: white;
      border-color: rgb(218, 214, 214);
      color: #2c3e50;
      width: 120px;
      margin-left: 10px;
    }
    .save-button {
      background-color: #429949;
      color: white;
      width: 120px;
      margin-right: 10px;
     }
</style>