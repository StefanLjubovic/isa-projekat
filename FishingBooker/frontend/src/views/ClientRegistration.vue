<template>
  <div class="form-content">
      <div class="form">
                    <div class="row">
                        <div class="col-md md-6 mb-4 ">
                            <div class="form-group mb-4 form">
                                <input type="text" class="form-control" placeholder="Email *" ref="email" value=""/>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Name *" ref="name" value=""/>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4">
                            <div class="form-group mb-4">
                                <input type="text" class="form-control" placeholder="City *" ref="city" value=""/>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Country *" ref ="country" value=""/>
                            </div>
                        </div>
                         <div class="col-md-6 mb-4">
                            <div class="form-group mb-4">
                                <input type="text" class="form-control" placeholder="Surname *" ref="surname" value=""/>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Street name *" ref="street" value=""/>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4">
                            <div class="form-group mb-4">
                                <input type="text" class="form-control" placeholder="Street number *" ref="number" value=""/>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Phone number *" ref="phone " value=""/>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group mb-4">
                                <input type="text" class="form-control"   placeholder="Street number *" ref="number" value=""/>
                            </div>
                            <div class="form-group">
                                <input type="text" class="form-control"  placeholder="Postal code *" ref="postal-code" value=""/>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4">
                            <div class="form-group mb-4">
                                <input type="text" class="form-control" placeholder="Confirm Password *" ref="repeat-password" value=""/>
                            </div>
                        </div>
                    </div>
                      <div class="btn-div"><button class="btn droptdown-btn mt-4" @click.prevent="getFormValues()">Save</button> <button class="btn cancel-btn mt-4">Cancel</button></div>
                </div>
                </div>
</template>

<script>
export default {
    methods:{
        getFormValues(){
            console.log('aaaa')
            console.log( this.$refs.name.value)
        }
    }
}
</script>

<style scoped>
.note
{
    text-align: center;
    height: 80px;
    background: -webkit-linear-gradient(left, #0072ff, #8811c5);
    color: #fff;
    font-weight: bold;
    line-height: 80px;
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
</style>