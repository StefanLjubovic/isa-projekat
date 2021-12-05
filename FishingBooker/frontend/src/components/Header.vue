<template>
    <div :class="'background-img'+role" class="background-img">
        <div class="headerr">
            <div class="logo">
                <h1 id="logo" @click="goToMainPage">Fishing booker</h1>
            </div>
            <div class="buttons-div">
                <div v-if="role == 5" class="unregistrated-user-options" >
                    <a href="#" class="link-light dropdown-toggle item" role="button" id="dropdownMenuButton" 
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Sign Up</a>
                    <div class="dropdown-menu drop" aria-labelledby="dropdownMenuButton">
                            <a class="dropdown-item" href="#" @click="openSignUp">As client</a>
                            <a class="dropdown-item" href="#">As advertiser</a>
                    </div>
                    <a href="#" class="link-light item" @click="$emit('open-modal')">Log In</a>
                </div>
                <div v-if="role != 5" class="registrated-user-option">
                    <a href="#" class="link-light item" @click="$emit('log-out')" v-if="userRole !=''">Log Out</a>
                </div>
                <div class="buttons-div"  v-if="userRole ==''">
           <a href="#" class="link-light dropdown-toggle item" 
           role="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Sign up</a>
           <div class="dropdown-menu drop" aria-labelledby="dropdownMenuButton">
             <a class="dropdown-item" href="#" @click="openSignUp">As client</a>
             <a class="dropdown-item" href="#" @click="openSignUpAsAdvertiser">As instructor</a>
            <a class="dropdown-item" href="#" @click="openSignUpAsAdvertiser">As cottage owner</a>
           </div>
           <a href="#" class="link-light item" @click="$emit('open-modal')">Log in</a>
          </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data(){
        return{
            show:true
        }
    },
    emits:['open-modal', 'log-out'],
    methods:{
        goToMainPage: function(){
             this.$router.push({ path: '/' })
             console.log(this.userRole)
        },
        openSignUp: function(){
            this.$router.push({ path: '/client-registration' })
        }
    },
    computed:{
        userRole(){
            return this.$store.getters.getRole;
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
    height: 50vh;
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
    justify-content: space-between;
    padding: 1% 15%;
    text-shadow: 1px 1px 4px #5f5f5f;
}
#logo{
    color:rgba(255, 255, 255, 0.88);
}
.buttons-div{
    width: 170px;
    margin-top: 15px;
    text-align: right;
}
.drop{
    margin-top: 2vh;
    text-shadow: none;
}

.item{
    text-decoration: none;
    margin-left: 15%;
    height: 15px;
    font-size: 18px;
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