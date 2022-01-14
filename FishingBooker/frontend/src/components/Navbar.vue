<template>
  <nav class="navbar navbar-expand-lg navbar-dark">
    <div class="navbar-nav navigation">
      <div class="options">
        <div v-if="userRole!= 'ROLE_COTTAGE_OWNER' && userRole != 'ROLE_SHIP_OWNER' && userRole != 'ROLE_INSTRUCTOR'" class="all-users">
          <!-- Options for clients and unregistrated users -->
          <a class="nav-item nav-link active" href="#"  v-bind:class="{ 'active-link text-light': state==0 }" @click="changeState(0)">All Adventures<span class="sr-only">(current)</span></a>
          <a class="nav-item nav-link active" href="#"  v-bind:class="{ 'active-link text-light': state==1 }" @click="changeState(1)">All Ships</a>
          <a class="nav-item nav-link active" href="#"  v-bind:class="{ 'active-link text-light': state==2 }" @click="changeState(2)">All Cottages</a>
        </div>

        <!-- Client options (userRole 'ROLE_CLIENT') -->
        <div v-if="userRole == 'ROLE_CLIENT'" class="client">
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
          
        <!-- Admin options (userRole 'ROLE_ADMIN') -->
        <div v-if="userRole == 'ROLE_ADMIN'" class="admin">
          <a class="nav-item nav-link active" href="#a" v-bind:class="{ 'active-link text-light': state==4 }" @click="changeState(4)">All Users</a>
          <div class="dropdown">
            <button class="nav-item nav-link active dropdown-toggle drop-btn" v-bind:class="{ 'active-link text-light': state==5 || state==6 || state==7 || state == 8}"
              ref="btnToggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              Requests
            </button>
            <div class="dropdown-menu dropdown-menu-admin" aria-labelledby="dropdownMenuButton">
              <a class="dropdown-item" href="#a" @click="changeState(5)" >Requests for Registration</a>
              <a class="dropdown-item" href="#a" @click="changeState(6)" >Requests for Deleting Account</a>
              <a class="dropdown-item" href="#a" @click="changeState(7)" >Report Requests</a>
              <a class="dropdown-item" href="#a" @click="changeState(8)" >Requests for Revisions and Evaluations</a>
            </div>
          </div>
          <a class="nav-item nav-link active" href="#a"  v-bind:class="{ 'active-link text-light': state==9 }" @click="changeState(9)">Analytics</a>
          <a class="nav-item nav-link active" href="#a"  v-bind:class="{ 'active-link text-light': state==10 }" @click="changeState(10)">Complaints</a>
        </div>

        <!-- Cottage owner options (userRole 'ROLE_COTTAGE_OWNER') -->
        <div v-if="userRole == 'ROLE_COTTAGE_OWNER'" class="cottage-owner">
          <a class="nav-item nav-link active" href="#"  v-bind:class="{ 'active-link text-light': state==2 }" @click="changeState(2)">My Cottages</a>
          <a class="nav-item nav-link active" href="#"  v-bind:class="{ 'active-link text-light': state==22 }" @click="changeState(22)">Reservation History</a>
          <a class="nav-item nav-link active" href="#"  v-bind:class="{ 'active-link text-light': state==24 }" @click="changeState(24)">Analytics</a>
        </div>

        <!-- Ship owner options (userRole 'ROLE_SHIP_OWNER') -->
        <div v-if="userRole == 'ROLE_SHIP_OWNER'" class="cottage-owner">
          <a class="nav-item nav-link active" href="#"  v-bind:class="{ 'active-link text-light': state==1 }"  @click="changeState(1)">My Ships</a>
          <a class="nav-item nav-link active" href="#"  v-bind:class="{ 'active-link text-light': state==42 }" @click="changeState(42)">Reservation History</a>
          <a class="nav-item nav-link active" href="#"  v-bind:class="{ 'active-link text-light': state==44 }" @click="changeState(44)">Analytics</a>
        </div>

        <!-- Fishing instructor options (userRole 'ROLE_INSTRUCTOR') -->
        <div v-if="userRole == 'ROLE_INSTRUCTOR'" class="instructor">
          <a class="nav-item nav-link active" href="#a"  v-bind:class="{ 'active-link text-light': state==0 }" @click="changeState(0)">My Adventures</a>
          <a class="nav-item nav-link active" href="#a"  v-bind:class="{ 'active-link text-light': state==1 }" @click="changeState(1)">Reservation History</a>
          <a class="nav-item nav-link active" href="#a"  v-bind:class="{ 'active-link text-light': state==2 }" @click="changeState(2)">My Schedule</a>
          <a class="nav-item nav-link active" href="#a"  v-bind:class="{ 'active-link text-light': state==4 }" @click="changeState(4)">Analytics</a>
        </div>
        
      </div>
      
      <div class="my-profile" v-if="userRole != ''">
        <a class="nav-item nav-link active" href="#"  v-bind:class="{ 'active-link text-light': state==3 }" @click="changeState(3)">My profile</a>
      </div>

    </div>
  </nav>
</template>

<script>
export default {
  props:['state'],
  computed:{
      userRole(){
          return this.$store.getters.getRole;
      }
  },
  methods:{
    changeState: function(state){
      this.$emit('change-state', state);
    }
  }
}
</script>

<style scoped>
.navbar {
  margin-top: -45px;
  height: 45px;
  background-color: rgba(19, 28, 37, 0.4);
}

.all-users{
  display: flex;
  align-items: center;
}


.client, .admin {
  display: flex;
  align-items: center;
}

.cottage-owner{
  display: flex;
  align-items: center;
  margin-right: 25%;
}

.ship-owner{
  display: flex;
  align-items: center;
  margin-right: 25%;
}

.cottage-owner a{
  width: 148px;
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

.dropdown-menu-admin {
  width: 300px;
}
.dropdown-item:hover {
  background-color: rgb(231, 227, 226);
  width: 100%;
}

.instructor {
  display: flex;
}
.instructor a {
  width: 148px;
}

</style>
