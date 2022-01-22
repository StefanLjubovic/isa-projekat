<template>
    <div v-if="selectedRequest" class="modal fade" id="response-to-request-modal">
        <div class="modal-dialog rounded">
            <div class="modal-header">
                <h3>What is the reason for rejection?</h3>
                <button class="btn btn-close close" data-dismiss="modal"><i class="fas fa-times"></i></button>
            </div>
            <div class="modal-content">
                <textarea class="form-control textarea" rows="8" v-model="response" placeholder="Write your response..."></textarea>
                <div class="confirm-buttons">
                    <button class="btn submit-btn" @click="sumbitRejection()" :disabled="!response">Submit</button>
                    <button class="btn cancel-btn" @click="cancelRejection()">Cancel</button>
                </div>
            </div>
        </div>
    </div>

    <div id="page">
        <h1>Requests for Deleting Account</h1>
        <div class="filter-search">
            <div class="dropdown">
                <button class="btn dropdown-toggle drop-btn" ref="btnToggle" id="dropdownMenuButton" data-toggle="dropdown" 
                aria-haspopup="true" aria-expanded="false">
                    Filter by role &ensp;
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <a class="dropdown-item" href="#a" @click="filterByRole(-1)" >All users</a>
                    <a class="dropdown-item" href="#a" @click="filterByRole('ROLE_CLIENT')" >Client</a>
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
                        <th scope="col">Reason</th>
                        <th scope="col"></th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="request in requests" :key="request.id">
                        <th scope="row">{{ requests.indexOf(request) + 1 }}</th>
                        <td>{{ request.userEmail }}</td>
                        <td>{{ request.userFirstName }}</td>
                        <td>{{ request.userLastName }}</td>
                        <td>{{ request.phoneNumber }}</td>

                        <td v-if="request.role == 'ROLE_CLIENT'">Client</td>
                        <td v-else-if="request.role == 'ROLE_ADMIN'">Administrator</td>
                        <td v-else-if="request.role == 'ROLE_COTTAGE_OWNER'">Cottage owner</td>
                        <td v-else-if="request.role == 'ROLE_SHIP_OWNER'">Ship owner</td>
                        <td v-else-if="request.role == 'ROLE_INSTRUCTOR'">Fishing instructor</td>
                        <td v-else></td>

                        <td>
                            <div id="to-hover">
                                <button class="btn btn-info"><i class="fas fa-info"></i></button>
                                <div id="to-show" class="card rounded">{{ request.reason }}</div>
                            </div> 
                        </td>
                        <td><button class="btn" @click="approveRequest(request)"><i class="fas fa-check"></i></button></td>
                        <td><button class="btn btn-delete" @click="disapproveRequest(request)"><i class="fas fa-times"></i></button></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import server from '../../server'

export default ({
    data() {
        return {
            allRequests: [],
            requests: [],
            searchParams: "",
            selectedRequest: undefined,
            filterParam: -1,
            response: ''
        }
    },
    computed:{
        token(){
            return this.$store.getters.getToken;
        }
    },
    mounted() {
        const headers = {
            'Content-Type': 'application/json;charset=UTF-8',
            Accept: 'application/json',
            'Authorization': `Bearer ${this.token}`
        }
        axios.get(`${server.baseUrl}/deleteRequest`, { headers: headers })
        .then((response) => {
            this.allRequests = response.data;
            this.requests = this.allRequests.slice();
        })
    },
    methods: {
        filterByRole: function(role) {
            this.filterParam = role;
            this.search();
        },
        search: function() {
            this.requests = this.allRequests.filter((request) => request.userEmail.includes(this.searchParams.toLowerCase())
                                               || request.userFirstName.toLowerCase().includes(this.searchParams.toLowerCase())
                                               || request.userLastName.toLowerCase().includes(this.searchParams.toLowerCase())
                                               || request.phoneNumber.includes(this.searchParams));
            if(this.filterParam != -1) {
                this.requests = this.requests.filter((request) => request.role == this.filterParam);
            }
        },
        approveRequest: function(request) {
            const headers = {
                'Content-Type': 'application/json;charset=UTF-8',
                Accept: 'application/json',
                'Authorization': `Bearer ${this.token}`
            }
            axios.put(`${server.baseUrl}/deleteRequest/approve`, request.id, { headers: headers })
            .then(() => {
                this.requests.splice(this.requests.indexOf(request), 1);
                this.allRequests.splice(this.allRequests.indexOf(request), 1);
                this.$swal({
                    position: 'top-end',
                    icon: 'success',
                    title: 'Account deleted!',
                    showConfirmButton: false,
                    timer: 2000
                })
            })
            .catch((error) => {
                if(error.response.data.message == "No such request.") {
                    this.requests.splice(this.requests.indexOf(this.selectedRequest), 1);
                    this.allRequests.splice(this.allRequests.indexOf(this.selectedRequest), 1);
                }
                this.$swal(error.response.data.message);
            })
        },
        disapproveRequest: function(request) {
            this.selectedRequest = request;
            window.$('#response-to-request-modal').modal('show');
        },
        sumbitRejection: function() {
            const headers = {
                'Content-Type': 'application/json;charset=UTF-8',
                Accept: 'application/json',
                'Authorization': `Bearer ${this.token}`
            }
            axios.put(`${server.baseUrl}/deleteRequest/reject/${this.selectedRequest.id}`, this.response, { headers: headers })
            .then(() => {
                this.requests.splice(this.requests.indexOf(this.selectedRequest), 1);
                this.allRequests.splice(this.allRequests.indexOf(this.selectedRequest), 1);
                window.$('#response-to-request-modal').modal('hide');
                this.response = '';
                this.$swal({
                    position: 'top-end',
                    icon: 'success',
                    title: 'Rejection email sent!',
                    showConfirmButton: false,
                    timer: 2000
                })
            })
            .catch((error) => {
                if(error.response.data.message == "No such request.") {
                    this.requests.splice(this.requests.indexOf(this.selectedRequest), 1);
                    this.allRequests.splice(this.allRequests.indexOf(this.selectedRequest), 1);
                    window.$('#response-to-request-modal').modal('hide');
                    this.response = '';
                }
                this.$swal(error.response.data.message);
            })
        },
        cancelRejection: function() {
            window.$('#response-to-request-modal').modal('hide');
            this.response = '';
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
    width: 40px;
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
</style>
