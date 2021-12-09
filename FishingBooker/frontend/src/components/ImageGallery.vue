<template>
  <div>
    <h2>{{ description }}</h2>
    <div class="images" v-viewer v-if="images.length > 0">
      <span class="gallery" v-for="src in images" :key="src">
          <img :src="src" v-if="images.indexOf(src) < 6">
      </span>
    </div>
    <div v-else>
      <p>There are no images to show...</p>
    </div>
  </div>
</template>

<script>

  export default ({
    props: {
        images: Array,
        description: String
    }
  })
</script>

<style scoped>
img {
    height: 140px;
    width: 219px;
    margin: 2px;
}

img:hover {
  cursor: pointer;
}

h2 {
  font-size: 20px;
  text-align: left;
  margin-bottom: 15px;
}

p {
  text-align: left;
}

</style>