<template>
  <table class="table table-color container">
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
    <tr v-for="entity in entities" :key="entity.name">
      <td>{{entity.name}}</td>
      <td>{{setDateFormat(entity.date)}}</td>
      <td>{{entity.duration}} days</td>
      <td>{{entity.price}}</td>
      <td><i class="fas fa-plus-square fa-2x icon" @click="$emit('open-complaint')"></i></td>
      <td><i class="fas fa-plus-square fa-2x icon" @click="$emit('open-revision')"></i></td>
    </tr>
  </tbody>
</table>
</template>

<script>
export default {
  props:['state','sort'],
  emits:['open-complaint','open-revision'],
  updated(){
    console.log(this.$props)
    if(this.$props.sort == 'Price')this.entities.sort(function(a, b){return a.price-b.price});
    if(this.$props.sort == 'Duration')this.entities.sort(function(a, b){return a.duration-b.duration});
    if(this.$props.sort == 'Date')this.entities.sort(function(a, b){ return new Date(b.date) - new Date(a.date);});
    if(this.state==4) this.entityName='Cottage';
    else if(this.state==5) this.entityName='Ship';
    else if(this.state==6) this.entityName='Adventure';
  },
  data(){
    return{
      entityName: 'Cottage',
      entities: [
        {
          name: 'Marijina vikendica',
          date: new Date(2021, 9, 9),
          duration: 7,
          price: 10000
        },
        {
          name: 'Marijina vikendica',
          date: new Date(2021, 10, 11),
          duration: 4,
          price: 30000
        },
        {
          name: 'Marijina vikendica',
          date: new Date(2021, 6, 5),
          duration: 11,
          price: 20000
        }
      ]
    }
  },
  mounted(){
    if(this.state==4) this.entityName='Cottage';
    else if(this.state==5) this.entityName='Ship';
    else if(this.state==6) this.entityName='Adventure';
  },
  methods:{
    setDateFormat(date){
        return date.toLocaleDateString("sr-RS")
    }
  }
}
</script>

<style scoped>
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