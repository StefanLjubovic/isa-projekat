<template>
  <div id="app">
  <div class="modal-overlay" @click="$emit('close-modal')"></div>
  <div class="container">
    <div class='wrapper'>
        <div class="header-border">
              <h2>Create reservation:</h2>
        </div>
        <div class="content">
            <div class="left">
                <h5 class="mb-4">{{GetEntityName()}}</h5>
                <span>
                    <h5 class="mb-4">Reservation from: </h5>
                    <input class="form-control date"    type="date"  id = "dateFromfield" v-model="dateFrom"/>
                </span>

                <!-- End of reservation for cottage -->
                <span  v-if="type=='Cottage'">
                    <h5 class="mb-4">Reservation to: </h5>
                    <input class="form-control date-to" type="date"  id = "dateTofield"   v-model="dateTo"/>
                </span>
                <!-- End of reservation for adventure or ship -->
                <div class="dropdown-row mb-4" v-if="type!='Cottage'">
                    <h5 id="drop-lab">Start: </h5>          
                    <div class="dropdown">
                        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            {{start}} : 00
                        </button>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <li v-for="index in getNumbers(7,21)" :key="index">
                                <span><a class="dropdown-item" href="#" @click="calculateAdventurePriceStart(index)">{{index}}:00 </a></span>
                            </li>
                        </div>
                    </div>
                </div>
                <div class="dropdown-row mb-4" v-if="type!='Cottage'">
                    <h5 id="drop-lab">End: </h5>          
                    <div class="dropdown">
                        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            {{end}} : 00
                        </button>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <li v-for="index in getNumbers(start+1,24)" :key="index">
                                <span><a class="dropdown-item" href="#" @click="calculateAdventurePriceEnd(index)">{{index}}:00 </a></span>
                            </li>
                        </div>
                    </div>
                </div>
            
               <div class="dropdown-row mb-4">
                    <h5 id="drop-lab">Services: </h5>          
                    <div class="dropdown">
                        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            {{GetFirstService()}}
                        </button>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <li v-for="(pricelistItem,index) in rentingEntity.pricelistItems" :key="pricelistItem.id">
                                <span v-if="index != 0"><a class="dropdown-item" href="#">{{getContentPrice(pricelistItem)}} </a><i v-if="!checkIfAlreadyAdded(pricelistItem)" class="fas fa-plus" @click="addRequest(index)"></i></span>
                            </li>
                        </div>
                    </div>
                </div>
                <div class="dropdown-row mb-4">
                    <h5 id="drop-lab">Requested: </h5>          
                    <div class="dropdown">
                        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            {{GetFirstRequest()}}
                        </button>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <li v-for="(request,index) in requests" :key="request">
                                <span><a class="dropdown-item" href="#">{{getContentPrice(request)}} </a><i class="fas fa-minus" @click="removeRequest(index)"  v-if="index != 0"></i></span>
                            </li>
                        </div>
                    </div>
                </div>
                <h5 class="mb-5">Maximum people: &nbsp;{{GetPersons()}}</h5>
                <div class="button-div">
                    <span><h4 id="price">Price : {{price}} rsd</h4><div class="btn1"><button class="btn droptdown-btn" @click="saveReservation" :disabled="!enabled">Save</button> <button class="btn cancel-btn"  @click="$emit('close-modal')">Cancel</button></div></span>
                </div>
            </div>
        </div>
    </div>
 </div>
 </div>
</template>

