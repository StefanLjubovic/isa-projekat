<template>
  <div id="app">
    <div class="modal-overlay"></div>
    <div class="modal-inner">
      <div class="login" v-if="!this.changePassword">
        <h1>Log In</h1>
        <div class="form-group input mb-4 mt-2">
            <input type="text" class="form-control input" placeholder="Email *" v-model="email" />
        </div>
        <div class="form-group">
          <input type="password" class="form-control input" placeholder="Password *" v-model="password"/>
        </div>
        <p class="error-text" v-if="error">Invalid email or password!</p>
        <div class="form-group button-div confirm-buttons">
          <button type="button" class="btn log-btn p-2" @click="Login">Log in</button>
          <button type="button" class="btn cancel-btn p-2" @click="$emit('close-modal')">Cancel</button>
        </div>
      </div>

      <div class="passwordChange" v-else>
        <h1>Change Password</h1>
        <div class="form-group input mb-4 mt-2">
            <input type="password" class="form-control input" placeholder="New password *" v-model="v$.newPassword.password1.$model" />
        </div>
        <div class="form-group">
          <input type="password" class="form-control input" placeholder="Repeat password *" v-model="v$.newPassword.password2.$model"/>
        </div>
        <p class="error-text" v-if="v$.newPassword.password1.$error">Password should have at least 6 characters!</p>
        <p class="error-text" v-else-if="v$.newPassword.password2.$error">Passwords do not match!</p>
        <div class="form-group button-div confirm-buttons">
          <button type="button" class="btn log-btn change-btn p-2" :disabled="v$.newPassword.$invalid" @click="submitChangePassword()">Change</button>
        </div>
      </div>
      
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import {mapActions} from 'vuex';
import server from '../server';
import useValidate from '@vuelidate/core'
import {required, sameAs, minLength} from '@vuelidate/validators'

export default {
  props: ['showModal'],
  data(){
    return{
      email : '',
      password: '',
      error : false,
      errorPassword: false,
      changePassword: false,
      newPassword: {
        password1: '',
        password2: ''
      }
    }
  },
  computed:{
      userRole(){
          return this.$store.getters.getRole;
      }
  },
  setup() {
        return { v$: useValidate() }
    },
    validations() {
        return {
          newPassword: {
            password1: { required, minLength: minLength(6)},
            password2: { required, sameAs:sameAs(this.newPassword.password1)}
          }
        }
    },
  methods:{
    ...mapActions(['fetchToken']),
    async Login(){
      const loginRequest = {
        'email' : this.email,
        'password': this.password
      }

      await this.fetchToken(loginRequest)
      .then(() => {
        if(this.userRole == 'ROLE_ADMIN') {
          axios.get(`${server.baseUrl}/user/passwordChanged`, { headers: { 'Authorization' : `Bearer ${this.$store.getters.getToken}` }})
          .then((response) => {
            if(!response.data) {
              this.changePassword = true;
            } else {
              this.$emit('close-modal')
            }
          })
        } else {
          if(this.userRole == 'ROLE_COTTAGE_OWNER') {
            this.$router.push({ name: 'Homepage', params: {data: 2 } });
          } else if (this.userRole == 'ROLE_SHIP_OWNER') {
            this.$router.push({ name: 'Homepage', params: {data: 1 } });
          } else if (this.userRole == 'ROLE_INSTRUCTOR') {
            this.$router.push({ name: 'Homepage', params: {data: 0 } });
          }
          this.$emit('close-modal')
        }
      })
      .catch(()=>{
         this.error=true
      })  
    },
    async submitChangePassword() {
      await server.changePassword(this.newPassword.password1, this.$store.getters.getToken)
      .then((response) => {
        if(response.success){
          this.$emit('close-modal')
          this.changePassword = false;

          this.$swal.fire({
            position: 'top-end',
            icon: 'success',
            title: 'Password changed!',
            showConfirmButton: false,
            timer: 1500
          })
        }else{
          this.$swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Something went wrong.',
            confirmButtonColor: '#2c3e50'
          })
        }
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

h1 {
  margin-bottom: 40px;
  text-shadow: none;
  color: #2c3e50;
}

.confirm-buttons {
  margin-top: 50px;
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
  width: 70vh;
  height: 50vh;
   background-color: #d7dceb;
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
  background: #2c3e50;
   width: 30%;
   margin-left: 50px;
}
.cancel-btn{
  background: white;
  color: #2c3e50;
  width: 30%;
  margin-right: 50px;
}

.change-btn {
  margin-left: -15px;
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

input {
  padding-left: 10px;
}

.error-text {
  color: red;
  text-shadow: none;
  margin-top: 5px;
  margin-bottom: -29px;
}

</style>
