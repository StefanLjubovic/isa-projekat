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
                            <input class="px-2 py-1 border rounded focus:outline-none focus:border-blue-300" placeholder="Adventure date and time*" :value="inputValue" v-on="inputEvents" />
                            <div class="text-danger" v-if="v$.sale.dateTimeFrom.$error">Value is required and can't be before the expiration date </div>
                        </template>
                    </v-date-picker>

                    <input type="text" class="form-control" placeholder="Duration in hours*" v-model="v$.sale.durationInHours.$model"/>
                    <div class="text-danger" v-if="v$.sale.durationInHours.$error">{{v$.sale.durationInHours.$errors[0].$message}} </div>

                    <input type="text" class="form-control" placeholder="Maximum number of persons*" v-model="v$.sale.maximumPersons.$model"/>
                    <div class="text-danger" v-if="v$.sale.maximumPersons.$error">{{v$.sale.maximumPersons.$errors[0].$message}} </div>

                    <input type="text" class="form-control" placeholder="Additional services included*" v-model="v$.sale.additionalServices.$model"/>
                    <div class="text-danger" v-if="v$.sale.additionalServices.$error">{{v$.sale.additionalServices.$errors[0].$message}} </div>

                    <input type="text" class="form-control" placeholder="Price*" v-model="v$.sale.price.$model"/>
                    <div class="text-danger" v-if="v$.sale.price.$error">{{v$.sale.price.$errors[0].$message}} </div>

                    <v-date-picker v-model="v$.sale.expireDateTime.$model" mode="dateTime" is24hr>
                        <template v-slot="{ inputValue, inputEvents }">
                            <input class="px-2 py-1 border rounded focus:outline-none focus:border-blue-300" placeholder="Sale expiration date and time*" :value="inputValue" v-on="inputEvents" />
                            <div class="text-danger" v-if="v$.sale.expireDateTime.$error">Value is required and can't be after the start date and before today</div>
                        </template>
                    </v-date-picker>
                </div>
                <div class="modal-footer">
                    <div class="confirm-buttons">
                        <button class="btn submit-btn" @click="createSale()" :disabled="v$.sale.$invalid">Submit</button>
                        <button class="btn cancel-btn" @click="cancelSale()">Cancel</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <transition name="fade" appear v-if="userRoleIsClient()">
        <ClientReservation :entity="adventure" :type="type" v-if="displayReservationModal" @close-modal='closeModal'/>
    </transition>

    <transition name="fade" appear v-else>
        <CreateReservation :entity="adventure" :type="type" v-if="displayReservationModal" @close-modal='closeModal'/>
    </transition>

    <div id="page" v-if="adventure">
        <AdventureCaption :adventureName="adventure.name" :adventureId="adventure.id" :entityName="'adventure'"
            @create-sale="openModalForCreatingSale()" @edit-entity="this.$emit('edit-adventure', this.adventure.id)" @entity-deleted="this.$emit('entity-deleted', 0)"/>
        <div class="content">
            <div class="left">
                <InstructorDetails :instructor="adventure.fishingInstructor"/><hr/>
                <ImageGallery :images="adventure.images" description="Photos from previous events"/><hr/>

                <Sales :sales="adventure.sales" v-if="userRole != ''" :adventure="adventure" @sale-to-reservation="saleToReservation"/><br/>

                <div class="btn-placeholder">
                    <h2>Schedule for this adventure</h2>
                    <button class="btn" @click="makeReservation()" v-if="userRole != 'ROLE_ADMIN' && userRole != ''">Make a reservation&nbsp;&ensp;<i class="fas fa-calendar-check"></i> </button>
                </div>
                <Calendar class="calendar" :events="events"/>
                
                <p>If you cancel the reservation, the instructor retains {{ adventure.cancellationPercentage }}% 
                    of the price!
                </p><hr/>
                <PricelistTable :pricelistItem="adventure.pricelistItems"/><hr/>
            </div>
            <div class="right">
                <AdventureTextDescription :adventure="adventure"/><hr/>
                <Map :cottage="adventure"/><br/><hr/>
            </div>
        </div>
    </div>
    
</template>

