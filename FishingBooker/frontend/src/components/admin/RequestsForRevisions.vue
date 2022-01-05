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
                    <button class="btn submit-btn" @click="approveRevision()"><i class="fas fa-check"></i> Approve </button>
                    <button class="btn cancel-btn" @click="disapproveRevision()"><i class="fas fa-times"></i> Disapprove </button>
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
                    <a class="dropdown-item" href="#a" @click="filterByAdvertiserType('ROLE_COTTAGE_OWNER')" >Cottage</a>
                    <a class="dropdown-item" href="#a" @click="filterByAdvertiserType('ROLE_SHIP_OWNER')" >Ship</a>
                    <a class="dropdown-item" href="#a" @click="filterByAdvertiserType('ROLE_INSTRUCTOR')" >Adventure</a>
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
                    <tr v-for="revision in revisions" :key="revision.reservationId">
                        <th scope="row">{{ revisions.indexOf(revision) + 1 }}</th>
                        <td>{{ revision.clientEmail }}</td>
                        <td>{{ revision.clientFullName }}</td>
                        <td>{{ revision.advertiserFullName }}</td>
                        <td>{{ revision.entityName }}</td>

                        <td><button class="btn btn-info" @click="revisionDeatils(revision)"><i class="fas fa-info"></i></button></td>
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
            allRevisions: [],
            revisions: [],
            searchParams: "",
            selectedRevision: undefined,
            filterRole: -1
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
        axios.get(`${server.baseUrl}/revision`, { headers: headers })
        .then((response) => {
            this.allRevisions = response.data;
            this.revisions = this.allRevisions.slice();
        })
    },
    methods: {
        filterByAdvertiserType: function(type) {
            this.filterRole = type;
            this.search();
        },
        revisionDeatils: function(revision) {
            this.selectedRevision = revision;
            window.$('#revision-modal').modal('show');
        },
        search: function() {
            this.revisions = this.allRevisions.filter((revision) => revision.clientEmail.includes(this.searchParams.toLowerCase())
                                               || revision.clientEmail.toLowerCase().includes(this.searchParams.toLowerCase())
                                               || revision.entityName.toLowerCase().includes(this.searchParams.toLowerCase())
                                               || revision.advertiserFullName.toLowerCase().includes(this.searchParams.toLowerCase()));
            if(this.filterRole != -1) {
                this.revisions = this.revisions.filter((revision) => revision.type == this.filterRole);
            }
        },
        approveRevision: function() {
            const headers = {
                'Content-Type': 'application/json;charset=UTF-8',
                Accept: 'application/json',
                'Authorization': `Bearer ${this.token}`
            }
            axios.put(`${server.baseUrl}/revision/approve`, this.selectedRevision.id, { headers: headers })
            .then(() => {
                this.revisions.splice(this.revisions.indexOf(this.selectedRevision), 1);
                this.allRevisions.splice(this.allRevisions.indexOf(this.selectedRevision), 1);
                window.$('#revision-modal').modal('hide');
                this.$swal({
                    position: 'top-end',
                    icon: 'success',
                    title: 'Revision successfully approved!',
                    showConfirmButton: false,
                    timer: 2000
                })
            })
        },
        disapproveRevision: function() {
            const headers = {
                'Content-Type': 'application/json;charset=UTF-8',
                Accept: 'application/json',
                'Authorization': `Bearer ${this.token}`
            }
            axios.delete(`${server.baseUrl}/revision/disapprove/${this.selectedRevision.id}`, { headers: headers })
            .then(() => {
                this.revisions.splice(this.revisions.indexOf(this.selectedRevision), 1);
                this.allRevisions.splice(this.allRevisions.indexOf(this.selectedRevision), 1);
                window.$('#revision-modal').modal('hide');
            })
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
