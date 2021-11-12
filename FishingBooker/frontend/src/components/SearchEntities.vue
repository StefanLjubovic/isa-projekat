<template>
  <div class="wrapper container">
      <div class="title-div">
      <h1>
          {{searchTitle}}
      </h1>
      <button type="button" class="btn btn-success" v-if="!searchTitle.includes('History') && (role==0 || role == 5)">Special offer&nbsp;&nbsp;<i class="fas fa-money-bill-wave"></i></button>
    </div>
    <div class="filter-div" v-if="!searchTitle.includes('History')">
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
      <button v-if="!searchTitle.includes('History')" type="button" class="btn confirm-btn" @click="sortAndFilterEntities"><i class="fas fa-search"></i></button>
    </div>
     <div class="sort-history" v-if="searchTitle.includes('History')">
        <div class="dropdown">
  <button class="btn droptdown-btn dropdown-toggle" ref="btnSort" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
      Sort by
  </button>
    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
        <a class="dropdown-item" href="#" @click="sortHistory('')">&nbsp;</a>
        <a class="dropdown-item" href="#" @click="sortHistory('Date')">Date</a>
        <a class="dropdown-item" href="#" @click="sortHistory('Duration')">Duration</a>
        <a class="dropdown-item" href="#" @click="sortHistory('Price')">Price</a>
  </div>
</div>
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
    },
    sortHistory: function(value){
        this.sort=value;
        this.$refs.btnSort.innerText = value
        if(value == '') this.$refs.btnSort.innerText = 'Sort by'
        this.$emit('sort-history',value);
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
.sort-history{
    display: flex;
    justify-content: flex-start;
    margin-left: 2vw;
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