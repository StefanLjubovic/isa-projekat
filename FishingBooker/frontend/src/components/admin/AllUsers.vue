<template>
    <!-- User deatils modal -->
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
                        <p>{{ selectedUser.address.streetName }}</p>
                        <p>{{ selectedUser.address.streetNumber }}</p>
                        <p>{{ selectedUser.address.postalCode }}</p>
                        <p>{{ selectedUser.address.city }}</p>
                        <p>{{ selectedUser.address.country }}</p>
                        <p>{{ selectedUser.phoneNumber }}</p>
                        <p>{{ roleToString(selectedUser.role.name) }}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Add new administrator modal -->
    <div class="modal fade" id="new-admin-modal">
        <div class="modal-dialog rounded">
            <div class="modal-header"  @click="cancelNewAdmin()">
                <h3>Add new administrator</h3>
                <button class="btn btn-close close"><i class="fas fa-times"></i></button>
            </div>
            <div class="modal-content">
                <input type="email" class="form-control" placeholder="Email*" v-model="v$.newAdmin.email.$model" >
                <div class="text-danger" v-if="v$.newAdmin.email.$error">{{v$.newAdmin.email.$errors[0].$message}} </div>

                <input type="text" class="form-control" placeholder="First name*" v-model="v$.newAdmin.firstName.$model" >
                <div class="text-danger" v-if="v$.newAdmin.firstName.$error">{{v$.newAdmin.firstName.$errors[0].$message}} </div>

                <input type="text" class="form-control" placeholder="Last name*" v-model="v$.newAdmin.lastName.$model" >
                <div class="text-danger" v-if="v$.newAdmin.lastName.$error">{{v$.newAdmin.lastName.$errors[0].$message}} </div>

                <input type="text" class="form-control" placeholder="Phone number*" v-model="v$.newAdmin.phoneNumber.$model" >
                <div class="text-danger" v-if="v$.newAdmin.phoneNumber.$error">{{v$.newAdmin.phoneNumber.$errors[0].$message}} </div>

                <input type="text" class="form-control" placeholder="Street name*" v-model="v$.newAdmin.address.streetName.$model" >
                <div class="text-danger" v-if="v$.newAdmin.address.streetName.$error">{{v$.newAdmin.address.streetName.$errors[0].$message}} </div>

                <input type="text" class="form-control" placeholder="Street number*" v-model="v$.newAdmin.address.streetNumber.$model" >
                <div class="text-danger" v-if="v$.newAdmin.address.streetNumber.$error">{{v$.newAdmin.address.streetNumber.$errors[0].$message}} </div>

                <input type="text" class="form-control" placeholder="Postal code*" v-model="v$.newAdmin.address.postalCode.$model" >
                <div class="text-danger" v-if="v$.newAdmin.address.postalCode.$error">{{v$.newAdmin.address.postalCode.$errors[0].$message}} </div>

                <input type="text" class="form-control" placeholder="City*" v-model="v$.newAdmin.address.city.$model" >
                <div class="text-danger" v-if="v$.newAdmin.address.city.$error">{{v$.newAdmin.address.city.$errors[0].$message}} </div>

                <input type="text" class="form-control" placeholder="Country*" v-model="v$.newAdmin.address.country.$model" >
                <div class="text-danger" v-if="v$.newAdmin.address.country.$error">{{v$.newAdmin.address.country.$errors[0].$message}} </div>

                <input type="password" class="form-control" placeholder="Password*" v-model="v$.newAdmin.password.$model"/>
                <div class="text-danger" v-if="v$.newAdmin.password.$error">{{v$.newAdmin.password.$errors[0].$message}} </div>

                <input type="password" class="form-control" placeholder="Confirm password*" v-model="v$.newAdmin.confirm.$model"/>
                <div class="text-danger" v-if="v$.newAdmin.confirm.$error">{{v$.newAdmin.confirm.$errors[0].$message}} </div>

                <div class="confirm-buttons">
                    <button class="btn submit-btn" :disabled="v$.newAdmin.$invalid" @click="submitNewAdmin()">Submit</button>
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
                    <a class="dropdown-item" href="#a" @click="filterByRole('ROLE_CLIENT')" >Client</a>
                    <a class="dropdown-item" href="#a" @click="filterByRole('ROLE_ADMIN')" >Administrator</a>
                    <a class="dropdown-item" href="#a" @click="filterByRole('ROLE_COTTAGE_OWNER')" >Cottage owner</a>
                    <a class="dropdown-item" href="#a" @click="filterByRole('ROLE_SHIP_OWNER')" >Ship owner</a>
                    <a class="dropdown-item" href="#a" @click="filterByRole('ROLE_INSTRUCTOR')" >Fishing instructor</a>
                </div>
            </div>
            <div class="search">
                <div class="form-outline">
                    <input v-model="searchParams" type="search" id="search" class="form-control" placeholder="Search users"/>
                </div>
                <button type="button" class="btn btn-search" @click="search()">
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

                        <td v-if="user.role.name == 'ROLE_CLIENT'">Client</td>
                        <td v-else-if="user.role.name == 'ROLE_ADMIN'">Administrator</td>
                        <td v-else-if="user.role.name == 'ROLE_COTTAGE_OWNER'">Cottage owner</td>
                        <td v-else-if="user.role.name == 'ROLE_SHIP_OWNER'">Ship owner</td>
                        <td v-else-if="user.role.name == 'ROLE_INSTRUCTOR'">Fishing instructor</td>
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
import {required, email, sameAs, minLength, maxLength, numeric} from '@vuelidate/validators'
import server from '../../server/index'
import axios from 'axios'

