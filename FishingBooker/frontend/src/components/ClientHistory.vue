<template>
<div class="container wrapper">
  <table class="table table-color">
  <thead>
    <tr>
      <th scope="col">{{entityName}}</th>
      <th scope="col">Reserved for</th>
      <th scope="col">Duration</th>
      <th scope="col">Price</th>
      <th scope="col">Complaint</th>
      <th scope="col">Revision</th>
    </tr>
  </thead>
  <tbody>
    <tr v-for="reservation in reservationsForDisplay" :key="reservation.id">
      <td>{{reservation.entityName}}</td>
      <td>{{setDateFormat(reservation.dateTime)}}</td>
      <td>{{reservation.durationInHours}} hours</td>
      <td>{{reservation.price}}</td>
      <td><i class="fas fa-plus-square fa-2x icon" @click="$emit('open-complaint',reservation)"></i></td>
      <td><i class="fas fa-plus-square fa-2x icon" @click="$emit('open-revision',reservation)"></i></td>
    </tr>
  </tbody>
</table>
</div>
</template>

<script>
export default {
  props:['state','sort','reservations'],
  emits:['open-complaint','open-revision'],
  updated(){
    if(this.$props.sort == 'Price')this.reservationsForDisplay.sort(function(a, b){return a.price-b.price});
    if(this.$props.sort == 'Duration')this.reservationsForDisplay.sort(function(a, b){return a.durationInHours-b.durationInHours});
    if(this.$props.sort == 'Date')this.reservationsForDisplay.sort(function(a, b){ return new Date(b.dateTime) - new Date(a.dateTime)});
    if(this.state==4) this.entityName='Cottage';
    else if(this.state==5) this.entityName='Ship';
    else if(this.state==6) this.entityName='Adventure';
  },
  data(){
    return{
      entityName: 'Cottage',
      reservationsForDisplay : []
    }
  },
  async mounted(){
    if(this.state==4) this.entityName='Cottage';
    else if(this.state==5) this.entityName='Ship';
    else if(this.state==6) this.entityName='Adventure';
    this.reservationsForDisplay = JSON.parse(JSON.stringify(this.reservations));
  },
  methods:{
     setDateFormat(timestamp){
      var date = new Date(timestamp);
        return date.toLocaleDateString("sr-RS")
    }
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
</style>