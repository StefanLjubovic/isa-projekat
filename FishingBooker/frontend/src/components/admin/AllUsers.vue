<template>
    <div id="page">
        <h1>All Users</h1>
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

                        <td><button class="btn btn-info" @click="userDeatils(user)"><i class="fas fa-info"></i></button></td>
                        <td><button class="btn btn-delete" @click="removeUser(user)"><i class="fas fa-trash"></i></button></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    
</template>

<script>
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
        }
    },
    mounted() {
        this.users = this.allUsers;
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
        userDetails: function(user) {
            console.log(user);
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

.btn-info {
    width: 40px;
    border-color: #2c3e50;
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


</style>