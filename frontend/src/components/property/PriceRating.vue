<template>
  <div>
    <q-card-section class="bg-grey-3">
      <div class="text-bold">
        Price Rating
      </div>
    </q-card-section>
    <!-- Badge for sales price -->
    <div
      :style="'max-width: 150px; position: relative; left: calc('+ price_badge_offset + '% - 40px)'"
    >
      <q-badge
        v-if="sale_price"
        color="grey-3"
        :text-color="loading ? 'black' : sale_price < estimated_price ? 'positive' : 'negative'"
        :label="loading ? '...' : '$' + sale_price.toLocaleString()"
      />
    </div>

    <q-linear-progress
      rounded
      size="20px"
      :value="loading? .5 : price_slider_position"
      :color="loading? 'transparent' :price_slider_color"
      class="q-mt-sm"
      style="margin: 10px; width: calc(100% - 20px)"
    />

    <!-- Overlay for market price -->
    <div
      class="bg-grey-8"
      style="height: 30px; width: 8px; border-radius: 4px; position: relative; top: -35px; left: calc(50% - 4px)"
    />
    <div
      class="full-width flex flex-center"
      style="position: relative; top: -20px"
    >
      <q-badge
        color="grey-3"
        text-color="grey-9"
        :label="loading ? '...' : '$' + estimated_price.toLocaleString()"
      />
    </div>

    <q-item-label
      caption
      style="padding: 10px; text-align: justify"
    >
      The price rating above compares the actual sales price to the estimated market price.
      <br>
      <br>
      Ratings are generated from aggregate data of past sales of comparable properties, as well as prices of nearby Airbnbs.
    </q-item-label>
  </div>
</template>

<script>
import {SLIDER_COLORS} from "../../constants/COLORS.js";

export default {
  name: "PriceRating",
  props: {
    loading: { type: Boolean, required: true},
    estimated_price: { type: Number, required: true},
    sale_price: { type: Number, required: true},
  },
  computed: {
    /**
     * The position of the slider comparing expected and actual price
     */
    price_slider_position() {
      if (this.sale_price > 0) {
        const ratio = this.sale_price / this.estimated_price;
        // Due to the middle of the slider meaning sales price equals expected, we divide by two
        return ratio / 2;
      }
      return 0;
    },

    /**
     * The color of the slider comparing expected and actual price
     */
    price_slider_color() {
      // Color distribution in intervals
      const ratio = Math.min(Math.max(Math.round(this.price_slider_position * 10) - 1, 0), 9);
      return SLIDER_COLORS[ratio];
    },

    /**
     * Offset of the actual price badge in %
     */
    price_badge_offset() {
      if (this.loading) { return 0; }
      const result = Math.round(this.price_slider_position * 100);
      return Math.min(Math.max(result, 10), 90);
    },
  },
};
</script>

<style scoped>

</style>
