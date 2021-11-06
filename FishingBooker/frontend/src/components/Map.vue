<template>
    <p>{{ address.streetName }} {{ address.streetNumber }}, {{ address.postalCode }}</p>
    <p>{{ address.city }}, {{ address.country }}</p>
    <br/>
    <img src="../assets/mapa.jpg"/>
    <br/>
</template>

<script>

export default ({
    props: {
        address: Object
    }
})
</script>

<style scoped>
p {
    margin-bottom: 0;
}

img {
    width: 100%;
    height: 300px;
}

</style>
