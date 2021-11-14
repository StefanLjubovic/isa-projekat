<template>
    <div id="page">
        <div class="caption">
            <h1>My Profile</h1>
            <div class="options">
                <button class="btn" @click="editMode=!editMode"><i class="fas fa-pen"></i></button>
                <button class="btn"><i class="fas fa-trash"></i></button>
            </div>
        </div>
        <div class="profile-form">
            <div class="card">
                <form>
                    <div class="inputs">
                        <div class="left">
                            <input type="email" class="form-control" placeholder="Email" v-model="state.user.email" disabled>
                            <input type="text" class="form-control" placeholder="First name" v-model="state.user.firstName" :disabled="!editMode">
                            <input type="text" class="form-control" placeholder="Last name" v-model="state.user.lastName" :disabled="!editMode">
                            <input type="text" class="form-control" placeholder="Phone number" v-model="state.user.phoneNumber" :disabled="!editMode">
                            <input type="text" class="form-control" placeholder="Street name" v-model="state.user.streetName" :disabled="!editMode">
                        </div>
                        <div class="right">
                            <input type="text" class="form-control" placeholder="Street number" v-model="state.user.streetNumber" :disabled="!editMode">
                            <input type="text" class="form-control" placeholder="Postal code" v-model="state.user.postalCode" :disabled="!editMode">
                            <input type="text" class="form-control" placeholder="City" v-model="state.user.city" :disabled="!editMode">
                            <input type="text" class="form-control" placeholder="Country" v-model="state.user.country" :disabled="!editMode">
                            <div class="buttons">
                                <button class="btn change-password" :disabled="!editMode">Change password</button>
                                <div class="confirm-buttons">
                                    <button class="btn save-button" @click.prevent="saveChanges()" :disabled="!editMode">Save</button>
                                    <button class="btn cancel-button" @click.prevent="editMode=false" :disabled="!editMode">Cancel</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import useValidate from '@vuelidate/core'
import {required,email,sameAs,minLength,numeric} from '@vuelidate/validators' 
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
        const rules = computed(()=>{
            return {
                email: {required,email },
                password: {required, minLength: minLength(6) },
                confirm: {required,sameAs:sameAs(state.user.password)},
                firstName: {required },
                lastName: {required },
                streetName: {required },
                streetNumber: {required,numeric },
                postalCode: {required,numeric },
                city: {required },
                country: {required },
                phoneNumber: {required,numeric },
            }
        })
        const v$=useValidate(rules,state.user)
        return {
            state,
            v$
        }
    },
    methods: {
        saveChanges() {
            this.v$.$validate()
            console.log(this.v$)

            this.editMode = !this.editMode;
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



</style>
