<template>
  <div>
    <!-- directive -->
    <div class="images" v-viewer>
      <img v-for="src in images" :key="src" :src="require('../assets/adventure/' + src)">
    </div>
  </div>
</template>

<script>

  export default ({
    props: {
        images: Array
    }
  })
</script>

<style scoped>
img {
    height: 150px;
    width: 250px;
    margin: 2px
}
</style>