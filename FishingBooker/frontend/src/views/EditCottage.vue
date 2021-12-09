<template>
        <div id="add-entity-form"> 
        <div class="title"><h1>Edit '{{cottage.name}}' </h1></div> 
        <div class="content">
            <div class="left-side">
                <input type="text" class="form-control" placeholder="Name*" v-model="cottage.name"/>
                <!-- Error Message -->
                <div class="input-errors" v-for="(error, index) of v$.cottage.name.$errors" :key="index">
                    <div class="text-danger">{{ error.$message }}</div>
                </div>

                <textarea class="reason-area" placeholder="Description*" v-model="cottage.description" rows="4" cols="65"></textarea>
                <!-- Error Message -->
                <div class="input-errors" v-for="(error, index) of v$.cottage.description.$errors" :key="index">
                     <div class="text-danger">{{ error.$message }}</div>
                </div>
                <br/> <br/><hr/>

                <!-- Number of rooms and beds per room -->
                <input type="number" class="form-control" placeholder="Number of rooms*" v-model="roomsNum" @input="changeRoomsNumber()"/><br/>
                <ol v-if="roomsNum">
                    <li v-for="room in cottage.rooms" :key="room.id">
                        <div class="room-type">
                            <input type="number" class="form-control" v-model="room.bedNumber"/>
                            <label>bed room</label>
                        </div>
                    </li>
                </ol>
                <hr/>
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
                        <input type="text" class="form-control" v-model="cottage.allowedBehavior[ab-1]"/>
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
                        <input type="text" class="form-control" v-model="cottage.unallowedBehavior[index]"/>
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
                <ul v-if="cottage.pricelistItems">
                    <li v-for="item in cottage.pricelistItems" :key="item.id">
                        <div class="pricelistItem">
                            <input type="text"   class="form-control" v-model="item.service" placeholder="Service*"/>
                            <input type="number" class="form-control" v-model="item.price"   placeholder="Price*"/>
                        </div>
                    </li>
                </ul><hr/>
                <br/>
                <!--Additional services -->
                <div class="multiple-inputs">
                    <h6> Additional services: </h6>
                    <div class="icons">
                        <span><i class="fas fa-plus-square fa-2x icon"  @click="additionalServicesNum += 1"></i></span>
                        <span><i class="fas fa-minus-square fa-2x icon" @click="additionalServicesNum -= 1"></i></span>
                    </div>
                </div>
                <ul v-if="additionalServicesNum">
                    <li v-for="ads in additionalServicesNum" :key="ads">
                        <div class="pricelistItem">
                            <input type="text"   class="form-control" placeholder="Service*"/>
                            <input type="number" class="form-control" placeholder="Price*"/>
                        </div>
                    </li>
                </ul><hr/><br/>
                 <!--Cancellation percentage -->
                 <label class="percentage-label">Percentage of price we keep in case of reservation cancellation:</label>
                <input type="number" class="form-control" v-model="cottage.cancellationPercentage" placeholder="*Percentage of price you keep, in case of reservation cancellation"/><br/><hr/>
            </div>

            <div class="right-side">
                <!--Images upload -->
                <div class="upload-images">
                    <p> Upload images of your cottage: </p>
                    <input type="file" class="file-upload" @change="imageAdded"/>
                </div> <br/>
                <div v-if="imagesFrontend" class="images-preview">
                        <div v-for="image in imagesFrontend" :key="image">
                            <img :src="image" />
                        </div>
                </div>               
                 <OpenLayersMap v-if="cottage.address" @change-address="changeAddress" :existedAddress ="cottage.address" ></OpenLayersMap>
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
    import OpenLayersMap from "@/components/entities/OpenLayersMap.vue"
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
            OpenLayersMap,
        },
        props:['cottageId'],
        setup() {
            return {v$: useValidate()}
        },
        computed:{
             state(){
                return this.$store.getters.getState;
            }

        },

        watch: {
            address : function () {
                this.initAddress();
            }
        },

        created(){
            this.getData();
        },
    
        data() {
            return{
                cottage: {
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
                    rooms: [
                        {
                            bedNumber: undefined
                        }
                    ],
                    cottageOwner:{}
                },
                roomsNum: undefined,
                allowedBehaviorNum: 1,
                unallowedBehaviorNum: 1,
                pricelistItemsNum: 1,
                additionalServicesNum: 1,
            }
        },
        validations() {
            return {
                cottage: {
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
                              
            },

            getData() {
                axios
                .get(`${server.baseUrl}/cottage/getOne/` + this.cottageId)
                .then(response => {
                    this.cottage = response.data;
                    //this.roomsNum = this.cottage.rooms.length;
                    this.allowedBehaviorNum = this.cottage.allowedBehavior.length;
                    this.unallowedBehaviorNum = this.cottage.unallowedBehavior.length;
                    this.pricelistItemNum = this.cottage.pricelistItems.length;
                    console.log(this.cottage);
                })
            },

            initAddress(){
                 axios
                .get(`${server.baseUrl}/cottage/getOne/` + this.cottageId)
                .then(response => {
                    this.cottage.address = response.data.address;
                })
            },

            changeAddress(data){
                if(data)
                    this.cottage.address = data
            },

            changeRoomsNumber(){
                this.cottage.rooms = []
                for(let i = 0; i < this.roomsNum; i++){
                     this.cottage.rooms.push({
                            bedNumber: undefined
                     });
                }
            },

            addPricelistItem() {
                this.pricelistItemsNum += 1;
                this.cottage.pricelistItem.push({
                    service: '',
                    price: undefined
                })
            },

            removePricelistItem(){
                this.pricelistItemsNum -= 1;
                this.cottage.pricelistItem.pop()
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

    #add-entity-form{
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
      margin-top: 25px;
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

    p{
        font-size: 20px;
        color: #1c3146;
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
