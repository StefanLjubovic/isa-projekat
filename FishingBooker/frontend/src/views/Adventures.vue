<template>
  <NavBar @change-state="changeState" :state="state"></NavBar>
  <SearchEntities :searchTitle="searchTitle" v-if="state==0 || state==1 ||state==2 "/>
  <div class="adventures-wrapper" v-if="state==0 || state==1 ||state==2 ">
   <div class="gap" v-for="entity in entities" :key="entity.name">
      <Entity :entity="entity"/>
    </div>
  </div>
  <ClientProfile v-if="state==3"/>
</template>

<script>
import NavBar from "@/components/Navbar.vue";
import Entity from "@/components/EntityDiv.vue";
import SearchEntities from "@/components/SearchEntities.vue";
import ClientProfile from "@/components/ClientProfile.vue";
export default {

    components:{
        NavBar,
        SearchEntities,
        Entity,
        ClientProfile
    },
    data(){
      return{
        state: 0,
        searchTitle: 'All adventures',
        entities :[
          {
            name: 'Marijino pecanje',
            address: 'Bulevar Kralja Petra 1, Novi Sad',
            desciption: 'blablablablabla blablablablabl ablablablabab lablablablablablabla'
          },
          {
            name: 'Marijino pecanje',
            address: 'Bulevar Kralja Petra 1',
            desciption: 'blablablablabla blablablablabl ablablablabab lablablablablablabla'
          },
          { 
            name: 'Marijino pecanje',
            address: 'Bulevar Kralja Petra 1',
            desciption: 'blablablablabla blablablablabl ablablablabab lablablablablablabla'
          },
          {
            name: 'Marijino pecanje',
            address: 'Bulevar Kralja Petra 1',
            desciption: 'blablablablabla blablablablabl ablablablabab lablablablablablabla'
          },
          {
            name: 'Marijino pecanje',
            address: 'Bulevar Kralja Petra 1',
            desciption: 'blablablablabla blablablablabl ablablablabab lablablablablablabla'
          },
          {
            name: 'Marijino pecanje',
            address: 'Bulevar Kralja Petra 1',
            desciption: 'blablablablabla blablablablabl ablablablabab lablablablablablabla'
          },
          {
            name: 'Marijino pecanje',
            address: 'Bulevar Kralja Petra 1',
            desciption: 'blablablablabla blablablablabl ablablablabab lablablablablablabla'
          },
        ]
      }
    },
    methods:{
      changeState: function(state){
        this.state=state;
        if(state==0)this.searchTitle="Adventures we offer";
        else if(state==1) this.searchTitle="Ships we offer";
        else if(state==2) this.searchTitle="Cottages we offer";
      }
    },
    created(){
      if(this.$route.params.data == undefined)this.state = 0
      else this.state = this.$route.params.data
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