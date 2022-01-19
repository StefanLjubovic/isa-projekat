<template>
    <div v-if="selectedComplaint" class="modal fade" id="response-to-complaint-modal">
        <div class="modal-dialog rounded">
            <div class="modal-header">
                <h3>Response to Complaint</h3>
                <button class="btn btn-close close" data-dismiss="modal"><i class="fas fa-times"></i></button>
            </div>
            <div class="modal-content">
                <p><b>From:</b> {{ selectedComplaint.clientEmail }} </p>
                <p><b>Content:</b> {{ selectedComplaint.content }} </p>
                <textarea class="form-control textarea" rows="8" v-model="complaintResponse" placeholder="Write your response..."></textarea>
                <div class="confirm-buttons">
                    <button class="btn submit-btn" @click="respondToComplaint()" :disabled="!complaintResponse">Submit</button>
                    <button class="btn cancel-btn" @click="cancelResponding()">Cancel</button>
                </div>
            </div>
        </div>
    </div>

    <div id="page">
        <h1>Complaints</h1>
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
        <div class="complaints-table card rounded">
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
                    <tr v-for="complaint in complaints" :key="complaint.id" >
                        <th scope="row">{{ complaints.indexOf(complaint) + 1 }}</th>
                        <td>{{ complaint.clientEmail }}</td>
                        <td>{{ complaint.clientFullName }}</td>
                        <td>{{ complaint.advertiserFullName }}</td>
                        <td>{{ complaint.entityName }}</td>

                        <td><button class="btn btn-info" @click="reportDeatils(complaint)">Read	&amp; Response</button></td>
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
            allComplaints: [],
            complaints: [],
            searchParams: "",
            selectedComplaint: undefined,
            filterParam: -1,
            complaintResponse: ''
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
        axios.get(`${server.baseUrl}/complaint`, { headers: headers })
        .then((response) => {
            this.allComplaints = response.data;
            this.complaints = this.allComplaints.slice();
        })
    },
    methods: {
        reportDeatils: function(complaint) {
            this.selectedComplaint = complaint;
            window.$('#response-to-complaint-modal').modal('show');
        },
        filterByAdvertiserType: function(type) {
            this.filterParam = type;
            this.search();
        },
        search: function() {
            this.complaints = this.allComplaints.filter((complaint) => complaint.clientEmail.includes(this.searchParams.toLowerCase())
                                               || complaint.clientFullName.toLowerCase().includes(this.searchParams.toLowerCase())
                                               || complaint.advertiserFullName.toLowerCase().includes(this.searchParams.toLowerCase())
                                               || complaint.entityName.toLowerCase().includes(this.searchParams.toLowerCase()));
            if(this.filterParam != -1) {
                this.complaints = this.complaints.filter((complaint) => complaint.type == this.filterParam);
            }
        },
        respondToComplaint: function() {
            const headers = {
                'Content-Type': 'application/json;charset=UTF-8',
                Accept: 'application/json',
                'Authorization': `Bearer ${this.token}`
            }
            console.log(this.selectedComplaint)
            axios.put(`${server.baseUrl}/complaint/respond/${this.selectedComplaint.id}`, this.complaintResponse, { headers: headers })
            .then(() => {
                this.allComplaints.splice(this.allComplaints.indexOf(this.selectedComplaint), 1);
                this.complaints.splice(this.complaints.indexOf(this.selectedComplaint), 1);
                window.$('#response-to-complaint-modal').modal('hide');
                this.complaintResponse = '';
                this.$swal({
                    position: 'top-end',
                    icon: 'success',
                    title: 'Response successfully sent on email!',
                    showConfirmButton: false,
                    timer: 2000
                })
            })
            .catch((error) => {
                this.$swal(error.response.data.message);
                console.log(error)
            })
        },
        cancelResponding: function() {
            window.$('#response-to-complaint-modal').modal('hide');
            this.complaintResponse = '';
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
    width: auto;
    border-color: #2c3e50;
}

.search {
    display: flex;
}

.form-control {
    width: 300px;
    margin-right: 10px;
}

.complaints-table {
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
