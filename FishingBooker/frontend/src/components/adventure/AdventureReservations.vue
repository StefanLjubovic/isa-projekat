<template>
<div class="page">
  <h1>Reservation History</h1>
  <div class="card">
    <table class=" table table-striped table-cottage-reservations">
    <thead>
      <tr>
        <th scope="col">Adventure</th>
        <th scope="col">Client</th>
        <th scope="col">Date and Time</th>
        <th scope="col">Duration</th>
        <th scope="col">Price</th>
        <th scope="col">Report</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="reservation in reservations" :key="reservation.id">
        <td>{{reservation.entityName}}</td>
        <td><a href="#" @click="openModalForClientDetails(reservation.clientEmail)">{{reservation.clientEmail}}</a></td>
        <td>{{this.dateFormat(reservation.dateTime)}}</td>
        <td>{{reservation.durationInHours}} hours</td>
        <td>{{reservation.price}} </td>
        <td><i class="fas fa-plus-square icon" @click="openModalForReport(reservation.clientEmail)"></i></td>
      </tr>
    </tbody>
  </table>
  </div>
</div>

<!--Modal for client information -->

<div v-if="selectedClient" class="modal fade" id="client-details-modal">
  <div class="modal-dialog rounded">
      <div class="modal-header">
          <h3>Client Information</h3>
          <button class="btn btn-close close" data-dismiss="modal"><i class="fas fa-times"></i></button>
      </div>
          <div class="modal-content">
            <div class="client-info">
               <div class="labels">
                   <p>Email:</p>
                    <p>First name:</p>
                    <p>Last name:</p>
                    <p>Street name:</p>
                    <p>Street number:</p>
                    <p>Postal code:</p>
                    <p>City:</p>
                    <p>Country:</p>
                    <p>Phone number:</p>
                 </div>
                <div class="info">
                    <p>{{ selectedClient.email }}</p>
                    <p>{{ selectedClient.firstName }}</p>
                    <p>{{ selectedClient.lastName }}</p>
                    <p>{{ selectedClient.address.streetName }}</p>
                    <p>{{ selectedClient.address.streetNumber }}</p>
                    <p>{{ selectedClient.address.postalCode }}</p>
                    <p>{{ selectedClient.address.city }}</p>
                    <p>{{ selectedClient.address.country }}</p>
                    <p>{{ selectedClient.phoneNumber }}</p>
                </div>
            </div>
       </div>
     </div>
  </div>

  <!--Modal for report -->

<div v-if="selectedClient" class="modal fade" id="report-modal">
  <div class="modal-dialog rounded">
      <div class="modal-header">
          <h3>Report</h3>
          <button class="btn btn-close close" data-dismiss="modal"><i class="fas fa-times"></i></button>
      </div>
          <div class="modal-content">
            <div class="client-info">
               <div class="labels">
                   <p>Email:</p>
                    <p>First name:</p>
                    <p>Last name:</p><br/>
                    <p>Comment:</p>                 
                 </div>
                <div class="info">
                    <p>{{ selectedClient.email }}</p>
                    <p>{{ selectedClient.firstName }}</p>
                    <p>{{ selectedClient.lastName }}</p>               
                </div>
            </div>
            <div class="comment-area">
               <textarea placeholder="Your comment" cols="50" rows="4"></textarea>
            </div> <br/>
            <div class="options">
                <input type="checkbox" id="penalty" name="penalty" value="penalty"/><span> Request a penalty for client</span><br/>
                <input type="checkbox" id="didnot-appear" name="didnot-appear" value="Client did not appear"/><span> Did not appear</span><br/>
            </div><br/>
            <div class="confirm-buttons">
                <button class="btn save-button"  @click.prevent="sendReport()" >Submit</button>
                <button class="btn cancel-button">Cancel</button>
            </div>
       </div>
     </div>
  </div>

</template>

<script>
import axios from 'axios';
import server from '../../server';
import moment from 'moment'

export default {
    name: "AdventureReservations",
  data(){
    return{
      reservations: [],
      selectedClient: undefined
    }
  },
  computed:{
    userRole(){
        return this.$store.getters.getRole;
    },
    token(){
        return this.$store.getters.getToken;
    }
  },
  mounted(){
    const headers = {
      'Content-Type': 'application/json;charset=UTF-8',
      Accept: 'application/json',
      'Authorization': `Bearer ${this.token}`
    }
    axios.get(`${server.baseUrl}/instructor/reservations`, { headers: headers })
    .then((response) => {
      this.reservations = response.data;
    })
  },
  methods: {
      openModalForClientDetails: function(clientEmail) {
        const headers = {
          'Content-Type': 'application/json;charset=UTF-8',
          Accept: 'application/json',
          'Authorization': `Bearer ${this.token}`
        }
        axios.get(`${server.baseUrl}/user/getByEmail/${clientEmail}`, { headers: headers })
        .then((response) => {
          this.selectedClient = response.data;
          window.$('#client-details-modal').modal('show');
        });
      },
      openModalForReport : function(client) {
        this.selectedClient = client;
        window.$('#report-modal').modal('show');
      },
      sendReport() {
            this.v$.$validate()
            console.log(this.v$)

            window.$('#report-modal').modal('hide');
      },
      dateFormat(value) {
        return moment(value).format("DD.MM.YYYY. HH:mm");
      },
  }
}
</script>

<style scoped>
    .page {
      margin-left: 15%;
      margin-right: 15%;
      margin-top: 50px;
      padding-bottom: 150px;
    }
    h1 {
      margin-bottom: 40px;
      text-align: left;
    }
    .table-cottage-reservations, .table-striped{   
        color: #2c3e50;
        text-align: center;
        margin-top: 10px ;
    }
    .icon{
        cursor: pointer;
        color: #151649;
        width: 90px;
        height: 80%;
    }
    .modal-dialog {
      background-color: #ffffff;
    }

    .client-info {
        display: flex;
        justify-content: space-between;
    }

    .comment-area{
      align-self: left;
      margin-left: 5px;
      border-width: 1px solid #888 ;
      font-size: 18px;
      resize: none;
      outline: none;

    }

    .options{
      text-align: left;
      margin-left: 12px;
    }

    .info {
        text-align: right;
    }

    .labels {
        text-align: left;
    }

    .modal-content {
        padding: 30px;
        font-size: 20px;
        background-color: rgb(211, 222, 223);
    }

    .btn-close {
      background-color: transparent;
      border-color: transparent;
      color: transparent;
      margin-right: 12px;
    }

    h3 {
      margin-left: 15px;
      margin-top: 17px;
    }

    .cancel-button {
      background-color: white;
      border-color: rgb(218, 214, 214);
      color: #2c3e50;
      width: 80px;
      margin-left: 10px;
    }

    .save-button {
      background-color: #2c3e50;
      color: white;
      width: 80px;
      margin-right: 10px;
  }

</style>