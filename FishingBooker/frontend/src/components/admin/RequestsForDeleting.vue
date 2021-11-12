<template>
    <div id="page">
        <h2>Requests for Deleting Account</h2>
        <div class="filter-search">
            <div class="dropdown">
                <button class="btn dropdown-toggle drop-btn" ref="btnToggle" id="dropdownMenuButton" data-toggle="dropdown" 
                aria-haspopup="true" aria-expanded="false">
                    Filter by role &ensp;
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <a class="dropdown-item" href="#a" @click="filterByRole(-1)" >All users</a>
                    <a class="dropdown-item" href="#a" @click="filterByRole(0)" >Client</a>
                    <a class="dropdown-item" href="#a" @click="filterByRole(2)" >Cottage owner</a>
                    <a class="dropdown-item" href="#a" @click="filterByRole(3)" >Ship owner</a>
                    <a class="dropdown-item" href="#a" @click="filterByRole(4)" >Fishing instructor</a>
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
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="request in requests" :key="request.registratedUser.id">
                        <th scope="row">{{ requests.indexOf(request) + 1 }}</th>
                        <td>{{ request.registratedUser.email }}</td>
                        <td>{{ request.registratedUser.firstName }}</td>
                        <td>{{ request.registratedUser.lastName }}</td>
                        <td>{{ request.registratedUser.phoneNumber }}</td>

                        <td v-if="request.registratedUser.role == 0">Client</td>
                        <td v-else-if="request.registratedUser.role == 1">Administrator</td>
                        <td v-else-if="request.registratedUser.role == 2">Cottage owner</td>
                        <td v-else-if="request.registratedUser.role == 3">Ship owner</td>
                        <td v-else-if="request.registratedUser.role == 4">Fishing instructor</td>
                        <td v-else></td>

                        <td><button class="btn btn-info" @click="requestDeatils(request)"><i class="fas fa-info"></i></button></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>

export default ({
    data() {
        return {
            allRequests: [
                {
                    registratedUser: {
                        id: 1,
                        email: "zdravkocolic@gmail.com",
                        firstName: "Zdravko",
                        lastName: "Colic",
                        phoneNumber: "0645555555",
                        role: 0
                    },
                    content: "Exercitation incididunt esse veniam cillum ea dolor enim labore fugiat enim labore nostrud eiusmod ullamco."
                },
                {
                    registratedUser: {
                        id: 2,
                        email: "anagavrilovic@gmail.com",
                        firstName: "Ana",
                        lastName: "Gavrilovic",
                        phoneNumber: "0645555555",
                        role: 3
                    },
                    content: "Exercitation incididunt esse veniam cillum ea dolor enim labore fugiat enim labore nostrud eiusmod ullamco."
                },
                {
                    registratedUser: {
                        id: 3,
                        email: "marijakljestan@gmail.com",
                        firstName: "Marija",
                        lastName: "Kljestan",
                        phoneNumber: "0645555555",
                        role: 2
                    },
                    content: "Exercitation incididunt esse veniam cillum ea dolor enim labore fugiat enim labore nostrud eiusmod ullamco."
                },
                {
                    registratedUser: {
                        id: 4,
                        email: "stefanljubovic@gmail.com",
                        firstName: "Stefan",
                        lastName: "Ljubovic",
                        phoneNumber: "0645555555",
                        role: 4
                    },
                    content: "Exercitation incididunt esse veniam cillum ea dolor enim labore fugiat enim labore nostrud eiusmod ullamco."
                },
            ],
            requests: [],
            searchParams: "",
        }
    },
    mounted() {
        this.requests = this.allRequests;
    },
    methods: {
        requestDeatils: function(request) {
            console.log(request);
        },
        filterByRole: function(role) {
            if(role == -1) {
                this.requests = this.allRequests;
                return;
            }

            this.requests = this.allRequests.filter((request) => request.registratedUser.role == role);
        },
        search: function() {
            this.requests = this.allRequests.filter((request) => request.registratedUser.email.includes(this.searchParams.toLowerCase())
                                               || request.registratedUser.firstName.toLowerCase().includes(this.searchParams.toLowerCase())
                                               || request.registratedUser.lastName.toLowerCase().includes(this.searchParams.toLowerCase())
                                               || request.registratedUser.phoneNumber.includes(this.searchParams));
        }
    }
})
</script>

<style scoped>
#page {
    margin: 50px 15%;
    padding-bottom: 150px;
}

h2 {
    text-align: left;
    margin-bottom: 15px;
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

.requests-table {
    margin-top: 20px;
    padding-top: 10px;
}

</style>
