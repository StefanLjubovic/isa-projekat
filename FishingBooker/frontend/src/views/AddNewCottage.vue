<template>
    <NavBar @change-state = "changeState"></NavBar>
    <div id="add-entity-form"> 
        <div class="title"><h1>New Cottage</h1></div> 
        <div class="content">

            <div class="left-side">
                <input type="text" class="form-control" placeholder="Name*" v-model="state.name"/>
                <span class="text-danger" v-if="v$.name.$error">
                   {{v$.name.$errors[0].$message}}
                </span><br/>
                <textarea class="reason-area" placeholder="Description*" v-model="state.registrationReason" rows="4" cols="65"></textarea>
                <br/> <br/><hr/>
                <!-- Number of rooms and beds per room -->
                <input type="number" class="form-control" placeholder="Number of rooms*" v-model="state.roomsNumber"/>
                <span class="text-danger" v-if="v$.roomsNumber.$error">
                   {{v$.roomsNumber.$errors[0].$message}}
                </span><br/>
                <ol v-if="state.roomsNumber">
                    <li v-for="index in state.roomsNumber" :key="index">
                        <div class="room-type">
                            <input type="number" class="form-control"/>
                            <label>bed room</label>
                        </div>
                    </li>
                </ol>
                <hr/>
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
                        <input type="text" class="form-control"/>
                    </li>
                </ul><hr/>
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
                        <input type="text" class="form-control"/>
                    </li>
                </ul><hr/>
                <br/>
                <!--Pricelist -->
                <div class="allowed-behavior">
                    <h6> Pricelist: </h6>
                    <div class="icons">
                        <span><i class="fas fa-plus-square fa-2x icon"  @click="pricelistItems += 1"></i></span>
                        <span><i class="fas fa-minus-square fa-2x icon" @click="pricelistItems -= 1"></i></span>
                    </div>
                </div>
                <ul v-if="pricelistItems">
                    <li v-for="index in pricelistItems" :key="index">
                        <div class="pricelistItem">
                            <input type="text"   class="form-control" placeholder="Service*"/>
                            <input type="number" class="form-control" placeholder="Price*"/>
                        </div>
                    </li>
                </ul><hr/>
                <br/>
                <!--Additional services -->
                <div class="allowed-behavior">
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
                </ul><hr/>
                <br/>
            </div>

            <div class="right-side">
                <!--Images upload -->
                <div class="upload-images">
                    <p> Upload images of your cottage: </p>
                    <input type="file" class="file-upload" @change="imageAdded"/>
                </div> <br/>
                <div v-if="images" class="images-preview">
                        <div v-for="image in images" :key="image">
                            <img :src="image" />
                        </div>
                </div>               
                 <OpenLayersMap></OpenLayersMap>
                 <div class="btn-div">
                     <button class="btn save-button" @click.prevent="submitForm()">Confirm</button> 
                     <button class="btn cancel-button">Cancel</button>
                 </div>
            </div>
        </div>
    </div>
</template>

<script>
    import NavBar from "@/components/Navbar.vue"
    import useValidate from '@vuelidate/core'
    import {required,numeric} from '@vuelidate/validators' 
    import {reactive, computed} from 'vue'
    import OpenLayersMap from "@/components/entities/OpenLayersMap.vue"

    export default ({
        components: {
                NavBar,
                OpenLayersMap,
        },
        data() {
            return{
                address: {
                    streetName: 'Kralja Dragutina',
                    streetNumber: '61',
                    postalcode: '21000',
                    city: 'Novi Sad',
                    country: 'Serbia',
                    longitude: '45.25937354724934',
                    latitude: '19.82703412668896'
                },
                roomsNum: undefined,
                allowedBehavior: 1,
                unallowedBehavior: 1,
                pricelistItems: 1,
                additionalServices: 1,
                images: [],
                imagesBackend: []
            }
        },

        methods: {

            imageAdded(e) 
            {
                const file = e.target.files[0];
                this.createBase64Image(file);
                this.images.push(URL.createObjectURL(file));
            },
            createBase64Image(file){
                const reader= new FileReader();
            
                reader.onload = (e) =>{
                    let img = e.target.result;
                    this.imagesBackend.push(img);
                }
                reader.readAsDataURL(file);
            },

        },

        setup() {
            const state = reactive({
                name: '',
                streetName: '',
                streetNumber: '',
                postalcode: '',
                city: '',
                country: '',
                description: ''
               
            })
            const rules = computed(()=>{
                return{
                name: {required},
                streetName: {required},
                streetNumber: {required},
                postalcode: {required, numeric},
                city: {required},
                country: {required},
                roomsNumber: {required, numeric}
            }
            })
            const v$=useValidate(rules,state)
            return {
                state,
                v$
            }
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

    .allowed-behavior{
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
