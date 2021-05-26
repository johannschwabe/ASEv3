<template>
  <q-dialog v-model="show_table">
    <q-card>
      <q-card-section>
        <div class="text-h6">
          Alert
        </div>
      </q-card-section>

      <q-card-section class="q-pt-none">
        Lorem ipsum dolor sit amet consectetur adipisicing elit. Rerum repellendus sit voluptate voluptas eveniet porro. Rerum blanditiis perferendis totam, ea at omnis vel numquam exercitationem aut, natus minima, porro labore.
      </q-card-section>

      <q-card-actions align="right">
        <q-btn
          flat
          label="OK"
          color="primary"
          @click="closeDialog"
        />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script>
export default {
  name: "TableView",
  computed: {
    /**
     * Whether the table view is open
     */
    show_table() {
      return this.$store.getters.tableOpen;
    },
  },
  methods: {
    /**
     * Closes the Table dialog
     */
    closeDialog() {
      this.$store.commit("toggleTable");
    },
  },
};
</script>

<style scoped>
  .table-view{
    position: absolute;
    width: calc(100% - 60px);
    height: calc(100% - 110px);
    margin: 30px;
    top: 50px;
    left: 0;
    z-index: 1000;
  }
</style>
