<template>
    <div id="add-entity-form"> 
        <div class="title"><h1>New Cottage</h1></div> 
        <div class="content">
            <div class="left-side">
                <input type="text" class="form-control" placeholder="Name*" v-model="newCottage.name"/>
                <!-- Error Message -->
                <div class="input-errors" v-for="(error, index) of v$.newCottage.name.$errors" :key="index">
                    <div class="text-danger">{{ error.$message }}</div>
                </div>

                <textarea class="reason-area" placeholder="Description*" v-model="newCottage.description" rows="4" cols="65"></textarea>
                <!-- Error Message -->
                <div class="input-errors" v-for="(error, index) of v$.newCottage.description.$errors" :key="index">
                     <div class="text-danger">{{ error.$message }}</div>
                </div>
                <br/> <br/><hr/>

                <!-- Number of rooms and beds per room -->
                <input type="number" class="form-control" placeholder="Number of rooms*" v-model="roomsNum" @input="changeRoomsNumber()"/><br/>
                <ol v-if="roomsNum">
                    <li v-for="room in newCottage.rooms" :key="room.id">
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
                        <input type="text" class="form-control" v-model="newCottage.allowedBehavior[ab-1]"/>
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
                        <input type="text" class="form-control" v-model="newCottage.unallowedBehavior[index]"/>
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
                <ul v-if="newCottage.pricelistItems">
                    <li v-for="item in newCottage.pricelistItems" :key="item.id">
                        <div class="pricelistItem">
                            <input type="text"   class="form-control" v-model="item.service" placeholder="Service*" :disabled="item.service == 'Standard offer'"/>
                            <input type="number" class="form-control" v-model="item.price"   placeholder="Price*"/>
                        </div>
                    </li>
                </ul><hr/>
                <br/>
                 <!--Cancellation percentage -->
                <input type="number" class="form-control" v-model="newCottage.cancellationPercentage" placeholder="*Percentage of price you keep, in case of reservation cancellation"/><br/><hr/>
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
                 <OpenLayersMap @change-address="changeAddress" :address ="newCottage.address" ></OpenLayersMap>
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
        data() {
            return{
                newCottage: {
                    name: '',
                    description: '',
                    cancellationPercentage: undefined,
                    images: [],
                    allowedBehavior: [],
                    unallowedBehavior: [],
                    address: {
                        streetName: "Bulevar Cara Lazara",
                        streetNumber: "171",
                        postalCode: "21000",
                        city: "Novi Sad",
                        country:  "Serbia",
                        longitude: 19,
                        latitude: 45
                    },
                    pricelistItems: [
                        {
                            service:'Standard offer',
                            price: 2500
                        }
                    ],
                    rooms: [
                        {
                            bedNumber: undefined
                        }
                    ],
                    cottageOwner:{
                        email: ''
                    }
                },
                ownerId: '',
                roomsNum: undefined,
                allowedBehaviorNum: 1,
                unallowedBehaviorNum: 1,
                imagesFrontend: []
            }
        },
        validations() {
            return {
                newCottage: {
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
                
                const headers = {
                    'Content-Type': 'application/json;charset=UTF-8',
                     Accept: 'application/json',
                    'Authorization': `Bearer ${this.token}`
                }
               
                axios.post(`${server.baseUrl}/cottage/add`, this.newCottage, {headers: headers})
                .then((response) => {
                    this.newCottage= { name: '', description: '', cancellationPercentage: 0, images: [], allowedBehavior: [], unallowedBehavior: [],
                    address: { streetName: '',  streetNumber: '', postalcode: '', city: '', country:  '', longitude: '', latitude: '' },
                    pricelistItems: [ { service:'', price: null }, ], rooms: [ { bedNumber: undefined },], cottageOwner:{}};
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

            changeAddress(data){ 
                this.newCottage.address = data
            },

            changeRoomsNumber(){
                this.newCottage.rooms = []
                for(let i = 0; i < this.roomsNum; i++){
                     this.newCottage.rooms.push({
                            bedNumber: undefined
                     });
                }
            },

            addPricelistItem() {
                this.newCottage.pricelistItems.push({
                    service: '',
                    price: undefined
                })
            },

            removePricelistItem(){
                if(this.newCottage.pricelistItems.length > 1)
                    this.newCottage.pricelistItems.pop()
            },

            imageAdded(e) {
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
                    this.newCottage.images.push(img);
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

    #add-entity-form{
        padding-top: 50px;
        padding-bottom: 50px;
        margin-left: 15%;
        margin-right: 15%;
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

    .file-upload{
       margin-left: 45%;  
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
