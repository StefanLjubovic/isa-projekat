<template>
  <NavBar @change-state="changeState"></NavBar>
  <SearchShips v-if="state==1"/>
  <SearchAdventures v-if="state==0"/>
  <div class="adventures-wrapper" v-if="state==0">
   <div class="gap" v-for="adventure in adventures" :key="adventure.name">
      <Adventure :adventure="adventure" @adventure-details="openAdventureDetails(adventure)"/>
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
            id: '1',
            name: 'Fishing in the Sunset',
            address: 'Bulevar Kralja Petra 1, Novi Sad',
            desciption: 'Qui ex veniam occaecat id cupidatat magna non consectetur.',
            averageGrade: 4.5,
            images: [
              '1.jpg',
              '2.jpg',
              '3.jpg',
              '4.jpg',
              '5.jfif'
            ],
            allowedBehaviour: [
              'Alkohol',
              'Kucni ljubimci',
              'Hranjenje riba'
            ],
            unallowedBehaviour: [
              'Pusenje',
              'Velika buka'
            ],
            fishingInstructor: {
              firstName: 'Stefan',
              lastName: 'Ljubovic',
              shortBiography: 'Sint eu minim est minim dolore reprehenderit dolore irure veniam cillum in aute sunt incididunt.'
            },
            maxPersons: 5,
            cancellationPercentage: 20
          },
          {
            id: '2',
            name: 'Fishing in the Sunset',
            address: 'Bulevar Kralja Petra 1, Novi Sad',
            desciption: 'Qui ex veniam occaecat id cupidatat magna non consectetur.',
            averageGrade: 4.5,
            images: [
              '1.jpg',
              '2.jpg',
              '3.jpg',
              '4.jpg',
              '5.jfif'
            ],
            allowedBehaviour: [
              'Alkohol',
              'Kucni ljubimci',
              'Hranjenje riba'
            ],
            unallowedBehaviour: [
              'Pusenje',
              'Velika buka'
            ],
            fishingInstructor: {
              firstName: 'Stefan',
              lastName: 'Ljubovic',
              shortBiography: 'Sint eu minim est minim dolore reprehenderit dolore irure veniam cillum in aute sunt incididunt.'
            },
            maxPersons: 5,
            cancellationPercentage: 20
          },
          {
            id: '3',
            name: 'Fishing in the Sunset',
            address: 'Bulevar Kralja Petra 1, Novi Sad',
            desciption: 'Qui ex veniam occaecat id cupidatat magna non consectetur.',
            averageGrade: 4.5,
            images: [
              '1.jpg',
              '2.jpg',
              '3.jpg',
              '4.jpg',
              '5.jfif'
            ],
            allowedBehaviour: [
              'Alkohol',
              'Kucni ljubimci',
              'Hranjenje riba'
            ],
            unallowedBehaviour: [
              'Pusenje',
              'Velika buka'
            ],
            fishingInstructor: {
              firstName: 'Stefan',
              lastName: 'Ljubovic',
              shortBiography: 'Sint eu minim est minim dolore reprehenderit dolore irure veniam cillum in aute sunt incididunt.'
            },
            maxPersons: 5,
            cancellationPercentage: 20
          }
        ]
      }
    },
    methods:{
      changeState: function(state){
        this.state=state;
      },
      openAdventureDetails: function(adventure) {
        this.$router.push({ path: `/adventureDetails/${adventure.id}` })
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