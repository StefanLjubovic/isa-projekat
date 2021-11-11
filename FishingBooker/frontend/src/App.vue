<template>
  <div id="appContainer">
    <Header :role="role" @open-modal="openLogin" @log-out="logOut"></Header>
    <transition name="fade" appear>
      <LoginModal v-if="showModal" @close-modal="closeLogin"></LoginModal>
    </transition>
    <router-view />
  </div>
</template>

<script>
import Header from "@/components/Header.vue";
import LoginModal from "@/components/LoginModal.vue";

export default {
  name:"App",
  components:{
    Header,
    LoginModal   
  },
  data(){
    return{
      showModal: false,
      role: 0,
      /*  0 - client
            1 - admin
            2 - cottage owner
            3 - ship owner
            4 - fishing instructor
            5 - unregistrated user  -> ovako u enumeraciji za role
        */
    }
  },
  methods:{
    openLogin: function(){
      this.showModal = true;
			document.getElementById('appContainer').style.overflow = 'hidden';
			document.getElementById('appContainer').style.height = '100vh';
		},
    closeLogin: function(){
      this.showModal = false;
			document.getElementById('appContainer').style.overflow = 'unset';
			document.getElementById('appContainer').style.height = 'unset';
    },
    logOut: function() {
      // TODO
    }
  }
}
</script>

<style>
#app {
  font-family: 'Calibri';
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  position: relative;
} 

body {
    background-color: #d7dceb
}
.fade-enter-active, .fade-leave-active {
    transition: opacity .5s
}
.fade-enter, .fade-leave-to /* .fade-leave-active in <2.1.8 */ {
    opacity: 0
}
html {
    height: 100%;
    width: 100%;
}
</style>