export function validName(name) {
  let validNamePattern = new RegExp("^[a-zA-ZšđžčćŠĐŽČĆ]+(?:[-'\\s][a-zA-ZšđžčćŠĐŽČĆ]+)*$");
  if (validNamePattern.test(name)){
    return true;
  }
  return false;
}

export default {
    data() {
        return {
            allUsers: [],
            users: [],
            searchParams: "",
            selectedUser: undefined,
            newAdmin: {
                email : '',
                firstName: '',
                lastName: '',
                phoneNumber: '',
                address: {
                    streetName: '',
                    streetNumber: '',
                    postalCode: '',
                    city: '',
                    country: '',
                },
                password: '',
                confirm: ''
            },
            filterRole: -1
        }
    },
    mounted() {
        const headers = {
            'Content-Type': 'application/json;charset=UTF-8',
            Accept: 'application/json',
            'Authorization': `Bearer ${this.$store.getters.getToken}`
        }

        axios.get(`${server.baseUrl}/user/allUsers`, {headers: headers})
        .then((response) => {
            this.allUsers = response.data;
            this.users = this.allUsers.slice();
        })
    },
    setup() {
        return { v$: useValidate() }
    },
    validations() {
        return {
            newAdmin: {
                email: { required, email },
                firstName: { required, name_validation: {
                        $validator: validName,
                        $message: 'Invalid Name. Valid name only contain letters, dashes (-) and spaces'
                    } 
                },
                lastName: { required, name_validation: {
                        $validator: validName,
                        $message: 'Invalid Name. Valid name only contain letters, dashes (-) and spaces'
                    } 
                },
                phoneNumber: { required, numeric, minLength: minLength(9), maxLength: maxLength(10) },
                address: {
                    streetName: { required },
                    streetNumber: { required },
                    postalCode: { required, numeric, minLength: minLength(5), maxLength: maxLength(5) },
                    city: { required, name_validation: {
                            $validator: validName,
                            $message: 'Invalid Name. Valid name only contain letters, dashes (-) and spaces'
                        } 
                    },
                    country: { required, name_validation: {
                            $validator: validName,
                            $message: 'Invalid Name. Valid name only contain letters, dashes (-) and spaces'
                        } 
                    },
                },
                password: { required, minLength: minLength(6) },
                confirm: { required, sameAs:sameAs(this.newAdmin.password) },
            }
        }
    },
    methods: {
        removeUser: function(user) {
            if(user.id == 1) {
                this.$swal({
                    icon: 'error',
                    title: 'Ooops...',
                    text: 'This account can\'t be deleted!',
                    confirmButtonColor: '#2c3e50'
                })
                return;
            }

            const headers = {
                'Content-Type': 'application/json;charset=UTF-8',
                Accept: 'application/json',
                'Authorization': `Bearer ${this.$store.getters.getToken}`
            }

            this.$swal({
                title: `Are you sure you want to delete ${user.email}'s account?`,
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Yes',
                confirmButtonColor: '#2c3e50'
            }).then((result) => {
                if(result.isConfirmed) {
                    axios.delete(`${server.baseUrl}/user/deleteUser/${user.id}`, { headers: headers })
                    .then(() => {
                        let index = this.users.indexOf(user);
                        if(index > -1) this.users.splice(index, 1);
                        let indexAll = this.allUsers.indexOf(user);
                        if(indexAll > -1) this.allUsers.splice(indexAll, 1);
                    });
                }
            })
        },

        openModalForUserDetails: function(user) {
            this.selectedUser = user;
            window.$('#user-details-modal').modal('show');
        },

        openModalForNewAdmin: function() {
            window.$('#new-admin-modal').modal('show');
        },

        filterByRole: function(role) {
            this.filterRole = role;
            this.search();
        },

        search: function() {
            this.users = this.allUsers.filter((user) => user.email.includes(this.searchParams.toLowerCase())
                                               || user.firstName.toLowerCase().includes(this.searchParams.toLowerCase())
                                               || user.lastName.toLowerCase().includes(this.searchParams.toLowerCase())
                                               || user.phoneNumber.includes(this.searchParams));
            if(this.filterRole != -1) {
                this.users = this.users.filter((user) => user.role.name == this.filterRole);
            }
        },

        submitNewAdmin: function() {
            const headers = {
                'Content-Type': 'application/json;charset=UTF-8',
                Accept: 'application/json',
                'Authorization': `Bearer ${this.$store.getters.getToken}`
            }

            axios.post(`${server.baseUrl}/auth/registerAdmin`, this.newAdmin, { headers: headers })
            .then((response) => {
                this.allUsers.push(response.data);
                this.search();

                this.newAdmin = { email : undefined, firstName: '', lastName: '', streetName: '', streetNumber: '', postalCode: '', city: '', country: '', phoneNumber: '', password: '', confirm: '' };
                window.$('#new-admin-modal').modal('hide');
                this.$swal({
                    position: 'top-end',
                    icon: 'success',
                    title: 'New administrator successufully added!',
                    showConfirmButton: false,
                    timer: 2000
                })
            })
            .catch(() => {
                this.$swal('There is already an account with this email!');
            })
        },

        cancelNewAdmin: function() {
            this.newAdmin = { email : undefined, firstName: '', lastName: '', streetName: '', streetNumber: '', postalCode: '', city: '', country: '', phoneNumber: '', password: '', confirm: '' }
            window.$('#new-admin-modal').modal('hide');
        },

        roleToString: function(role) {
            if(role == 'ROLE_CLIENT') return "Client";
            else if(role == 'ROLE_ADMIN') return "Administrator";
            else if(role == 'ROLE_COTTAGE_OWNER') return "Cottage owner";
            else if(role == 'ROLE_SHIP_OWNER') return "Ship owner";
            else if(role == 'ROLE_INSTRUCTOR') return "Fishing instructor";
            else return "";
        },
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

.btn-search {
    margin-top: 5px;
    height: 40px;
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
    margin-top: 7px;
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

.text-danger {
    margin-top: -5px;
    text-align: left;
    font-size: 13px;
}
</style>