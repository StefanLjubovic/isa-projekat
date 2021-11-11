<template>
<div class="container">
    <h1 class="mt-4">My Reservations</h1>
  <table class="table table-color">
  <thead>
    <tr>
      <th scope="col">Name</th>
      <th scope="col">Date from</th>
      <th scope="col">Duration</th>
      <th scope="col">Price</th>
      <th scope="col">Cancel</th>
    </tr>
  </thead>
  <tbody>
    <tr v-for="entity in entities" :key="entity.name">
      <td>{{entity.name}}</td>
      <td>{{entity.date}}</td>
      <td>{{entity.duration}} days</td>
      <td>{{entity.price}}</td>
      <td><i class="fas fa-window-close fa-2x icon"></i></td>
    </tr>
  </tbody>
</table>
</div>
</template>

<script>
export default {
  props:['state'],
  emits:['open-complaint'],
  updated(){
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
          date: '5.10.2021',
          duration: 5,
          price: 50000
        },
        {
          name: 'Marijina vikendica',
          date: '5.10.2021',
          duration: 5,
          price: 50000
        },
        {
          name: 'Marijina vikendica',
          date: '5.10.2021',
          duration: 5,
          price: 50000
        }
      ]
    }
  },
  mounted(){
    if(this.state==4) this.entityName='Cottage';
    else if(this.state==5) this.entityName='Ship';
    else if(this.state==6) this.entityName='Adventure';
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
    h1{
        display: flex;
        justify-content: flex-start;
    }
</style>