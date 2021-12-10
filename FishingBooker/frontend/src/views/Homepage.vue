<template>
  <NavBar @change-state="changeState" :state="state"></NavBar>
  
  <!-- Client and unregistrated user options (userRole 0 && 5) -->
  <div v-if="userRole == 'ROLE_CLIENT' || userRole == ''">
    <SearchEntities v-if="state!=3 && state!=7 && state!=8 && state!=25 && state!=30" :searchTitle="searchTitle"  @filter-sort="filterSort" @sort-history="sortHistory"/>
    <div v-if="state==0 || state==1 || state==2" class="adventures-wrapper">
      <div class="gap" v-for="entity in entitiesForDisplay" :key="entity.name">
        <Entity :entity="entity" @entity-details="openEntityDetails(entity)"/>
      </div>
    </div>
    <CottageDetails v-if="state == 25" :entityId="selectedEntityId"/>
    <AdventureDetails v-if="state == 30" :entityId="selectedEntityId"/>
  </div>
    <div v-if="userRole == 'ROLE_CLIENT'">
    <ClientHistory v-if="state==4 || state==5 || state==6" :state='state' @open-complaint="openComplaint" @open-revision="openRevision" :sort="historySort"/>
    <transition name="fade" appear>
    <Complaint v-if="showComplaint" @close-modal="closeComplaint" :title="confirmTitle"/>
    </transition>
    <transition name="fade" appear>
    <RevisionModal v-if="showRevision" @close-modal="closeRevision"/>
    </transition>
    <transition name="fade" appear>
      <ConfirmModal v-if="showCancelation" @close-cancelation="closeCancelation"/>
    </transition>
    <ClientReservations v-if="state==7" @open-cancelation="openCancelation"/>
    <MyProfile v-if="state == 3"/>
    <h1 class="mt-4 subscription-title container" v-if="state==8" >My Subscriptions</h1>
    <div v-if="state==8" class="adventures-wrapper">
      <div class="gap" v-for="entity in entitiesForDisplay" :key="entity.name">
        <Entity :entity="entity" @entity-details="openEntityDetails(entity)"/>
      </div>
    </div>
    </div>

  <!-- Admin options (userRole "ROLE_ADMIN") -->
  <div v-if="userRole == 'ROLE_ADMIN'">
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
    <AdventureDetails v-if="state == 30" :entityId="selectedEntityId" @entity-deleted="changeState"/>
    <CottageDetails v-if="state == 25" :entityId="selectedEntityId" @entity-deleted="changeState"/>
  </div>

  <!-- Cottage owner options (userRole 'ROLE_COTTAGE_OWNER') -->
  <div v-if="userRole == 'ROLE_COTTAGE_OWNER'">
    <div v-if="state == 2">
        <button  type="button" id="add-new-cottage" @click="addNewCottage()" class="btn btn-success"> <i class="fas fa-plus"></i>&nbsp;  Add new cottage</button>
        <SearchEntities :searchTitle="searchTitle"  @filter-sort="filterSort"/>
    </div>
    <div v-if="state == 2" class="cottages-wrapper">
        <div class="gap" v-for="entity in entitiesForDisplay" :key="entity.name">
        <Entity :entity="entity" @entity-details="openEntityDetails(entity)"/>
      </div>
    </div>  
    <CottageReservations v-if="state==22"/>
    <MyProfile v-if="state == 3"/>
    <MyScheduleInstructor v-if="state == 23"/>
    <OwnerAnalytics v-if="state == 24"/>
    <CottageDetails v-if="state == 25" :entityId="selectedEntityId" @edit-cottage="editCottage" @entity-deleted="changeState"/>
    <AddNewCottage v-if="state == 26" />
    <EditCottage    v-if="state == 27" :cottageId="selectedCottageId"/>

  </div>

  <!-- Ship owner options (userRole 'ROLE_SHIP_OWNER') -->
  <div v-if="userRole == 'ROLE_SHIP_OWNER'">

  </div>

  <!-- Fishing instructor options (userRole 'ROLE_INSTRUCTOR') -->
  <div v-if="userRole == 'ROLE_INSTRUCTOR'">
    <div v-if="state == 0">
        <button  type="button" id="add-new-cottage" @click="addNewAdventure()" class="btn btn-success"><i class="fas fa-plus"></i>&nbsp;  Add new adventure </button>
        <SearchEntities :searchTitle="'My Adventures'"  @filter-sort="filterSort"/>
    </div>
    <div v-if="state == 0" class="cottages-wrapper">
        <div class="gap" v-for="entity in entitiesForDisplay" :key="entity.name">
        <Entity :entity="entity" @entity-details="openEntityDetails(entity)"/>
      </div>
    </div> 
    <AdventureReservations v-if="state == 1"/>
    <MyScheduleInstructor v-if="state == 2"/>
    <OwnerAnalytics v-if="state == 4"/>
    <MyProfile v-if="state == 3"/>
    <AdventureDetails v-if="state == 30" :entityId="selectedEntityId" @entity-deleted="changeState"/>
    <AddNewAdventure v-if="state == 31" @entity-added="changeState"/>
  </div>
  
</template>

