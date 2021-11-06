<template>
    <div class="content">
        <p> {{ adventure.description }} </p>
        <br/>

        <p>Capacity: {{ adventure.maxPersons }} persons</p>
        <br/>

        <p class="ul-caption">Provided fishing equipment: </p>
        <ul>
            <div v-for="e in adventure.fishingEquipment" :key="e">
                <li>{{ e }}</li>
            </div>
        </ul>
        <br/>

        <p class="ul-caption"><i class="fas fa-check-circle"></i> Allowed behaviour: </p>
        <ul>
            <div v-for="ab in adventure.allowedBehaviour" :key="ab">
                <li>{{ ab }}</li>
            </div>
        </ul>
        <br/>

        <p class="ul-caption"><i class="fas fa-times-circle"></i> Unallowed behaviour: </p>
        <ul>
            <div v-for="uab in adventure.unallowedBehaviour" :key="uab">
                <li>{{ uab }}</li>
            </div>
        </ul>
    </div>
</template>

<script>

export default ({
    props: {
        adventure: Object,
    }
})
</script>

<style scoped>

.content {
    display: flex;
    align-items: flex-start;
    text-align: left;
    flex-direction: column;
}

.ul-caption {
    margin-bottom: 0px;
}

</style>
