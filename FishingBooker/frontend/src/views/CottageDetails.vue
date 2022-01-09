<template>
    <!-- Add new sale modal -->
    <div class="modal fade" id="new-sale-modal" role="dialog">
        <div class="modal-dialog rounded" role="document">
            <div class="modal-content">
                <div class="modal-header" @click="cancelSale()">
                    <h3>Define Sale</h3>
                    <button class="btn btn-close close"></button>
                </div>
                <div class="modal-body">
                    <v-date-picker v-model="v$.sale.dateTimeFrom.$model" mode="dateTime" is24hr>
                        <template v-slot="{ inputValue, inputEvents }">
                            <input class="px-2 py-1 border rounded focus:outline-none focus:border-blue-300" placeholder="Period date and time*" :value="inputValue" v-on="inputEvents" />
                            <div class="text-danger" v-if="v$.sale.dateTimeFrom.$error">Value is required and can't be before the expiration date </div>
                        </template>
                    </v-date-picker>

                    <input type="number" class="form-control" placeholder="Duration*" v-model="v$.sale.durationInHours.$model"/>
                    <div class="text-danger" v-if="v$.sale.durationInHours.$error">{{v$.sale.durationInHours.$errors[0].$message}} </div>

                    <input type="number" class="form-control" placeholder="Maximum number of persons*" v-model="v$.sale.maximumPersons.$model"/>
                    <div class="text-danger" v-if="v$.sale.maximumPersons.$error">{{v$.sale.maximumPersons.$errors[0].$message}} </div>

                    <input type="text" class="form-control" placeholder="Additional services included*" v-model="v$.sale.additionalServices.$model"/>
                    <div class="text-danger" v-if="v$.sale.additionalServices.$error">{{v$.sale.additionalServices.$errors[0].$message}} </div>

                    <input type="number" class="form-control" placeholder="Price*" v-model="v$.sale.price.$model"/>
                    <div class="text-danger" v-if="v$.sale.price.$error">{{v$.sale.price.$errors[0].$message}} </div>

                    <v-date-picker v-model="v$.sale.expireDateTime.$model" mode="dateTime" is24hr>
                        <template v-slot="{ inputValue, inputEvents }">
                            <input class="px-2 py-1 border rounded focus:outline-none focus:border-blue-300" placeholder="Sale expiration date and time*" :value="inputValue" v-on="inputEvents" />
                            <div class="text-danger" v-if="v$.sale.expireDateTime.$error">Value is required and can't be after the start date</div>
                        </template>
                    </v-date-picker>
                </div>
                <div class="modal-footer">
                    <div class="confirm-buttons">
                        <button class="btn submit-btn" @click="createSale()" >Submit</button>
                        <button class="btn cancel-btn" @click="cancelSale()">Cancel</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <transition name="fade" appear v-if="userRoleIsClient()">
        <ClientReservation :entity="cottage" :type="type" v-if="displayReservationModal" @close-modal='closeModal'/>
    </transition>
    <transition name="fade" appear v-else>
        <CreateReservation :entity="cottage" :type="type" v-if="displayReservationModal" @close-modal='closeModal'/>
    </transition>

    <div id="profile">
        <AdventureCaption :adventureName="this.cottage.name" :adventureId="this.entityId" :entityName="'cottage'"
            @create-sale="openModalForCreatingSale()" @edit-entity="this.$emit('edit-cottage', this.entityId)" @entity-deleted="this.$emit('entity-deleted', 2)"/>
        <div class="content">
            <div class="left-side">
                <ImageGallery :images="cottage.images"  description="Photos of our cottage"/><hr/>
                <Sales :sales="cottage.sales" v-if="userRole != ''" :adventure="cottage" @sale-to-reservation="saleToReservation"/><br/>
                  <div class="btn-wrap">
                      <h2>Schedule for this adventure</h2>
                    <button class="btn" @click="makeReservation()">Make a reservation&nbsp;&ensp;<i class="fas fa-calendar-check"></i> </button>
                </div>
                <Calendar class="calendar" :events="undefined"/>
                <p>In case of reservation cancellation, cottage owner retains {{ cottage.cancellationPercentage }}% of the price! </p><hr/>
                <PricelistTable :pricelistItem="cottage.pricelistItems"/><hr/>
            </div>
            <div class="right-side">
                <CottageTextDescription :cottage="cottage" /><hr/>
                <Map :cottage="cottage"/><br/><hr/>
            </div>
        </div>
    </div>
</template>

