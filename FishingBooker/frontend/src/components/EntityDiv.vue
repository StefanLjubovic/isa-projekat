<template>
  <div class="div-wrapper" @click="$emit('entity-details')">
      <div class="img-div">
          <img class="img-class" :src="entity.image" alt="none">
      </div>
      <div class="entity-content">
          <div>
      <h4>{{entity.name}}</h4>
      <h6><i class="fas fa-map-marker-alt"></i> &nbsp;{{getRestaurantInfo(entity)}}</h6>
      <p id="desc"><i class="fas fa-info-circle"></i> &nbsp;{{entity.description}}</p>
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
        getRestaurantInfo(entity){
            if(entity.address != 'undefined' && entity.address !=null) return entity.address.streetName+ ' '+  entity.address.streetNumber
        }
    },
    emits: ['entity-details']
}
</script>

<style scoped>
.div-wrapper{
    width: 21vw;
    height: 55vh;
    border-radius: 10px;
    background: white;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    cursor: pointer;
    position:relative;
}
.img-div{
    padding: 15px;
}
.img-class{
    width: 100%;
    height: 170px;
    border-radius: 10px;
}
#desc{
    padding: 10px;
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
    color: #FDCC0D ;  
}
.star{
   color: #FDCC0D ;  
   margin-left:5px;
   bottom:15px;
   position: absolute;
   bottom: 22px;
   right:10px;

}


.entity-content{
    display: flex;
    justify-content: space-between;
    flex-direction: column;
}
</style>