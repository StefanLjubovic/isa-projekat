<template>
  <nav class="navbar navbar-expand-lg navbar-dark">
    <div class="navbar-nav navigation">
      <div class="options">
        <!-- Options for all roles -->
        <a class="nav-item nav-link active" href="#" v-bind:class="{ 'active-link text-light': state==0 }" @click="changeState(0)">All Adventures<span class="sr-only">(current)</span></a>
        <a class="nav-item nav-link active" href="#"  v-bind:class="{ 'active-link text-light': state==1 }" @click="changeState(1)">All Ships</a>
        <a class="nav-item nav-link active" href="#"  v-bind:class="{ 'active-link text-light': state==2 }" @click="changeState(2)">All Cottages</a>
        
        <!-- Client options (role 0) -->
        <div v-if="role == 0" class="client">
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
          <a class="nav-item nav-link active" href="#"  v-bind:class="{ 'active-link text-light': state==7 }" @click="changeState(7)">Reservations</a>
          <a class="nav-item nav-link active" href="#"  v-bind:class="{ 'active-link text-light': state==8 }" @click="changeState(8)">Subscriptions</a>
        </div>
        
        <!-- Admin options (role 1) -->
        <div v-if="role == 1">

        </div>

        <!-- Cottage owner options (role 2) -->
        <div v-if="role == 2">

        </div>

        <!-- Ship owner options (role 3) -->
        <div v-if="role == 3">

        </div>

        <!-- Fishing instructor options (role 4) -->
        <div v-if="role == 4">

        </div>
      </div>
      
      <div class="my-profile">
        <a class="nav-item nav-link active" href="#"  v-bind:class="{ 'active-link text-light': state==3 }" @click="changeState(3)">My profile</a>
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
.navbar {
  margin-top: -45px;
  height: 45px;
  background-color: rgba(19, 28, 37, 0.4);
}


.client {
  display: flex;
  align-items: center;
}

.navigation{
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-left: 15%;
  margin-right: 15%;
  width: 100%;
}

.options {
  display: flex;
  align-items: center;
}

div > a{
  font-size: 16px;
  width: 115px;
}
div > button {
  width: 115px;
}
.drop-btn{
  border:none;
  background: transparent;
  font-size: 16px;
}
.active-link {
  background-color: rgba(25, 35, 46, 0.7);
  height: 45px;
  padding-top: 10px;
}
.profile{
  margin-left: 10vw;
}
</style>
