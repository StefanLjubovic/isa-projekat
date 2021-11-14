<template>
    <div v-if="selectedRevision" class="modal fade" id="revision-modal">
        <div class="modal-dialog rounded">
            <div class="modal-header">
                <h3>Revision Details</h3>
                <button class="btn btn-close close" data-dismiss="modal"><i class="fas fa-times"></i></button>
            </div>
            <div class="modal-content">
                <p><b>Content:</b> {{ selectedRevision.content }} </p>
                <p><b>Mark:</b> {{ selectedRevision.mark }} </p>
                <div class="confirm-buttons">
                    <button class="btn submit-btn"><i class="fas fa-check"></i> Approve </button>
                    <button class="btn cancel-btn"><i class="fas fa-times"></i> Disapprove </button>
                </div>
            </div>
        </div>
    </div>

    <div id="page">
        <h1>Requests for Revisions and Evaluations</h1>
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
            selectedRevision: undefined
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
            this.selectedRevision = revision;
            window.$('#revision-modal').modal('show');
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

.revisions-table {
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
    margin-top: 20px;
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


</style>
