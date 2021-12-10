<template>
    <div id="add-entity-form"> 
        <div class="title"><h1>New Adventure</h1></div> 
        <div class="content">

            <div class="left-side">
                <input type="text" class="form-control" placeholder="Name*" v-model="v$.newAdventure.name.$model"/>
                <span class="text-danger" v-if="v$.newAdventure.name.$error">{{v$.newAdventure.name.$errors[0].$message}}</span>

                <textarea class="form-control reason-area" placeholder="Description*" v-model="v$.newAdventure.description.$model" ></textarea>
                <span class="text-danger" v-if="v$.newAdventure.description.$error">{{v$.newAdventure.description.$errors[0].$message}}</span>

                <input type="text" class="form-control max-persons" placeholder="Maximum persons*" v-model="v$.newAdventure.maxPersons.$model"/>
                <span class="text-danger" v-if="v$.newAdventure.maxPersons.$error">{{v$.newAdventure.maxPersons.$errors[0].$message}}</span>
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
                        <input type="text" class="form-control" v-model="newAdventure.fishingEquipment[index-1]"/>
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
                        <input type="text" class="form-control" v-model="newAdventure.allowedBehavior[index-1]"/>
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
                        <input type="text" class="form-control" v-model="newAdventure.unallowedBehavior[index-1]"/>
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
                <ul v-if="newAdventure.pricelistItems">
                    <li v-for="item in newAdventure.pricelistItems" :key="item.id">
                        <div class="pricelistItem">
                            <input type="text"   class="form-control" v-model="item.service" placeholder="Service*"/>
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
                <input type="number" class="form-control" v-model="newAdventure.cancellationPercentage"/><br/><hr/>
            </div>

            <div class="right-side">
                <!--Images upload -->
                <div class="upload-image">
                    <div class="upload-images">
                        <p>Upload images from your adventure: </p>
                        <input type="file" class="file-upload" @change="imageAdded"/>
                    </div>
                    <div v-if="imagesFrontend" class="images-preview">
                            <div v-for="image in imagesFrontend" :key="image">
                                <img :src="image" class="image-show" />
                            </div>
                    </div>  
                </div>
                             
                 <OpenLayersMap @change-address="changeAddress" :address ="newAdventure.address" ></OpenLayersMap>

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
    import OpenLayersMap from "@/components/entities/OpenLayersMap.vue"
    import server from '../server/index'
    import axios from 'axios'

    export default ({
        components: {
            OpenLayersMap,
        },
        setup() {
            return {v$: useValidate()}
        },
        computed:{
            token(){
                return this.$store.getters.getToken;
            }
        },
        data() {
            return{
                newAdventure: {
                    name: '',
                    description: '',
                    cancellationPercentage: 0,
                    images: [],
                    allowedBehavior: [],
                    unallowedBehavior: [],
                    fishingEquipment: [],
                    maxPersons: undefined,
                    address: {
                        streetName: '',
                        streetNumber: '',
                        postalCode: '',
                        city: '',
                        country:  '',
                        longitude: undefined,
                        latitude: undefined
                    },
                    pricelistItems: [
                        {
                            service:'',
                            price: undefined
                        }
                    ],
                    fishingInstructor: {
                        email: ''
                    }
                },
                allowedBehavior: 1,
                unallowedBehavior: 1,
                pricelistItems: 1,
                additionalServices: 1,
                fishingEquipment: 1,
                imagesFrontend: []
            }
        },
        validations() {
            return {
                newAdventure: {
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
                
                axios.post(`${server.baseUrl}/adventure/add`, this.newAdventure, {headers: headers})
                .then((response) => {
                    this.newAdventure= { name: '', description: '', cancellationPercentage: 0, images: [], allowedBehavior: [], unallowedBehavior: [],
                    address: { streetName: '',  streetNumber: '', postalcode: '', city: '', country:  '', longitude: '', latitude: '' },
                    pricelistItem: [ { service:'', price: null }, ], rooms: [ { bedNumber: undefined },], cottageOwner:{}};
                    this.$emit('entity-added', 0)
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
            changeAddress: function(data) {
                this.newAdventure.address = data
            },
            addPricelistItem: function() {
                this.pricelistItems += 1;
                this.newAdventure.pricelistItems.push({
                    service: '',
                    price: undefined
                })
            },
            removePricelistItem: function() {
                this.pricelistItems -= 1;
                this.newAdventure.pricelistItems.pop()
            },
            imageAdded: function(e) {
                const file = e.target.files[0];  
                console.log(file)        
                this.createBase64Image(file);
                this.imagesFrontend.push(URL.createObjectURL(file));
            },
            createBase64Image(file){
                const reader= new FileReader();
            
                reader.onload = (e) =>{
                    let img = e.target.result;
                     console.log(img)  
                    this.newAdventure.images.push(img);
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

  .upload-image {
      display: flex;
      flex-direction: column;
      align-items: flex-start;
  }

  .upload-images p {
      margin-left: -35px;
  }

  .images-preview{
      display: flex;
      flex-wrap: wrap;
      margin-top: 20px;
  }

  .image-show {
      width: 150px;
      height: 100px;
  }

</style>
