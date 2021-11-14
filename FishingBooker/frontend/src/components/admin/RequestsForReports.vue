<template>
    <div id="page">
        <h1>Report Requests for Penalty</h1>
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
                        <th scope="col"></th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="report in reports" :key="report.id">
                        <th scope="row">{{ reports.indexOf(report) + 1 }}</th>
                        <td>Marija Kljestan</td>
                        <td>Marijina vikendica</td>
                        <td>{{ report.client.email }}</td>
                        <td>{{ report.client.firstName }} {{ report.client.lastName }}</td>

                        <td>
                            <div id="to-hover">
                                <button class="btn btn-info"><i class="fas fa-info"></i></button>
                                <div id="to-show" class="card rounded">{{ report.content }}</div>
                            </div> 
                        </td>
                        <td><button class="btn" @click="approveReport(report)"><i class="fas fa-check"></i></button></td>
                        <td><button class="btn btn-delete" @click="disapproveReport(report)"><i class="fas fa-times"></i></button></td>
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
            allReports: [
                {
                    client: {
                        id: 1,
                        email: "zdravkocolic@gmail.com",
                        firstName: "Zdravko",
                        lastName: "Colic",
                        phoneNumber: "0645555555"
                    },
                    content: "Exercitation incididunt esse veniam cillum ea dolor enim labore fugiat enim labore nostrud eiusmod ullamco.",
                    isBadReview: true,
                    notAppeared: false
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
                    isBadReview: false,
                    notAppeared: false
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
                    isBadReview: true,
                    notAppeared: true
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
                    isBadReview: false,
                    notAppeared: true
                },
            ],
            reports: [],
            searchParams: "",
        }
    },
    mounted() {
        this.reports = this.allReports;
    },
    methods: {
        filterByContent: function(content) {
            if(content == -1) {
                this.reports = this.allReports;
            }
            else if(content == 0) {
                this.reports = this.allReports.filter((report) => report.isBadReview == true);
            }
            else if(content == 1) {
                this.reports = this.allReports.filter((report) => report.notAppeared == true);
            }
        },
        search: function() {
            this.reports = this.allReports.filter((report) => report.client.email.includes(this.searchParams.toLowerCase())
                                               || report.client.firstName.toLowerCase().includes(this.searchParams.toLowerCase())
                                               || report.client.lastName.toLowerCase().includes(this.searchParams.toLowerCase()));
        },
        approveReport: function(report) {
            console.log(report);
        },
        disapproveReport: function(report) {
            console.log(report);
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
