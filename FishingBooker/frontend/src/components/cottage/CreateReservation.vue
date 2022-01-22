<template>
  <div id="app">
  <div class="modal-overlay" @click="$emit('close-modal')"></div>
  <div class="container">
    <div class='wrapper'>
        <div class="header-border">
              <h2>Create reservation</h2>
        </div>
        <div class="content">
            <div class="left">
                <span class="date-span">
                    <h5 >Reservation date: </h5>
                     <v-date-picker class="date-picker-override" v-model="reservation.dateTime" mode="dateTime" is24hr>
                        <template v-slot="{ inputValue, inputEvents }">
                            <input class="width:100% px-5 py-2 border rounded focus:outline-none focus:border-blue-300" placeholder="Period date and time*" :value="inputValue" v-on="inputEvents" />
                            <!--div class="text-danger" v-if="v$.sale.dateTimeFrom.$error">Value is required and can't be before the expiration date </div-->
                        </template>
                    </v-date-picker>
                </span>
                <!-- End of reservation for cottage or ship -->
                <span  v-if="type=='Cottage'">
                    <h5 class="mb-4">Duration in days: </h5>
                    <input class="input-field-duration" type="number"  id = "dateTofield"   v-model="reservation.durationInHours"/>
                </span>
                <!-- End of reservation for adventure -->
                <span  v-if="type!='Cottage'">
                    <h5> Duration in hours: </h5>
                    <input class="input-field-duration-hours" type="number"  id = "dateTofield"   v-model="reservation.durationInHours"/>
                </span>
                <!-- Services -->
               <div class="dropdown-row mb-4">
                    <h5 id="drop-lab">Services: </h5>          
                    <div class="right-side-services">
                        <input type="checkbox" checked disabled value="Standard offer">
                        <label > Standard offer &nbsp; {{price}} rsd</label><br/>
                        <span class="pricelist-item" v-for="(pricelistItem) in pricelistItemsCopy" :key="pricelistItem.id">
                            <input type="checkbox" :value="pricelistItem.service" @change="changeTotalPrice()" v-model="reservation.additionalServices">
                            <label for="pricelistItem.service"> {{pricelistItem.service}} &nbsp; {{pricelistItem.price}} rsd</label>
                        </span>

                    </div>
                </div>
                <span>
                    <h5 class="mb-5">Guest number: </h5><hr/>
                    <input class="input-field"  type="number"  id = "dateFromfield" v-model="reservation.maxPersons" disabled/>
                </span>
                <hr id="total-hr"/>
                <div>
                    <span class="bottom-span">
                        <h5 id="price"> Total : </h5>
                        <h5>{{reservation.price}} rsd</h5>
                    </span><br/>
                     <div class="btn1">
                        <button class="submit-btn" @click="saveReservation">Submit</button> 
                        <button class="btn cancel-btn"  @click="$emit('close-modal')">Cancel</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
 </div>
 </div>
</template>

<script>
import server from '../../server/index';
import axios from 'axios'

