<template>
  <div>
    <!-- Break-even calculator -->
    <q-card-section class="bg-grey-3">
      <div class="text-bold">
        Break-even Calculator
      </div>
    </q-card-section>

    <q-card-section>
      <!-- Price per night -->
      <q-input
        v-model.number="break_even.price"
        color="secondary"
        type="number"
        label="Price per night:"
        prefix="$"
      />

      <!-- Occupancy rate -->
      <q-item-label
        caption
        style="padding: 10px 0 24px 0"
      >
        Occupancy rate:
      </q-item-label>
      <q-slider
        v-model="break_even.occupancy_rate"
        color="secondary"
        :min="0"
        :max="1"
        :step="0.05"
        label
        label-always
        :label-value="(Math.round(break_even.occupancy_rate * 1000))/10 + '%'"
      />
      <q-input
        v-model.number="break_even.maintenance"
        color="secondary"
        type="number"
        label="Maintenance cost per sq.ft.:"
        prefix="$"
      />
      <!-- Mortgage cost -->
      <q-item-label
        caption
        style="padding: 10px 0 24px 0"
      >
        Mortgage cost:
      </q-item-label>
      <q-slider
        v-model="break_even.mortgage_rate"
        color="secondary"
        :min="0"
        :max="0.1"
        :step="0.001"
        label
        label-always
        :label-value="(Math.round(break_even.mortgage_rate * 1000))/10 + '%'"
      />
      <!-- Mortgage ratio -->
      <q-item-label
        caption
        style="padding: 10px 0 24px 0"
      >
        Mortgage ratio:
      </q-item-label>
      <q-slider
        v-model="break_even.mortgage_ratio"
        color="secondary"
        :min="0.1"
        :max="1"
        :step="0.01"
        label
        label-always
        :label-value="Math.round(break_even.mortgage_ratio * 100) + '%'"
      />

      <!-- Calculate button -->
      <q-btn
        style="width: 100%; margin-top: 10px"
        label="Calculate"
        color="secondary"
        @click="fetchCalculatedBreakEven(
          id,
          break_even.price,
          break_even.occupancy_rate,
          break_even.maintenance,
          break_even.mortgage_rate,
          break_even.mortgage_ratio
        )"
      />
    </q-card-section>

    <!-- Output section -->
    <q-card-section style="text-align: center">
      <q-item-label header>
        Predicted break-even:
      </q-item-label>
      <h5 style="padding: 0; margin: 0">
        {{ getBreakEvenString(break_even.output) }}
      </h5>
    </q-card-section>
  </div>
</template>

<script>
import axios from "axios";
import {BACKEND_URL} from "../../constants/API.js";
import { getBreakEvenString } from "../../data/helpers.js";

export default {
  name: "BreakEvenCalculator",
  props: {
    id: { type: String, required: true },
    price: { type: Number, required: true },
  },
  data() {
    return {
      break_even: {
        price: this.price,
        occupancy_rate: 0.9,
        maintenance: 2.0,
        mortgage_rate: 0.025,
        mortgage_ratio: 0.8,
        output: null, // Calculated break-even duration
      },
    };
  },
  methods: {
    getBreakEvenString,

    /**
     * Fetches the property's break-even within the calculator, using all parameters
     * @param {string} id - the property's ID
     * @param {number?} price - price per night
     * @param {number?} occupancy_rate - occupancy rate
     * @param {number?} maintenance - maintenance cost per sq. ft. per month
     * @param {number?} mortgage_rate - mortgage cost
     * @param {number?} mortgage_ratio - mortgage ratio
     */
    fetchCalculatedBreakEven(id, price = this.price_per_night, occupancy_rate = 0.9, maintenance = 2.0, mortgage_rate = 0.025, mortgage_ratio = 0.8) {
      axios({
        url: BACKEND_URL,
        method: "post",
        data: {
          query: `
            {
              calculateBreakEven(id: "${id}", price: ${price}, occupancyRate: ${occupancy_rate}, maintenance: ${maintenance}, mortgageRate: ${mortgage_rate}, mortgageRatio: ${mortgage_ratio})
            }
          `,
        },
      }).then((result) => {
        if (result.data.data.calculateBreakEven) {
          this.break_even.output = result.data.data.calculateBreakEven;
        }
      });
    },

  },
};
</script>

<style scoped>

</style>
