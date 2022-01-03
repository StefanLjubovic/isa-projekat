<template>
    <div class="text">
        <p> {{ ship.description }} </p> <br/>

        <p> <span class="ul-caption">Capacity:  </span> {{ ship.capacity }} persons </p> <br/>

        <p class="ul-caption">Fishing equipment: </p>
        <ul>
            <div v-for="e in ship.fishingEquipment" :key="e">
                <li> {{ e }} </li>
            </div>
        </ul>
        <br/>

        <p class="ul-caption">Navigation equipment: </p>
        <ul>
            <div v-for="e in ship.navigationEquipment" :key="e">
                <li> {{ e }} </li>
            </div>
        </ul>
        <br/>

        <p class="ul-caption"><i class="fas fa-check-circle"></i>&nbsp; Allowed behavior: </p>
        <ul>
            <div v-for="ab in ship.allowedBehavior" :key="ab">
                <li>{{ ab }}</li>
            </div>
        </ul>
        <br/>

        <p class="ul-caption"><i class="fas fa-times-circle"></i>&nbsp; Unallowed behavior: </p>
        <ul>
            <div v-for="uab in ship.unallowedBehavior" :key="uab">
                <li>{{ uab }}</li>
            </div>
        </ul>
    </div>
</template>

<script>

export default ({
    props: {
        ship: Object    
    }
})
</script>

<style scoped>

.text {
    display: flex;
    align-items: flex-start;
    text-align: left;
    flex-direction: column;
    margin-top: 30px;
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
