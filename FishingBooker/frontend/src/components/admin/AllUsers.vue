<template>
    <div v-if="selectedUser" class="modal fade" id="user-details-modal">
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
                        <p>Role:</p>
                    </div>
                    <div class="info">
                        <p>{{ selectedUser.email }}</p>
                        <p>{{ selectedUser.firstName }}</p>
                        <p>{{ selectedUser.lastName }}</p>
                        <p>{{ selectedUser.streetName }}</p>
                        <p>{{ selectedUser.streetNumber }}</p>
                        <p>{{ selectedUser.postalCode }}</p>
                        <p>{{ selectedUser.city }}</p>
                        <p>{{ selectedUser.country }}</p>
                        <p>{{ selectedUser.phoneNumber }}</p>
                        <p>{{ roleToString(selectedUser.role) }}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div v-if="selectedUser" class="modal fade" id="new-admin-modal">
        <div class="modal-dialog rounded">
            <div class="modal-header">
                <h3>Add new administrator</h3>
                <button class="btn btn-close close" data-dismiss="modal"><i class="fas fa-times"></i></button>
            </div>
            <div class="modal-content">
                <input type="email" class="form-control" placeholder="Email*" v-model="state.user.email" >
                <input type="text" class="form-control" placeholder="First name*" v-model="state.user.firstName" >
                <input type="text" class="form-control" placeholder="Last name*" v-model="state.user.lastName" >
                <input type="text" class="form-control" placeholder="Phone number*" v-model="state.user.phoneNumber" >
                <input type="text" class="form-control" placeholder="Street name*" v-model="state.user.streetName" >
                <input type="text" class="form-control" placeholder="Street number*" v-model="state.user.streetNumber" >
                <input type="text" class="form-control" placeholder="Postal code*" v-model="state.user.postalCode" >
                <input type="text" class="form-control" placeholder="City*" v-model="state.user.city" >
                <input type="text" class="form-control" placeholder="Country*" v-model="state.user.country" >
                <input type="password" class="form-control" placeholder="Password*" v-model="state.user.password"/>
                <input type="password" class="form-control" placeholder="Confirm password*" v-model="state.user.confirm"/>

                <div class="confirm-buttons">
                    <button class="btn submit-btn" @click="submitNewAdmin()">Submit</button>
                    <button class="btn cancel-btn" @click="cancelNewAdmin()">Cancel</button>
                </div>
            </div>
        </div>
    </div>

    <div id="page">
        <div class="title d-flex justify-content-between">
            <h1>All Users</h1>
            <button class="btn new-admin" @click="openModalForNewAdmin()"><i class="fas fa-user-plus"></i>&ensp;New administrator</button>
        </div>
        <div class="filter-search">
            <div class="dropdown">
                <button class="btn dropdown-toggle drop-btn" ref="btnToggle" id="dropdownMenuButton" data-toggle="dropdown" 
                aria-haspopup="true" aria-expanded="false">
                    Filter by role &ensp;
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <a class="dropdown-item" href="#a" @click="filterByRole(-1)" >All Users</a>
                    <a class="dropdown-item" href="#a" @click="filterByRole(0)" >Client</a>
                    <a class="dropdown-item" href="#a" @click="filterByRole(1)" >Administrator</a>
                    <a class="dropdown-item" href="#a" @click="filterByRole(2)" >Cottage owner</a>
                    <a class="dropdown-item" href="#a" @click="filterByRole(3)" >Ship owner</a>
                    <a class="dropdown-item" href="#a" @click="filterByRole(4)" >Fishing instructor</a>
                </div>
            </div>
            <div class="search">
                <div class="form-outline">
                    <input v-model="searchParams" type="search" id="search" class="form-control" placeholder="Search users"/>
                </div>
                <button type="button" class="btn" @click="search()">
                    <i class="fas fa-search"></i>
                </button>
            </div>
        </div>
        <div class="users-table card rounded">
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th scope="col"></th>
                        <th scope="col">E-mail</th>
                        <th scope="col">First name</th>
                        <th scope="col">Last name</th>
                        <th scope="col">Phone number</th>
                        <th scope="col">Role </th>
                        <th scope="col"></th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="user in users" :key="user.id">
                        <th scope="row">{{ users.indexOf(user) + 1 }}</th>
                        <td>{{ user.email }}</td>
                        <td>{{ user.firstName }}</td>
                        <td>{{ user.lastName }}</td>
                        <td>{{ user.phoneNumber }}</td>

                        <td v-if="user.role == 0">Client</td>
                        <td v-else-if="user.role == 1">Administrator</td>
                        <td v-else-if="user.role == 2">Cottage owner</td>
                        <td v-else-if="user.role == 3">Ship owner</td>
                        <td v-else-if="user.role == 4">Fishing instructor</td>
                        <td v-else></td>

                        <td><button class="btn btn-info" @click="openModalForUserDetails(user)"><i class="fas fa-info"></i></button></td>
                        <td><button class="btn btn-delete" @click="removeUser(user)"><i class="fas fa-trash"></i></button></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    
