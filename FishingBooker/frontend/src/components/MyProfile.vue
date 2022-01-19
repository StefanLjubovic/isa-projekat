<template>
    <div id="page">
        <div class="caption">
            <h1>My Profile</h1>
            <div class="options">
                <button class="btn" @click="editMode=!editMode"><i class="fas fa-pen"></i></button>
                <button class="btn" @click="openModalForDeletingAccount()"><i class="fas fa-trash"></i></button>
            </div>
        </div>
        <div class="profile-form">
            <div class="card">
                <form>
                    <div class="inputs">
                        <div class="left">
                            <input type="email" class="form-control" placeholder="Email" v-model="v$.user.email.$model" disabled>
                            <div class="text-danger" v-if="v$.user.email.$error">{{v$.user.email.$errors[0].$message}} </div>

                            <input type="text" class="form-control" placeholder="First name" v-model="v$.user.firstName.$model" :disabled="!editMode">
                            <div class="text-danger" v-if="v$.user.firstName.$error">{{v$.user.firstName.$errors[0].$message}} </div>

                            <input type="text" class="form-control" placeholder="Last name" v-model="v$.user.lastName.$model" :disabled="!editMode">
                            <div class="text-danger" v-if="v$.user.lastName.$error">{{v$.user.lastName.$errors[0].$message}} </div>

                            <input type="text" class="form-control" placeholder="Phone number" v-model="v$.user.phoneNumber.$model" :disabled="!editMode">
                            <div class="text-danger" v-if="v$.user.phoneNumber.$error">{{v$.user.phoneNumber.$errors[0].$message}} </div>

                            <input type="text" class="form-control" placeholder="Street name" v-model="v$.user.address.streetName.$model" :disabled="!editMode">
                            <div class="text-danger" v-if="v$.user.address.streetName.$error">{{v$.user.address.streetName.$errors[0].$message}} </div>

                            <input type="text" class="form-control" placeholder="Street number" v-model="v$.user.address.streetNumber.$model" :disabled="!editMode">
                            <div class="text-danger" v-if="v$.user.address.streetNumber.$error">{{v$.user.address.streetNumber.$errors[0].$message}} </div>
                        </div>
                        <div class="right">
                            <input type="text" class="form-control" placeholder="Postal code" v-model="v$.user.address.postalCode.$model" :disabled="!editMode">
                            <div class="text-danger" v-if="v$.user.address.postalCode.$error">{{v$.user.address.postalCode.$errors[0].$message}} </div>

                            <input type="text" class="form-control" placeholder="City" v-model="v$.user.address.city.$model" :disabled="!editMode">
                            <div class="text-danger" v-if="v$.user.address.city.$error">{{v$.user.address.city.$errors[0].$message}} </div>

                            <input type="text" class="form-control" placeholder="Country" v-model="v$.user.address.country.$model" :disabled="!editMode">
                            <div class="text-danger" v-if="v$.user.address.country.$error">{{v$.user.address.country.$errors[0].$message}} </div>

                             <input type="password" class="form-control" placeholder="Password" v-model="v$.password.password.$model" :disabled="!editMode">
                            <div class="text-danger" v-if="v$.password.password.$error">{{v$.password.password.$errors[0].$message}} </div>

                             <input type="password" class="form-control" placeholder="Confirm password" v-model="v$.password.confirm.$model" :disabled="!editMode">
                            <div class="text-danger" v-if="v$.password.confirm.$error">{{v$.password.confirm.$errors[0].$message}} </div>

                            <div v-if="userRole=='ROLE_CLIENT'" class="mb-4 penalty-div">
                             <h4 class="cancelation-label">Number of penalties: {{getPenealties()}}</h4><i class="fas fa-info-circle fa-2x info" @click="penaltyInfo()"></i>
                            </div>
                            <div v-if="userRole=='ROLE_CLIENT'" class="mb-4 penalty-div">
                             <h4 class="cancelation-label">{{getPoints()}}</h4><i class="fas fa-info-circle fa-2x info" @click="pointsInfo()"></i>
                            </div>
                            <div class="buttons">
                                <button class="btn cancel-button" @click.prevent="changePassword()" :disabled="!editMode || v$.password.$invalid">changePassword</button>
                                <div class="confirm-buttons">
                                    <button class="btn save-button" @click.prevent="saveChanges()" :disabled="!editMode">Save</button>
                                    <button class="btn cancel-button" @click.prevent="cancelEditing()" :disabled="!editMode">Cancel</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>


 <!--Modal for deleting account -->

