<template>
    <div class="text">
        <p> {{ adventure.description }} </p>
        <br/>

        <p><span class="ul-caption">Capacity:</span> {{ adventure.maxPersons }} persons</p>
        <br/>

        <p class="ul-caption">Provided fishing equipment: </p>
        <ul>
            <div v-for="e in adventure.fishingEquipment" :key="e">
                <li>{{ e }}</li>
            </div>
        </ul>
        <br/>

        <p class="ul-caption"><i class="fas fa-check-circle"></i>&nbsp; Allowed behaviour: </p>
        <ul>
            <div v-for="ab in adventure.allowedBehavior" :key="ab">
                <li>{{ ab }}</li>
            </div>
        </ul>
        <br/>

        <p class="ul-caption"><i class="fas fa-times-circle"></i>&nbsp; Unallowed behaviour: </p>
        <ul>
            <div v-for="uab in adventure.unallowedBehavior" :key="uab">
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

.text {
    display: flex;
    align-items: flex-start;
    text-align: left;
    flex-direction: column;
    margin-top: 5px;
}

.ul-caption {
    margin-bottom: 0px;
    font-weight: bold;
}


br {
    display: block; 
    content: ""; 
    margin-top: 7px; 
}

</style>
