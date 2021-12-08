<template>
    <!-- Response to rejection modal -->
    <div v-if="selectedRequest" class="modal fade" id="response-to-rejection-modal">
        <div class="modal-dialog rounded">
            <div class="modal-header">
                <h3>What is the reason for rejection?</h3>
                <button class="btn btn-close close" data-dismiss="modal"><i class="fas fa-times"></i></button>
            </div>
            <div class="modal-content">
                <textarea class="form-control textarea" rows="8" placeholder="Write your response..."
                v-model="v$.responseToRejection.$model"></textarea>
                <div class="text-danger" v-if="v$.responseToRejection.$error">{{v$.responseToRejection.$errors[0].$message}} </div>
                <div class="confirm-buttons">
                    <button class="btn submit-btn" :disabled="v$.responseToRejection.$invalid" @click="sumbitRejection()">Submit</button>
                    <button class="btn cancel-btn" @click="cancelRejection()">Cancel</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Page -->
    <div id="page">
        <h1>Requests for Registration</h1>
        <div class="filter-search">
            <div class="dropdown">
                <button class="btn dropdown-toggle drop-btn" ref="btnToggle" id="dropdownMenuButton" data-toggle="dropdown" 
                aria-haspopup="true" aria-expanded="false">
                    Filter by role &ensp;
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <a class="dropdown-item" href="#a" @click="filterByRole(-1)" >All users</a>
                    <a class="dropdown-item" href="#a" @click="filterByRole('ROLE_COTTAGE_OWNER')" >Cottage owner</a>
                    <a class="dropdown-item" href="#a" @click="filterByRole('ROLE_SHIP_OWNER')" >Ship owner</a>
                    <a class="dropdown-item" href="#a" @click="filterByRole('ROLE_INSTRUCTOR')" >Fishing instructor</a>
                </div>
            </div>
            <div class="search">
                <div class="form-outline">
                    <input v-model="searchParams" type="search" id="search" class="form-control" placeholder="Search"/>
                </div>
                <button type="button" class="btn" @click="search()">
                    <i class="fas fa-search"></i>
                </button>
            </div>
        </div>
        <div class="requests-table card rounded">
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th scope="col"></th>
                        <th scope="col">E-mail</th>
                        <th scope="col">First name</th>
                        <th scope="col">Last name</th>
                        <th scope="col">Phone number</th>
                        <th scope="col">Role </th>
                        <th scope="col">Explanation</th>
                        <th scope="col"></th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="request in requests" :key="request.id">
                        <th scope="row">{{ requests.indexOf(request) + 1 }}</th>
                        <td>{{ request.email }}</td>
                        <td>{{ request.firstName }}</td>
                        <td>{{ request.lastName }}</td>
                        <td>{{ request.phoneNumber }}</td>

                        <td v-if="request.role.name == 'ROLE_COTTAGE_OWNER'">Cottage owner</td>
                        <td v-else-if="request.role.name == 'ROLE_SHIP_OWNER'">Ship owner</td>
                        <td v-else-if="request.role.name == 'ROLE_INSTRUCTOR'">Fishing instructor</td>
                        <td v-else></td>

                        <td>
                            <div id="to-hover">
                                <button class="btn btn-info"><i class="fas fa-info"></i></button>
                                <div id="to-show" class="card rounded">{{ request.explanation }}</div>
                            </div> 
                        </td>
                        <td><button class="btn" @click="approveRequest(request)"><i class="fas fa-check"></i></button></td>
                        <td><button class="btn btn-delete" @click="rejectRequest(request)"><i class="fas fa-times"></i></button></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>
import useValidate from '@vuelidate/core'
import { required } from '@vuelidate/validators'
import axios from 'axios'
import server from '../../server'

