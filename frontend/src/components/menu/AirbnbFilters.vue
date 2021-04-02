<template>
  <div>
    <!-- Price range filter TODO specific filters for airbnb/property/rating-->
    <q-item>
      <q-item-section avatar>
        <q-icon name="mdi-cash" />
      </q-item-section>
      <q-item-section>
        <q-item-label>Price range</q-item-label>
        <q-item-label caption>
          Set the range of price per night to show
        </q-item-label>
      </q-item-section>
    </q-item>
    <div style="padding: 10px 15px 0 15px">
      <q-range
        v-model="price_filter"
        :min="price_filter_limits.min"
        :max="price_filter_limits.max"
        :left-label-value="'$' + price_filter.min"
        :right-label-value="price_filter_max_label"
        label-always
      />
    </div>

    <q-separator />
  </div>
</template>

<script>
export default {
  name: "AirbnbFilters",

  data() {
    return {
      // Upper/lower bounds for price filter
      price_filter_limits: {
        min: 0,
        max: 500,
      },

      price_filter: { // TODO store?
        min: 0,
        max: 500,
      },
    };
  },
  computed: {
    price_filter_max_label() {
      return this.price_filter.max === this.price_filter_limits.max ? "∞" : `$${this.price_filter.max.toString()}`;
    },
  },
};
</script>

<style scoped>

</style>