export default {
    props:{
        entity : Object,
        type : String
    },
    emits: ['new-reservation'],
    data(){
        return{
            requests: [],
            currentRequest : '',
            price : 0,
            priceOneDay : 0,
            dateFrom :new Date(),
            dateTo : new Date(),
            persons : undefined,
            start : 7,
            end: 10,
            rentingEntity: this.entity,
            pricelistItemsCopy: [],
            reservation: {
                dateTime: undefined,
                durationInHours: undefined,
                maxPersons: undefined,
                additionalServices: [],
                price: undefined,
                entityId: this.entity.id,
                entityName: this.entity.name,
                entityVersion: this.entity.version 
            }            
        }
    },
    watch: {
        dateTo(){
            this.CalculatePrice()
        }
    },
    computed:{
        state(){
             return this.$store.getters.getState;
        },
        token(){
            return this.$store.getters.getToken;
        }
    },
    mounted(){
        this.rentingEntity = this.entity
        this.pricelistItemsCopy = this.rentingEntity.pricelistItems.map(a => {return {...a}})
        this.getPersons();
        this.getPrice();
        this.pricelistItemsCopy = this.rentingEntity.pricelistItems.filter(function(el) { return el.service != "Standard offer"; }); 
    },
    methods:{
        getNumbers:function(start,stop){
            return new Array(stop-start).fill(start).map((n,i)=>n+i);
        },
        getContentPrice(pricelistItem){
            return pricelistItem.service + ':     '+pricelistItem.price+'  rsd'
        },
        GetFirstRequest(){
            if(this.requests.length)
                return this.rentingEntity.pricelistItems[0].service + '  '+ this.rentingEntity.pricelistItems[0].price + '  rsd';
            return 'No current requests.'
        },
        addRequest(index){
            this.requests.push(this.rentingEntity.pricelistItems[index])
            this.priceOneDay +=this.rentingEntity.pricelistItems[index].price
             this.price = this.priceOneDay * Math.abs(this.end - this.start)
        },
        getPrice(){
             for (let el of this.pricelistItemsCopy){
                if(el.service == 'Standard offer'){
                    this.reservation.price = el.price;
                    this.price = el.price;
            }
        }
        },
        removeRequest(index){
            this.priceOneDay -=this.requests[index].price
            this.price = this.priceOneDay * Math.abs(this.end - this.start)
            this.requests.splice(index, 1)          
        },
        calculateAdventurePriceEnd(index){
            this.end = index;
            this.price = this.priceOneDay * Math.abs(this.end - this.start)
        },
        calculateAdventurePriceStart(index){
            this.start = index;
            if(this.start > this.end) this.end = index +1
            this.price = this.priceOneDay * Math.abs(this.end - this.start)
        },
        getPersons(){
            if(this.type == 'Adventure') this.reservation.maxPersons = this.rentingEntity.maxPersons;
            else if(this.type =='Cottage'){
                let max = 0;
                this.rentingEntity.rooms.map(room => {
                    max+=room.bedNumber
                })
                this.reservation.maxPersons =  max
            }
            else this.reservation.maxPersons = this.rentingEntity.capacity
        },

        changeTotalPrice(){
            this.reservation.price = this.price;
            for(let el of this.reservation.additionalServices){
                let mapEl = this.rentingEntity.pricelistItems.filter(item => { if (item.service == el) return item; });
                this.reservation.price += mapEl[0].price;
            }
        },
        GetEntityName(){
            if(this.type =='Adventure') return this.rentingEntity.name;
            else if(this.type =='Ship') return this.rentingEntity.name;
            return this.rentingEntity.name;
        },
        changeButtonContext(index){
            this.persons = index
        },
        fetchEntity(){

        },
        saveReservation(){
            const headers = {
                'Content-Type': 'application/json;charset=UTF-8',
                'Accept': 'application/json',
                'Authorization': `Bearer ${this.token}`
            }

            if(this.type == 'Cottage')
                this.reservation.durationInHours *= 24;

            axios.post(`${server.baseUrl}/reservation/createByAdvertiser`, this.reservation, { headers: headers })
                .then(() => {
                    this.$emit('new-reservation', this.reservation)
                    this.reservation = { dateTime: '', durationInHours: '', additionalServices: [], price: undefined }

                    this.$swal({
                        position: 'top-end',
                        icon: 'success',
                        title: 'Successfully created reservation!',
                        showConfirmButton: false,
                        timer: 3000
                    })
                })
                .catch(error => {
                    this.$swal({
                        position: 'top-end',
                        icon: 'info',
                        title: error.response.data.message,
                        showConfirmButton: false,
                        timer: 2000
                    })
                 })
                this.$emit('close-modal')
        }
    }
}
</script>