export default ({
    data() {
        return {
            allRequests: [],
            requests: [],
            searchParams: "",
            selectedRequest: undefined,
            responseToRejection: '',
            filterRole: -1
        }
    },
    setup() {
        return { v$: useValidate() }
    },
    validations() {
        return {
           responseToRejection: { required }
        }
    },
    mounted() {
        axios.get(`${server.baseUrl}/regRequest`)
        .then((response) => {
            this.allRequests = response.data;
            this.requests = this.allRequests.slice();
        })
        
    },
    methods: {
        approveRequest: function(request) {
            axios.get(`${server.baseUrl}/regRequest/approve/${request.id}`)
            .then(() => {
                let index = this.requests.indexOf(request);
                if(index > -1) this.requests.splice(index, 1);
                let indexAll = this.allRequests.indexOf(request);
                if(indexAll > -1) this.allRequests.splice(indexAll, 1);

                this.$swal({
                    position: 'top-end',
                    icon: 'success',
                    title: 'Request approved successfully!',
                    showConfirmButton: false,
                    timer: 2000
                }); 
            })
            .catch((error) => {
                this.$swal(error.response.data); 
            })

                       
        },

        rejectRequest: function(request) {
            this.selectedRequest = request;
            window.$('#response-to-rejection-modal').modal('show');
        },

        sumbitRejection: function() {
            let rejection = {
                id: this.selectedRequest.id,
                rejectionReason: this.responseToRejection
            }
            
            axios.put(`${server.baseUrl}/regRequest/reject`, rejection)
            .then(() => {
                let index = this.requests.indexOf(this.selectedRequest);
                if(index > -1) this.requests.splice(index, 1);
                let indexAll = this.allRequests.indexOf(this.selectedRequest);
                if(indexAll > -1) this.allRequests.splice(indexAll, 1);
                
                this.responseToRejection = '';
                window.$('#response-to-rejection-modal').modal('hide');
            })
        },

        cancelRejection: function() {
            this.responseToRejection = '';
            window.$('#response-to-rejection-modal').modal('hide');
        },

        filterByRole: function(role) {
            this.filterRole = role;
            this.search();
        },

        search: function() {
            this.requests = this.allRequests.filter((request) => request.email.includes(this.searchParams.toLowerCase())
                                               || request.firstName.toLowerCase().includes(this.searchParams.toLowerCase())
                                               || request.lastName.toLowerCase().includes(this.searchParams.toLowerCase())
                                               || request.phoneNumber.includes(this.searchParams));
            if(this.filterRole != -1) {
                this.requests = this.requests.filter((request) => request.role.name == this.filterRole);
            }
        }
    }
})
</script>

<style scoped>
#page {
    margin: 50px 15%;
    padding-bottom: 150px;
}

h1 {
    text-align: left;
    margin-bottom: 40px;
}

.filter-search {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.btn {
    background-color: #2c3e50;
    color: white;
}
.btn-delete {
    color: #2c3e50;
    background-color: white;
    border-color: #cfd3d8;
}

.btn-info {
    width: 40px;
    background-color: transparent;
    color: #2c3e50;
    border-color: transparent;
}

.search {
    display: flex;
}

.form-control {
    width: 300px;
    margin-right: 10px;
}

.requests-table {
    margin-top: 20px;
    padding-top: 10px;
}

.modal-dialog {
    background-color: white;
}

.modal-content {
    padding: 30px;
    font-size: 17px;
    text-align: left;
}

.btn-close {
    background-color: transparent;
    border-color: transparent;
    color: transparent;
}

.textarea {
    margin-bottom: 20px;
    width: auto;
}

.confirm-buttons {
    display: flex;
    justify-content: flex-end;
    margin-right: 10px;
}

.cancel-btn {
    background-color: white;
    color: #2c3e50;
    border-color: #cfd3d8;
    margin-left: 10px;
}

#to-show {
    padding: 20px;
    display: none;
    background-color: #ffffff;
    position:absolute;
    width:250px;
    height: auto;
    border-color: #2c3e50;
}

#to-hover:hover > #to-show {
    display: block; 
}

.text-danger {
    margin-top: -15px;
    margin-bottom: 10px;
    text-align: left;
    font-size: 13px;
}
</style>