<script>
import server from '../../server/index';
import moment from 'moment';
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
            persons : 1,
            start : 7,
            end: 10,
            rentingEntity :this.entity,
            enabled : false           
        }
    },
    watch: {
        dateFrom(){
            var today = new Date(this.dateFrom);
            var dd = today.getDate() + 1;
            var mm = today.getMonth() + 1; //January is 0!
            var yyyy = today.getFullYear();

            if (dd < 10) {
                dd = '0' + dd;
            }
            if (mm < 10) {
                mm = '0' + mm;
            } 
            today = yyyy + '-' + mm + '-' + dd;
            this.enabled = true;
            if(this.type == 'Cottage') document.getElementById("dateTofield").setAttribute("min", today);
        },
        dateTo(){
            this.CalculatePrice()
        }
  },
    mounted(){
        this.rentingEntity = this.entity
        this.requests.push(this.rentingEntity.pricelistItems[0])
        this.priceOneDay +=this.rentingEntity.pricelistItems[0].price
        if(this.type !='Cottage') this.price = this.priceOneDay * Math.abs(this.end - this.start)
        else this.price = this.priceOneDay
        var today = new Date();
        var dd = today.getDate();
        var mm = today.getMonth() + 1; //January is 0!
        var yyyy = today.getFullYear();
        if (dd < 10) {
        dd = '0' + dd;
        }
        if (mm < 10) {
        mm = '0' + mm;
        } 
        today = yyyy + '-' + mm + '-' + dd;
        document.getElementById("dateFromfield").setAttribute("min", today);
        if(this.type == 'Cottage')document.getElementById("dateTofield").setAttribute("min", today);
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
              if(this.type == 'Cottage') this.CalculatePrice()
        },
        GetFirstService(){
             if(this.rentingEntity.pricelistItems.length)
                return this.rentingEntity.pricelistItems[0].service + '  '+ this.rentingEntity.pricelistItems[0].price + '  rsd';
        },
        removeRequest(index){
            this.priceOneDay -=this.requests[index].price
            this.price = this.priceOneDay * Math.abs(this.end - this.start)
            this.requests.splice(index, 1)   
            if(this.type == 'Cottage') this.CalculatePrice()       
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
        GetPersons(){
            if(this.type == 'Adventure') return this.rentingEntity.maxPersons;
            else if(this.type =='Cottage'){
                let max = 0;
                this.rentingEntity.rooms.map(room => {
                    max+=room.bedNumber
                })
                return  max
            }
            else return this.rentingEntity.capacity
        },
        checkIfAlreadyAdded(pricelistItem){
            var i;
            for (i = 0; i < this.requests.length; i++) {
                if ( this.requests[i].id === pricelistItem.id) {
                    return true;
                }
            }
            return false;
        },
        CalculatePrice(){
            const diffTime = Math.abs(new Date(this.dateTo) - new Date(this.dateFrom));
            const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)); 
            this.price = this.priceOneDay * diffDays
        },
        GetEntityName(){
            if(this.type =='Adventure') return 'Adventure: '+ this.rentingEntity.name;
            else if(this.type =='Ship') return 'Ship: ' + this.rentingEntity.name;
            return 'Cottage: '+ this.rentingEntity.name;
        },
        changeButtonContext(index){
            this.persons = index
        },
        async saveReservation(){
            const diffTime = Math.abs(new Date(this.dateTo) - new Date(this.dateFrom));
            const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)); 
            let services = []
            this.requests.map(r => services.push(r.service))
            let time =0
            let durationRet = 0
            if(this.type != 'Cottage') {
                time =  moment(this.dateFrom).add(this.start*60, 'm').toDate();
                durationRet = Math.abs(this.end-this.start)
            }
            else{
                time= new Date(this.dateFrom)
                durationRet= diffDays * 24
            }
            const client=await server.getLoggedUser()
            const reservation = {
                rentingEntity : this.rentingEntity,
                dateTime : time,
                client : client.data,
                price : this.price,
                isCanceled : false,
                additionalServices : services,
                maxPersons : this.GetPersons(),
                durationInHours : durationRet
            }
            await server.saveReservation(reservation)
            .then(resp=> {
                if(resp.success){
                    var endTime = new Date(reservation.dateTime);
                    endTime.setHours(parseInt(endTime.getHours()) + parseInt(reservation.durationInHours));
                    this.$emit('new-reservation', { dateFrom: reservation.dateTime, dateTo: endTime});
                    
                    this.$swal.fire({
                        icon: 'success',
                        title: 'Success',
                        text: 'Reservation succesfully created!',
                        confirmButtonColor: '#2c3e50'
                    })
                }else{
                    console.log(resp.data)
                    this.$swal.fire({
                            icon: 'error',
                            title: 'Oops...',
                            text: resp.data.message,
                    })     
                    this.$emit('close-modal')
                }
            })
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
    .content{
        display: flex;
        justify-content: flex-start;
        flex-direction: row;
        flex: 17;
    }
    .left{
        padding: 3rem;
        display: flex;
        justify-content: flex-start;
        flex-direction: column;
        width: 100%;
    }
    .dropdown-row{
        display:flex;
        flex-direction: row;
    }
    .request-input{
        flex:2;
    }
    .request{
        width: 70%;
        margin-right:1rem ;
    }
    .btn1{
        display: flex;
        justify-self: flex-end;
        align-self: flex-end;
        margin-left:11rem ;
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
        border-bottom:2px solid #5a5a73;
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
    .droptdown-btn{
        width: 5vw;
        height: 3rem;
        color:white;
        border-radius: 5px;
    background: #0e0f40;
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
    .fade-enter-active, .fade-leave-active {
        transition: opacity .5s
    }
    .fade-enter, .fade-leave-to /* .fade-leave-active in <2.1.8 */ {
        opacity: 0
    }
</style>