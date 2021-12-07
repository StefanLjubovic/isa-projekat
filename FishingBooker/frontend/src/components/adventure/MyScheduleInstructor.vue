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
            <button class="btn"><i class="fas fa-check"></i></button>
        </div>
        <CalendarView />
    </div>
</template>

<script>
import CalendarView from "@/components/CalendarView.vue"

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
        };
    },
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