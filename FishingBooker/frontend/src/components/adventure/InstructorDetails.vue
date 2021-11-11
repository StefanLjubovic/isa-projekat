<template>
    <div class="instructor">
        <div>
            <img class="photo rounded" src="../../assets/adventure/instructor.png"/>
        </div>
        <div>
            <div class="instr-text">
                <p class="instr-name">{{ instructor.firstName }} {{ instructor.lastName }} - instructor</p>
                <p>{{ instructor.shortBiography }}</p>
            </div>
        </div>
    </div>
</template>

<script>

export default ({
    props: {
        instructor: Object,
    }
})
</script>


<style scoped>
.instructor {
    display: flex;
}

.photo {
    width: 120px;
    height: 120px;
}

.instr-text {
    padding-left: 15px;
    display: flex;
    flex-direction: column;
    text-align: left;
}

.instr-name {
    font-size: 20px;
}
</style>