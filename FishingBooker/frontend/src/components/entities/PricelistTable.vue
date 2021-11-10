<template>
    <h2>Pricelist</h2>
    <table class="table table-striped">
        <thead>
            <tr>
                <th scope="col-md-6">Service</th>
                <th scope="col-md-6">Price</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="item in pricelist" :key="item">
                <td>{{ item.service }}</td>
                <td>{{ item.price }}</td>
            </tr>
        </tbody>
    </table>
</template>

<script>
export default {
    props: {
        pricelist: Array
    }
}
</script>

<style scoped>
h2 {
    font-size: 20px;
    text-align: left;
}

.table-striped, .table {
    color: #2c3e50;
    text-align: left;
}

</style>