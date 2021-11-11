<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
      <div class="navbar-nav navigation">
        <a class="nav-item nav-link active" href="#" v-bind:class="{ 'active-link text-light': state==0 }" @click="changeState(0)">All Adventures<span class="sr-only">(current)</span></a>
        <a class="nav-item nav-link active" href="#"  v-bind:class="{ 'active-link text-light': state==1 }" @click="changeState(1)">All Ships</a>
        <a class="nav-item nav-link active" href="#"  v-bind:class="{ 'active-link text-light': state==2 }" @click="changeState(2)">All Cottages</a>
        <a class="nav-item nav-link active" href="#"  v-bind:class="{ 'active-link text-light': state==3 }" @click="changeState(3)">My profile</a>
        <div class="dropdown">
          <button class="nav-item nav-link active dropdown-toggle drop-btn" v-bind:class="{ 'active-link text-light': state==4 || state==5 || state==6}"
            ref="btnToggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            History
          </button>
          <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
              <a class="dropdown-item" href="#" @click="changeState(4)" >Cottages</a>
              <a class="dropdown-item" href="#" @click="changeState(5)" >Ships</a>
              <a class="dropdown-item" href="#" @click="changeState(6)" >Adventures</a>
          </div>
        </div>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  props:['state'],
  methods:{
    changeState: function(state){
      this.$emit('change-state',state);
    }
  }
}
</script>

<style>
.navigation{
  display: flex;
  margin-left: 20vw;
}

div > a{
  font-size: 20px;
  color: #0e0f40;
}
.drop-btn{
  border:none;
  background: transparent;
  font-size: 20px;
}
.active-link{
  color:white;
  background:#0e0f40;
}

</style>
