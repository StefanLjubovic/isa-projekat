<template>
<div class="container">
  <table class=" table table-striped table-cottage-reservations">
  <thead>
    <tr>
      <th scope="col">Cottage</th>
      <th scope="col">Client</th>
      <th scope="col">Date from</th>
      <th scope="col">Duration</th>
      <th scope="col">Price</th>
      <th scope="col">Report</th>
    </tr>
  </thead>
  <tbody>
    <tr v-for="r in reservations" :key="r.id">
      <td>{{r.name}}</td>
      <td><a href="#">{{r.client}}</a></td>
      <td>{{r.dateTime}}</td>
      <td>{{r.duration}} days</td>
      <td>{{r.price}} </td>
      <td><i class="fas fa-plus-square icon"></i></td>
    </tr>
  </tbody>
</table>
</div>

<!--div class="bg-modal">
      <div class="modal-content">
        <div class="login-title">
          <h3 style="color: rgb(161, 89, 21); font-weight: bolder;"> Client information </h3>
        </div>
        <div v-on:click="loginClose" class="close">+</div>
        <div class = "form-div">
            <input v-model="usernameLog" type="text" class="login-inputs" placeholder="korisničko ime"><br/>
            <input v-model="passwordLog" type="password" class="login-inputs" placeholder="lozinka"> <br/><br/>
            <button v-on:click="loginUser" class="button" style="background-color: rgb(224, 142, 64); color: white;"> Prijavite se</button>
        </div>
      </div>
    
</div-->

</template>

<script>
export default {
  props:['state'],
  emits:['open-complaint'],
  updated(){ },
  data(){
    return{
      reservations: [
        {
          id: '1',
          name: 'Marijina vikendica',
          client: 'anagavrilovic@gmail.com',
          dateTime: '5.10.2021',
          duration: 5,
          price: 50000
        },
        {
          id: '2',
          name: 'Marijina vikendica',
          client: 'stefanljubovic@gmail.com',
          dateTime: '5.11.2021',
          duration: 7,
          price: 70000
        },
        {
          id: '3',
          name: 'Marijina vikendica',
          client: 'monicageller@gmail.com',
          dateTime: '5.11.1994',
          duration: 7,
          price: 70000
        }
      ]
    }
  },
  mounted(){
   
  }
}
</script>

<style scoped>
    .table-cottage-reservations, .table-striped{   
        color: #2c3e50;
        text-align: center;
        border-radius: 10px;
        margin-top: 5% ;
        margin-left: 10%;
        margin-right: 10%;
        width: 80%;
    }
    .icon{
        cursor: pointer;
        color: #151649;
        size: inherit;
    }
    .bg-modal{
      width: 100%;
      height: 100%;
      background-color: rbg(0,0,0, 0.7);
      position: fixed;
      top:0;
      display: none;
      justify-content: center;
      align-items: center;
    }

    .form-div{
      margin-top: 100px;
    }

     .modal-content{
      width: 500px;
      height: 600px;
      border-radius: 10px;
      text-align: center;
      padding: 20px;
      position: relative;
    }
    .login-title{
      margin-top: 50px;
      font-weight: bold;
      align-self: center;
      align-items: center;
    }

    .close{
      position: absolute;
      top:0;
      right: 15px;
      font-size: 40px;
      transform: rotate(45deg);
      cursor: pointer;
    }

</style>