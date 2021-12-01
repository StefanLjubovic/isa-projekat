<template>
    <div class="page">
        <h1>Analytics</h1>
        <div class="money-percentage">
            <h5>Money percentage from successful reservation:</h5>
            <div class="input-percentage">
                <input type="text" class="form-control form-control-money" v-model="moneyPercentageBackup" :disabled="!editMode">
                <p>%</p>
                <button class="btn" @click="editMode = !editMode" :disabled="editMode"><i class="fas fa-pen"></i></button>
                <button class="btn" @click="saveMoneyPercentage()" :disabled="!editMode"><i class="fas fa-check"></i></button>
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
                        <th scope="col">Entity name</th>
                        <th scope="col">Type</th>
                        <th scope="col">Income</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="reservation in reservations" :key="reservation.id">
                        <th scope="row">{{ reservations.indexOf(reservation) + 1 }}</th>
                        <td>{{ reservation.entityName }}</td>
                        <td>{{ reservation.type }}</td>
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
            moneyPercentage: 30,
            moneyPercentageBackup: undefined,
            editMode: false,
            dateFrom: "",
            dateTo: "",
            allReservations: [ 
                {
                    entityName: "Marijina vikendica",
                    type: "Cottage",
                    income: "5000"
                },
                {
                    entityName: "Anin brod",
                    type: "Ship",
                    income: "4000"
                },
                {
                    entityName: "Fishing in the Sunset",
                    type: "Adventure",
                    income: "350"
                },
            ],
            reservations: []
        }
    },
    mounted() {
        this.moneyPercentageBackup = this.moneyPercentage;
        this.reservations = this.allReservations;
    },
    methods: {
        saveMoneyPercentage: function() {
            this.moneyPercentage = this.moneyPercentageBackup;
            this.editMode = false;
        },
        getTotalIncome: function() {
            let sum = 0;
            for(let res of this.reservations) {
                sum += parseInt(res.income)
            }
            return sum;
        }
    }
})
</script>

<style scoped>
.page {
    margin: 50px 15%;
    padding-bottom: 100px;
}

h1 {
    text-align: left;
    margin-bottom: 40px;
}

.money-percentage {
    display: flex;
    justify-content: space-between;
    margin-right: 17%;
}

.form-control-money {
    width: 100px;
    text-align: right;
    height: 35px;
}

.input-percentage {
    display: flex;
}

.input-percentage > p {
    font-size: 25px;
    margin-top: -3px;
    margin-left: 5px;
    margin-right: 30px;
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
    margin-right:210px;
    margin-top: 20px;
    font-size: 20px;
}
</style>
