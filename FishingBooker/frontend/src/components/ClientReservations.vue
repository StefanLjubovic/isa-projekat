<template>
<div class="container wrapper">
  <h1 class="mt-4">My Reservations</h1>
  <table class="table table-color">
  <thead>
    <tr>
      <th scope="col">Name</th>
      <th scope="col">Reserved for</th>
      <th scope="col">Duration</th>
      <th scope="col">Price</th>
      <th scope="col">Cancel</th>
    </tr>
  </thead>
  <tbody>
    <tr v-for="reservation in reservations" :key="reservation.id">
      <td>{{reservation.entityName}}</td>
      <td>{{setDateFormat(reservation.dateTime)}}</td>
      <td>{{GetDuration(reservation)}}</td>
      <td>{{reservation.price}}</td>
      <td><i class="fas fa-window-close fa-2x icon" v-if="checkDate(reservation)" @click="$emit('open-cancelation',reservation)"></i></td>
    </tr>
  </tbody>
</table>
</div>
</template>

<script>
export default {
  props:['state','reservations'],
  emits:['open-complaint','open-cancelation'],
  updated(){
    if(this.state==4) this.entityName='Cottage';
    else if(this.state==5) this.entityName='Ship';
    else if(this.state==6) this.entityName='Adventure';
  },
  methods:{
    checkDate(entity){
      console.log(entity)
      var dt = new Date();
      dt.setDate(dt.getDate() + 3);
       var date = new Date(entity.dateTime );
       console.log(date)
       console.log(dt)
      if(date < dt)
        return false
      return true
    },
    setDateFormat(timestamp){
      var date = new Date(timestamp);
        return date.toLocaleDateString("sr-RS")
    },
    GetDuration(reservation){
      console.log(reservation)
      if(reservation.durationInHours > 24) return reservation.durationInHours /24+' days'
      else return reservation.durationInHours + ' hours'
    }
  },
  data(){
    return{
      entityName: 'Cottage'
    }
  },
  async mounted(){
    if(this.state==4) this.entityName='Cottage';
    else if(this.state==5) this.entityName='Ship';
    else if(this.state==6) this.entityName='Adventure';
  }
}
</script>

<style scoped>
    .wrapper{
      min-height: 50vh;
    }
    .table-color{
    background: white;
    border-radius: 10px;
    margin-top:5vh ;
    }
    .icon{
        cursor: pointer;
        color: #0e0f40;
    }
    h1{
        display: flex;
        justify-content: flex-start;
    }
</style>