<template>
    <vue-cal class="calendar vuecal--green-theme"
             active-view="week" 
             :disable-views="['years', 'day']"
             today-button
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

<style scoped>
.calendar {
    background-color: white;
    color: #2c3e50;
}

.vuecal__event.unavaliable {background-color: rgba(255, 102, 102, 0.9);border: 1px solid rgb(235, 82, 82);color: #fff;}
</style>
