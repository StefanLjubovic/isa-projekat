<template>
  <table class="table table-color container">
  <thead>
    <tr>
      <th scope="col">{{entityName}}</th>
      <th scope="col">Date from</th>
      <th scope="col">Duration</th>
      <th scope="col">Price</th>
      <th scope="col">Complaint</th>
      <th scope="col">Revision</th>
    </tr>
  </thead>
  <tbody>
    <tr v-for="entity in entities" :key="entity.name">
      <td>{{entity.name}}</td>
      <td>{{entity.date}}</td>
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
  props:['state'],
  emits:['open-complaint','open-revision'],
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
</style>