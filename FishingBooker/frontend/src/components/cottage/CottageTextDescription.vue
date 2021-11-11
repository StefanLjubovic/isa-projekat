<template>
    <div class="text">
        <p> {{ cottage.description }} </p>
        <br/>

        <p class="ul-caption">Rooms ({{cottage.rooms.length}}): </p>
        <ul>
            <div v-for="r in cottage.rooms" :key="r">
                <li>{{ r.bedNumber }} bed room</li>
            </div>
        </ul>
        <br/>

        <p class="ul-caption"><i class="fas fa-check-circle"></i>&nbsp; Allowed behaviour: </p>
        <ul>
            <div v-for="ab in cottage.allowedBehaviour" :key="ab">
                <li>{{ ab }}</li>
            </div>
        </ul>
        <br/>

        <p class="ul-caption"><i class="fas fa-times-circle"></i>&nbsp; Unallowed behaviour: </p>
        <ul>
            <div v-for="uab in cottage.unallowedBehaviour" :key="uab">
                <li>{{ uab }}</li>
            </div>
        </ul>
    </div>
</template>

<script>

export default ({
    props: {
        cottage: Object
      
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
