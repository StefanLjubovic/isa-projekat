<template>
    <div id="add-entity-form" v-if="adventure"> 
        <div class="title"><h1>Edit &nbsp;<b>{{backupAdventure.name}}</b></h1></div> 
        <div class="content">

            <div class="left-side">
                <h6>Name: </h6>
                <input type="text" class="form-control" placeholder="Name*" v-model="v$.adventure.name.$model"/>
                <span class="text-danger" v-if="v$.adventure.name.$error">{{v$.adventure.name.$errors[0].$message}}</span><br/>

                <h6>Description: </h6>
                <textarea class="form-control reason-area" placeholder="Description*" v-model="v$.adventure.description.$model" ></textarea>
                <span class="text-danger" v-if="v$.adventure.description.$error">{{v$.adventure.description.$errors[0].$message}}</span><br/>

                <h6>Maximum persons: </h6>
                <input type="text" class="form-control max-persons" placeholder="Maximum persons*" v-model="v$.adventure.maxPersons.$model"/>
                <span class="text-danger" v-if="v$.adventure.maxPersons.$error">{{v$.adventure.maxPersons.$errors[0].$message}}</span>
                <hr/>

                <!-- Fishing equipment -->
                <div class="allowed-behavior">
                    <h6> Fishing equipment: </h6>
                    <div class="icons">
                    <span><i class="fas fa-plus-square fa-2x icon" @click="fishingEquipment += 1"></i></span>
                    <span><i class="fas fa-minus-square fa-2x icon" @click="fishingEquipment -= 1"></i></span>
                    </div>
                </div>
                <ul v-if="fishingEquipment">
                    <li v-for="index in fishingEquipment" :key="index">
                        <input type="text" class="form-control" v-model="adventure.fishingEquipment[index-1]"/>
                    </li>
                </ul>
                <br/>

                <!-- Allowed behavior -->
                <div class="allowed-behavior">
                    <h6> Allowed behavior: </h6>
                    <div class="icons">
                    <span><i class="fas fa-plus-square fa-2x icon" @click="allowedBehavior += 1"></i></span>
                    <span><i class="fas fa-minus-square fa-2x icon" @click="allowedBehavior -= 1"></i></span>
                    </div>
                </div>
                <ul v-if="allowedBehavior">
                    <li v-for="index in allowedBehavior" :key="index">
                        <input type="text" class="form-control" v-model="adventure.allowedBehavior[index-1]"/>
                    </li>
                </ul>
                <br/>

                <!-- Unallowed behavior -->
                <div class="allowed-behavior">
                    <h6> Unallowed behavior: </h6>
                    <div class="icons">
                    <span><i class="fas fa-plus-square fa-2x icon"  @click="unallowedBehavior += 1"></i></span>
                    <span><i class="fas fa-minus-square fa-2x icon" @click="unallowedBehavior -= 1"></i></span>
                    </div>
                </div>
                <ul v-if="unallowedBehavior">
                    <li v-for="index in unallowedBehavior" :key="index">
                        <input type="text" class="form-control" v-model="adventure.unallowedBehavior[index-1]"/>
                    </li>
                </ul>
                <br/>

                <!--Pricelist -->
                <div class="allowed-behavior">
                    <h6> Pricelist: </h6>
                    <div class="icons">
                        <span><i class="fas fa-plus-square fa-2x icon"  @click="addPricelistItem()"></i></span>
                        <span><i class="fas fa-minus-square fa-2x icon" @click="removePricelistItem()"></i></span>
                    </div>
                </div>
                <ul v-if="adventure.pricelistItems">
                    <li v-for="item in adventure.pricelistItems" :key="item.id">
                        <div class="pricelistItem">
                            <input type="text"   class="form-control" v-model="item.service" placeholder="Service*" :disabled="item.service == 'Standard offer'"/>
                            <input type="number" class="form-control" v-model="item.price" placeholder="Price*"/>
                        </div>
                    </li>
                </ul>
                <br/>

                <!--Additional services -->
                <!--<div class="allowed-behavior">
                    <h6> Additional services: </h6>
                    <div class="icons">
                        <span><i class="fas fa-plus-square fa-2x icon"  @click="additionalServices += 1"></i></span>
                        <span><i class="fas fa-minus-square fa-2x icon" @click="additionalServices -= 1"></i></span>
                    </div>
                </div>
                <ul v-if="additionalServices">
                    <li v-for="index in additionalServices" :key="index">
                        <div class="pricelistItem">
                            <input type="text"   class="form-control" placeholder="Service*"/>
                            <input type="number" class="form-control" placeholder="Price*"/>
                        </div>
                    </li>
                </ul>
                <br/>-->

                <!--Cancellation percentage -->
                <p class="percentage-label">Percentage of price you keep, in case of reservation cancellation:</p>
                <input type="number" class="form-control" v-model="adventure.cancellationPercentage"/><br/><hr/>
            </div>

            <div class="right-side">
                <!--Images upload -->
                <div class="upload-images">
                    <p> Upload images of your adventure: </p>
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
                    <br/>
                    <h5>ADDRESS </h5>
                    <h6>Street name: </h6>
                    <input id="streetID"        type="text"   class="form-control"  v-model="adventure.address.streetName"     placeholder="Street name*">  <br/>
                    <h6>Street number: </h6>
                    <input id="streetNumID"     type="text"   class="form-control"  v-model="adventure.address.streetNumber"   placeholder="Street number*"><br/>
                    <h6>Postal code: </h6>
                    <input id="postalcodeID"    type="text"   class="form-control"  v-model="adventure.address.postalCode"     placeholder="Postalcode*">   <br/>
                    <h6>City: </h6>
                    <input id="cityID" 	        type="text"   class="form-control"  v-model="adventure.address.city"           placeholder="City*">         <br/>
                    <h6>Country: </h6>
                    <input id="countryID" 	    type="text"   class="form-control"  v-model="adventure.address.country"        placeholder="Country*">      <br/>
                    <h6>Latitude (optional): </h6>
                    <input id="latitudeID"      type="number" class="form-control"  v-model="adventure.address.latitude"       placeholder="Latitude*">     <br/>
                    <h6>Longitude (optional): </h6>
                    <input id="longitudeID"     type="number" class="form-control"  v-model="adventure.address.longitude"      placeholder="Longitude*">    <br/>
                </div> 
                <!-- <OpenLayersMap @change-address="changeAddress" :address ="adventure.address" ></OpenLayersMap>-->

                <br/><br/>
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
    import {required, numeric} from '@vuelidate/validators'
    //import OpenLayersMap from "@/components/entities/OpenLayersMap.vue"
    import server from '../server/index'
    import axios from 'axios'

    export default ({
        components: {
            //OpenLayersMap,
        },
        props:['adventureId'],
        setup() {
            return {v$: useValidate()}
        },
        computed:{
            token(){
                return this.$store.getters.getToken;
            }
        },
        created(){
            this.getData();
        },
        data() {
            return{
                adventure: undefined,
                backupAdventure: {},
                allowedBehavior: 1,
                unallowedBehavior: 1,
                pricelistItems: 1,
                additionalServices: 1,
                fishingEquipment: 1,
                imagesFrontend: [],
                advId: this.adventureId
            }
        },
        validations() {
            return {
                adventure: {
                    name: { required },
                    description: {required},
                    maxPersons: { required, numeric }
                },
            }
        },
        methods: {
            submitForm: function() {
                this.v$.$validate();  

                const headers = {
                    'Content-Type': 'application/json;charset=UTF-8',
                     Accept: 'application/json',
                    'Authorization': `Bearer ${this.token}`
                }
                
                this.adventure.allowedBehavior = this.adventure.allowedBehavior.slice(0, this.allowedBehavior)
                this.adventure.unallowedBehavior = this.adventure.unallowedBehavior.slice(0, this.unallowedBehavior)
                this.adventure.fishingEquipment = this.adventure.fishingEquipment.slice(0, this.fishingEquipment)
                console.log(this.adventure)
                this.adventure.fishingInstructor = {}
                axios.put(`${server.baseUrl}/adventure/update`, this.adventure, {headers: headers})
                .then(() => {
                    
                    this.$emit('adventure-updated', this.adventure.id)
                    this.$swal({
                        icon: 'success',
                        title: 'Successfully edited adventure!',
                        showConfirmButton: false,
                        timer: 2000
                    })
                    
                })
                .catch(() => {
                    this.$swal('Internal server error!');
                })
            },

            getData() {
                const headers = {
                    'Content-Type': 'application/json;charset=UTF-8',
                     Accept: 'application/json',
                    'Authorization': `Bearer ${this.token}`
                }

                axios
                .get(`${server.baseUrl}/adventure/` + this.advId, {headers: headers})
                .then(response => {
                    this.adventure = response.data;
                    this.imagesFrontend = this.adventure.images;
                    this.allowedBehavior = this.adventure.allowedBehavior.length;
                    this.unallowedBehavior = this.adventure.unallowedBehavior.length;
                    this.pricelistItems = this.adventure.pricelistItems.length;
                    this.fishingEquipment = this.adventure.fishingEquipment.length;
                    this.backupAdventure = {...this.adventure};

                    this.adventure.pricelistItems = [];
                    let firstEl = undefined;
                     for (let el of this.backupAdventure.pricelistItems)
                        if(el.service == 'Standard offer')
                            firstEl = el;
                    
                    this.adventure.pricelistItems.push(firstEl);
                    let otherElems = this.backupAdventure.pricelistItems.filter(function(item) { return item.service != "Standard offer"; }); 
                    this.adventure.pricelistItems.push.apply(this.adventure.pricelistItems, otherElems);
                })
            },

            changeAddress: function(data) {
                this.adventure.address = data
            },
            addPricelistItem: function() {
                this.pricelistItems += 1;
                this.adventure.pricelistItems.push({
                    service: '',
                    price: undefined
                })
            },
            removePricelistItem: function() {
                if(this.adventure.pricelistItems.length > 1){
                    this.pricelistItems -= 1;
                    this.adventure.pricelistItems.pop()
                }
            },

            removeImage(image) {
                const index = this.adventure.images.indexOf(image);
                if (index > -1) {
                  this.adventure.images.splice(index, 1);
                }
            },
            imageAdded: function(e) {
                const file = e.target.files[0];  
                console.log(file)        
                this.createBase64Image(file);
                //this.imagesFrontend.push(URL.createObjectURL(file));
            },
            createBase64Image(file){
                const reader= new FileReader();
            
                reader.onload = (e) =>{
                    let img = e.target.result;
                     console.log(img)  
                    this.adventure.images.push(img);
                }
                reader.readAsDataURL(file);
            },
        }
        
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

    #add-entity-form{
        padding-top: 50px;
        padding-bottom: 150px;
        margin-left: 15%;
        margin-right: 15%;
    }

    .content {
        display: flex;
        margin-top: 50px;
        justify-content: space-between;
    }

    .percentage-label {
        text-align: left;
    }

    .left-side {
        width: 50%;
    }
    .right-side {
        width: 50%;
    }
    .form-control{
        width: 90%;
        height: 40px;
   
    }

     ul .form-control{
        width : 96%;
        margin-left: -33px;
        margin-bottom: 10px;
    }

    .reason-area{
      margin-top: 25px;
      height: 15%;
    }

    .max-persons {
        margin-top: 25px;
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

    .allowed-behavior{
        display: flex;
        align-items: center;
        margin-bottom: 10px;
        justify-content: space-between;
    }

    .icons, .pricelistItem{
        display: flex;
        justify-content: space-between;
        margin-right: 10%;
    }

    .icons span{
        margin-left: 10px;
    }

    .pricelistItem .form-control {
        width: 53%;
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

  ul {
      list-style-type: none;
  }

  .upload-images {
      display: flex;
      flex-direction: column;
      align-items: flex-start;
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

  .image-show {
      width: 150px;
      height: 100px;
  }

  h6, h5 {
      text-align: left;
  }

  .btn-close{ 
        background-color: transparent;
        margin-left: 2px;
        margin-bottom: 25%;
    }

    .display-images{
        display: flex;
        margin-left: 10%;
        margin-bottom: 5px;
    }

</style>
