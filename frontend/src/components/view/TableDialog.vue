<template>
  <q-dialog v-model="show_table">
    <q-card class="table-view">
      <q-card-section class="row justify-between">
        <div class="text-h6">
          Rating Overview
        </div>
        <q-btn
          flat
          round
          color="black"
          icon="mdi-close"
          @click="closeDialog"
        />
      </q-card-section>

      <q-card-section class="q-pt-none">
        <q-table />
      </q-card-section>
    </q-card>
  </q-dialog>
</template>

<script>
import axios from "axios";
import {BACKEND_URL} from "../../constants/API.js";

export default {
  name: "TableDialog",
  data() {
    return {
      scores: null,
      loading: false,
    };
  },

  computed: {
    /**
     * Whether the table dialog is open
     */
    show_table() {
      return this.$store.getters.tableOpen;
    },
  },
  mounted() {
    this.fetchAllScores();
  },

  methods: {
    /**
     * Closes the Table dialog
     */
    closeDialog() {
      this.$store.commit("toggleTable");
    },

    /**
     * Fetches all property scores and their info
     */
    fetchAllScores() {
      this.loading = true;
      axios({
        url: BACKEND_URL,
        method: "post",
        data: {
          query: `
            {
              allScores(){
                id
                score
                price
                address
                neighbourhood
              }
            }
          `,
        },
      }).then((result) => {
        console.log("Got data:", result.data.data);
        if (result.data.data.allScores) {
          this.scores = result.data.data.allScores;
        }
      }).finally(() => {
        this.loading = false;
      });
    },
  },
};
</script>

<style scoped>
  .table-view{
    width: 100%;
    max-width: 100%;
    height: calc(100% - 50px);
    margin-top: 50px
  }
</style>