<style scoped>
    #app {
        position: absolute;
        display: flex;
        justify-content: center;
        align-items: center;
        left:0;
        top:0;
        width: 100vw;
        min-height: 100vh;
        overflow-x: hidden;
    }
    .modal-overlay {
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        z-index: 98;
        background-color: rgba(0, 0, 0, 0.3);
    }
    .date-picker-override{
        margin-left: 3.3%;
        width: 0px;
    }
    .container{
        flex-direction: column;
        justify-content: space-around;
        width: 45%;
        background: white;
        z-index: 1000;
        position: fixed;
        border-radius: 16px;
        padding: 25px;
    }
    .wrapper{
        height: 70%;
        border-radius: 15px;
        display: flex;
        justify-content: center;
        flex-direction: column;
    }
    .bottom-span{
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: space-between;
        padding-right: 20%;
    }
     .input-field{
        border-color: lightgrey;
        border-radius: 3px;
        width: 51.5%;
        height: 35px;
        margin-left: 5%; 
    }
    .date-span{
        display: flex;
        flex-direction: row;
        align-items: center;
    }
    .input-field-duration{
        border-color: lightgrey;
        border-radius: 3px;
        width: 51.5%;
        height: 35px;
        margin-left: 4%;
    }

    .input-field-duration-hours{
        border-color: lightgrey;
        border-radius: 3px;
        width: 51%;
        height: 35px;
        margin-left: 3%;
    }
    .content{
        display: flex;
        justify-content: flex-start;
        flex-direction: row;
        flex: 17;
    }
    .right-side{
        margin-inline-start: 50%;
    }
    .right-side-services{
        margin-inline-start: 15%;
    }
    .left{
        padding: 3rem;
        padding-left: 12%;
        display: flex;
        justify-content: flex-start;
        flex-direction: column;
        gap: 10px;
        width: 100%;
    }
    #total-hr{
        margin-right: 15%;
    }
    .dropdown-row{
        display:flex;
        flex-direction: row;
    }
    .request-input{
        flex:2;
    }
    input[type=checkbox] {
        transform: scale(1.8);
        margin-right: 10px;
    }
    .pricelist-item{
        align-items: center;
    }
    .request{
        width: 70%;
        margin-right:1rem ;
    }
    .btn1{
        display: flex;
        justify-self: flex-end;
        align-self: flex-end;
        margin-left:17rem ;
    }
    .button-div{
        justify-content: space-between;
        align-self: flex-end;
        flex-direction: row;
        width: 100%;
    }
    h4{
        display: flex;
        justify-items: flex-end;
        align-self: flex-start;
    }  
    h2{
        color: #5a5a73;
    }
    h5{
        display: flex;
        justify-content: flex-start;
    }
    i{
        cursor: pointer;
        display: flex;
        justify-content: center;
        align-self: center;
        color: #0e0f40;
        margin-right: 1rem;
    }
    .header-border{
        width: 80%;
        align-self: center;
        border-bottom:1px solid #5a5a73;
        flex: 1;
    }
    #request{
        flex:1
    }
    span{
        display: flex;
        flex-direction: row;
        width: 100%;
    }
    #drop-lab{
        margin-right: 1rem;
    }
    .submit-btn{
        width: 5vw;
        height: 3rem;
        border-radius: 5px;
        background-color: #2c3e50;
        color: white;
    }
    .persons{
        margin-left:1rem ;
    }
    .cancel-btn{
        width: 5vw;
        height: 3rem;
        background:white;
        border-radius: 5px;
        color: #0e0f40;
        margin-left: 1rem;
    }
    .date{
        height: 70%;
        width: 50%;
        margin-left:1rem ;
    }
    .date-to{
        height: 70%;
        width: 50%;
        margin-left:2.4rem ;
    }
    .cancel-btn {
        background-color: white;
        color: #2c3e50;
        border-color: #cfd3d8;
        margin-left: 10px;
    }
    .fade-enter-active, .fade-leave-active {
        transition: opacity .5s
    }
    .fade-enter, .fade-leave-to /* .fade-leave-active in <2.1.8 */ {
        opacity: 0
    }
</style>