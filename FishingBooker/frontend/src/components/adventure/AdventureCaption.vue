<template>
    <div class="container">
        <div class="caption">
            <h1>{{adventureName}}</h1>
        </div>
        <div class="options" v-if="userRole == 'ROLE_CLIENT'">
            <button class="btn" @click="toggleSubscribe" v-if="!subscribed">Subscribe to this {{this.entityName}}&nbsp;&ensp;<i class="fas fa-bell"></i> </button>
            <button class="btn" @click="toggleSubscribe" v-else>Unsubscribe from this {{this.entityName}}&nbsp;&ensp;<i class="fas fa-bell-slash"></i> </button>
        </div>

        <div class="options" v-if="userRole != 'ROLE_CLIENT' && userRole!=''">
            <button class="btn" @click="this.$emit('create-sale')" v-if="userRole != 'ROLE_ADMIN'">Create sale&nbsp;&ensp;<i class="fas fa-bell"></i> </button>
            <button class="btn" @click="this.$emit('edit-entity')" v-if="userRole != 'ROLE_ADMIN'"><i class="fas fa-solid fa-pen"></i> </button>
            <button class="btn" @click="deleteEntity()"><i class="fas fa-solid fa-trash"></i></button>
        </div>
    </div>
    
</template>

<script>
import axios from "axios";
import server from "../../server";


export default ({
    props: {
        adventureName: String,
        adventureId: Number,
        entityName: String
    },
    data() {
        return {
            subscribed: false,
            name: this.adventureName,
            advID: this.adventureId
        }
    },
    computed:{
        userRole(){
            return this.$store.getters.getRole;
        }
    },
    methods: {
        toggleSubscribe: function() {
            this.subscribed = !this.subscribed;
        },
        deleteEntity : function() {
            this.$swal({
                title: `Are you sure you want to delete adventure '${this.name}'?`,
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Yes',
                cancelButtonText: 'Cancel',
                confirmButtonColor: '#2c3e50'
            }).then((result) => {
                if(result.isConfirmed) {
                    console.log(this.advID)
                    axios.delete(`${server.baseUrl}/entity/delete/${this.advID}`)
                    .then(() => {
                        this.$swal({
                            position: 'top-end',
                            icon: 'success',
                            title: 'Adventure deleted!',
                            showConfirmButton: false,
                            timer: 2000
                        })
                        .then(() => this.$emit('entity-deleted'))
                    })
                } 
            })
        }
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
