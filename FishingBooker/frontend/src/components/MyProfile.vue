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

                            <input type="text" class="form-control" placeholder="Street name" v-model="v$.user.streetName.$model" :disabled="!editMode">
                            <div class="text-danger" v-if="v$.user.streetName.$error">{{v$.user.streetName.$errors[0].$message}} </div>
                        </div>
                        <div class="right">
                            <input type="text" class="form-control" placeholder="Street number" v-model="v$.user.streetNumber.$model" :disabled="!editMode">
                            <div class="text-danger" v-if="v$.user.streetNumber.$error">{{v$.user.streetNumber.$errors[0].$message}} </div>

                            <input type="text" class="form-control" placeholder="Postal code" v-model="v$.user.postalCode.$model" :disabled="!editMode">
                            <div class="text-danger" v-if="v$.user.postalCode.$error">{{v$.user.postalCode.$errors[0].$message}} </div>

                            <input type="text" class="form-control" placeholder="City" v-model="v$.user.city.$model" :disabled="!editMode">
                            <div class="text-danger" v-if="v$.user.city.$error">{{v$.user.city.$errors[0].$message}} </div>

                            <input type="text" class="form-control" placeholder="Country" v-model="v$.user.country.$model" :disabled="!editMode">
                            <div class="text-danger" v-if="v$.user.country.$error">{{v$.user.country.$errors[0].$message}} </div>
                            <div v-if="userRole=='ROLE_CLIENT'" class="mb-4 penalty-div">
                             <h4 class="cancelation-label">Number of penalties: {{getPenealties()}}</h4><i class="fas fa-info-circle fa-2x info" @click="penaltyInfo()"></i>
                            </div>
                            <div class="buttons">
                                <button class="btn change-password" :disabled="!editMode">Change password</button>
                                <div class="confirm-buttons">
                                    <button class="btn save-button" @click.prevent="saveChanges()" :disabled="!editMode || v$.user.$invalid">Save</button>
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
            <textarea class="reason-area" cols="40" rows="6"></textarea><br/>
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
            editMode: false,
            penalty : 1,
            user: {
                email : 'stefan@gmail.com',
                firstName: 'Stefan',
                lastName: 'Ljubovic',
                streetName: 'Mihajla Pupina',
                streetNumber: 11,
                postalCode: 24000,
                city: 'Subotica',
                country: 'Srbija',
                phoneNumber: '063103130',
                password: 'stefan123',
                confirm: 'stefan123'
            },
            userBackup: undefined
        }
    },
    mounted() {
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
                streetName: { required },
                streetNumber: { required },
                postalCode: { required, numeric, minLength: minLength(5), maxLength: maxLength(5) },
                city: { required, alpha },
                country: { required, alpha },
                phoneNumber: { required, numeric, minLength: minLength(9), maxLength: maxLength(10) },
                password: { required, minLength: minLength(6) },
                confirm: { required, sameAs: sameAs(this.user.password) },
            }
        }
    },
    methods: {
        saveChanges() {
            this.userBackup = this.user;
            this.editMode = !this.editMode;
        },
        cancelEditing() {
            this.user = {...this.userBackup};
            this.editMode = !this.editMode;
        },

        openModalForDeletingAccount() {
            window.$('#delete-account-modal').modal('show');
        },

        sendRequest(){
            window.$('#delete-account-modal').modal('hide');
        },
        cancelRequest() {
            // TODO
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
