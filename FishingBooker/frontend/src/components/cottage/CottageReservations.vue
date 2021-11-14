<template>

<div class="container">
  <table class=" table table-striped table-cottage-reservations">
  <thead>
    <tr>
      <th scope="col">Cottage</th>
      <th scope="col">Client</th>
      <th scope="col">Date from</th>
      <th scope="col">Duration</th>
      <th scope="col">Price</th>
      <th scope="col">Report</th>
    </tr>
  </thead>
  <tbody>
    <tr v-for="reservation in reservations" :key="reservation.id">
      <td>{{reservation.name}}</td>
      <td><a href="#" @click="openModalForUserDetails(reservation.client)">{{reservation.client.email}}</a></td>
      <td>{{reservation.dateTime}}</td>
      <td>{{reservation.duration}} days</td>
      <td>{{reservation.price}} </td>
      <td><i class="fas fa-plus-square icon"></i></td>
    </tr>
  </tbody>
</table>
</div>

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

</template>

<script>
export default {
  props:['state'],
  emits:['open-complaint'],
  updated(){ },
  data(){
    return{
      reservations: [
        {
          id: '1',
          name: 'Marijina vikendica',
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
          name: 'Marijina vikendica',
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
          name: 'Marijina vikendica',
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
      openModalForUserDetails: function(client) {
        this.selectedClient = client;
        window.$('#client-details-modal').modal('show');
     },
  }
}
</script>

<style scoped>
    .table-cottage-reservations, .table-striped{   
        color: #2c3e50;
        text-align: center;
        border-radius: 10px;
        margin-top: 5% ;
        margin-left: 10%;
        margin-right: 10%;
        width: 80%;
    }
    .icon{
        cursor: pointer;
        color: #151649;
        size: inherit;
    }
    .modal-dialog {
      background-color: #ffffff;
    }

    .client-info {
        display: flex;
        justify-content: space-between;
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

</style>