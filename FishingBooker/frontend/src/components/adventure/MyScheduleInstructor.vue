<template>
    <div class="page">
        <h1>My Schedule</h1>
        <div v-if="userRole == 'ROLE_INSTRUCTOR'" class="unavaliable-period">
            <p>Define unavaliable period: </p>
            <form @submit.prevent>
                <div class="mb-4">
                    <v-date-picker v-model="range" mode="dateTime" :masks="masks" is-range>
                        <template v-slot="{ inputValue, inputEvents, isDragging }">
                            <div class="date-picker">
                                <div class="date-input">
                                    <i class="fas fa-calendar-alt icon"></i>
                                    <input class="form-control"
                                        :class="isDragging ? 'text-gray-600' : 'text-gray-900'"
                                        :value="inputValue.start" v-on="inputEvents.start" />
                                </div>
                                <span class="flex-shrink-0 m-2">
                                    <i class="fas fa-arrow-right"></i>
                                </span>
                                <div class="date-input">
                                    <input
                                        class="form-control"
                                        :class="isDragging ? 'text-gray-600' : 'text-gray-900'"
                                        :value="inputValue.end"/>
                                    <i class="fas fa-calendar-alt icon"></i>
                                </div>
                            </div>
                        </template>
                    </v-date-picker>
                </div>
            </form>
            <button class="btn" @click="defineUnavaliablePeriod()"><i class="fas fa-check"></i></button>
        </div>
        <Calendar class="calendar" :events="events"/>
    </div>
</template>

<script>
import moment from 'moment'
import axios from 'axios';
import server from '../../server';
import Calendar from '../Calendar.vue'

export default {
    components: {
        Calendar
    },
    computed:{
        userRole(){
            return this.$store.getters.getRole;
        }
    },
    data() {
        return {
            range: {
                start: new Date(),
                end: new Date(),
            },
            masks: {
                input: 'YYYY-MM-DD h:mm A',
            },
            events: [],
            reservations: []
        };
    },
    mounted() {
        const headers = {
            'Content-Type': 'application/json;charset=UTF-8',
            Accept: 'application/json',
            'Authorization': `Bearer ${this.$store.getters.getToken}`
        }

        axios.get(`${server.baseUrl}/instructor/unavailablePeriods`, {headers: headers})
        .then((response) => {
            for(let period of response.data) {
                this.events.push({
                    start : new Date(period.fromDateTime), 
                    end : new Date(period.toDateTime),
                    title : period.message,
                    class: 'calendar-unavailable'
                })
            }
        })

        axios.get(`${server.baseUrl}/sale/instructor/`, { headers: headers })
        .then((response) => {
            for(let sale of response.data) {
                var endTime = new Date(sale.dateTimeFrom);
                endTime.setHours(parseInt(endTime.getHours()) + parseInt(sale.durationInHours));

                this.events.push({
                    start : new Date(sale.dateTimeFrom), 
                    end : endTime,
                    title : `SALE for ${sale.entityName}`,
                    content: `<p style="font-size: 12px; color="light-gray;"> Expires on ${this.dateFormat(sale.expireDateTime)} </p> `,
                    class: 'calendar-sale'
                })
            }
        })

        axios.get(`${server.baseUrl}/instructor/reservations`, { headers: headers })
        .then((response) => {
            for(let reservation of response.data) {
                var endTime = new Date(reservation.dateTime);
                endTime.setHours(parseInt(endTime.getHours()) + parseInt(reservation.durationInHours));

                this.events.push({
                    start : new Date(reservation.dateTime), 
                    end : endTime,
                    title : `BOOKED`,
                    content: `<p style="font-size: 13px;"> Adventure ${reservation.entityName} </p>
                              <p style="font-size: 13px;"> Client ${reservation.clientName} </p>  `,
                    class: 'calendar-booked'
                })
            }
        })
    },
    methods: {
        defineUnavaliablePeriod: function() {
            let period = {
                fromDateTime : this.range.start,
                toDateTime : this.range.end
            }

            const headers = {
                'Content-Type': 'application/json;charset=UTF-8',
                Accept: 'application/json',
                'Authorization': `Bearer ${this.$store.getters.getToken}`
            }

            console.log(JSON.stringify(period));
            axios.post(`${server.baseUrl}/instructor/unavailablePeriod`, period, {headers: headers})
            .then((response) => {
                this.events.push({
                    start : new Date(response.data.fromDateTime), 
                    end : new Date(response.data.toDateTime),
                    title : response.data.message,
                    class: 'calendar-unavailable'
                });
                this.$swal({
                    position: 'top-end',
                    icon: 'success',
                    title: 'Unavailable period saved!',
                    showConfirmButton: false,
                    timer: 2000
                })
            })
            .catch((error) => {
                this.$swal(error.response.data.message);
                console.log(error.response.data.message);
            })
        },
        dateFormat(value) {
            return moment(value).format("DD.MM.YYYY. HH:mm");
        },
    }
}
</script>

<style scoped>
.page {
    margin-top: 50px;
    margin-left: 15%;
    margin-right: 15%;
    padding-bottom: 150px;
}

h1 {
    text-align: left;
    margin-bottom: 40px;
}

.unavaliable-period {
    width: auto;
    height: auto;
    margin-bottom: -30px;
    display: flex;
}

.unavaliable-period > p {
    font-size: large;
    margin-top: 5px;
    margin-right: 20px;
}

.date-input, .date-picker {
    display: flex;
}

.icon {
    margin-top: 10px;
    margin-right: 10px;
    margin-left: 10px;
}

.btn {
    color: white;
    background-color: #2c3e50;
    width: 38px;
    height: 38px;
    margin-left: 20px;
    padding-left: 10px;
}

.calendar {
    margin-top: 30px;
    height: 120vh;
}
</style>