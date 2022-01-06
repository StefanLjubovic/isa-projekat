<template>
    <div class="page">
        <h1>Analytics</h1>
        <div class="money-percentage">
            <h2> Average grade of my {{entityType}} </h2>
            <div class="requests-table card rounded">
                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th scope="col"></th>
                            <th scope="col">Name</th>
                            <th scope="col">Mark</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="entity in entities" :key="entity.id">
                            <th scope="row">{{ entities.indexOf(entity) + 1 }}</th>
                            <td>{{ entity.name }}</td>
                            <td>{{ entity.averageGrade }}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="total-income">
                <p class="total-income-title">Average grade</p>
                <p class="total-income-grade">{{ getAverageGrade() }}</p>
            </div>         
        </div>

        <h2>Income over a period of time</h2>
        <div class="filter-dates">
            <input type="text" class="form-control form-control-dates" v-model="dateFrom" placeholder="Date From"
                onfocus="(this.type='date')" onblur="(this.type='text')">
            <input type="text" class="form-control form-control-dates" v-model="dateTo" placeholder="Date To"
                onfocus="(this.type='date')" onblur="(this.type='text')">
            <button class="btn btn-option" @click="search()"><i class="fas fa-search"></i></button>
            <button class="btn btn-option"><i class="far fa-file-pdf"></i></button>
        </div>
        <div class="requests-table card rounded">
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th scope="col"></th>
                        <th scope="col">Name</th>
                        <th scope="col">Date from</th>
                        <th scope="col">Date to</th>
                        <th scope="col">Income</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="reservation in reservations" :key="reservation.entityName">
                        <th scope="row">{{ reservations.indexOf(reservation) + 1 }}</th>
                        <td>{{ reservation.entityName }}</td>
                        <td>{{ convertToDate(reservation.dateFrom) }}</td>
                        <td>{{ convertToDate(reservation.dateTo) }}</td>
                        <td>{{ reservation.income }}</td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="total-income">
            <p class="total-income-title">Total income</p>
            <p class="total-income-value">{{ getTotalIncome() }}</p>
        </div>
    </div>
</template>

<script>
import server from '../server'
import axios from 'axios'
import moment from 'moment'

export default ({
    data() {
        return {
            entityType: "",
            dateFrom: undefined,
            dateTo: undefined,
            allReservations: [],
            reservations: [],
            entities: [],
        }
    },
    computed:{
        userRole(){
            return this.$store.getters.getRole;
        },
        token(){
          return this.$store.getters.getToken;
        }
    },
    mounted() {
       this.fetchData();

        if(this.userRole == "ROLE_COTTAGE_OWNER")
            this.entityType = 'cottages';
        else if(this.userRole == "ROLE_SHIP_OWNER")
            this.entityType = 'ships';
        else
            this.entityType = 'adventures';
    },
    methods: {
        fetchData: function(){
            const headers = {
              'Content-Type': 'application/json;charset=UTF-8',
               Accept: 'application/json',
              'Authorization': `Bearer ${this.token}`
            }

            if(this.userRole == 'ROLE_INSTRUCTOR') {   
                axios.get(`${server.baseUrl}/instructor/adventures`, {headers: headers})
                .then((response) => {
                    this.entities = response.data;
                })
                axios.get(`${server.baseUrl}/instructor/reservation-income`, {headers: headers})
                .then((response) => {
                    this.reservations = response.data;
                    this.allReservations = response.data;
                })
            } else if (this.userRole == 'ROLE_COTTAGE_OWNER'){
                axios.get(`${server.baseUrl}/cottageOwner/cottages`, {headers: headers})
                .then((response) => {
                    this.entities = response.data;
                })
                 axios.get(`${server.baseUrl}/cottageOwner/reservation-income`, {headers: headers})
                    .then((response) => {
                    this.reservations = response.data;
                    this.allReservations = response.data;
                })
            }else {
                axios.get(`${server.baseUrl}/shipOwner/ships`, {headers: headers})
                .then((response) => {
                    this.entities = response.data;
                })
                axios.get(`${server.baseUrl}/shipOwner/reservation-income`, {headers: headers})
                .then((response) => {
                    this.reservations = response.data;
                    this.allReservations = response.data;
                })
            }
        },

        getTotalIncome: function() {
            let sum = 0;
            for(let res of this.reservations) 
                sum += parseInt(res.income);
            
            return sum;
        },
        getAverageGrade: function() {
            let averageGrade = 0;
            for(let entity of this.entities)
                averageGrade += entity.averageGrade;
            
            return (averageGrade/(this.entities.length)).toFixed(1);
        },

        search: function() { 
            this.reservations = []

            let searchDateFrom = undefined;
            if(!this.dateFrom) {
                searchDateFrom = new Date(-8640000000000000);
            }
            else {
                searchDateFrom = new Date(this.dateFrom);
                searchDateFrom.setHours(0,0,0,0);
            }

            let searchDateTo = undefined;
            if(!this.dateTo){
                searchDateTo = new Date(8640000000000000);
            } 
            else{
                searchDateTo = new Date(this.dateTo);
                searchDateTo.setHours(0,0,0,0);
            }
            
            for(let reservation of this.allReservations) {
                console.log("JSON.stringify(reservation)")
                let reservationBegin = new Date(reservation.dateFrom).setHours(0,0,0,0);
                let reservationEnd = new Date(reservation.dateTo).setHours(0,0,0,0);

                if(searchDateFrom < reservationBegin && reservationEnd < searchDateTo){
                    this.reservations.push(reservation);
                }
            }
        },
        convertToDate(date){
            var d = new Date(date);
            return moment(d).format("DD.MM.YYYY. HH:mm");
        }
    }
})
</script>

<style scoped>
    .page {
        margin: 50px 17%;
        padding-bottom: 100px;
    }
    h1 {
        text-align: left;
        margin-bottom: 40px;
    }
    .btn {
        margin-top: -5px;
        width: 40px;
        height: 40px;
    }
    h2 {
        margin-top: 40px;
        margin-bottom: 20px;
        text-align: left;
    }
    .filter-dates {
        display: flex;
    }
    .form-control-dates {
        margin-right: 10px;
        width: 25vw;
    }
    .btn-option {
        background-color: #2c3e50;
        color: white;
        margin-top: 0px;
        margin-right: 10px;
    }
    .card {
        margin-top:20px;
        width: 57vw;
        padding-top: 10px;
    }
    .table, .table-striped, .table-hover {
        color: #2c3e50;
    }
    .total-income{ 
        display: flex;
        justify-content: space-between;
        margin-left: 30px;
        margin-right:160px;
        margin-top: 20px;
        font-size: 20px;
    }
</style>
