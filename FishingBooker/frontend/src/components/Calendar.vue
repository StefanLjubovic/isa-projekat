<template>
    <vue-cal class="calendar"
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
    components: { VueCal }
})
</script>

<style>
.calendar {
    background-color: white;
    color: #2c3e50;
}

.vuecal__menu, .vuecal__cell-events-count {background-color: #2c3e50; color: white;}
.vuecal__title-bar {background-color: #e0e2e4;}
.vuecal__cell--today, .vuecal__cell--current {background-color: rgba(229, 237, 245, 0.4);}
.vuecal:not(.vuecal--day-view) .vuecal__cell--selected {background-color: rgba(222, 232, 241, 0.4);}
.vuecal__cell--selected:before {border-color: #1e2c3a;}

.vuecal__event.calendar-unavailable { 
    font-size: 15px;
    background-color: #df6060e6;
    border: 1px solid rgb(165, 60, 60);
    color: #fff;
}

.vuecal__event.calendar-sale { 
    font-size: 15px;
    background-color: #43aa62e6;
    border: 1px solid #2e7944e6;
    color: #fff;
}

.vuecal__event.calendar-booked { 
    font-size: 15px;
    background-color: #d3c60fe6;
    border: 1px solid #a7a40de6;
    color: #fff;
}

</style>
