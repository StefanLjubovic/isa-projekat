<template>
  <table class="table table-color container">
  <thead>
    <tr>
      <th scope="col">Cottage</th>
      <th scope="col">Date from</th>
      <th scope="col">Duration</th>
      <th scope="col">Price</th>
      <th scope="col">Complaint</th>
      <th scope="col">Revision</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th scope="row">1</th>
      <td>Mark</td>
      <td>Otto</td>
      <td>@mdo</td>
    </tr>
    <tr>
      <th scope="row">2</th>
      <td>Jacob</td>
      <td>Thornton</td>
      <td>@fat</td>
    </tr>
    <tr>
      <th scope="row">3</th>
      <td>Larry</td>
      <td>the Bird</td>
      <td>@twitter</td>
      <td><i class="fas fa-plus-square fa-2x icon"></i></td>
      <td><i class="fas fa-plus-square fa-2x icon"></i></td>
    </tr>
  </tbody>
</table>
</template>

<script>
export default {

}
</script>

<style scoped>
    .table-color{
    background: white;
    border-radius: 10px;
    }
    .icon{
        cursor: pointer;
        color: #0e0f40;

    }
</style>