<template>
    <vue-cal class="calendar vuecal--green-theme"
             active-view="week" 
             :disable-views="['years', 'day']"
             today-button
             events-count-on-year-view
             events-on-month-view="short"
             :events="events"/>
</template>

<script>
import VueCal from 'vue-cal'
import 'vue-cal/dist/vuecal.css'

export default ({
    props: [
        'events'
    ],
    components: { VueCal },
    data() {

    }
})
</script>

<style>
.calendar {
    background-color: white;
    color: #2c3e50;
}

.vuecal__event.calendar-unavailable { 
    font-size: 15px;
    background-color: #df6060e6;
    border: 1px solid rgb(235, 82, 82);
    color: #fff;
}

.vuecal__event.calendar-sale { 
    font-size: 15px;
    background-color: #43aa62e6;
    border: 1px solid #2e7944e6;
    color: #fff;
}

</style>