<script>
    import CreateReservation from "@/components/cottage/CreateReservation.vue"
    import ClientReservation from "@/components/client/ClientReservation.vue"
    import AdventureCaption from "@/components/adventure/AdventureCaption.vue"
    import ImageGallery from "@/components/ImageGallery.vue"
    import Sales from "@/components/adventure/Sales.vue"
    import Calendar from "@/components/Calendar.vue"
    import PricelistTable from "@/components/entities/PricelistTable.vue"  
    import CottageTextDescription from "@/components/cottage/CottageTextDescription.vue"
    import Map from "@/components/entities/ShowLocationOnMap.vue"
    import axios from 'axios'
    import server from '../server/index'
    import useValidate from '@vuelidate/core'
    import {required, numeric} from '@vuelidate/validators'
    import moment from "moment"

    export default {
        props:['entityId'],
        emits:['edit-cottage'],
        components: {
            CreateReservation,
            ClientReservation,
            AdventureCaption,
            ImageGallery,
            Sales,
            Calendar,
            PricelistTable,
            CottageTextDescription,
            Map
        },
        data() {
            return {
                type: 'Cottage',
                id: this.entityId,
                displayReservationModal : false,
                cottage: {
                    name: '',
                    address: {
                        streetName: '',
                        streetNumber: '',
                        postalCode: '',
                        city: '',
                        country: '',
                        longitude: undefined,
                        latitude: undefined
                    },
                    description: '',
                    averageGrade: undefined,
                    images: [],
                    allowedBehaviour: [],
                    unallowedBehaviour: [],
                    cancellationPercentage: undefined,
                    pricelistItem: [],
                    rooms: [],
                    sales: []
               },
               sale: {
                    dateTimeFrom : '',
                    durationInHours: '',
                    maximumPersons: '',
                    expireDateTime: '',
                    additionalServices: '',
                    price: ''
               },
               events: []
            } 
        },
        computed:{
            userRole(){
                return this.$store.getters.getRole;
            },
            state(){
                return this.$store.getters.getState;
            },
             token(){
                 return this.$store.getters.getToken;
            }
        },
        created() {
            this.fetchData()
        },
        setup() {
            return { v$: useValidate() }
        },
        validations() {
            return {
                sale: {
                    dateTimeFrom : { 
                        required, 
                        minValue(val, { expireDateTime }) {
                            return new Date(val) > new Date(expireDateTime);
                        }
                    },
                    durationInHours: { required, numeric },
                    maximumPersons: { required, numeric },
                    expireDateTime: { 
                        required, 
                        minValue(val) {
                            return new Date(val) > new Date();
                        },
                        maxValue(val, {dateTimeFrom}){
                            return new Date(dateTimeFrom) > new Date(val);
                        }
                    },
                    price: { required, numeric },
                    additionalServices: {  }
                }
            }
        },
        methods: {
            fetchData: function(){
                axios
                .get(`${server.baseUrl}/cottage/getOne/` + this.entityId)
                .then(response => {
                    this.cottage = response.data;
                    for(let sale of this.cottage.sales) {
                        var endTime = new Date(sale.dateTimeFrom);
                        endTime.setHours(parseInt(endTime.getHours()) + parseInt(sale.durationInHours));
                        this.events.push({
                            start : new Date(sale.dateTimeFrom), 
                            end : endTime,
                            title : 'SALE',
                            content: `<p style="font-size: 12px; color="light-gray;"> Expires on ${this.dateFormat(sale.expireDateTime)} </p>`,
                            class: 'calendar-sale'
                         })
                    }
                })
            },
            openModalForCreatingSale(){
                window.$('#new-sale-modal').modal('show');
            },
            makeReservation: function() {
                this.displayReservationModal = true;
                document.getElementById('appContainer').style.overflow ='hidden';
                document.getElementById('appContainer').style.height='100vh';
            },  
            closeModal: function(){
                this.displayReservationModal = false;
                document.getElementById('appContainer').style.overflow = 'unset';
                document.getElementById('appContainer').style.height='unset';
            },
            createSale: function() { 
                const headers = {
                    'Content-Type': 'application/json;charset=UTF-8',
                    'Accept': 'application/json',
                    'Authorization': `Bearer ${this.token}`
                }

                axios.post(`${server.baseUrl}/sale/${this.id}`, this.sale, { headers: headers })
                .then((response) => {
                    this.cottage.sales.push(response.data);

                    var endTime = new Date(this.sale.dateTimeFrom);
                    endTime.setHours(parseInt(endTime.getHours()) + parseInt(this.sale.durationInHours));

                    this.events.push({
                        start : new Date(this.sale.dateTimeFrom), 
                        end : endTime,
                        title : 'SALE',
                        content: `<p style="font-size: 12px; color="light-gray;"> Expires on ${this.dateFormat(this.sale.expireDateTime)} </p>`,
                        class: 'calendar-sale'
                    })

                    window.$('#new-sale-modal').modal('hide');
                    this.sale = { dateTimeFrom : '', durationInHours: '', maximumPersons: '', expireDateTime: '', additionalServices: '', price: '' }

                    this.$swal({
                        position: 'top-end',
                        icon: 'success',
                        title: 'New sale created!',
                        showConfirmButton: false,
                        timer: 2000
                    })
                })
              },
            cancelSale: function() {
                this.sale = { dateTimeFrom : '', durationInHours: '', maximumPersons: '', expireDateTime: '', additionalServices: '', price: '' }
                window.$('#new-sale-modal').modal('hide');
            },
            editEntity: function() {},
            dateFormat(value) {
                return moment(value).format("DD.MM.YYYY. HH:mm");
            },
            userRoleIsClient(){
                if(this.userRole == "ROLE_CLIENT") return true;
                else return false;
            }
        }
    }

</script>

<style scoped>
    #profile {
        padding-top: 50px;
        padding-bottom: 50px;
        margin-left: 15%;
        margin-right: 15%;
    }
    hr {
        margin-top: 30px;
        margin-bottom: 30px;
    }
    h2 {
        font-size: 25px;
    }
    .content {
        display: flex;
        margin-top: 50px;
        justify-content: space-between;
    }
    .left-side {
        width: 63%;
    }
    .right-side {
        width: 30%;
    }
    .btn-wrap {
        display: flex;
        justify-content: space-between;
    }
    .btn {
        background-color: #2c3e50;
        color: white;
    }
    .btn-close{ 
        background-color: transparent;
    }
    .cancel-btn {
        background-color: white;
        color: #2c3e50;
        border-color: #cfd3d8;
        margin-left: 10px;
    }
    input {
        width: 465px;
        margin-bottom: 25px;
    }
    .text-danger {
        margin-top: -20px;
        margin-bottom: 5px;
        text-align: left;
        font-size: 13px;
    }

    .calendar {
        margin-top: 20px;
        height: 100vh;
        margin-bottom: 20px;
    }
</style>