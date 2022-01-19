<template>
  <div>
    <v-calendar class="calendar" :masks="masks" :attributes="unavailablePeriods">
      <template v-slot:day-content="{ day, attributes }">
        <div class="days-container">
          <span >{{ day.day }}</span>
          <div class="day-in-calendar" >
            <div v-for="attr in attributes" :key="attr.id">
              <div class="card-style" :class="bindClass(attr.customData.isUnavailable)">
                <!--<span v-if="index == 0"> {{ msToTime(attr.dates[0].startTime) }} </span>
                <span v-if="index == attributes.length - 1"> {{ msToTime(attr.dates[0].endTime) }} </span>-->
                <div>{{ attr.customData.title }}</div>
              </div>
              <div class="spacing"></div>
            </div>
          </div>
        </div>
      </template>
    </v-calendar>
  </div>
</template>

<script>

export default {
  props: [
    'unavailablePeriods'
  ],
  data() {
    return {
      masks: {
        weekdays: 'WWW',
      },
    };
  },
  methods: {
    bindClass: function(isUnavaliable) {
      if(isUnavaliable) return 'bg-red rounded text-white';
      else return 'bg-blue rounded text-white';
    },
    msToTime(duration) {
      var minutes = Math.floor((duration / (1000 * 60)) % 60);
      var hours = Math.floor((duration / (1000 * 60 * 60)) % 24);

      hours = (hours < 10) ? "0" + hours : hours;
      minutes = (minutes < 10) ? "0" + minutes : minutes;

      return hours + ":" + minutes;
    }
  }
}
</script>

<style scoped>
.calendar {
  width: 100%;
  height: 740px;
  margin: 30px 0;
}

.day-in-calendar {
  height: 110px;
  padding: 2px;
  overflow-y: scroll;
  border: gray;
}
::-webkit-scrollbar {
    width: 0px;
    background: transparent; 
}

.bg-blue {
  background-color: #2c3e50;
}
.bg-red {
  background-color: rgb(230, 116, 116);
  height: 80px;
}
.text-white {
  color: white;
  font-size: 12px;
}

.spacing {
  height: 5px;
}


</style>