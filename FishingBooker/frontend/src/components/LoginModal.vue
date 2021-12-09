<template>
  <div id="app">
  <div class="modal-overlay" @click="$emit('close-modal')"></div>
   <div class="modal-inner">
     <h1>Log In</h1>
      <div class="form-group input mb-4 mt-2">
       <input type="text" class="form-control input" placeholder="Email *" v-model="email" />
      </div>
      <div class="form-group">
       <input type="password" class="form-control input" placeholder="Password *" v-model="password"/>
      </div>
      <div class="form-group mt-4 button-div">
        <button type="button" class="btn log-btn p-2" @click="Login">Log in</button>
       <button type="button" class="btn cancel-btn p-2" @click="$emit('close-modal')">Cancel</button>
      </div>
</div>
</div>
</template>

<script>
import {mapActions} from 'vuex';
export default {
  props: ['showModal'],
  data(){
    return{
      email : '',
      password: ''
    }
  },
  computed:{
      userRole(){
          return this.$store.getters.getRole;
      }
  },
  methods:{
    ...mapActions(['fetchToken']),
    Login(){
      const loginRequest = {
        'email' : this.email,
        'password': this.password
      }
      this.fetchToken(loginRequest)
      .then(() => {
        if(this.userRole == 'ROLE_COTTAGE_OWNER') {
          this.$router.push({ name: 'Homepage', params: {data: 2 } });
        } else if (this.userRole == 'ROLE_SHIP_OWNER') {
          this.$router.push({ name: 'Homepage', params: {data: 1 } });
        } else if (this.userRole == 'ROLE_INSTRUCTOR') {
          this.$router.push({ name: 'Homepage', params: {data: 0 } });
        }
        this.$emit('close-modal')
      })
      
    }
  }
}
</script>

<style scoped>
*{
 margin: 0;
 padding: 0;
 box-sizing: border-box;
}

#app {
 position: absolute;
 
 display: flex;
 justify-content: center;
 align-items: center;
  left:0;
 top:0;
 width: 100vw;
 min-height: 100vh;
 overflow-x: hidden;
}


.modal-overlay {
 position: absolute;
 top: 0;
 left: 0;
 right: 0;
 bottom: 0;
 z-index: 98;
 background-color: rgba(0, 0, 0, 0.3);
}
.modal-inner{
  flex-direction: column;
  justify-content: space-around;
  width: 400px;
  height: 300px;
   background-color: #8495e8;
  z-index: 1000;
   position: fixed;
  border-radius: 16px;
 
 padding: 25px;
}
.button-div{
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-around;
}
.log-btn{
  color:white;
  background: #0e0f40;
   width: 30%;
}
.cancel-btn{
  background: white;
  color: #0e0f40;
  width: 30%;
}
.input{
  height: 50px;
}
.fade-enter-active, .fade-leave-active {
    transition: opacity .5s
}
.fade-enter, .fade-leave-to /* .fade-leave-active in <2.1.8 */ {
    opacity: 0
}

</style>
