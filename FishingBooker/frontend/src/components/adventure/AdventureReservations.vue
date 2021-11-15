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
        <td>{{reservation.name}}</td>
        <td><a href="#" @click="openModalForClientDetails(reservation.client)">{{reservation.client.email}}</a></td>
        <td>{{reservation.dateTime}}</td>
        <td>{{reservation.duration}} days</td>
        <td>{{reservation.price}} </td>
        <td><i class="fas fa-plus-square icon" @click="openModalForReport(reservation.client)"></i></td>
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
                    <p>{{ selectedClient.streetName }}</p>
                    <p>{{ selectedClient.streetNumber }}</p>
                    <p>{{ selectedClient.postalCode }}</p>
                    <p>{{ selectedClient.city }}</p>
                    <p>{{ selectedClient.country }}</p>
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
export default {
    name: "AdventureReservations",
  data(){
    return{
      reservations: [
        {
          id: '1',
          name: 'Fishing in the Sunset',
          client: {
            email: 'anagavrilovic@gmail.com',
            firstName: 'Ana',
            lastName: 'Gavrilovic',
            streetName: 'Bulevar Kralja Petra I',
            streetNumber: '65',
            postalCode: '21000',
            city: 'Novi Sad',
            country: 'Serbia',
            phoneNumber: '0651234567'

          },
          dateTime: '5.10.2021',
          duration: 5,
          price: 50000
        },
        {
          id: '2',
          name: 'Fishing in the Sunset',
          client: {
            email: 'stefanljubovic@gmail.com',
            firstName: 'Stefan',
            lastName: 'Ljubovic',
            streetName: 'Bulevar Despota Stefana',
            streetNumber: '7',
            postalCode: '21000',
            city: 'Novi Sad',
            country: 'Serbia',
            phoneNumber: '0641234567'

          },
          dateTime: '5.11.2021',
          duration: 7,
          price: 70000
        },
        {
          id: '3',
          name: 'Fishing in the Sunset',
          client: {
            email: 'tamarapantic@gmail.com',
            firstName: 'Tamara',
            lastName: 'Pantic',
            streetName: 'Bulevar Despota Stefana',
            streetNumber: '5a',
            postalCode: '21000',
            city: 'Novi Sad',
            country: 'Serbia',
            phoneNumber: '0661234567'

          },
          dateTime: '5.11.2020',
          duration: 7,
          price: 70000
        }
      ],
      selectedClient: undefined
    }
  },
  mounted(){
   
  },
  methods: {
      openModalForClientDetails: function(client) {
        this.selectedClient = client;
        window.$('#client-details-modal').modal('show');
      },
      openModalForReport : function(client) {
        this.selectedClient = client;
        window.$('#report-modal').modal('show');
      },
      sendReport() {
            this.v$.$validate()
            console.log(this.v$)

            window.$('#report-modal').modal('hide');
      }
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