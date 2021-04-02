<template>
  <div>
    <!-- Price range filter TODO specific filters for airbnb/property/rating-->
    <q-item>
      <q-item-section avatar>
        <q-icon name="mdi-home" />
      </q-item-section>
      <q-item-section>
        <q-item-label>Apartment type</q-item-label>
        <q-item-label caption>
          The type of rental property
        </q-item-label>
      </q-item-section>
    </q-item>
    <div
      style="padding-bottom: 10px"
      class="full-width flex flex-center"
    >
      <q-btn-dropdown
        style="width: 150px"
        color="grey-3"
        text-color="text"
        no-caps
        :label="apartment_type_filter"
      >
        <q-list>
          <q-item
            v-for="type in apartment_types"
            :key="type"
            v-close-popup
            clickable
            @click="onApartmentTypeChange(type)"
          >
            <q-item-section>
              <q-item-label>{{ type }}</q-item-label>
            </q-item-section>
          </q-item>
        </q-list>
      </q-btn-dropdown>
    </div>

    <q-separator />

    <!-- Price range -->
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

    <!-- Availability range -->
    <q-item>
      <q-item-section avatar>
        <q-icon name="mdi-calendar" />
      </q-item-section>
      <q-item-section>
        <q-item-label>Availability</q-item-label>
        <q-item-label caption>
          Show only Airbnbs that are booked for a certain number of days
        </q-item-label>
      </q-item-section>
    </q-item>
    <div style="padding: 10px 15px 0 15px">
      <q-range
        v-model="availability_filter"
        :min="0"
        :max="365"
        :left-label-value="availability_filter.min"
        :right-label-value="availability_filter.max"
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

      price_filter: { // TODO store
        min: 0,
        max: 500,
      },

      availability_filter: { // TODO store
        min: 0,
        max: 365,
      },

      apartment_types: [
        "Any",
        "Entire home/apt",
        "Private room",
        "Shared room",
      ],

      // TODO store
      apartment_type_filter: "Any",
    };
  },
  computed: {
    price_filter_max_label() {
      return this.price_filter.max === this.price_filter_limits.max ? "∞" : `$${this.price_filter.max.toString()}`;
    },
  },
  methods: {
    /**
     * TODO
     * @param {String} type
     */
    onApartmentTypeChange(type) {
      this.apartment_type_filter = type;
    },
  },
};
</script>

<style scoped>

</style>
