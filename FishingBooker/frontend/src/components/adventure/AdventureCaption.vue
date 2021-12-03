<template>
    <div class="container">
        <div class="caption">
            <h1>{{adventureName}}</h1>
        </div>
        <div class="options" v-if="role == 0 || role==5">
            <button class="btn" @click="toggleSubscribe" v-if="!subscribed">Subscribe to this adventure&nbsp;&ensp;<i class="fas fa-bell"></i> </button>
            <button class="btn" @click="toggleSubscribe" v-else>Unsubscribe from this adventure&nbsp;&ensp;<i class="fas fa-bell-slash"></i> </button>
        </div>

        <div class="options" v-if="role != 0 && role!=5">
            <button class="btn" >Create sale&nbsp;&ensp;<i class="fas fa-bell"></i> </button>
            <button class="btn"><i class="fas fa-solid fa-pen"></i> </button>
            <button class="btn" @click="deleteEntity()"><i class="fas fa-solid fa-trash"></i></button>
        </div>
    </div>

    <!--Delete entity modal -->

    <div class="modal fade" id="delete-modal">
        <div class="modal-dialog rounded">
            <div class="modal-header">
            <button class="btn btn-close close" data-dismiss="modal"><i class="fas fa-times"></i></button>
            </div>
            <div class="modal-content">
                <p>Are you sure you want to delete '{{adventureName}}'? </p>
                <div class="btn-div">
                     <button class="btn save-button" @click.prevent="submitForm()">Confirm</button> 
                     <button class="btn cancel-button">Cancel</button>
                </div>
            </div>
        </div>
    </div>
    
</template>

<script>

export default ({
    props: {
        adventureName: String,
    },
    data() {
        return {
            subscribed: false,
        }
    },
    methods: {
        toggleSubscribe: function() {
            this.subscribed = !this.subscribed;
        },
        deleteEntity : function() {
        window.$('#delete-modal').modal('show');
      },
    }
})
</script>

<style scoped>

.container {
    display: flex;
    justify-content: space-between;
}

.btn {
    background-color: #2c3e50;
    color: white;
    margin-top: 10px;
    margin-right: 10px;
}

.modal-dialog {
      background-color: #ffffff;
      margin-top: 10%;
}

.modal-content {
        padding: 30px;
        font-size: 20px;
        background-color: rgb(211, 222, 223);
}

.btn-close {
      background-color: transparent;
      border-color: transparent;
      color: transparent;
      margin-right: 12px;
}

  .cancel-button {
      background-color: white;
      border-color: rgb(218, 214, 214);
      color: #2c3e50;
      width: 120px;
      margin-left: 10px;
    }

    .save-button {
      background-color: #5b78da;
      color: white;
      width: 120px;
      margin-right: 10px;
     }

</style>
