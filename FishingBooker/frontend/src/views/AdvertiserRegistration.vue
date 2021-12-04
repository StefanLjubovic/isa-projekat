<template>
<div class="container">
  <div class="form-content">
      <div class="form form-wrapper">
                    <div class="row">
                        <div class="col-md md-6 mb-4 ">
                            <div class="form-group mb-4 form">
                                <input type="text" class="form-control" placeholder="Name*" v-model="form.name"/>
                                <!-- Error Message -->
                                <div class="input-errors" v-for="(error, index) of v$.form.name.$errors" :key="index">
                                    <div class="text-danger">{{ error.$message }}</div>
                                </div>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Surname*" v-model="form.surname"/>
                                <!-- Error Message -->
                                <div class="input-errors" v-for="(error, index) of v$.form.surname.$errors" :key="index">
                                    <div class="text-danger">{{ error.$message }}</div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4">
                            <div class="form-group mb-4">
                                <input type="text" class="form-control" placeholder="Street name*" ref="city" v-model="form.streetName"/>
                                <!-- Error Message -->
                                <div class="input-errors" v-for="(error, index) of v$.form.streetName.$errors" :key="index">
                                    <div class="text-danger">{{ error.$message }}</div>
                                </div>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Street number*" v-model="form.streetNumber"/>
                                <!-- Error Message -->
                                <div class="input-errors" v-for="(error, index) of v$.form.streetNumber.$errors" :key="index">
                                    <div class="text-danger">{{ error.$message }}</div>
                                </div>
                            </div>
                        </div>
                         <div class="col-md-6 mb-4">
                            <div class="form-group mb-4">
                                <input type="text" class="form-control" placeholder="E-mail*" v-model="form.email"/>
                                 <!-- Error Message -->
                                 <div class="input-errors" v-for="(error, index) of v$.form.email.$errors" :key="index">
                                    <div class="text-danger">{{ error.$message }}</div>
                                </div>
                            </div>
                            <div class="form-group">
                                <input type="password" class="form-control" placeholder="Password*" v-model="form.password"/>
                                <!-- Error Message -->
                                <div class="input-errors" v-for="(error, index) of v$.form.password.$errors" :key="index">
                                    <div class="text-danger">{{ error.$message }}</div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4">
                            <div class="form-group mb-4">
                                <input type="text" class="form-control" placeholder="City*" v-model="form.city"/>
                                 <!-- Error Message -->
                                 <div class="input-errors" v-for="(error, index) of v$.form.city.$errors" :key="index">
                                    <div class="text-danger">{{ error.$message }}</div>
                                </div>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Postal code*" v-model="form.postalCode"/>
                                <!-- Error Message -->
                                <div class="input-errors" v-for="(error, index) of v$.form.postalCode.$errors" :key="index">
                                    <div class="text-danger">{{ error.$message }}</div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group mb-4">
                                <input type="password" class="form-control"   placeholder="Confirm password*" v-model="form.confirmPassword"/>
                                <!-- Error Message -->
                                <div class="input-errors" v-for="(error, index) of v$.form.confirmPassword.$errors" :key="index">
                                    <div class="text-danger">{{ error.$message }}</div>
                                </div>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control"  placeholder="Phone number*" v-model="form.phone"/>
                                 <!-- Error Message -->
                                 <div class="input-errors" v-for="(error, index) of v$.form.phone.$errors" :key="index">
                                    <div class="text-danger">{{ error.$message }}</div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4">
                            <div class="form-group mb-4">
                                <input type="text" class="form-control" placeholder="Country*" v-model="form.country"/>
                                <!-- Error Message -->
                                 <div class="input-errors" v-for="(error, index) of v$.form.country.$errors" :key="index">
                                    <div class="text-danger">{{ error.$message }}</div>
                                </div>
                            </div>
                            <div class="form-group mb-4">
                                <select required class="form-control" v-model="form.role">
                                    <option disabled value=""> Select role*</option>
                                    <option> Cottage owner</option>
                                    <option> Ship owner</option>
                                    <option> Fishing instructor</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4">
                            <div class="form-group mb-4">
                                <textarea class="reason-area" placeholder="Reason for registration*" v-model="form.registrationReason" rows="4" cols="65"></textarea>
                                 <!-- Error Message -->
                                 <div class="input-errors" v-for="(error, index) of v$.form.registrationReason.$errors" :key="index">
                                    <div class="text-danger">{{ error.$message }}</div>
                                </div>
                            </div>                
                        </div>
                        <div class="col-md-6 mb-4">
                            <div class="form-group mb-4" v-if="form.role=='Fishing instructor'">
                                <textarea class="reason-area" placeholder="Your biography*" v-model="form.biography" rows="4" cols="65"></textarea>
                                 <!-- Error Message -->
                                 <div class="input-errors" v-for="(error, index) of v$.form.biography.$errors" :key="index">
                                    <div class="text-danger">{{ error.$message }}</div>
                                </div>
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

export function validName(name) {
  let validNamePattern = new RegExp("^[a-zA-Z]+(?:[-'\\s][a-zA-Z]+)*$");
  if (validNamePattern.test(name))
    return true;
  
  return false;
}

export default {
    setup(){
        return {v$: useValidate()}
    },
    data(){
        return{
            form : {
                email : '',
                name: '',
                surname: '',
                streetName: '',
                streetNumber: '',
                postalCode: '',
                city: '',
                country:'',
                phone: '',
                password: '',
                role: '',
                confirmPassword: '',
                registrationReason: '',
                biography: ''
            }
        }
    },

    validations() {
        return {
            form: {
                email: { required, email },
                name: { 
                    required, name_validation: {
                        $validator: validName,
                        $message: 'Invalid Name. Valid name only contain letters, dashes (-) and spaces'
                    } 
                },
                surname: { 
                    required, name_validation: {
                        $validator: validName,
                        $message: 'Invalid Name. Valid name only contain letters, dashes (-) and spaces'
                    } 
                },
                streetName: {required },
                streetNumber: {required },
                postalCode: {required, numeric },
                city: {required },
                country: {required },
                phone: {required, numeric },
                password: { required, min: minLength(6) },
                confirmPassword: {required, sameAs:sameAs(this.form.password)},
                registrationReason: {required},
                biography: {required}
            },
        }
    },
    methods:{
        submitForm(){
            this.v$.$validate()
            if(this.v$.$errors.length == 0){
                const advertiserRequest= {
                    firstName: this.form.name,
                    lastName: this.form.surname,
                    phoneNumber: this.form.phone,
                    email : this.form.email,
                    password: this.form.password,
                    role: this.form.role,
                    explanation: this.form.registrationReason,
                    biography: this.form.biography,
                    address: {
                        streetName: this.form.streetName,
                        streetNumber: this.form.streetNumber,
                        postalCode: this.form.postalCode,
                        city: this.form.city,
                        country:this.form.country
                    }
                }
            this.$swal('Success!',
            'Your request is successfully sent to administrator!',
            'success');
            this.v$.$reset();
            console.log(advertiserRequest)
            }
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