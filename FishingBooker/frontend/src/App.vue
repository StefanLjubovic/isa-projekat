<template>
<div>
  <Header @open-modal="showModal=true"></Header>
  <transition name="fade" appear>
    <LoginModal v-if="showModal" @close-modal="showModal=false"></LoginModal>
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
      showModal: false
    }
  }
}
</script>
<style>
#app {
  font-family: 'Playfair Display', serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

.fade-enter-active, .fade-leave-active {
    transition: opacity .5s
}
.fade-enter, .fade-leave-to /* .fade-leave-active in <2.1.8 */ {
    opacity: 0
}
</style>