<div class="modal fade" id="delete-account-modal">
  <div class="modal-dialog rounded">
      <div class="modal-header">
          <h3>What is the reason for deleting?</h3>
          <button class="btn btn-close close" data-dismiss="modal"><i class="fas fa-times"></i></button>
      </div>
          <div class="modal-content">
              <br/>
            <textarea class="reason-area" cols="40" rows="6" v-model="content"></textarea><br/>
            <div class="confirm-buttons">
                <button class="btn save-button"  @click.prevent="sendRequest()" >Submit</button>
                <button class="btn cancel-button" @click="cancelRequest()">Cancel</button>
            </div>
       </div>
     </div>
  </div>

</template>

<script>
import useValidate from '@vuelidate/core'
import {required, email, sameAs, minLength, maxLength, numeric, alpha} from '@vuelidate/validators'
import Server from '../server';
import {mapActions} from 'vuex';
export function validName(name) {
  let validNamePattern = new RegExp("^[a-zA-ZšđžčćŠĐŽČĆ]+(?:[-'\\s][a-zA-ZšđžčćŠĐŽČĆ]+)*$");
  if (validNamePattern.test(name)){
    return true;
  }
  return false;
}

export default ({
    data() {
        return {
            points : 1200,
            editMode: false,
            penalty : 1,
            user: {},
            password : {
                password : '',
                confirm : ''
            },
            userBackup: undefined,
            content : ''
        }
    },
    async mounted() {
        const resp=await Server.getLoggedUser()
        this.user = resp.data
        this.userBackup = {...this.user};
    },
    computed:{
        userRole(){
            return this.$store.getters.getRole;
        }
    },
    setup() {
        return { v$: useValidate() }
    },
    validations() {
        return {
            user: {
                email: { required, email },
                firstName: { required, name_validation: {
                        $validator: validName,
                        $message: 'Invalid Name. Valid name only contain letters, dashes (-) and spaces'
                    } 
                },
                lastName: { required, name_validation: {
                        $validator: validName,
                        $message: 'Invalid Name. Valid name only contain letters, dashes (-) and spaces'
                    } 
                },
                address:{
                streetName: { required },
                streetNumber: { required },
                postalCode: { required, numeric, minLength: minLength(5), maxLength: maxLength(5) },
                city: { required, alpha },
                country: { required, alpha },
                },
                phoneNumber: { required, numeric, minLength: minLength(9), maxLength: maxLength(10) },
            },
            password : {
                password: { required, minLength: minLength(6) },
                confirm: { required, sameAs: sameAs(this.password.password) },
            }
        }
    },
    methods: {
        ...mapActions(['updateToken']),
        async saveChanges() {
            this.userBackup = this.user;
            this.editMode = !this.editMode;
            const dto = {
                id : this.user.id,
                firstname : this.user.firstName,
                lastname : this.user.lastName,
                address : this.user.address,
                phoneNumber : this.user.phoneNumber
            }
            await Server.updateUser(dto,this.$store.getters.getToken)
            .then(resp=> {
                if(resp.success){
                    this.$swal.fire({
                        icon: 'success',
                        title: 'Success',
                        text: 'Profile updated successfully!',
                        confirmButtonColor: '#2c3e50'
                    })
                }else{
                    this.$swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: 'Something went wrong.',
                        confirmButtonColor: '#2c3e50'
                    })
                }
            })
        },
        cancelEditing() {
            this.user = {...this.userBackup};
            this.editMode = !this.editMode;
        },

        openModalForDeletingAccount() {
            window.$('#delete-account-modal').modal('show');
        },

        async sendRequest(){
            window.$('#delete-account-modal').modal('hide');
            const data = this.content
            await Server.saveDeleteRequest(data,this.$store.getters.getToken)
            .then(resp=> {
                if(resp.success){
                    this.$swal.fire(
                        'Success',
                        'Request for deleting sent to admin.',
                        'success')
                }else{
                    this.$swal.fire(
                        'Oops...',
                        'Something went wrong!',
                        'error')
                }
            })
        },
        cancelRequest() {
            this.user = {...this.userBackup}
            this.editMode = !this.editMode
        },
        async changePassword(){
              await Server.changePassword(this.password.password,this.$store.getters.getToken)
            .then(async resp=> {
                if(resp.success){
                    this.$swal.fire({
                        icon: 'success',
                        title: 'Success',
                        text: 'Password updated successfully!',
                        confirmButtonColor: '#2c3e50'
                    })
                    await this.updateToken(resp.data)
                }else{
                    this.$swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: 'Something went wrong.',
                        confirmButtonColor: '#2c3e50'
                    })
                }
             this.userBackup = {...this.user};
            this.editMode = !this.editMode;
            })
        },
        getPenealties(){
            if(this.penalty == 0)
                return 'None 👍'
            else if(this.penalty == 1)
                return 'One ❌'
            else if(this.penalty == 2)
                return 'Two ❌❌'
        },
        penaltyInfo(){
            this.$swal.fire(
            'Penalty info',
            'For every canceled reservation client gets penalty.If client gets three penalties in a mounth he is banned.',
            'question'
            )
        },
        getPoints(){
            return 'Points collected: ' + this.points
        },
        pointsInfo(){
            if(this.points < 1000){
                this.$swal.fire(
                'Loyality program: regular',
                'With this program client does not get discount on reservations, make more reservations to advance to silver program!',
                'question'
                )
            }else if(this.points < 1500){
                this.$swal.fire(
                'Loyality program: silver',
                'With this program client gets 10% discount on reservations, make more reservations to advance to gold program!',
                'question'
                )
            }else{
                this.$swal.fire(
                'Loyality program: gold',
                'With this program client gets 15% discount on reservations!',
                'question'
                )
            }
        }
    }
})
</script>

