<template>
  <div class="div-wrapper" @click="$emit('adventure-details')">
      <div class="img-div">
          <img class="img-class" src='../assets/fish.jpg' alt="none">
      </div>
      <h4>🚗{{adventure.name}}</h4>
      <h6>📍{{adventure.address}}</h6>
      <p id="desc">📖{{adventure.desciption}}</p>
    <div class="grade-div"><p>4.5<i class="fas fa-star star"></i></p></div>
  </div>
</template>

<script>
export default {
    props:{
        adventure:Object,

    },
    methods:{

    },
    emits: ['adventure-details']
}
</script>

<style>
.div-wrapper{
    width: 300px;
    height: 50vh;
    border-radius: 10px;
    background: white;
    display: flex;
    flex-direction: column;
    cursor: pointer;
}
.img-div{
    width: 100%;
    height: 50%;
    padding: 15px;
}
.img-class{
    width: 100%;
    height: auto;
    border-radius: 10px;
}
#desc{
    display: flex;
    flex-wrap: wrap;
    flex-direction: row;
    color: #5a5a73;
    font-weight: 400;
    word-wrap:break-word;
}
.grade-div{
    display: flex;
    flex-direction: row;
    justify-content: flex-end;
    align-items: flex-end;
    align-items: flex-end;
    padding-right: 40px;
    font-size: 25px;
    position: relative;
}
.star{
   color: #ffff0f ;  
   margin-left:5px;
   bottom:15px;
   position: absolute;
   bottom: 17px;
   right:10px;

}
</style>