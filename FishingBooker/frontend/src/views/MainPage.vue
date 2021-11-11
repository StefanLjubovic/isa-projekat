<template>
  <div class="page">
      <div class="header">
          <div class="logo">
              <h1 id="logo">Fishing booker</h1>
          </div>
          <div class="buttons-div">
           <a href="#" class="link-light dropdown-toggle item" 
           role="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Sign up</a>
           <div class="dropdown-menu drop" aria-labelledby="dropdownMenuButton">
        <a class="dropdown-item" href="#" @click="openSignUp">As client</a>
    <a class="dropdown-item" href="#">As instructor</a>
    <a class="dropdown-item" href="#">As cottage owner</a>
  </div>
           <a href="#" class="link-light item" @click="showModal=true">Log in</a>
        </div>
      </div>
      <div class="body">
      <div class="body-gap"></div>
      <div class="message">
          <h1>For&nbsp; your&nbsp;  best &nbsp; fishing <br> &emsp;&emsp;&emsp;&emsp;&emsp;experience!</h1>
      </div>
      </div>
      <div class="footer">
          <div class="big-button" @click="routeCotages">
              <h2 style="color:white">See all <br>cotages</h2>
              <i class="fas fa-home fa-5x" style="color:white"></i>
          </div>
          <div class="big-button" @click="routeShips">
              <h2 style="color:white">See all <br>ships</h2>
              <i class="fas fa-ship fa-5x" style="color:white"></i>
          </div>
          <div class="big-button" @click="routeAdventures">
              <h2 style="color:white">See all <br>advenutures</h2>
              <i class="fas fa-car-side fa-5x" style="color:white"></i>
          </div>
      </div>
         <transition name="fade" appear>
  <LoginModal v-if="showModal" @close-modal="showModal=false"></LoginModal>
  </transition>
  </div>
</template>

<script>
import LoginModal from "@/components/LoginModal.vue";
export default {
    data(){
        return{
            showModal: false,
        }
    },
    methods:{
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
        box-shadow: inset 0 0 0 1000px rgba(0,0,0,.2);
        display: flex;
        flex-direction: column;
    }
    .header{
        display:flex;
        flex:1;
        flex-direction: row;
        justify-content: space-between;
        padding: 20px;
    }
   #logo{
        color:rgba(14, 14, 14, 0.88)
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
        font-size: 25px;
    }
    .footer{
        display: flex;
        flex:2;
        justify-content: center;
    }
    .drop{
        margin-top: 2vh;
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
        height: 80%;
        width: 15%;
        border-radius: 10px;
        background: transparent;
        box-shadow: inset 0 0 0 1000px rgba(0,0,0,.4);
        margin-right: 30px;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        cursor:pointer;
    }
    .big-button:hover {
  color: grey;
  box-shadow: 0 0 5px grey;
  text-shadow: 0 0 5px grey;
}


.fade-enter-active, .fade-leave-active {
    transition: opacity .5s
}
.fade-enter, .fade-leave-to /* .fade-leave-active in <2.1.8 */ {
    opacity: 0
}
</style>