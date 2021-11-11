<template>
    <div class="container">
        <div class="caption">
            <h1>{{adventureName}}</h1>
        </div>
        <div class="options">
            <button class="btn" @click="toggleSubscribe" v-if="!subscribed">Subscribe to this adventure&nbsp;&ensp;<i class="fas fa-bell"></i> </button>
            <button class="btn" @click="toggleSubscribe" v-else>Unsubscribe from this adventure&nbsp;&ensp;<i class="fas fa-bell-slash"></i> </button>
        </div>
    </div>
    
</template>

<script>

export default ({
    props: {
        adventureName: String,
    },
    data() {
        return {
            subscribed: false,
        }
    },
    methods: {
        toggleSubscribe: function() {
            this.subscribed = !this.subscribed;
        }
    }
})
</script>

<style scoped>

.container {
    display: flex;
    justify-content: space-between;
}

.btn {
    background-color: #2c3e50;
    color: white;
    margin-top: 10px;
}


</style>
