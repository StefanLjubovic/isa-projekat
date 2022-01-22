<template>
    <div class="sales-placeholder" v-if="sales !== undefined && sales.length > 0">
        <div v-for="sale in salesArray" :key="sale.id" class="sale-for">
            <div class="btn btn-sale" @click="displaySaleInfo(sale)">
                <div>
                    <h3><i class="fas fa-money-bill-wave"></i> SALE </h3>
                    <p>Date&amp;Time: {{this.dateFormat(sale.dateTimeFrom)}}</p>
                    <p v-if="!adventure.hasOwnProperty('cottageOwner')">Duration: {{sale.durationInHours}} hours</p>
                    <p v-else> Duration: {{ convertToDays(sale.durationInHours) }} days </p>
                    <h3>ONLY FOR {{sale.price}} RSD </h3>
                </div>
                <div>
                    <i class="fas fa-info-circle info-icon"></i>
                </div>
            </div>
        </div> 
    </div>
    <hr v-if="sales !== undefined && sales.length != 0"/>
</template>

<script>
import moment from 'moment'
import server from '../../server';
export default ({
    props: {
        sales: Array,
        adventure: Object
    },
    emits: ['sale-to-reservation','update-entity'],
    data() {
        return {
            salesArray: this.sales,
            entityId: this.adventure.id       
        }
    },
    computed:{
        userRole(){
            return this.$store.getters.getRole;
        }
    },
    methods: {
        displaySaleInfo: async function(sale) {
            console.log(this.salesArray)
            console.log(JSON.stringify(this.adventure));
            if(this.userRole == 'ROLE_COTTAGE_OWNER' || this.userRole == 'ROLE_SHIP_OWNER' || this.userRole == 'ROLE_INSTRUCTOR' || this.userRole == ''){
                this.$swal({
                    title: '<h2>SALE INFO</h2>',
                    icon: 'info',
                    html:
                        `<p style="font-size: 14px">Date&amp;Time: ${this.dateFormat(sale.dateTimeFrom)}</p> ` +
                        `<p>Duration: ${sale.durationInHours} hours</p>` +
                        `<p style="font-size: 14px">Maximum persons: ${sale.maximumPersons} </p> ` + 
                        `<p style="font-size: 14px">Additional services: ${sale.additionalServices} </p> ` +
                        `<p style="font-size: 14px">ONLY FOR: ${sale.price} RSD</p> ` + 
                        `<p style="color: #a0a0a0; font-size: 14px">Sale expires on ${this.dateFormat(sale.expireDateTime)}</p> ` ,
                        confirmButtonColor: '#2c3e50'
                })
            } else {
                this.$swal({
                    title: '<h2>SALE INFO</h2>',
                    icon: 'info',
                    html:
                        `<p style="font-size: 14px">Date&amp;Time: ${this.dateFormat(sale.dateTimeFrom)}</p> ` +
                        `<p style="font-size: 14px">Duration: ${sale.durationInHours} hours</p> ` +
                        `<p style="font-size: 14px">Maximum persons: ${sale.maximumPersons} </p> ` + 
                        `<p style="font-size: 14px">Additional services: ${sale.additionalServices} </p> ` +
                        `<p style="font-size: 14px">ONLY FOR: ${sale.price} RSD</p> ` + 
                        `<p style="color: #a0a0a0; font-size: 14px">Sale expires on ${this.dateFormat(sale.expireDateTime)}</p> ` +
                        `<h3>Do you want to book this adventure?</h3>`,
                    showCloseButton: true,
                    showCancelButton: true,
                    confirmButtonText: 'Book!',
                    cancelButtonText: 'No',
                    confirmButtonColor: '#2c3e50'
                }).then(async (result) => {
                    if (result.value) {
                        const client=await server.getLoggedUser()
                        const reservation = {
                dateTime : sale.dateTimeFrom,
                durationInHours :  sale.durationInHours,
                maxPersons : sale.maximumPersons,
                additionalServices : ['item'],
                price : sale.price,
                isCanceled : false,
                entityId: this.entityId,
                client : client.data,
                entityVersion: this.adventure.version,
                sale : sale
            }
            console.log(this.adventure.pricelistItems)
                        await server.saveFastReservation(reservation)
                        .then(resp=> {
                            if(resp.success){
                                this.$swal.fire({
                                    icon: 'success',
                                    title: 'Success',
                                    text: 'Reservation succesfully created!',
                                    confirmButtonColor: '#2c3e50'
                                })
                                console.log('Indexxx'+ this.salesArray.indexOf(sale))
                                console.log(this.salesArray)
                                this.$emit('update-entity',resp.data);
                                this.$emit('sale-to-reservation', reservation);
                                this.salesArray.splice(this.salesArray.indexOf(sale), 1);
                            }
                        })
                    }
                });
            }
        },
        dateFormat(value) {
            return moment(value).format("DD.MM.YYYY. HH:mm");
        },
        convertToDays(durationInHours){
            return durationInHours / 24;
        }
    }
})
</script>

<style scoped>

h3 {
    font-weight: bold;
    font-size: 20px;
    text-align: left;
}

.sales-placeholder {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
}

.btn-sale {
    width: 320px;
    height: 120px;
    background-color: rgb(26, 160, 66);
    color: white;
    text-align: left;
    display: flex;
    justify-content: space-between;
    margin-bottom: 25px;
}

p { 
    font-size: 15px;
    margin: 0;
}

.btn-info {
    background-color: transparent;
    border-color: transparent;
    font-size: 30px;
}

.info-icon {
    font-size: 30px;
    color: white;
}

</style>
