<template>
  <div id="calendar-view">
    <calendar
      :eventCategories="eventCategories"
      :events="events"
      ref="calendar"
    />
  </div>
</template>
<script>
const Calendar = require('vue-sweet-calendar')
import 'vue-sweet-calendar/dist/SweetCalendar.css'

export default {
  data() {
    return {
      eventCategories: [
        {
          id: 1,
          title: 'Personal',
          textColor: 'white',
          backgroundColor: 'Blue'
        },
        {
          id: 2,
          title: 'Company-wide',
          textColor: 'white',
          backgroundColor: 'red'
        },
        {
          id: 3,
          title: 'National',
          textColor: 'white',
          backgroundColor: 'green'
        }
      ],
      events: [
        {
          title: 'Event 1',
          start: '2019-04-02',
          end: '2019-04-04',
          repeat: 'monthly',
          categoryId: 1
        },
        {
          title: 'Event 2',
          start: '2019-04-08',
          end: '2019-04-09',
          repeat: 'yearly',
          categoryId: 1
        },
        {
          title: 'Event 3',
          start: '2019-04-10',
          end: '2019-04-11',
          repeat: 'never',
          categoryId: 2
        },
        {
          title: 'Event 4',
          start: '2019-04-23',
          end: '2019-04-23',
          repeat: 'monthly',
          categoryId: 2
        },
        {
          title: 'test5',
          start: '2021-06-17',
          end: '2021-06-18',
          repeat: 'weekly',
          categoryId: 3
        },
      ]
    }
  },
  methods: {
    goToday() {
      this.$refs.calendar.goToday()
    }
  },
  components: {
    Calendar 
  }
}
</script>

<style scoped>
#calendar-view {
    height: 500px;
    background-color: rgb(73, 96, 122);
    margin: 20px 0;
}
</style>