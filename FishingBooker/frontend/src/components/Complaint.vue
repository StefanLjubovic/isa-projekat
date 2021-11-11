<template>
  <div id="app">
  <div class="modal-overlay" @click="$emit('close-modal')"></div>
   <div class="modal-inner">
     <h1>What didnt you like?</h1>
      <textarea id="w3review" name="w3review" rows="6" cols="50" class="mt-4"/>
      <div class="form-group mt-4 button-div">
        <button type="button" class="btn log-btn p-2">Submit</button>
       <button type="button" class="btn cancel-btn p-2" @click="$emit('close-modal')">Cancel</button>
      </div>
</div>
</div>
</template>

<script>
export default {
  props: ['showModal'],
}
</script>

<style scoped>
*{
 margin: 0;
 padding: 0;
 box-sizing: border-box;
}

#app {
 position: absolute;
 
 display: flex;
 justify-content: center;
 align-items: center;
 left:0;
 top:0;
 width: 100vw;
 min-height: 100vh;
 overflow-x: hidden;
}


.modal-overlay {
 position: absolute;
 top: 0;
 left: 0;
 right: 0;
 bottom: 0;
 z-index: 98;
 background-color: rgba(0, 0, 0, 0.3);
}
.modal-inner{
  flex-direction: column;
  justify-content: space-around;
  width: 25vw;
  height: 40vh;
   background-color: #8495e8;
  z-index: 1000;
   position: fixed;
  border-radius: 16px;
 
 padding: 25px;
}
.button-div{
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-around;
}
.log-btn{
  color:white;
  background: #0e0f40;
   width: 30%;
}
.cancel-btn{
  background: white;
  color: #0e0f40;
  width: 30%;
}

textarea{
    border-radius: 5px;
    border:none;
    height: 15vh;
}

</style>
