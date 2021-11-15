<template>
  <NavBar @change-state="changeState" :state="state"></NavBar>
  
  <!-- Client and unregistrated user options (role 0 && 5) -->
  <div v-if="role == 0 || role == 5">
    <SearchEntities v-if="state!=3 && state!=7 && state!=8" :searchTitle="searchTitle"  @filter-sort="filterSort"/>
    <div v-if="state==0 || state==1 || state==2" class="adventures-wrapper">
      <div class="gap" v-for="entity in entitiesForDisplay" :key="entity.name">
        <Entity :entity="entity" @entity-details="openEntityDetails(entity)"/>
      </div>
    </div>
    <ClientProfile v-if="state==3"/>
    <ClientHistory v-if="state==4 || state==5 || state==6" :state='state' @open-complaint="openComplaint" @open-revision="openRevision"/>
    <Complaint v-if="showComplaint" @close-modal="closeComplaint"/>
    <RevisionModal v-if="showRevision" @close-modal="closeRevision"/>
    <ClientReservations v-if="state==7"/>
    <h1 class="mt-4 subscription-title container" v-if="state==8" >My Subscriptions</h1>
    <div v-if="state==8" class="adventures-wrapper">
      <div class="gap" v-for="entity in entitiesForDisplay" :key="entity.name">
        <Entity :entity="entity" @entity-details="openEntityDetails(entity)"/>
      </div>
    </div>
  </div>

  <!-- Admin options (role 1) -->
  <div v-if="role == 1">
    <SearchEntities v-if="state == 0 || state == 1 || state == 2" :searchTitle="searchTitle"  @filter-sort="filterSort"/>
    <div v-if="state == 0 || state == 1 || state == 2" class="adventures-wrapper">
      <div class="gap" v-for="entity in entitiesForDisplay" :key="entity.name">
        <Entity :entity="entity" @entity-details="openEntityDetails(entity)"/>
      </div>
    </div>
    <AllUsers v-if="state == 4"/>
    <Requests :state="state" v-if="state == 5 || state == 6 || state == 7 || state == 8" />
    <Complaints v-if="state == 10"/>
    <MyProfile v-if="state == 3"/>
    <AdminAnalytics v-if="state == 9"/>
  </div>

  <!-- Cottage owner options (role 2) -->
  <div v-if="role == 2">
    <div v-if="state == 21">
        <button  type="button" id="add-new-cottage" class="btn btn-success"> <i class="fas fa-plus"></i>&nbsp;  Add new cottage</button>
        <SearchEntities :searchTitle="searchTitle"  @filter-sort="filterSort"/>
    </div>
    <div v-if="state == 21" class="cottages-wrapper">
        <div class="gap" v-for="entity in entitiesForDisplay" :key="entity.name">
        <Entity :entity="entity" @entity-details="openEntityDetails(entity)"/>
      </div>
    </div>  
    <CottageReservations v-if="state==22"/>
    <MyProfile v-if="state == 3"/>
  </div>

  <!-- Ship owner options (role 3) -->
  <div v-if="role == 3">

  </div>

  <!-- Fishing instructor options (role 4) -->
  <div v-if="role == 4">
    <div v-if="state == 0">
        <button  type="button" id="add-new-cottage" class="btn btn-success"><i class="fas fa-plus"></i>&nbsp;  Add new adventure </button>
        <SearchEntities :searchTitle="''"  @filter-sort="filterSort"/>
    </div>
    <div v-if="state == 0" class="cottages-wrapper">
        <div class="gap" v-for="entity in entitiesForDisplay" :key="entity.name">
        <Entity :entity="entity" @entity-details="openEntityDetails(entity)"/>
      </div>
    </div> 
  </div>
  <AdventureReservations v-if="state == 1"/>
  <MyProfile v-if="state == 3"/>
</template>

<script>
import NavBar from "@/components/Navbar.vue"
import Entity from "@/components/EntityDiv.vue"
import SearchEntities from "@/components/SearchEntities.vue"
import ClientProfile from "@/components/ClientProfile.vue"
import ClientHistory from "@/components/ClientHistory.vue"
import ClientReservations from "@/components/ClientReservations.vue"
import Complaint from "@/components/Complaint.vue"
import RevisionModal from "@/components/client/RevisionModal.vue"
import Server from '../server'
import AllUsers from "@/components/admin/AllUsers.vue"
import Requests from "@/components/admin/Requests.vue"
import Complaints from "@/components/admin/Complaints.vue"
import CottageReservations from "@/components/cottage/CottageReservations.vue"
import MyProfile from "@/components/MyProfile.vue"
import AdminAnalytics from "@/components/admin/AdminAnalytics.vue"
import AdventureReservations from "@/components/adventure/AdventureReservations.vue"

export default {
    components:{
        NavBar,
        SearchEntities,
        Entity,
        ClientProfile,
        ClientHistory,
        Complaint,
        ClientReservations,
        AllUsers,
        Requests,
        Complaints,
        RevisionModal,
        CottageReservations,
        MyProfile,
        AdminAnalytics,
        AdventureReservations
    },
    data(){
      return{
        state: 0,
        searchTitle: 'All adventures',
        showComplaint: false,
        showRevision: false,
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

        else if(state==21) this.searchTitle=""
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
          // navigacija za detalje o brodu
        } else if (this.state == 2 || this.state == 21) {
          this.$router.push({ path: `/cottageDetails/${entity.id}` })
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
      },
      closeRevision: function(){
          this.showRevision=false;
        document.getElementById('appContainer').style.overflow = 'unset';
        document.getElementById('appContainer').style.height='unset';
      },
      openRevision: function(){
        this.showRevision=true;
        document.getElementById('appContainer').style.overflow ='hidden';
        document.getElementById('appContainer').style.height='100vh';
      },
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
}

.cottages-wrapper{
  height: 100%;
  display: flex;
  padding-top: 50px;
  padding-bottom: 20px;
  padding-left: 5%;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: flex-start;
}

.subscription-title{
  display: flex;
  justify-content: flex-start;
}
.gap{
  margin-left: 10vw;
  margin-top: 5vh;
}

#add-new-cottage{
  margin-left: 58%;
  margin-top: 3%;
}
</style>