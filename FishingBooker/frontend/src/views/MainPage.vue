<template>
  <div class="page">
      <div class="header">
          <div class="logo">
              <h2 id="logo">Fishing booker</h2>
          </div>
          <div class="buttons-div" v-if="userRole == ''">
           <a href="#" class="link-light dropdown-toggle item" 
           role="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Sign up</a>
           <div class="dropdown-menu drop" aria-labelledby="dropdownMenuButton">
             <a class="dropdown-item" href="#" @click="openSignUp">As client</a>
             <a class="dropdown-item" href="#" @click="openSignUpAsAdvertiser">As advertiser</a>
           </div>
           <a href="#" class="link-light item" @click="showModal=true">Log in</a>
          </div>
          <div class="registrated-user-option mt-3"  v-if="userRole !=''">
              <div class="help"></div>
                    <a href="#" class="link-light item" @click="logOut">Log Out</a>
         </div>
       </div>
      <div class="body">
      <div class="body-gap"></div>
      <div class="message">
          <h1>For your best fishing experience!</h1>
      </div>
      </div>
      <div class="footer">
          <div class="big-button" @click="routeCotages">
              <h2 style="color:white">See all <br>cottages</h2>
              <i class="fas fa-home fa-4x" style="color:white"></i>
          </div>
          <div class="big-button" @click="routeShips">
              <h2 style="color:white">See all <br>ships</h2>
              <i class="fas fa-ship fa-4x" style="color:white"></i>
          </div>
          <div class="big-button" @click="routeAdventures">
              <h2 style="color:white">See all <br>advenutures</h2>
              <i class="fas fa-fish fa-4x" style="color:white"></i>
          </div>
      </div>
         <transition name="fade" appear>
  <LoginModal v-if="showModal" @close-modal="showModal=false"></LoginModal>
  </transition>
  </div>
</template>

<script>
import LoginModal from "@/components/LoginModal.vue";
import {mapActions} from 'vuex';
export default {
    data(){
        return{
            showModal: false,
        }
    },
    methods:{
        ...mapActions(['fetchToken']),
        routeAdventures: function(){
            this.$router.push({ name: 'Homepage', params: {data: 0 } })
        },
        routeCotages: function(){
            this.$router.push({ name: 'Homepage', params: {data: 2 } })
        },
        routeShips: function(){
            this.$router.push({ name: 'Homepage', params: {data: 1} })
        },
        openSignUp: function(){
            this.$router.push({ path: '/client-registration' })
        },
        openSignUpAsAdvertiser: function(){
            this.$router.push({ path: '/advertiser-registration' })
        },
         logOut: function() {
        // TODO
         this.$store.dispatch('logout')
        }
    },
    computed:{
        userRole(){
            return this.$store.getters.getRole;
        }
    },
    async mounted(){
        if(typeof(this.$route.query.id) !="undefined"){
                    this.$swal('Success!',
                    'Client has been registered!',
                    'success');
                
        }
    },
    components:{
        LoginModal,
    }
}
</script>

<style scoped>
    .page{
        position: fixed;
        top: 0;
        left: 0;
        bottom: 0;
        right: 0;
        overflow: auto;
        background:url(../assets/main_page.jpg);
        background-size:cover;
        box-shadow: inset 0 0 0 1000px rgba(16, 50, 112, 0.2);
        display: flex;
        flex-direction: column;
        text-shadow: 1px 1px 3px #363636;
    }
    .header{
        display:flex;
        flex:1;
        flex-direction: row;
        justify-content: space-between;
        margin: 2vh 5vw;
    }
   #logo{
        color:rgba(255, 255, 255, 0.88);
        margin-top:15px;
        margin-left: -130px;
    }
    .logo{
        display: flex;
        flex: 1;
        justify-content: center;
    }
    .buttons-div{
        display: flex;
        flex: 1;
        justify-content: flex-end;
        margin-right: 8%;
        margin-top: 15px;
    }
    .registrated-user-option{
        flex :1;
        display:flex;
        flex-direction: row;
        justify-content: right;
        margin-right: 8%;
    }
    .item{
        text-decoration: none;
        margin-right: 5%;
        height: 15px;
        font-size: 20px;
    }
    .message{
        display: flex;
        flex:1;
        color:white;
        justify-content: center;
    }
    h1 {
        font-size: 70px;
        text-align: right;
        margin-right: 22%;
        margin-top: -20px;
        width: 1000px;
    }
    .footer{
        display: flex;
        flex:2;
        justify-content: space-between;
        margin-left: 15%;
        margin-right: 15%;
    }
    .drop{
        margin-top: 2vh;
        text-shadow: none;
    }
    .body{
        display:flex;
        flex-direction: row;
        flex:2;
    }
    .body-gap{
        flex:1;
    }
    .big-button{
        height: 90%;
        width: 27%;
        margin-top: -30px;
        border-radius: 10px;
        background: transparent;
        box-shadow: inset 0 0 0 1000px rgba(3, 17, 37, 0.4);
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        cursor:pointer;
    }
    .big-button:hover {
  background-color: rgba(52, 66, 87, 0.5);
  box-shadow: 5px 10px 18px #272727;
  margin-top: -33px;
}

i {
    margin-top: 20px;
}

.fade-enter-active, .fade-leave-active {
    transition: opacity .5s
}
.fade-enter, .fade-leave-to /* .fade-leave-active in <2.1.8 */ {
    opacity: 0
}
</style>