<template>
    <div :class="'background-img'+role" class="background-img">
        <div class="headerr">
            <div class="logo">
                <h1 id="logo" @click="goToMainPage">Fishing booker</h1>
            </div>
            <div class="buttons-div">
                <div class="unregistrated-user-options" v-if="role == 5">
                    <a href="#" class="link-light dropdown-toggle item" role="button" id="dropdownMenuButton" 
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Sign Up</a>
                    <div class="dropdown-menu drop" aria-labelledby="dropdownMenuButton">
                            <a class="dropdown-item" href="#" @click="openSignUp">As client</a>
                            <a class="dropdown-item" href="#">As advertiser</a>
                    </div>
                    <a href="#" class="link-light item" @click="$emit('open-modal')">Log In</a>
                </div>
                <div class="registrated-user-option">
                    <a href="#" class="link-light item" @click="$emit('log-out')">Log Out</a>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    props: {
        role: Number
    },
    data(){
        return{
            show:true,
        }
    },
    emits:['open-modal', 'log-out'],
    methods:{
        goToMainPage: function(){
             this.$router.push({ path: '/' })
        },
        openSignUp: function(){
            this.$router.push({ path: '/client-registration' })
        }
    }
}
</script>

<style scoped>
.background-img {
    background-size: cover; 
    background-repeat: no-repeat;
    background-position: center center;
    width: 100%;
    height: 45vh;
}

.background-img0, .background-img5 {
    background-image: url(../assets/fish.jpg);
}
.background-img1 {
    background-image: url(../assets/admin.jpg);
}
.background-img2 {
    background-image: url(../assets/cottage.jpg);
}
.background-img3 {
    background-image: url(../assets/ship-owner.jpg);
}
.background-img4 {
    background-image: url(../assets/instructor.jpg);
}

 .headerr{
        display:flex;
        flex:1;
        flex-direction: row;
        justify-content: space-between;
        padding: 20px;
    }
   #logo{
        color:rgba(14, 14, 14, 0.88)
    }
    .logo{
        display: flex;
        flex: 1;
        justify-content: center;
    }
    .buttons-div{
        display: flex;
        flex: 1;
        justify-content:flex-end;
        margin-right: 8%;
        margin-top: 15px;
    }
    .drop{
        margin-top: 2vh;
    }

    .item{
        text-decoration: none;
        margin-right: 5%;
        height: 15px;
        font-size: 20px;
    }

    .fade-enter-active, .fade-leave-active {
    transition: opacity .5s
}
.fade-enter, .fade-leave-to /* .fade-leave-active in <2.1.8 */ {
    opacity: 0
}
.page-wraper{
     position: relative;
     width: 100vw;
 min-height: 100vh;
 overflow-x: hidden;
}
h1{
    cursor: pointer;
}

</style>