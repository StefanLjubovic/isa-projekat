<template>
  <NavBar @change-state="changeState" :state="state"></NavBar>
    <h1 class="mt-4 subscription-title container" v-if="state==8">My Subscriptions</h1>
  <SearchEntities :searchTitle="searchTitle" v-if="state!=3 && state !=7 && state !=8" @filter-sort="filterSort"/>
  <div class="adventures-wrapper" v-if="state==0 || state==1 ||state==2 || state==8">
   <div class="gap" v-for="entity in entitiesForDisplay" :key="entity.name">
      <Entity :entity="entity" @entity-details="openEntityDetails(entity)"/>
    </div>
  </div>
  <ClientProfile v-if="state==3"/>
  <ClientHistory v-if="state==4 || state==5 || state==6" :state='state' @open-complaint="openComplaint"/>
  <Complaint v-if="showComplaint" @close-modal="closeComplaint"/>
  <ClientReservations v-if="state==7"/>
</template>

<script>
import NavBar from "@/components/Navbar.vue";
import Entity from "@/components/EntityDiv.vue";
import SearchEntities from "@/components/SearchEntities.vue";
import ClientProfile from "@/components/ClientProfile.vue";
import ClientHistory from "@/components/ClientHistory.vue";
import ClientReservations from "@/components/ClientReservations.vue";
import Complaint from "@/components/Complaint.vue";
import Server from '../server'

export default {
    components:{
        NavBar,
        SearchEntities,
        Entity,
        ClientProfile,
        ClientHistory,
        Complaint,
        ClientReservations
    },
    data(){
      return{
        state: 0,
        searchTitle: 'All adventures',
        showComplaint: false,
        entities: [],
        entitiesForDisplay: []
      }
    },
    methods:{
      changeState: async function(state){
        this.state=state;
        const resp=await Server.getAllEntities(this.state)
        this.entitiesForDisplay=JSON.parse(JSON.stringify(resp.data));
        this.entities=resp.data;
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
      },
      closeComplaint: function(){
        this.showComplaint=false;
        document.getElementById('appContainer').style.overflow = 'unset';
        document.getElementById('appContainer').style.height='unset';
      },
      openComplaint: function(){
        this.showComplaint=true;
        document.getElementById('appContainer').style.overflow ='hidden';
        document.getElementById('appContainer').style.height='100vh';
      }
    },

    async mounted(){
      if(this.$route.params.data == undefined)this.state = 0
      else this.state = this.$route.params.data
      const resp=await Server.getAllEntities(this.state)
      this.entitiesForDisplay=JSON.parse(JSON.stringify(resp.data));
      console.log(this.entitiesForDisplay)
      this.entities=resp.data;
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
  margin-left: 6vw;
}
.subscription-title{
  display: flex;
  justify-content: flex-start;
}
.gap{
  margin-left: 10vw;
  margin-top: 5vh;
}
</style>