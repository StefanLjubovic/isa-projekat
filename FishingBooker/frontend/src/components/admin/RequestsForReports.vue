<template>
    <div id="page">
        <h1>Report Requests</h1>
        <div class="filter-search">
            <div class="dropdown">
                <button class="btn dropdown-toggle drop-btn" ref="btnToggle" id="dropdownMenuButton" data-toggle="dropdown" 
                aria-haspopup="true" aria-expanded="false">
                    Filter by content &ensp;
                </button>
                <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <a class="dropdown-item" href="#a" @click="filterByContent(-1)">All reports</a>
                    <a class="dropdown-item" href="#a" @click="filterByContent(0)" >Bad comment</a>
                    <a class="dropdown-item" href="#a" @click="filterByContent(1)" >Client didn't show up</a>
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
        <div class="reports-table card rounded">
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th scope="col"></th>
                        <th scope="col">Advertiser</th>
                        <th scope="col">Service</th>
                        <th scope="col">E-mail</th>
                        <th scope="col">Client</th>
                        <th scope="col">Report</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="report in reports" :key="report.id">
                        <th scope="row">{{ reports.indexOf(report) + 1 }}</th>
                        <td>{{ report.advertiserFullName }}</td>
                        <td>{{ report.entityName }}</td>
                        <td>{{ report.clientEmail }}</td>
                        <td>{{ report.clientFullName }}</td>

                        <td>
                            <div id="to-hover">
                                <button class="btn btn-info"><i class="fas fa-info"></i></button>
                                <div id="to-show" class="card rounded">{{ report.content }}</div>
                            </div> 
                        </td>
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
            allReports: [],
            reports: [],
            searchParams: "",
            filterParam: -1
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
        axios.get(`${server.baseUrl}/report`, { headers: headers })
        .then((response) => {
            this.allReports = response.data;
            this.reports = this.allReports.slice();
        })
    },
    methods: {
        filterByContent: function(content) {
            this.filterParam = content;
            this.search();
        },
        search: function() {
            this.reports = this.allReports.filter((report) => report.clientEmail.includes(this.searchParams.toLowerCase())
                                               || report.clientFullName.toLowerCase().includes(this.searchParams.toLowerCase())
                                               || report.advertiserFullName.toLowerCase().includes(this.searchParams.toLowerCase())
                                               || report.entityName.toLowerCase().includes(this.searchParams.toLowerCase()));
            if(this.filterParam == 0) {
                this.reports = this.reports.filter((report) => report.badReview == true);
            }
            else if(this.filterParam == 1) {
                this.reports = this.reports.filter((report) => report.notAppeared == true);
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

.reports-table {
    margin-top: 20px;
    padding-top: 10px;
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
