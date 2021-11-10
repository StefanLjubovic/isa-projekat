<template>
  <div class="wrapper container">
      <div class="title-div">
      <h1>
          {{searchTitle}}
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
       <input type="text" class="form-control" placeholder="Search by name*" v-model="name" />
      </div>
      <div class="form-group">
       <input type="text" class="form-control" placeholder="Search by address*"  v-model="address"  />
      </div>
      <div class="form-group">
       <input type="text" class="form-control" placeholder="Search by mark*" v-model="mark" />
      </div>
      <button type="button" class="btn confirm-btn" @click="sortAndFilterEntities"><i class="fas fa-search"></i></button>
    </div>
  </div>
</template>

<script>
export default {
data(){
    return{
        sort: "",
        name: "",
        address:"",
        mark: ""
    }
},
props:['searchTitle']
,
methods:{
    changeButtonState: function(state){
        this.sort=state;
        this.$refs.btnToggle.innerText = state
    },
    sortAndFilterEntities: function(){
        this.$emit('filter-sort',this.sort,this.name,this.address,this.mark);
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