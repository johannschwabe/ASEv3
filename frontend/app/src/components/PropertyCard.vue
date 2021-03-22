<template>
  <q-card class="propertyCard">
    <div class="row">
      <q-avatar>
        AB
      </q-avatar>
      <p>
        Firstname Lastname
      </p>
    </div>

    <q-card-section>

    </q-card-section>
  </q-card>
</template>

<script>
export default {
  name: "PropertyCard"
}
</script>

<style scoped>
  .propertyCard{
    height: calc(100% - 90px);
    width: 200px;
    position: absolute;
    top: 0;
    right: 0;
    margin: 20px
  }
</style>
