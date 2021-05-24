<template>
  <div>
    <q-card-section class="bg-grey-3">
      <div class="text-bold">
        Overall Rating
      </div>
    </q-card-section>

    <!-- Rating slider-->
    <q-linear-progress
      v-if="rating > 0 && rating <= 10"
      rounded
      size="40px"
      :value="rating_slider_position"
      :color="rating_slider_color"
      class="q-mt-sm"
      style="margin: 10px; width: calc(100% - 20px)"
    >
      <div class="absolute-full flex flex-center">
        <q-badge
          color="white"
          text-color="grey-9"
          :label="rating_object.shortname"
        />
      </div>
    </q-linear-progress>

    <div
      class="text-h5 text-grey-8"
      style="text-align: center; margin: 10px 0"
    >
      {{ rating_object.name }}
    </div>
  </div>
</template>

<script>
import {SLIDER_COLORS} from "../../constants/COLORS.js";
import {RATINGS} from "../../constants/RATINGS.js";

export default {
  name: "OverallRating",
  props: {
    rating: { type: Number, required: true},
  },
  computed: {
    /**
     * The position of the slider showing the overall rating
     */
    rating_slider_position() {
      return this.rating / 10;
    },

    /**
     * The color of the slider showing the rating
     */
    rating_slider_color() {
      // Color distribution in intervals
      const ratio = Math.min(Math.max(Math.round(this.rating_slider_position * 10) - 1, 0), 9);
      return SLIDER_COLORS[9 - ratio];
    },

    /**
     * The rating object, containing number, shortname and description
     */
    rating_object() {
      return RATINGS[this.rating];
    },
  },
};
</script>

<style scoped>

</style>