<script>
import CreateReservation from "@/components/cottage/CreateReservation.vue"
import ClientReservation from "@/components/client/ClientReservation.vue"
import AdventureCaption from "@/components/adventure/AdventureCaption.vue"
import InstructorDetails from "@/components/adventure/InstructorDetails.vue"
import AdventureTextDescription from "@/components/adventure/AdventureTextDescription.vue"
import Sales from "@/components/adventure/Sales.vue"
import Map from "@/components/entities/ShowLocationOnMap.vue"
import ImageGallery from "@/components/ImageGallery.vue"
import Calendar from "@/components/Calendar.vue"
import PricelistTable from "@/components/entities/PricelistTable.vue"
import axios from 'axios'
import server from '../server'
import useValidate from '@vuelidate/core'
import {required, numeric} from '@vuelidate/validators'
import moment from 'moment'

export default {
    components: {
        AdventureCaption,
        InstructorDetails,
        AdventureTextDescription,
        Map,
        ImageGallery,
        Calendar,
        PricelistTable,
        ClientReservation,
        CreateReservation,
        Sales
    },
    props: [
        'entityId'
    ],
    emits: ['entity-deleted', 'edit-adventure'],
    data() {
        return {
            adventureId: this.entityId,
            adventure: undefined,
            type : 'Adventure',
            sale: {
                dateTimeFrom : '',
                durationInHours: '',
                maximumPersons: '',
                expireDateTime: '',
                additionalServices: '',
                price: ''
            },
            displayReservationModal : false,
            events: []
        }
    },
    computed:{
        userRole(){
            return this.$store.getters.getRole;
        },
        token() {
            return this.$store.getters.getToken;
        }
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
    created() {
        const headers = {
            'Content-Type': 'application/json;charset=UTF-8',
                Accept: 'application/json',
            'Authorization': `Bearer ${this.token}`
        }

        axios.get(`${server.baseUrl}/adventure/${this.adventureId}`, { headers: headers })
        .then((response) => {
            this.adventure = response.data;

            for(let sale of this.adventure.sales) {
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

            axios.get(`${server.baseUrl}/instructor/unavailablePeriods/${this.adventure.fishingInstructor.id}`, { headers: headers })
            .then((res) => {
                for(let period of res.data) {
                    this.events.push({
                        start : new Date(period.fromDateTime), 
                        end : new Date(period.toDateTime),
                        title : period.message,
                        class: 'calendar-unavailable'
                    })
                }
            })

            axios.get(`${server.baseUrl}/reservation/entity/${this.adventure.id}`, { headers: headers })
            .then((res) => {
                for(let reservation of res.data) {
                    var endTime = new Date(reservation.dateTime);
                    endTime.setHours(parseInt(endTime.getHours()) + parseInt(reservation.durationInHours));
                    this.events.push({
                        start : new Date(reservation.dateTime), 
                        end : endTime,
                        title : 'BOOKED',
                        class: 'calendar-booked'
                    })
                }
            })
        })
        .catch((error) => (console.log(error)));
    },
    methods: {
        openModalForCreatingSale: function() {
            window.$('#new-sale-modal').modal('show');
        },
        cancelSale: function() {
            this.sale = { dateTimeFrom : '', durationInHours: '', maximumPersons: '', expireDateTime: '', additionalServices: '', price: '' }
            window.$('#new-sale-modal').modal('hide');
        },
        createSale: function() {
            const headers = {
                'Content-Type': 'application/json;charset=UTF-8',
                'Accept': 'application/json',
                'Authorization': `Bearer ${this.token}`
            }

            axios.post(`${server.baseUrl}/sale/${this.adventure.id}`, this.sale, { headers: headers })
            .then((response) => {
                this.adventure.sales.push(response.data);

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

                console.log(this.adventure.sales);
                this.$swal({
                    position: 'top-end',
                    icon: 'success',
                    title: 'New sale created!',
                    showConfirmButton: false,
                    timer: 2000
                })
            })
            .catch((error) => {
                this.$swal(error.response.data.message);
            })
        },
        saleToReservation(reservation) {
            for(let e of this.events) {
                var endTime = new Date(reservation.dateTime);
                endTime.setHours(parseInt(endTime.getHours()) + parseInt(reservation.durationInHours));
                
                if(e.start.getTime() === new Date(reservation.dateTime).getTime() && e.end.getTime() === endTime.getTime()) {
                    e.title = "BOOKED";
                    e.class = "calendar-booked";
                    e.content = "";
                    break;
                }
            }
        },
        editEntity: function() {},
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

#page {
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

.left {
    width: 63%;
}
.right {
    width: 30%;
}

.btn-placeholder {
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
