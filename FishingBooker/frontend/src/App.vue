<template>
  <div id="appContainer">
    <Header @open-modal="openLogin" @log-out="logOut"></Header>
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
      this.$store.dispatch('logout')
       this.$router.push({ name: 'MainPage'})
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
