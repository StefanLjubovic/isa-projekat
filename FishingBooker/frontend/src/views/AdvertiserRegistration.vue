<template>
<div class="container">
  <div class="form-content">
      <div class="form form-wrapper">
                    <div class="row">
                        <div class="col-md md-6 mb-4 ">
                            <div class="form-group mb-4 form">
                                <input type="text" class="form-control" placeholder="Name*" v-model="state.name"/>
                                <span class="text-danger" v-if="v$.name.$error">
                                    {{v$.name.$errors[0].$message}}
                                </span>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Surname*" v-model="state.surname"/>
                                <span v-if="v$.surname.$error" class="text-danger">
                                    {{v$.surname.$errors[0].$message}}
                                </span>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4">
                            <div class="form-group mb-4">
                                <input type="text" class="form-control" placeholder="Street name*" ref="city" v-model="state.streetName"/>
                                <span v-if="v$.streetName.$error" class="text-danger">
                                    {{v$.streetName.$errors[0].$message}}
                                </span>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Street number*" v-model="state.streetNumber"/>
                                 <span v-if="v$.streetNumber.$error" class="text-danger">
                                    {{v$.streetNumber.$errors[0].$message}}
                                </span>
                            </div>
                        </div>
                         <div class="col-md-6 mb-4">
                            <div class="form-group mb-4">
                                <input type="text" class="form-control" placeholder="E-mail*" v-model="state.email"/>
                                 <span v-if="v$.email.$error" class="text-danger">
                                    {{v$.email.$errors[0].$message}}
                                </span>
                            </div>
                            <div class="form-group">
                                <input type="password" class="form-control" placeholder="Password*" v-model="state.password.password"/>
                                <span v-if="v$.password.password.$error" class="text-danger">
                                    {{v$.password.password.$errors[0].$message}}
                                </span>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4">
                            <div class="form-group mb-4">
                                <input type="text" class="form-control" placeholder="City*" v-model="state.city"/>
                                 <span v-if="v$.city.$error" class="text-danger">
                                    {{v$.city.$errors[0].$message}}
                                </span>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Postal code*" v-model="state.postalCode"/>
                                <span v-if="v$.postalCode.$error" class="text-danger">
                                    {{v$.postalCode.$errors[0].$message}}
                                </span>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group mb-4">
                                <input type="password" class="form-control"   placeholder="Confirm password*" v-model="state.password.confirm"/>
                                <span v-if="v$.password.confirm.$error" class="text-danger">
                                    {{v$.password.confirm.$errors[0].$message}}
                                </span>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control"  placeholder="Phone number*" v-model="state.phone"/>
                                 <span v-if="v$.phone.$error" class="text-danger">
                                    {{v$.phone.$errors[0].$message}}
                                </span>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4">
                            <div class="form-group mb-4">
                                <input type="text" class="form-control" placeholder="Country*" v-model="state.country"/>
                                 <span v-if="v$.country.$error" class="text-danger">
                                    {{v$.country.$errors[0].$message}}
                                </span>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4" style="display:flex">
                            <div class="form-group mb-4">
                                <textarea class="reason-area" placeholder="Reason for registration*" v-model="state.registrationReason" rows="4" cols="65"></textarea>
                                 <!--span v-if="v$.registrationReason.$error" class="text-danger">
                                    {{v$.registrationReason.$errors[0].$message}}
                                </span-->
                            </div>
                            <div class="form-group mb-4 buttons">
                                <div class="btn-div">
                                    <button class="btn droptdown-btn mt-4 save-button" @click.prevent="submitForm()">Confirm</button> 
                                    <button class="btn cancel-btn mt-4 cancel-button">Cancel</button>
                                </div>
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
    setup(){
        const state = reactive({
            email: '',
             password: {
                password: '',
                confirm: ''
            },
            name: '',
            surname: '',
            streetName: '',
            streetNumber: '',
            postalCode: '',
            city: '',
            country: '',
            phone: '',
            registrationReason: ''
        })
        const rules = computed(()=>{
            return{
            email: {required,email },
            password: {
                password: {required, minLength: minLength(6) },
                confirm: {required,sameAs:sameAs(state.password.password)}
            },
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
        const v$=useValidate(rules,state)
        return {
            state,
            v$
        }
    },
    methods:{
        submitForm(){
            this.v$.$validate()
            const client= {
                email : this.state.email,
                name: this.state.name,
                surname: this.state.surname,
                streetName: this.state.streetName,
                streetNumber: this.state.streetNumber,
                postalCode: this.state.postalCode,
                city: this.state.city,
                country:this.state.country,
                phone: this.state.phone,
                password: this.state.password.password,
                confirm: this.state.password.confirm
            }
            console.log(client)
        }
    }
}
</script>

<style scoped>
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
    height: 50px;
    color:white;
    border-radius: 5px;
  background: #0e0f40;
}
.cancel-btn{
    width: 8vw;
    height: 50px;
    background:white;
    border-radius: 5px;
  color: #0e0f40;
  margin-left: 50px;
}

.btn-div{
    display: flex;
    justify-content: flex-end;
}

.form-wrapper{
    background: white;
    border-radius: 15px;
    padding: 20px;
}

 .reason-area{
      align-self: left;
      margin-left: 5px;
      margin-top: 25px;
      background-color: #ffffff;
      border-width: 0.5px solid rgb(248, 244, 244) ;
      font-size: 18px;
      resize: none;
      outline: none;
      -webkit-border-radius: 5px;
      -moz-border-radius: 15px;
      border-radius: 15px;
    }

   .buttons{
       display: flex;
       align-items: right;
       margin-left: 250px;
       margin-top: 40px;
   } 

     .cancel-button {
      background-color: white;
      border-color: rgb(218, 214, 214);
      color: #2c3e50;
      width: 120px;
      margin-left: 10px;
    }

    .save-button {
      background-color: #2c3e50;
      color: white;
      width: 120px;
      margin-right: 10px;
  }
</style>