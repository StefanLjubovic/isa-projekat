<template>
  <div class="div-wrapper" @click="$emit('entity-details')">
      <div class="img-div">
          <img class="img-class" src='../assets/fish.jpg' alt="none">
      </div>
      <div class="entity-content">
          <div>
      <h4>🚗{{entity.name}}</h4>
      <h6>📍{{entity.address.streetName}} {{entity.address.streetNumber}}</h6>
      <p id="desc">📖{{entity.description}}</p>
      </div>
    <div class="grade-div"><p>{{entity.averageGrade}}<i class="fas fa-star star"></i></p></div>
    </div>
  </div>
</template>

<script>
export default {
    props:{
        entity:Object,

    },
    methods:{

    },
    emits: ['entity-details']
}
</script>

<style scoped>
.div-wrapper{
    width: 16vw;
    height: 45vh;
    border-radius: 10px;
    background: white;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    cursor: pointer;
    position:relative;
}
.img-div{
    width: 100%;
    height: 40%;
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
    position: absolute;
    bottom:5px;
    right:5px;
}
.star{
   color: #FDCC0D ;  
   margin-left:5px;
   bottom:15px;
   position: absolute;
   bottom: 17px;
   right:10px;

}
.entity-content{
    display: flex;
    justify-content: space-between;
    flex-direction: column;
}
</style>