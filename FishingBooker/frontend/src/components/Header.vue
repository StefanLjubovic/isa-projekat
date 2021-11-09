<template>
  <div class="background-img">
      <div class="headerr">
          <div class="logo">
              <h1 id="logo" @click="goToMainPage">Fishing booker</h1>
          </div>
          <div class="buttons-div">
           <a href="#" class="link-light">Sign up</a>
           <a href="#" class="link-light" @click="$emit('open-modal')">Log in</a>
        </div>
      </div>
  </div>
</template>

<script>
export default {
    data(){
        return{
            show:true
        }
    },
    emits:['open-modal'],
    methods:{
        goToMainPage: function(){
             this.$router.push({ path: '/' })
        }
    }
}
</script>

<style scoped>
.background-img{
    width:100%;
    height: 40vh;
    background:url(../assets/fish-crop.jpg);
    background-size:cover;
}
 .headerr{
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
        justify-content:flex-end;
        margin-right: 8%;
        margin-top: 15px;
    }

    a{
        text-decoration: none;
        margin-right: 5%;
        height: 15px;
        font-size: 20px;
    }

    .fade-enter-active, .fade-leave-active {
    transition: opacity .5s
}
.fade-enter, .fade-leave-to /* .fade-leave-active in <2.1.8 */ {
    opacity: 0
}
.page-wraper{
     position: relative;
     width: 100vw;
 min-height: 100vh;
 overflow-x: hidden;
}
h1{
    cursor: pointer;
}

</style>