<style scoped>
#page {
    margin-left: 15%;
    margin-right: 15%;
    margin-top: 50px;
    padding-bottom: 100px;
}

.penalty-div{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
}

.caption {
    display: flex;
    justify-content: space-between;
}

.options {
    display: flex;
}

.btn {
    width: 55px;
}

.cancelation-label{
    display: flex;
    justify-content: flex-start;
}
.card {
    margin-top: 20px;
    padding: 40px;
}

.inputs {
    display: flex;
    justify-content: space-between;
}

.form-control {
    width: 30vw;
    margin-bottom: 25px;
}

.buttons {
    display: flex;
    justify-content: space-between;
}

.change-password, .cancel-button {
    background-color: white;
    border-color: rgb(218, 214, 214);
    color: #2c3e50;
    width: auto;
}

.confirm-buttons {
    display: flex;
}

.save-button {
    background-color: #2c3e50;
    color: white;
    width: 70px;
    margin-right: 10px;
}

.modal-content {
    padding: 30px;
    font-size: 20px;
    background-color: rgb(211, 222, 223);
}

.modal-dialog{
    background-color: #ffffff;
}

.btn-close {
    background-color: transparent;
    border-color: transparent;
    color: transparent;
    margin-right: 0px;
    margin-bottom: 15px;
}

 h3 {
    margin-left: 7%;
    margin-top: 18px;
}

.info{
    cursor: pointer;
}

.reason-area{
    align-self: left;
    margin-left: 5px;
    background-color: #ffffff;
    border-width: 1px solid #888 ;
    font-size: 18px;
    resize: none;
    outline: none;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    border-radius: 10px;
}

.confirm-buttons{
    margin-left: 30%;
}

.text-danger {
    margin-top: -24px;
    margin-bottom: 5px;
    text-align: left;
    font-size: 13px;
}
</style>