</template>

<script>
import useValidate from '@vuelidate/core'
import {required,email,sameAs,minLength,numeric} from '@vuelidate/validators' 
import {reactive, computed} from 'vue'

export default {
    data() {
        return {
            allUsers: [
                {
                    id: 1,
                    email: "zdravkocolic@gmail.com",
                    firstName: "Zdravko",
                    lastName: "Colic",
                    phoneNumber: "0645555555",
                    role: 0
                },
                {
                    id: 2,
                    email: "anagavrilovic@gmail.com",
                    firstName: "Ana",
                    lastName: "Gavrilovic",
                    phoneNumber: "0645555555",
                    role: 3
                },
                {
                    id: 3,
                    email: "marijakljestan@gmail.com",
                    firstName: "Marija",
                    lastName: "Kljestan",
                    phoneNumber: "0645555555",
                    role: 2
                },
                {
                    id: 4,
                    email: "stefanljubovic@gmail.com",
                    firstName: "Stefan",
                    lastName: "Ljubovic",
                    phoneNumber: "0645555555",
                    role: 4
                },
            ],
            users: [],
            searchParams: "",
            selectedUser: undefined
        }
    },
    mounted() {
        this.users = this.allUsers;
    },
    setup() {
        let user = {
                email : '',
                firstName: '',
                lastName: '',
                streetName: '',
                streetNumber: undefined,
                postalCode: undefined,
                city: '',
                country: '',
                phoneNumber: '',
                password: '',
                confirm: ''
        }
        let state = reactive({
            user
        })
        const rules = computed(()=>{
            return {
                email: {required,email },
                password: {required, minLength: minLength(6) },
                confirm: {required,sameAs:sameAs(state.user.password)},
                firstName: {required },
                lastName: {required },
                streetName: {required },
                streetNumber: {required,numeric },
                postalCode: {required,numeric },
                city: {required },
                country: {required },
                phoneNumber: {required,numeric },
            }
        })
        const v$=useValidate(rules,state.user)
        return {
            state,
            v$
        }
    },
    methods: {
        removeUser: function(user) {
            if(confirm("Are you sure you want to delete " + user.email + '\'s account?')){
                let index = this.users.indexOf(user);
                if(index > -1){
                    this.users.splice(index, 1);
                }
            }
        },
        openModalForUserDetails: function(user) {
            this.selectedUser = user;
            window.$('#user-details-modal').modal('show');
        },
        openModalForNewAdmin: function() {
            window.$('#new-admin-modal').modal('show');
        },
        filterByRole: function(role) {
            if(role == -1) {
                this.users = this.allUsers;
                return;
            }

            this.users = this.allUsers.filter((user) => user.role == role);
        },
        search: function() {
            this.users = this.allUsers.filter((user) => user.email.includes(this.searchParams.toLowerCase())
                                               || user.firstName.toLowerCase().includes(this.searchParams.toLowerCase())
                                               || user.lastName.toLowerCase().includes(this.searchParams.toLowerCase())
                                               || user.phoneNumber.includes(this.searchParams));
        },
        roleToString: function(role) {
            if(role == 0) return "Client";
            else if(role == 1) return "Administrator";
            else if(role == 2) return "Cottage owner";
            else if(role == 3) return "Ship owner";
            else if(role == 4) return "Fishing instructor";
            else return "";
        },
        submitNewAdmin: function() {
            window.$('#new-admin-modal').modal('hide');
        },
        cancelNewAdmin: function() {
            window.$('#new-admin-modal').modal('hide');
        }
    }
}
</script>

<style scoped>
#page {
    margin: 50px 15%;
    padding-bottom: 120px;
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

.new-admin{
    height: 40px;
    margin-top: 5px;
}

.btn-info {
    width: 40px;
    border-color: #2c3e50;
}

.btn-close {
    background-color: transparent;
    border-color: transparent;
    color: transparent;
}

.search {
    display: flex;
}

.form-control {
    width: 300px;
    margin-right: 10px;
}

.users-table {
    margin-top: 20px;
    padding-top: 10px;
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

.modal-content > input {
    width: 100%;
    margin-bottom: 10px;
}

.confirm-buttons {
    display: flex;
    justify-content: flex-end;
    margin-right: 10px;
    margin-top: 20px;
}

.cancel-btn {
    background-color: white;
    color: #2c3e50;
    border-color: #cfd3d8;
    margin-left: 10px;
}
</style>