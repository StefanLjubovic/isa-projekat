<template>
<div class="container">
<div class="profile-header">
    <div class="ml-4">
    <h1>
        My profile
    </h1>
    </div>
    <div class="icon-div">
        <i class="fas fa-pen fa-lg m-4 icons" @click="edit=!edit"></i>
        <i class="fas fa-trash fa-lg mr-4 icons"></i>
    </div>
</div>
  <div class="form-content">
      <div class="form form-wrapper">
                    <div class="row">
                        <div class="col-md md-6 mb-4 ">
                            <div class="form-group mb-4 form">
                                <input type="text" class="form-control" placeholder="Email *" 
                                v-model="state.client.email" :disabled="edit"/>
                                <span class="text-danger" v-if="v$.email.$error">
                                    {{v$.email.$errors[0].$message}}
                                </span>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Name *"
                                 v-model="state.client.name" :disabled="edit"/>
                                <span v-if="v$.name.$error" class="text-danger">
                                    {{v$.name.$errors[0].$message}}
                                </span>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4">
                            <div class="form-group mb-4">
                                <input type="text" class="form-control" placeholder="City *" ref="city" 
                                v-model="state.client.city" :disabled="edit"/>
                                <span v-if="v$.city.$error" class="text-danger">
                                    {{v$.city.$errors[0].$message}}
                                </span>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Country *" 
                                v-model="state.client.country" :disabled="edit"/>
                                 <span v-if="v$.country.$error" class="text-danger">
                                    {{v$.country.$errors[0].$message}}
                                </span>
                            </div>
                        </div>
                         <div class="col-md-6 mb-4">
                            <div class="form-group mb-4">
                                <input type="text" class="form-control" placeholder="Surname *" 
                                v-model="state.client.surname" :disabled="edit"/>
                                 <span v-if="v$.surname.$error" class="text-danger">
                                    {{v$.surname.$errors[0].$message}}
                                </span>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Street name *" 
                                v-model="state.client.streetName" :disabled="edit"/>
                                <span v-if="v$.streetName.$error" class="text-danger">
                                    {{v$.streetName.$errors[0].$message}}
                                </span>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4">
                            <div class="form-group mb-4">
                                <input type="text" class="form-control" placeholder="Phone number *" 
                                v-model="state.client.phone" :disabled="edit"/>
                                 <span v-if="v$.phone.$error" class="text-danger">
                                    {{v$.phone.$errors[0].$message}}
                                </span>
                            </div>
                            <div class="form-group">
                                <input type="password" class="form-control" placeholder="Password*" 
                                v-model="state.client.password" :disabled="edit"/>
                                <span v-if="v$.password.$error" class="text-danger">
                                    {{v$.password.$errors[0].$message}}
                                </span>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group mb-4">
                                <input type="text" class="form-control"   placeholder="Street number *"
                                v-model="state.client.streetNumber" :disabled="edit"/>
                                <span v-if="v$.streetNumber.$error" class="text-danger">
                                    {{v$.streetNumber.$errors[0].$message}}
                                </span>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control"  placeholder="Postal code *" 
                                v-model="state.client.postalCode" :disabled="edit"/>
                                 <span v-if="v$.postalCode.$error" class="text-danger">
                                    {{v$.postalCode.$errors[0].$message}}
                                </span>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4">
                            <div class="form-group mb-4">
                                <input type="password" class="form-control" placeholder="Confirm Password *" 
                                v-model="state.client.confirm" :disabled="edit"/>
                                 <span v-if="v$.confirm.$error" class="text-danger">
                                    {{v$.confirm.$errors[0].$message}}
                                </span>
                            </div>
                        </div>
                    </div>
                      <div class="btn-div">
                          <div class="gap">
                          </div>
                          <div class="btns">
                          <button class="btn cancel-btn mt-4" @click="changePassword" :disabled="edit">Change password</button>
                          <div>
                          <button class="btn droptdown-btn mt-4" @click.prevent="submitForm()" :disabled="edit">Save</button>
                          <button class="btn cancel-btn mt-4">Cancel</button>
                          </div>
                        </div>
                       </div>
                </div>
                </div>
                </div>
</template>

<script>
import useValidate from '@vuelidate/core'
import {required,email,sameAs,minLength,numeric} from '@vuelidate/validators' 
import {reactive, computed} from 'vue'
export default {
    data(){
        return{
            edit: true
        }
    },
    setup(){
          let client= {
                email : 'stefan@gmail.com',
                name: 'Stefan',
                surname: 'Ljubovic',
                streetName: 'Mihajla Pupina',
                streetNumber: 11,
                postalCode: 24000,
                city: 'Subotica',
                country: 'Srbija',
                phone: '063103130',
                password: 'stefan123',
                confirm: 'stefan123'
            }
        let state = reactive({
            client
        })
        const rules = computed(()=>{
            return{
            email: {required,email },
            password: {required, minLength: minLength(6) },
            confirm: {required,sameAs:sameAs(state.client.password)},
            name: {required },
            surname: {required },
            streetName: {required },
            streetNumber: {required,numeric },
            postalCode: {required,numeric },
            city: {required },
            country: {required },
            phone: {required,numeric },
        }
        })
        const v$=useValidate(rules,state.client)
        return {
            state,
            v$
        }
    },
    methods:{
        submitForm(){
            this.v$.$validate()
            console.log(this.v$)
        },
        changePassword(){
            this.v$.password.$touch()
            this.v$.confirm.$touch()
            console.log(this.v$.password.$error )
            if(this.v$.confirm.$error || this.v$.password.$error){
                console.log('yey')
            }
        }
    }
}
</script>

<style scoped>
.profile-header{
    display: flex;
    flex-direction: row;
    justify-content: space-between;
}

.form-content
{
    padding: 5%;
    border: 1px solid #ced4da;
    margin-bottom: 2%;
}
.form-control{
    border-radius:1.5rem;
}
.btnSubmit
{
    border:none;
    border-radius:1.5rem;
    padding: 1%;
    width: 20%;
    cursor: pointer;
    background: #0062cc;
    color: #fff;
}
.droptdown-btn{
    width: 8vw;
    height: 60px;
    color:white;
    border-radius: 5px;
  background: #0e0f40;
  margin-right: 10px;
}
h1{
    margin-left: 4vw;
}
.cancel-btn{
    width: 8vw;
    height: 60px;
    background:white;
    border-radius: 5px;
  color: #0e0f40;
  border-color:#0e0f40 ;
}

.btn-div{
    display: flex;
}
.btns{
    flex:1.4;
    display: flex;
    justify-content: space-between;
    width: 500px;
}
.form-wrapper{
    background: white;
    border-radius: 15px;
    padding: 20px;
}
.gap{
    flex:1;
}
.icons{
    cursor: pointer;
}
.icon-div{
    margin-right: 5vw;
}
</style>