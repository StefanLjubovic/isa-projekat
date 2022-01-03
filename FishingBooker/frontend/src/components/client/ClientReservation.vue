<template>
  <div id="app">
  <div class="modal-overlay" @click="$emit('close-modal')"></div>
  <div class="container">
      <div class='wrapper'>
        <div class="header-border">
              <h2>Create reservation:</h2>
         </div>
         <div class="content">
          <div class="left">
              <h5 class="mb-4">{{GetEntityName()}}</h5>
              <span><h5 class="mb-4">Reservation from: </h5><input
           class="form-control date" 
           type="date" 
          id = "dateFromfield"
          v-model="dateFrom"/></span>
             <span><h5 class="mb-4">Reservation to: </h5><input
           class="form-control date-to" 
           type="date" 
          id = "dateTofield"
          v-model="dateTo"/></span>
              <span class="mb-4 mr-2"><h5 id="request">Additionall request:</h5> <span class="request-input"><input type="text" class="form-control request" v-model="currentRequest" /><i class="fas fa-plus fa-sm" @click="addRequest"></i></span></span>
              <div class="dropdown-row mb-4">
                  <h5 id="drop-lab">Requested: </h5>          
                <div class="dropdown">
                    <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        {{GetFirstRequest()}}
                    </button>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                        <li v-for="(request,index) in requests" :key="request">
                            <span><a class="dropdown-item" href="#">{{request}} </a><i class="fas fa-minus" @click="removeRequest(index)"></i></span>
                        </li>
                    </div>
            </div>
</div>
            <h5 class="mb-5">Maximum people: &nbsp;{{entity.maxPersons}}
            </h5>
                      <div class="button-div">
              <span><h4 id="price">Price : {{price}} rsd</h4><div class="btn1"><button class="btn droptdown-btn" @click="saveReservation">Save</button> <button class="btn cancel-btn"  @click="$emit('close-modal')">Cancel</button>
              </div>
              </span>
          </div>
          </div>
          </div>
      </div>
  </div>
   </div>
</template>

<script>
import server from '../../server/index';
export default {
    props:{
        rentingEntity : Object
    },
    data(){
        return{
            requests: [],
            currentRequest : '',
            entity:{
                maxPersons : 8,
                type : 'adventure',
                name : 'Marijina vikendica'
            },
            price : 3000,
            dateFrom :new Date(),
            dateTo : new Date(),
            persons : 1
        }
    },
     watch: {
    dateFrom(){
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
    document.getElementById("dateTofield").setAttribute("min", today);
  },
    methods:{
        addRequest(){
            if(this.currentRequest =='') return;
            this.requests.push(this.currentRequest)
            this.currentRequest = '';
        },
        GetFirstRequest(){
            if(this.requests.length)
                return this.requests[0]
            return 'No current requests.'
        },
        removeRequest(index){
            this.requests.splice(index, 1)
        },
        GetPersons(){
            if(this.entity.type == 'adventure') return this.entity.maxPersons;
            let max = 0;
            this.entity.rooms.map(room => {
                max+=room.bedNumber
            })
            return  max;
        },
        GetEntityName(){
            if(this.entity.type =='adventure') return 'Adventure: '+this.entity.name;
            else if(this.entity.type =='ship') return 'Ship: '+this.entity.name;
            return 'Cottage: '+this.entity.name;
        },
        changeButtonContext(index){
            this.persons = index
        },
        async saveReservation(){
            const diffTime = Math.abs(new Date(this.dateTo) - new Date(this.dateFrom));
            const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)); 
            console.log(this.dateTo,this.dateFrom,'aaaaaaaaaaaa')
            console.log(diffTime)
            const client=await server.getLoggedUser()
            const reservation = {
                rentingEntity : this.rentingEntity,
                dateTime : this.dateFrom,
                client : client.data,
                price : this.price,
                isCanceled : false,
                additionalServices : this.requests,
                maxPersons : this.GetPersons(),
                durationInHours : diffDays*24
            }

            await server.saveReservation(reservation)
            .then(resp=> {
                if(resp.success){
                    this.$swal.fire({
                        icon: 'success',
                        title: 'Success',
                        text: 'Reservation succesfully created!',
                        confirmButtonColor: '#2c3e50'
                    })
                }
        }).catch(resp=> {
                    this.$swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: resp.data,
                    })
        })
        this.$emit('close-modal')
        
}
    }
}
</script>

<style scoped>
#app {
 position: absolute;
 
 display: flex;
 justify-content: center;
 align-items: center;
 left:0;
 top:0;
 width: 100vw;
 min-height: 100vh;
 overflow-x: hidden;
}

.modal-overlay {
 position: absolute;
 top: 0;
 left: 0;
 right: 0;
 bottom: 0;
 z-index: 98;
 background-color: rgba(0, 0, 0, 0.3);
}
.container{
     flex-direction: column;
  justify-content: space-around;
   width: 45%;
   background: white;
  z-index: 1000;
   position: fixed;
  border-radius: 16px;
 padding: 25px;
}
    .wrapper{
        height: 70%;
        border-radius: 15px;
        display: flex;
        justify-content: center;
        flex-direction: column;
    }

    .content{
        display: flex;
        justify-content: flex-start;
        flex-direction: row;
        flex: 17;
    }
    .left{
        padding: 3rem;
        display: flex;
        justify-content: flex-start;
        flex-direction: column;
        width: 100%;
    }
    .dropdown-row{
        display:flex;
        flex-direction: row;
    }
    .request-input{
        flex:2;
    }
    .request{
        width: 70%;
        margin-right:1rem ;
    }

    .btn1{
        display: flex;
        justify-self: flex-end;
        align-self: flex-end;
        margin-left:11rem ;
    }
    .button-div{
        justify-content: space-between;
        align-self: flex-end;
        flex-direction: row;
        width: 100%;
    }

  h4{
      display: flex;
      justify-items: flex-end;
      align-self: flex-start;
      border-bottom:2px solid #5a5a73;
  }  
h2{
    color: #5a5a73;
}
h5{
    display: flex;
    justify-content: flex-start;
}
i{
    cursor: pointer;
    display: flex;
    justify-content: center;
    align-self: center;
     color: #0e0f40;
     margin-right: 1rem;
}
.header-border{
    width: 80%;
    align-self: center;
    border-bottom:1px solid #5a5a73;
    flex: 1;
}
#request{
    flex:1
}

span{
    display: flex;
    flex-direction: row;
    width: 100%;
}
#drop-lab{
    margin-right: 1rem;
}

.droptdown-btn{
    width: 5vw;
    height: 3rem;
    color:white;
    border-radius: 5px;
  background: #0e0f40;
}
.persons{
    margin-left:1rem ;
}
.cancel-btn{
    width: 5vw;
    height: 3rem;
    background:white;
    border-radius: 5px;
  color: #0e0f40;
  margin-left: 1rem;
}
.date{
    height: 70%;
    width: 50%;
    margin-left:1rem ;
}
.date-to{
    height: 70%;
    width: 50%;
    margin-left:2.4rem ;
}
.fade-enter-active, .fade-leave-active {
    transition: opacity .5s
}
.fade-enter, .fade-leave-to /* .fade-leave-active in <2.1.8 */ {
    opacity: 0
}
</style>