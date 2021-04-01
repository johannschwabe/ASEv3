<template>
  <q-card
    v-scroll
    class="propertyCard overflow-hidden"
  >
    <q-scroll-area style="height: 100%">
      <!-- Card header: Address & price -->
      <q-card-section class="bg-primary text-white no-padding">
        <img
          :src="listing_image_url"
          alt="listing image"
        >
        <div
          class="row justify-between"
          style="padding: 20px"
        >
          <div
            class="text-h6"
          >
            {{ capitalizeWords(property.ADDRESS.toLowerCase()) }}
          </div>

          <div
            class="text-h6"
          >
            ${{ property['SALE PRICE'].toLocaleString() }}
          </div>
        </div>
      </q-card-section>

      <!-- Rating title -->
      <q-card-section class="bg-grey-3">
        <div class="text-bold">
          Rating
        </div>
      </q-card-section>

      <!-- TODO generated rating -->
      <q-linear-progress
        rounded
        size="40px"
        :value=".8"
        color="light-green-5"
        class="q-mt-sm"
        style="margin: 10px; width: calc(100% - 20px)"
      >
        <div class="absolute-full flex flex-center">
          <q-badge
            color="white"
            text-color="grey-9"
            label="AA"
          />
        </div>
      </q-linear-progress>

      <div
        class="text-h5 text-grey-8"
        style="text-align: center; margin-bottom: 10px;"
      >
        Very good
      </div>

      <q-separator />

      <div style="margin: 10px 10px 40px 10px">
        <b>Price rating</b>
      </div>

      <!-- Badge for sales price -->
      <div
        style="position: relative; left: calc(30% - 30px)"
      >
        <q-badge
          color="grey-3"
          text-color="positive"
          :label="'$' + property['SALE PRICE'].toLocaleString()"
        />
      </div>

      <q-linear-progress
        rounded
        size="20px"
        :value=".3"
        color="green-5"
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
          label="$790.000"
        />
      </div>

      <q-item-label
        caption
        style="padding: 10px"
      >
        Ratings are generated from aggregate data blabla.
        <br>
        <a href="https://www.google.ch">Learn how we determine ratings</a>
        <!-- TODO -->
      </q-item-label>

      <!-- Info title -->
      <q-card-section class="bg-grey-3">
        <div class="text-bold">
          Info
        </div>
      </q-card-section>

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <b>Built</b>
        </q-card-section>
        <q-separator vertical />
        <q-card-section class="col-8">
          {{ property['YEAR BUILT'] }}
        </q-card-section>
      </q-card-section>

      <q-separator />

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <b>Zip Code</b>
        </q-card-section>
        <q-separator vertical />
        <q-card-section class="col-8">
          {{ property['ZIP CODE'] }}
        </q-card-section>
      </q-card-section>

      <q-separator />

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <b>Category</b>
        </q-card-section>
        <q-separator vertical />
        <q-card-section>
          {{ capitalizeWords(property['BUILDING CLASS CATEGORY'].toLowerCase()) }}
        </q-card-section>
      </q-card-section>

      <q-separator />

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <b>Neighbourhood</b>
        </q-card-section>
        <q-separator vertical />
        <q-card-section>
          {{ capitalizeWords(property['NEIGHBORHOOD'].toLowerCase()) }}
        </q-card-section>
      </q-card-section>

      <q-separator />

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <b>Coordinates</b>
        </q-card-section>
        <q-separator vertical />
        <q-card-section>
          {{ property.lat.toFixed(6) }}, {{ property.lng.toFixed(6) }}
        </q-card-section>
      </q-card-section>

      <!-- Pricing title -->
      <q-card-section class="bg-grey-3">
        <div class="text-bold">
          Pricing & Profitability
        </div>
      </q-card-section>

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <b>Price</b>
        </q-card-section>
        <q-separator vertical />
        <q-card-section>
          ${{ property['SALE PRICE'].toLocaleString() }}
        </q-card-section>
      </q-card-section>

      <q-separator />

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <b>Est. Price per Night</b>
        </q-card-section>
        <q-separator vertical />
        <q-card-section>
          $123 TODO
        </q-card-section>
      </q-card-section>

      <!-- Rating title -->
      <q-card-section class="bg-grey-3">
        <div class="text-bold">
          Break-even
        </div>
      </q-card-section>

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <b>At 100%</b>
        </q-card-section>
        <q-separator vertical />
        <q-card-section>
          2 years 5 months TODO
        </q-card-section>
      </q-card-section>

      <q-separator />

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <b>At 80%</b>
        </q-card-section>
        <q-separator vertical />
        <q-card-section>
          5 years 5 months TODO
        </q-card-section>
      </q-card-section>

      <q-separator />

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <b>At 60%</b>
        </q-card-section>
        <q-separator vertical />
        <q-card-section>
          8 years 5 months TODO
        </q-card-section>
      </q-card-section>

      <q-separator />

      <q-item-label
        caption
        style="padding: 10px"
      >
        The break-even rating considers the following:
        <br>
        - Est. maintenance cost of 10%
        <br>
        - The stated capacity at the estimated price per night
        <br>
        - Mortgage costs of 1.23% ($5678 p.a.)
        <!-- TODO -->
      </q-item-label>

      <q-separator />

      <!-- Action buttons TODO -->
      <q-card-actions align="center">
        <q-btn flat>
          Favorite
        </q-btn>
      </q-card-actions>
    </q-scroll-area>
  </q-card>
</template>

<script>
import { API_KEY } from "../data/API.js";
import { capitalizeWords } from "../data/helpers.js";

export default {
  name: "PropertyCard",
  props: {
    property: { type: Object, required: true },
  },
  computed: {
    /**
     * Gets the image URL for a street view image of the property
     * @returns {string}
     */
    listing_image_url() {
      const lat = this.property.lat.toFixed(6);
      const lng = this.property.lng.toFixed(6);
      return `https://maps.googleapis.com/maps/api/streetview?size=500x300&location=${lat},${lng}&fov=120&pitch=15&key=${API_KEY}`;
    },
  },

  methods: {
    capitalizeWords,
  },
};
</script>

<style scoped>
  .propertyCard{
    height: calc(100% - 90px);
    width: 500px;
    position: absolute;
    top: 0;
    right: 0;
    margin: 20px;
  }
</style>
