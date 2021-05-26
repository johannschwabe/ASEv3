<template>
  <q-dialog :value="show_table">
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

      <q-card-section
        v-if="loading"
        class="flex flex-center vertical-middle"
        style="height: calc(100% - 100px)"
      >
        <div class="column items-center">
          <q-circular-progress
            indeterminate
            size="100px"
            color="secondary"
          />
          <q-item-label
            caption
            style="margin-top: 20px"
          >
            Loading all scores... (this may take a while)
          </q-item-label>
        </div>
      </q-card-section>
      <q-card-section v-else>
        <q-table
          virtual-scroll
          :pagination="{rowsPerPage: 15}"
          :data="scores"
          :columns="columns"
          @row-click="onSelect"
        />
      </q-card-section>
    </q-card>
  </q-dialog>
</template>

<script>
import axios from "axios";
import {BACKEND_URL} from "../../constants/API.js";
import { capitalizeWords } from "../../data/helpers.js";

export default {
  name: "TableDialog",
  data() {
    return {
      columns: [
        {
          name: "score",
          required: true,
          label: "Score",
          align: "left",
          field: (row) => row.score,
          format: (val) => `${val}`,
          sortable: true,
          classes: "bg-grey-2 text-bold",
          headerClasses: "bg-secondary text-white",
        },
        {
          name: "address",
          required: true,
          label: "Address",
          align: "left",
          field: (row) => capitalizeWords(row.address),
          format: (val) => `${val}`,
          sortable: false,
        },
        {
          name: "neighbourhood",
          required: false,
          label: "Neighbourhood",
          align: "left",
          field: (row) => capitalizeWords(row.neighbourhood),
          format: (val) => `${val}`,
          sortable: false,
        },
        {
          name: "price",
          required: true,
          label: "Price",
          align: "left",
          field: (row) => row.price,
          format: (val) => `$${val.toLocaleString()}`,
          sortable: true,
        },
      ],
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
     * Upon selecting a row, close dialog and select the chosen property
     */
    onSelect(row) {
      console.log(row, "selected");
      // TODO
    },

    /**
     * Fetches all property scores and their info
     */
    async fetchAllScores() {
      this.loading = true;

      // Wait 5s
      await new Promise((resolve) => setTimeout(resolve, 5000));
      console.log("Fetch scores!");

      axios({
        url: BACKEND_URL,
        method: "post",
        data: {
          query: `
            {
              allScores{
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
        if (result.data.data.allScores && result.data.data.allScores.length > 0) {
          console.log("Save", result.data.data.allScores.length, "entries!");
          this.scores = result.data.data.allScores;
          this.loading = false;
        } else {
          // Re-fetch if no data available yet
          console.log("Re-fetch!");
          this.fetchAllScores();
        }
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
