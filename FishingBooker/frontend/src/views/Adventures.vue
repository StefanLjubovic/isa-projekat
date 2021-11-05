<template>
  <NavBar @change-state="changeState"></NavBar>
  <SearchAdventures v-if="state==0"/>
  <SearchShips v-if="state==1"/>
  <div class="adventures-wrapper" v-if="state==0">
   <div class="gap" v-for="adventure in adventures" :key="adventure.name">
      <Adventure :adventure="adventure"/>
    </div>
  </div>

</template>

<script>
import NavBar from "@/components/Navbar.vue";
import Adventure from "@/components/AdventureDiv.vue";
import SearchAdventures from "@/components/SearchAdventures.vue";
import SearchShips from "@/components/SearchShips.vue";
export default {

    components:{
        NavBar,
        SearchAdventures,
        Adventure,
        SearchShips
    },
    data(){
      return{
        state: 0,
        adventures :[
          {
            name: 'Marijina vikendica',
            address: 'Bulevar Kralja Petra 1, Novi Sad',
            desciption: 'blablablablabla blablablablabl ablablablabab lablablablablablabla'
          },
          {
            name: 'Marijina vikendica',
            address: 'Bulevar Kralja Petra 1',
            desciption: 'blablablablabla blablablablabl ablablablabab lablablablablablabla'
          },
          { 
            name: 'Marijina vikendica',
            address: 'Bulevar Kralja Petra 1',
            desciption: 'blablablablabla blablablablabl ablablablabab lablablablablablabla'
          },
          {
            name: 'Marijina vikendica',
            address: 'Bulevar Kralja Petra 1',
            desciption: 'blablablablabla blablablablabl ablablablabab lablablablablablabla'
          },
          {
            name: 'Marijina vikendica',
            address: 'Bulevar Kralja Petra 1',
            desciption: 'blablablablabla blablablablabl ablablablabab lablablablablablabla'
          },
          {
            name: 'Marijina vikendica',
            address: 'Bulevar Kralja Petra 1',
            desciption: 'blablablablabla blablablablabl ablablablabab lablablablablablabla'
          },
          {
            name: 'Marijina vikendica',
            address: 'Bulevar Kralja Petra 1',
            desciption: 'blablablablabla blablablablabl ablablablabab lablablablablablabla'
          },
        ]
      }
    },
    methods:{
      changeState: function(state){
        this.state=state;
      }
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