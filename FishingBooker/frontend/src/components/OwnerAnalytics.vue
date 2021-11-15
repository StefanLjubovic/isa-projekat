<template>
    <div class="page">
        <h1>Analytics</h1>
        <div class="money-percentage">
            <h2> Average grade of my {{entityType}} </h2>
            <div class="requests-table card rounded">
                <table class="table table-striped table-hover">
                    <thead>
                        <tr>
                            <th scope="col"></th>
                            <th scope="col">Name</th>
                            <th scope="col">Mark</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="entity in entities" :key="entity.id">
                            <th scope="row">{{ entities.indexOf(entity) + 1 }}</th>
                            <td>{{ entity.entityName }}</td>
                            <td>{{ entity.mark }}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="total-income">
                <p class="total-income-title">Average grade</p>
                <p class="total-income-grade">{{ getAverageGrade() }}</p>
            </div>         
        </div>

        <h2>Income over a period of time</h2>
        <div class="filter-dates">
            <input type="text" class="form-control form-control-dates" v-model="dateFrom" placeholder="Date From"
                onfocus="(this.type='date')" onblur="(this.type='text')">
            <input type="text" class="form-control form-control-dates" v-model="dateTo" placeholder="Date To"
                onfocus="(this.type='date')" onblur="(this.type='text')">
            <button class="btn btn-option"><i class="fas fa-search"></i></button>
            <button class="btn btn-option"><i class="far fa-file-pdf"></i></button>
        </div>
        <div class="requests-table card rounded">
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th scope="col"></th>
                        <th scope="col">Name</th>
                        <th scope="col">Income</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="reservation in reservations" :key="reservation.id">
                        <th scope="row">{{ reservations.indexOf(reservation) + 1 }}</th>
                        <td>{{ reservation.entityName }}</td>
                        <td>{{ reservation.income }}</td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="total-income">
            <p class="total-income-title">Total income</p>
            <p class="total-income-value">{{ getTotalIncome() }}</p>
        </div>
    </div>
</template>

<script>

export default ({
    data() {
        return {
            entityType: "Cottage",
            dateFrom: "",
            dateTo: "",

            allEntities: [
                {
                    entityName: "Marijina vikendica",
                    mark: "4.9"
                },
                {
                    entityName: "The Cottage",
                    mark: "4.5"
                },
                {
                    entityName: "Cabin in the woods",
                    mark: "2.5"
                },

            ],

            allReservations: [ 
                {
                    entityName: "Marijina vikendica",
                    type: "Cottage",
                    income: "5000"
                },
                {
                    entityName: "The Cottage",
                    type: "Cottage",
                    income: "4000"
                },
                {
                    entityName: "Cabin in the woods",
                    type: "Cottage",
                    income: "350"
                },
            ],
            reservations: [],
            entities: []
        }
    },
    mounted() {
        this.reservations = this.allReservations;
        this.entities = this.allEntities;
    },
    methods: {
        getTotalIncome: function() {
            let sum = 0;
            for(let res of this.reservations) {
                sum += parseInt(res.income)
            }
            return sum;
        },
        getAverageGrade: function() {
            let averageGrade = 0;
            for(let entity of this.entities){
                averageGrade += parseInt(entity.mark);
            }

            return (averageGrade/(this.entities.length)).toFixed(2);
        }
    }
})
</script>

<style scoped>
    .page {
        margin: 50px 17%;
        padding-bottom: 100px;
    }

    h1 {
        text-align: left;
        margin-bottom: 40px;
    }

    .btn {
        margin-top: -5px;
        width: 40px;
        height: 40px;
    }

    h2 {
        margin-top: 40px;
        margin-bottom: 20px;
        text-align: left;
    }

    .filter-dates {
        display: flex;
    }

    .form-control-dates {
        margin-right: 10px;
        width: 25vw;
    }

    .btn-option {
        background-color: #2c3e50;
        color: white;
        margin-top: 0px;
        margin-right: 10px;
    }

    .card {
        margin-top:20px;
        width: 57vw;
        padding-top: 10px;
    }
    .table, .table-striped, .table-hover {
        color: #2c3e50;
    }

    .total-income{ 
        display: flex;
        justify-content: space-between;
        margin-left: 30px;
        margin-right:220px;
        margin-top: 20px;
        font-size: 20px;
    }
</style>
