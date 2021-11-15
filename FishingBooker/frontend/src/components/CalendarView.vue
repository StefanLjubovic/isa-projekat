<template>
  <div>
    <v-calendar class="calendar" :masks="masks" :attributes="attributes">
      <template v-slot:day-content="{ day, attributes }">
        <div class="days-container">
          <span >{{ day.day }}</span>
          <div class="day-in-calendar" >
            <div v-for="attr in attributes" :key="attr.key">
              <div class="card-style" :class="bindClass(attr.customData.isUnavaliable)">
                <div>15:00</div>
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
  /*props: {
    dateList,
  },*/
  data() {
    const month = new Date().getMonth();
    const year = new Date().getFullYear();
    return {
      masks: {
        weekdays: 'WWW',
      },
      attributes: [
        {
          key: 1,
          customData: {
            title: 'Lunch with mom.',
            isUnavaliable: true
          },
          dates: new Date(year, month, 2, 15, 30, 0),
        },
        {
          key: 2,
          customData: {
            title: 'Take Noah to basketball practice',
            isUnavaliable: false
          },
          dates: new Date(year, month, 2, 15, 30, 0),
        },
        {
          key: 3,
          customData: {
            title: "Noah's basketball game.",
            isUnavaliable: true
          },
          dates: new Date(year, month, 5, 15, 30, 0),
        },
        {
          key: 4,
          customData: {
            title: 'Take car to the shop',
            isUnavaliable: false
          },
          dates: new Date(year, month, 5, 15, 30, 0),
        },
        {
          key: 4,
          customData: {
            title: 'Meeting with new client.',
            isUnavaliable: false
          },
          dates: new Date(year, month, 7, 15, 30, 0),
        },
        {
          key: 5,
          customData: {
            title: "Mia's gymnastics practice.",
            isUnavaliable: true
          },
          dates: new Date(year, month, 11, 15, 30, 0),
        },
        {
          key: 6,
          customData: {
            title: 'Cookout with friends.',
            isUnavaliable: false
          },
          dates: { months: 5, ordinalWeekdays: { 2: 1 } },
        },
        {
          key: 7,
          customData: {
            title: "Mia's gymnastics recital.",
            isUnavaliable: false
          },
          dates: new Date(year, month, 22, 15, 30, 0),
        },
        {
          key: 8,
          customData: {
            title: 'Visit great grandma.',
            isUnavaliable: true
          },
          dates: new Date(year, month, 2, 15, 30, 0),
        },
      ],
    };
  },
  methods: {
    bindClass: function(isUnavaliable) {
      if(isUnavaliable) return 'bg-red rounded text-white';
      else return 'bg-blue rounded text-white';
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
}
.text-white {
  color: white;
  font-size: 12px;
}

.spacing {
  height: 5px;
}


</style>