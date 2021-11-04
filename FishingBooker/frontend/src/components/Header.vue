<template>
  <div class="background">
      <div class="header">
          <div class="logo">
              <h1 id="logo">Fishing booker</h1>
          </div>
          <div class="buttons-div">
           <a href="#" class="link-light">Sign up</a>
           <a href="#" class="link-light">Log in</a>
        </div>
      </div>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
.background{
    width:100%;
    height: 40vh;
    background:url(../assets/fish-crop.jpg);
    background-size:cover;
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
        justify-content: end;
        margin-right: 8%;
        margin-top: 15px;
    }

    a{
        text-decoration: none;
        margin-right: 5%;
        height: 15px;
        font-size: 20px;
    }

</style>