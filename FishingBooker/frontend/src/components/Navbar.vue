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

.active-link{
  color:white;
  background:#0e0f40;
}
</style>
