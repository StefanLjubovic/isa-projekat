<template>
  <div class="wrapper container">
      <div class="title-div">
      <h1>
          Adventures we offer
      </h1>
      <button type="button" class="btn btn-success">Special offer&nbsp;&nbsp;<i class="fas fa-money-bill-wave"></i></button>
    </div>
    <div class="filter-div">
        <div class="dropdown">
  <button class="btn droptdown-btn dropdown-toggle" ref="btnToggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
  </button>
    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
        <a class="dropdown-item" href="#" @click="changeButtonState('')">&nbsp;</a>
        <a class="dropdown-item" href="#" @click="changeButtonState('Ime')">Ime</a>
        <a class="dropdown-item" href="#" @click="changeButtonState('Lokacija')">Lokacija</a>
        <a class="dropdown-item" href="#" @click="changeButtonState('Ocena')">Ocena</a>
  </div>
</div>
        <div class="form-group">
       <input type="text" class="form-control" placeholder="Search by name*" value="" />
      </div>
      <div class="form-group">
       <input type="text" class="form-control" placeholder="Search by address*" value="" />
      </div>
      <div class="form-group">
       <input type="text" class="form-control" placeholder="Search by mark*" value="" />
      </div>
      <button type="button" class="btn confirm-btn"><i class="fas fa-search"></i></button>
    </div>
  </div>
</template>

<script>
export default {
data(){
    return{
        sort: ""
    }
},
methods:{
    changeButtonState: function(state){
        this.sort=state;
        this.$refs.btnToggle.innerText = state
    }
}
}
</script>

<style scoped>
.wrapper{
    display: flex;
    flex-direction: column;
    justify-content: space-around;
}
.title-div{
    display: flex;
    justify-content: space-between;
    flex-direction: row;
    padding: 30px;
}
.filter-div{
    display: flex;
    flex-direction: row;
    justify-content:space-around ;
}
input{
    width: 300px;
}

.droptdown-btn{
    width: 10vw;
    color:white;
  background: #0e0f40;
}
.confirm-btn{
    color:white;
  background: #0e0f40;
  width: 4vw;
}
</style>