<script>
import NavBar from "@/components/Navbar.vue"
import Entity from "@/components/EntityDiv.vue"
import SearchEntities from "@/components/SearchEntities.vue"
import ClientHistory from "@/components/ClientHistory.vue"
import ClientReservations from "@/components/ClientReservations.vue"
import Complaint from "@/components/Complaint.vue"
import RevisionModal from "@/components/client/RevisionModal.vue"
import Server from '../server'
import AllUsers from "@/components/admin/AllUsers.vue"
import Requests from "@/components/admin/Requests.vue"
import Complaints from "@/components/admin/Complaints.vue"
import CottageReservations from "@/components/cottage/CottageReservations.vue"
import CottageDetails from "@/views/CottageDetails.vue"
import AddNewCottage from "@/views/AddNewCottage.vue"
import AddNewAdventure from "@/views/AddNewAdventure.vue"
import EditCottage from "@/views/EditCottage.vue"
import MyProfile from "@/components/MyProfile.vue"
import AdminAnalytics from "@/components/admin/AdminAnalytics.vue"
import OwnerAnalytics from "@/components/OwnerAnalytics.vue"
import AdventureReservations from "@/components/adventure/AdventureReservations.vue"
import MyScheduleInstructor from "@/components/adventure/MyScheduleInstructor.vue"
import ConfirmModal from "@/components/client/ConfirmModal"
import AdventureDetails from "@/views/AdventureDetails.vue"
import server from '../server'
import axios from 'axios'


export default {
    components:{
        NavBar,
        SearchEntities,
        Entity,
        ClientHistory,
        Complaint,
        ClientReservations,
        AllUsers,
        Requests,
        Complaints,
        RevisionModal,
        CottageReservations,
        CottageDetails,
        AddNewCottage,
        EditCottage,
        MyProfile,
        AdminAnalytics,
        OwnerAnalytics,
        AdventureReservations,
        MyScheduleInstructor,
        ConfirmModal,
        AdventureDetails,
        AddNewAdventure
    },
    data(){
      return{
        confirmTitle: 'Are you sure you want to cancel reservation?',
        state: 0,
        searchTitle: 'All adventures',
        showComplaint: false,
        showRevision: false,
        showCancelation: false,
        entities: [],
        entitiesForDisplay: [],
        historySort : '',
        selectedEntityId: undefined,
        selectedCottageId: undefined
      }
    },
    computed:{
        userRole(){
            return this.$store.getters.getRole;
        }
    },
    methods:{

      changeState: async function(state){
        this.state=state;
        console.log(state);
        if(state == 0 || state == 1 || state == 2) {
          if(this.userRole == 'ROLE_INSTRUCTOR') {
            axios.get(`${server.baseUrl}/instructor/adventures`, {
              headers: {
                'Authorization' : `Bearer ${this.$store.getters.getToken}`
              }
            }).then((response) => {
              this.entities = response.data;
            })
          } else {
            const resp=await Server.getAllEntities(this.state)
            this.entitiesForDisplay=JSON.parse(JSON.stringify(resp.data));
            this.entities=resp.data;
          }

        }
        if (state==8){
          const resp=await Server.getSubscriptions(this.$store.getters.getToken)
          this.entitiesForDisplay=JSON.parse(JSON.stringify(resp.data));
          this.entities=resp.data;
        }

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
                    entity.address.streetName.toLowerCase().concat(' ').concat( entity.address.streetNumber).includes(address.toLowerCase()) &&
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
          this.selectedEntityId = entity.id;
          this.state = 30;
        } else if (this.state == 1) {
          // navigacija za detalje o brodu
        } else if (this.state == 2) {
          this.selectedEntityId = entity.id;
          this.state = 25;
        }
      },
      closeComplaint: function(){
        this.showComplaint=false;
        document.getElementById('appContainer').style.overflow = 'unset';
        document.getElementById('appContainer').style.height='unset';
      },
      openComplaint: function(entity){
        console.log(entity)
        this.showComplaint=true;
        document.getElementById('appContainer').style.overflow ='hidden';
        document.getElementById('appContainer').style.height='100vh';
      },
      closeRevision: function(){
          this.showRevision=false;
        document.getElementById('appContainer').style.overflow = 'unset';
        document.getElementById('appContainer').style.height='unset';
      },
      openRevision: function(entity){
        console.log(entity)
        this.showRevision=true;
        document.getElementById('appContainer').style.overflow ='hidden';
        document.getElementById('appContainer').style.height='100vh';
      },
      closeCancelation: function(){
        this.showCancelation=false;
         document.getElementById('appContainer').style.overflow = 'unset';
        document.getElementById('appContainer').style.height='unset';
      },
       openCancelation: function(entity){
         console.log(entity)
        this.showCancelation=true;
        document.getElementById('appContainer').style.overflow ='hidden';
        document.getElementById('appContainer').style.height='100vh';
      },
      editCottage(id){
        this.selectedCottageId = id;
        this.state = 27;
      },
      addNewCottage: function() {
        this.state = 26;
      },
      addNewAdventure: function() {
        this.state = 31;
      },
      sortHistory: function(value){
        this.historySort = value
      }
    },
    async mounted(){
      if(this.$route.params.data == undefined)this.state = 0
      else this.state = this.$route.params.data

      const resp=await Server.getAllEntities(this.state)
      this.entitiesForDisplay=JSON.parse(JSON.stringify(resp.data));
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

.fade-enter-active, .fade-leave-active {
    transition: opacity .5s
}
.fade-enter, .fade-leave-to /* .fade-leave-active in <2.1.8 */ {
    opacity: 0
}
</style>