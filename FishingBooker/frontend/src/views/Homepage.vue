<template>
  <NavBar @change-state="changeState" :state="state"></NavBar>

  <!-- Options for all roles -->
  <SearchEntities v-if="state!=3" :searchTitle="searchTitle"  @filter-sort="filterSort"/>
  <div v-if="state==0 || state==1 ||state==2" class="adventures-wrapper">
    <div class="gap" v-for="entity in entitiesForDisplay" :key="entity.name">
      <Entity :entity="entity" @entity-details="openEntityDetails(entity)"/>
    </div>
  </div>
  
  <!-- Client and unregistrated user options (role 0 && 5) -->
  <div v-if="role == 0 || role == 5">
    <ClientProfile v-if="state==3"/>
    <ClientHistory v-if="state==4 || state==5 || state==6"/>
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
  
</template>

<script>
import NavBar from "@/components/Navbar.vue";
import Entity from "@/components/EntityDiv.vue";
import SearchEntities from "@/components/SearchEntities.vue";
import ClientProfile from "@/components/ClientProfile.vue";
import ClientHistory from "@/components/ClientHistory.vue";
//import Server from '../server'

export default {
    components:{
        NavBar,
        SearchEntities,
        Entity,
        ClientProfile,
        ClientHistory
    },
    data(){
      return{
        state: 0,
        searchTitle: 'All adventures',
        entities: [],
        entitiesForDisplay: []
      }
    },
    methods:{
      changeState: async function(state){
        this.state=state;
        /*const resp=await Server.getAllEntities(this.state)
        this.entitiesForDisplay=JSON.parse(JSON.stringify(resp.data));
        this.entities=resp.data;*/
        if(state==0)this.searchTitle="Adventures we offer";
        else if(state==1) this.searchTitle="Ships we offer";
        else if(state==2) this.searchTitle="Cottages we offer";
        else if(state==4) this.searchTitle="History of reserved cottages"
        else if(state==5) this.searchTitle="History of reserved ships"
        else if(state==6) this.searchTitle="History of reserved adventures"
      },

      filterSort: function(sort,name,address,mark){
        console.log(sort,name,address,mark)
        this.entitiesForDisplay=this.entities.filter(
          (entity) => 
                    entity.name.toLowerCase().includes(name.toLowerCase()) &&
                    entity.averageGrade
                                      .toString() 
                                      .toLowerCase()
                                      .includes(mark.toLowerCase())

                    
          );
          if(sort=="Ime") this.entitiesForDisplay.sort(function(a,b){return ('' + a.name).localeCompare(b.name)});
          else if(sort=="Ocena")this.entitiesForDisplay.sort(function(a, b){return a.averageGrade-b.averageGrade});
        console.log(this.entities)
      },
      openEntityDetails: function(entity) {
        if(this.state == 0){
          this.$router.push({ path: `/adventureDetails/${entity.id}` })
        } else if (this.state == 1) {
          // navigacija za detalje o kolibi
        } else if (this.state == 2) {
          // navigacija za detalje o brodu
        }
      }
    },
    async mounted(){
      if(this.$route.params.data == undefined)this.state = 0
      else this.state = this.$route.params.data
      /*const resp=await Server.getAllEntities(this.state)
      this.entitiesForDisplay=JSON.parse(JSON.stringify(resp.data));
      console.log(this.entitiesForDisplay)
      this.entities=resp.data;*/
      if(this.state==0) this.searchTitle="Adventures we offer";
        else if(this.state==1)this.searchTitle="Ships we offer"
        else if(this.state==2) this.searchTitle="Cottages we offer";
    }
}
</script>

<style>
.adventures-wrapper{
  height: 100%;
  display: flex;
  padding-top: 50px;
  padding-bottom: 20px;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: flex-start;
}
.gap{
  margin-left: 10vw;
  margin-top: 5vh;
}
</style>