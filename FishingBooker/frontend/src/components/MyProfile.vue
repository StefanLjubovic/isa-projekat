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
                            <input type="email" class="form-control" placeholder="Email" v-model="state.user.email" disabled>
                            <div class="text-danger" v-if="v$.email.$error">{{v$.email.$errors[0].$message}} </div>

                            <input type="text" class="form-control" placeholder="First name" v-model="state.user.firstName" :disabled="!editMode">
                            <div class="text-danger" v-if="v$.firstName.$error">{{v$.firstName.$errors[0].$message}} </div>

                            <input type="text" class="form-control" placeholder="Last name" v-model="state.user.lastName" :disabled="!editMode">
                            <div class="text-danger" v-if="v$.lastName.$error">{{v$.lastName.$errors[0].$message}} </div>

                            <input type="text" class="form-control" placeholder="Phone number" v-model="state.user.phoneNumber" :disabled="!editMode">
                            <div class="text-danger" v-if="v$.phoneNumber.$error">{{v$.phoneNumber.$errors[0].$message}} </div>

                            <input type="text" class="form-control" placeholder="Street name" v-model="state.user.streetName" :disabled="!editMode">
                            <div class="text-danger" v-if="v$.streetName.$error">{{v$.streetName.$errors[0].$message}} </div>
                        </div>
                        <div class="right">
                            <input type="text" class="form-control" placeholder="Street number" v-model="state.user.streetNumber" :disabled="!editMode">
                            <div class="text-danger" v-if="v$.streetNumber.$error">{{v$.streetNumber.$errors[0].$message}} </div>

                            <input type="text" class="form-control" placeholder="Postal code" v-model="state.user.postalCode" :disabled="!editMode">
                            <div class="text-danger" v-if="v$.postalCode.$error">{{v$.postalCode.$errors[0].$message}} </div>

                            <input type="text" class="form-control" placeholder="City" v-model="state.user.city" :disabled="!editMode">
                            <div class="text-danger" v-if="v$.city.$error">{{v$.city.$errors[0].$message}} </div>

                            <input type="text" class="form-control" placeholder="Country" v-model="state.user.country" :disabled="!editMode">
                            <div class="text-danger" v-if="v$.country.$error">{{v$.country.$errors[0].$message}} </div>

                            <div class="buttons">
                                <button class="btn change-password" :disabled="!editMode">Change password</button>
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
import {required, email, sameAs, minLength, numeric, alpha} from '@vuelidate/validators' 
import {reactive, computed} from 'vue'

export default ({
    data() {
        return {
            editMode: false
        }
    },
    setup() {
        let user = {
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
        }
        let state = reactive({
            user
        })
        const rules = computed(() => {
            return {
                email: { required, email },
                password: { required, minLength: minLength(6) },
                confirm: { required, sameAs: sameAs(state.user.password) },
                firstName: { required, alpha },
                lastName: { required, alpha },
                streetName: { required },
                streetNumber: { required },
                postalCode: { required, numeric },
                city: { required, alpha },
                country: { required, alpha },
                phoneNumber: { required, numeric },
            }
        })
        const v$ = useValidate(rules, state.user)
        return {
            state,
            v$
        }
    },
    methods: {
        saveChanges() {
            this.v$.$validate()
            if(this.v$.$error)
                return

            this.editMode = !this.editMode;
        },
        cancelEditing() {
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
    margin-top: -25px;
    margin-bottom: 5px;
    text-align: left;
    font-size: 13px;
}
</style>
