<template>
  <div class="wrapper container">
    <div class="title-div">
      <h1>
        {{ searchTitle }}
      </h1>
      <button type="button" class="btn btn-success" v-bind:class="{ 'active': offerActivate }" @click="getOffers" v-if="
          !searchTitle.includes('History') &&
          userRole != 'ROLE_COTTAGE_OWNER' &&
          userRole != 'ROLE_SHIP_OWNER' &&
          userRole != 'ROLE_INSTRUCTOR'" >
        Special offer&nbsp;&nbsp;<i class="fas fa-money-bill-wave"></i>
      </button>
    </div>
     <div class="filter-div" v-if="!searchTitle.includes('History')">
      <div class="dropdown">
        <button class="btn droptdown-btn dropdown-toggle" ref="btnToggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" ></button>
        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
          <a class="dropdown-item" href="#" @click="changeButtonState('')"
            >&nbsp;</a >
          <a class="dropdown-item" href="#" @click="changeButtonState('Ime')"
            >Ime</a >
          <a
            class="dropdown-item"
            href="#"
            @click="changeButtonState('Lokacija')"
            >Lokacija</a >
          <a class="dropdown-item" href="#" @click="changeButtonState('Ocena')"
            >Ocena</a >
        </div>
      </div>
      <div class="form-group">
        <input
          type="text"
          class="form-control"
          placeholder="Search by name*"
          v-model="name" />
      </div>
      <div class="form-group">
        <input
          type="text"
          class="form-control"
          placeholder="Search by address*"
          v-model="address" />
      </div>
      <div class="form-group">
         <input
          placeholder="Date from* "
           class="textbox-n form-control" 
           type="text" 
           onfocus="(this.type='date')"
          id = "dateFromfield"
          :formatter="format"
          v-model="dateFrom"/>
      </div>
      <div class="form-group">
        <input
          placeholder="Date to* "
           class="textbox-n form-control" 
           type="text" 
           onfocus="(this.type='date')"
          id = "dateTofield"
          v-model="dateTo"/>
      </div>
      <div class="form-group input-mark">
        <input
          type="text"
          class="form-control"
          placeholder="Mark*"
          v-model="mark"  />
      </div>
      <button
        v-if="!searchTitle.includes('History')"
        type="button"
        class="btn confirm-btn"
        @click="sortAndFilterEntities" >
        <i class="fas fa-search"></i>
      </button>
    </div>
    <div class="sort-history" v-if="searchTitle.includes('History')">
      <div class="dropdown">
        <button
          class="btn droptdown-btn dropdown-toggle"
          ref="btnSort"
          type="button"
          id="dropdownMenuButton"
          data-toggle="dropdown"
          aria-haspopup="true"
          aria-expanded="false" > Sort by
        </button>
        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
          <a class="dropdown-item" href="#" @click="sortHistory('')">&nbsp;</a>
          <a class="dropdown-item" href="#" @click="sortHistory('Date')"
            >Date</a >
          <a class="dropdown-item" href="#" @click="sortHistory('Duration')"
            >Duration</a >
          <a class="dropdown-item" href="#" @click="sortHistory('Price')"
            >Price</a >
        </div>
      </div>
    </div>
  </div>
</template>



<script>
export default {
  data() {
    return {
      sort: "",
      name: "",
      address: "",
      mark: "",
      offerActivate : false,
      dateFrom : undefined,
      dateTo : undefined,
    };
  },
  props: ["searchTitle"],
  emits:['get-offers'],
  computed: {
    userRole() {
      return this.$store.getters.getRole;
    }
  },
  watch: {
    searchTitle(){
     this.offerActivate = false
    this.$emit('get-offers',this.offerActivate)
    },
    dateFrom(){
      console.log(this.dateFrom)
    var today = new Date(this.dateFrom);
    var dd = today.getDate() + 1;
    var mm = today.getMonth() + 1; //January is 0!
    var yyyy = today.getFullYear();

    if (dd < 10) {
      dd = '0' + dd;
    }

    if (mm < 10) {
      mm = '0' + mm;
    } 
    today = yyyy + '-' + mm + '-' + dd;
    document.getElementById("dateTofield").setAttribute("min", today);
    }
  },
  // updated(){
  //   this.offerActivate = false
  //    this.$emit('get-offers',this.offerActivate)
  // },
  methods: {
    changeButtonState: function (state) {
      this.sort = state;
      this.$refs.btnToggle.innerText = state;
    },
    sortAndFilterEntities: function () {
      this.$emit("filter-sort", this.sort, this.name, this.address, this.mark , new Date(this.dateFrom),new Date(this.dateTo));
    },
    getOffers(){
      console.log(this.dateFrom)
      this.offerActivate= !this.offerActivate
      this.$emit('get-offers',this.offerActivate)
    },
    sortHistory: function (value) {
      console.log(value);
      this.sort = value;
      this.$refs.btnSort.innerText = value;
      if (value == "") this.$refs.btnSort.innerText = "Sort by";
      this.$emit("sort-history", value);
    }
  },
  mounted(){
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1; //January is 0!
    var yyyy = today.getFullYear();

    if (dd < 10) {
      dd = '0' + dd;
    }

    if (mm < 10) {
      mm = '0' + mm;
    } 
    today = yyyy + '-' + mm + '-' + dd;
    document.getElementById("dateFromfield").setAttribute("min", today);
  }
};
</script>

<style scoped>
.wrapper {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}
.title-div {
  display: flex;
  justify-content: space-between;
  flex-direction: row;
  margin-left: 9%;
  margin-right: 9%;
  margin-bottom: 30px;
  margin-top: 40px;
}
.filter-div {
  display: flex;
  justify-content: space-evenly;
  margin-left: 7%;
  margin-right: 7%;
}
.sort-history {
  display: flex;
  justify-content: flex-start;
  margin-left: 2vw;
}
.active{
  transform: scale(0.95);
  box-shadow: 3px 2px 22px 1px rgba(0, 0, 0, 0.24);
}
input {
  width: 100%;
}
.input-mark{
  width:7%;
}
.droptdown-btn {
  width: 100%;
  color: white;
  background: #0e0f40;
}
.confirm-btn {
  color: white;
  background: #0e0f40;
  width: 4vw;
}
</style>