<template>
    <div id="page">
        <h2>Requests for Revisions and Evaluations</h2>
        <div class="filter-search">

            <div class="dropdown">
                <button class="btn dropdown-toggle drop-btn" ref="btnToggle" id="dropdownMenuButton" data-toggle="dropdown" 
                aria-haspopup="true" aria-expanded="false">
                    Filter by type &ensp;
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <a class="dropdown-item" href="#a" @click="filterByAdvertiserType(-1)">All types</a>
                    <a class="dropdown-item" href="#a" @click="filterByAdvertiserType(2)" >Cottage</a>
                    <a class="dropdown-item" href="#a" @click="filterByAdvertiserType(3)" >Ship</a>
                    <a class="dropdown-item" href="#a" @click="filterByAdvertiserType(4)" >Adventure</a>
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
        <div class="revisions-table card rounded">
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th scope="col"></th>
                        <th scope="col">E-mail</th>
                        <th scope="col">Client</th>
                        <th scope="col">Advertiser</th>
                        <th scope="col">Service</th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="revision in revisions" :key="revision.id">
                        <th scope="row">{{ revisions.indexOf(revision) + 1 }}</th>
                        <td>{{ revision.client.email }}</td>
                        <td>{{ revision.client.firstName }} {{ revision.client.lastName }}</td>
                        <td>Marija Kljestan</td>
                        <td>Marijina vikendica</td>

                        <td><button class="btn btn-info" @click="revisionDeatils(revision)"><i class="fas fa-info"></i></button></td>
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
            allRevisions: [
                {
                    client: {
                        id: 1,
                        email: "zdravkocolic@gmail.com",
                        firstName: "Zdravko",
                        lastName: "Colic",
                        phoneNumber: "0645555555"
                    },
                    content: "Exercitation incididunt esse veniam cillum ea dolor enim labore fugiat enim labore nostrud eiusmod ullamco.",
                    mark: 4,
                    isApproved: false
                },
                {
                    client: {
                        id: 2,
                        email: "anagavrilovic@gmail.com",
                        firstName: "Ana",
                        lastName: "Gavrilovic",
                        phoneNumber: "0645555555"
                    },
                    content: "Exercitation incididunt esse veniam cillum ea dolor enim labore fugiat enim labore nostrud eiusmod ullamco.",
                    mark: 5,
                    isApproved: false
                },
                {
                    client: {
                        id: 3,
                        email: "marijakljestan@gmail.com",
                        firstName: "Marija",
                        lastName: "Kljestan",
                        phoneNumber: "0645555555"
                    },
                    content: "Exercitation incididunt esse veniam cillum ea dolor enim labore fugiat enim labore nostrud eiusmod ullamco.",
                    mark: 3,
                    isApproved: true
                },
                {
                    client: {
                        id: 4,
                        email: "stefanljubovic@gmail.com",
                        firstName: "Stefan",
                        lastName: "Ljubovic",
                        phoneNumber: "0645555555"
                    },
                    content: "Exercitation incididunt esse veniam cillum ea dolor enim labore fugiat enim labore nostrud eiusmod ullamco.",
                    mark: 4,
                    isApproved: true
                },
            ],
            revisions: [],
            searchParams: "",
        }
    },
    mounted() {
        this.revisions = this.allRevisions;
    },
    methods: {
        filterByAdvertiserType: function(type) {
            if(type == -1) {
                this.revisions = this.allRevisions;
                return;
            }

            // filter po roli advertisera
        },
        revisionDeatils: function(revision) {
            console.log(revision);
        },
        search: function() {
            this.revisions = this.allRevisions.filter((revision) => revision.client.email.includes(this.searchParams.toLowerCase())
                                               || revision.client.firstName.toLowerCase().includes(this.searchParams.toLowerCase())
                                               || revision.client.lastName.toLowerCase().includes(this.searchParams.toLowerCase()));
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

.revisions-table {
    margin-top: 20px;
    padding-top: 10px;
}

</style>
