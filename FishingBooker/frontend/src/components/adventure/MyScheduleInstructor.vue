<template>
    <div class="page">
        <h1>My Schedule</h1>
        <div v-if="userRole == 'ROLE_INSTRUCTOR'" class="unavaliable-period">
            <p>Define unavaliable period: </p>
            <form @submit.prevent>
                <div class="mb-4">
                    <v-date-picker v-model="range" mode="dateTime" :masks="masks" is-range >
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
                                        :value="inputValue.end" v-on="inputEvents.end" />
                                    <i class="fas fa-calendar-alt icon"></i>
                                </div>
                            </div>
                        </template>
                    </v-date-picker>
                </div>
            </form>
            <button class="btn" @click="defineUnavaliablePeriod()"><i class="fas fa-check"></i></button>
        </div>
        <CalendarView :unavailablePeriods="unavailablePeriods"/>
    </div>
</template>

<script>
import CalendarView from "@/components/CalendarView.vue"
import axios from 'axios';
import server from '../../server';

export default {
    components: {
        CalendarView
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
            unavailablePeriods: [],
            reservations: []
        };
    },
    mounted() {
        axios.get(`${server.baseUrl}/instructor/unavailablePeriods`, {
            headers: {
                'Authorization': `Bearer ${this.$store.getters.getToken}`
            }
        })
        .then((response) => {
            for(let period of response.data) {
                this.unavailablePeriods.push({
                    id : period.id,
                    dates : { start : new Date(period.fromDateTime), end : new Date(period.toDateTime) },
                    customData : { title : period.message, isUnavailable : true }
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
            axios.post(`${server.baseUrl}/instructor/unavailablePeriod`, period, {
                headers: {
                    'Authorization': `Bearer ${this.$store.getters.getToken}`
                }
            })
            .then((response) => {
                this.unavailablePeriods.push({
                    id : response.data.id,
                    dates : { start : new Date(response.data.fromDateTime), end : new Date(response.data.toDateTime) },
                    customData : { title : response.data.message, isUnavailable : true }
                });
                this.$swal({
                    position: 'top-end',
                    icon: 'success',
                    title: 'Unavaliable period saved!',
                    showConfirmButton: false,
                    timer: 2000
                })
            })
            .catch(() => {
                this.$swal("There is already defined unavailable period in this range!");
            })
        }
    }
}
</script>

<style scoped>
.page {
    margin-top: 50px;
    margin-left: 15%;
    margin-right: 15%;
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

</style>