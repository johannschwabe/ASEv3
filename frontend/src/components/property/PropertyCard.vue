<template>
  <q-card
    v-scroll
    class="propertyCard overflow-hidden"
  >
    <div style="position: absolute; top: 0; right: 0; z-index: 1">
      <q-btn
        flat
        color="white"
        icon="mdi-close"
        @click="onHide"
      />
    </div>
    <q-scroll-area style="height: 100%">
      <!-- Card header: Address & price -->
      <q-card-section class="bg-secondary text-white no-padding">
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
            <q-spinner-dots v-if="loading" />
            <div v-else>
              {{ property ? capitalizeWords(property.address.toLowerCase()) : "" }}
            </div>
          </div>

          <div
            class="text-h6"
          >
            <q-spinner-dots v-if="loading" />
            <div v-else>
              ${{ property ? property.salePrice.toLocaleString() : "" }}
            </div>
          </div>
        </div>
      </q-card-section>

      <!-- Overall rating -->
      <overall-rating
        :rating="rating"
      />

      <q-separator />

      <!-- Price rating -->
      <price-rating
        :estimated_price="estimated_price"
        :sale_price="property ? property.salePrice : 0"
        :loading="loading"
      />

      <q-separator />

      <!-- Neighbourhood rating -->
      <neighbourhood-rating
        :rating="neighbourhood_rating"
      />

      <q-separator />

      <!-- Break Even -->
      <q-card-section class="bg-grey-3">
        <div class="text-bold">
          Break-even
        </div>
      </q-card-section>

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <strong>At 100%</strong>
        </q-card-section>
        <q-separator vertical />

        <q-card-section v-if="loading">
          <q-spinner-dots />
        </q-card-section>
        <q-card-section v-else>
          {{ getBreakEvenString(break_even_100) }}
        </q-card-section>
      </q-card-section>

      <q-separator />

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <strong>At 80%</strong>
        </q-card-section>
        <q-separator vertical />

        <q-card-section v-if="loading">
          <q-spinner-dots />
        </q-card-section>
        <q-card-section v-else>
          {{ getBreakEvenString(break_even_80) }}
        </q-card-section>
      </q-card-section>

      <q-separator />

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <strong>At 60%</strong>
        </q-card-section>
        <q-separator vertical />

        <q-card-section v-if="loading">
          <q-spinner-dots />
        </q-card-section>
        <q-card-section v-else>
          {{ getBreakEvenString(break_even_60) }}
        </q-card-section>
      </q-card-section>

      <q-separator />

      <q-item-label
        caption
        style="padding: 10px"
      >
        The break-even duration considers the following:
        <br>
        - Est. maintenance cost of $2 per sq.ft. per month
        <br>
        - The stated occupancy rate at the estimated price per night
        <br>
        - Mortgage costs of 3% at 75% of sale price as mortgage
        <br>
        <br>
        If no break-even duration is given, break-even is not achievable at that occupancy rate, or data is missing.
      </q-item-label>

      <!-- Info title -->
      <q-card-section class="bg-grey-3">
        <div class="text-bold">
          Info
        </div>
      </q-card-section>

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <strong>Built</strong>
        </q-card-section>
        <q-separator vertical />
        <q-card-section class="col-8">
          <q-spinner-dots v-if="loading" />
          <div v-else>
            {{ property ? property.yearBuilt : "" }}
          </div>
        </q-card-section>
      </q-card-section>

      <q-separator />

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <strong>Zip Code</strong>
        </q-card-section>
        <q-separator vertical />
        <q-card-section class="col-8">
          <q-spinner-dots v-if="loading" />
          <div v-else>
            {{ property ? property.zipCode : "" }}
          </div>
        </q-card-section>
      </q-card-section>

      <q-separator />

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <strong>Category</strong>
        </q-card-section>
        <q-separator vertical />
        <q-card-section>
          <q-spinner-dots v-if="loading" />
          <div v-else>
            {{ property ? capitalizeWords(property.buildingClassCategory.toLowerCase()) : "" }}
          </div>
        </q-card-section>
      </q-card-section>

      <q-separator />

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <strong>Neighbourhood</strong>
        </q-card-section>
        <q-separator vertical />
        <q-card-section>
          <q-spinner-dots v-if="loading" />
          <div v-else>
            {{ property ? capitalizeWords(property.neighbourhood.toLowerCase()) : "" }}
          </div>
        </q-card-section>
      </q-card-section>

      <q-separator />

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <strong>Coordinates</strong>
        </q-card-section>
        <q-separator vertical />
        <q-card-section>
          <q-spinner-dots v-if="loading" />
          <div v-else>
            {{ property ? `${property.latitude.toFixed(6)}, ${property.longitude.toFixed(6)}` : "" }}
          </div>
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
          <strong>Price</strong>
        </q-card-section>
        <q-separator vertical />
        <q-card-section>
          <q-spinner-dots v-if="loading" />
          <div v-else>
            {{ property ? `$${property.salePrice.toLocaleString()}` : "" }}
          </div>
        </q-card-section>
      </q-card-section>

      <q-separator />

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <strong>Est. Price per Night</strong>
        </q-card-section>
        <q-separator vertical />

        <q-card-section v-if="loading">
          <q-spinner-dots />
        </q-card-section>
        <q-card-section v-else>
          {{ price_per_night !== null ? "$" + Math.round(price_per_night) : "N/A" }}
        </q-card-section>
      </q-card-section>

      <q-separator />

      <break-even-calculator
        v-if="property && price_per_night"
        :id="property.id"
        :price="price_per_night"
      />
    </q-scroll-area>
  </q-card>
</template>

<script>
import axios from "axios";
import OverallRating from "./OverallRating.vue";
import PriceRating from "./PriceRating.vue";
import BreakEvenCalculator from "./BreakEvenCalculator.vue";
import NeighbourhoodRating from "./NeighbourhoodRating.vue";
import {API_KEY, BACKEND_URL} from "../../constants/API.js";
import { capitalizeWords, getBreakEvenString } from "../../data/helpers.js";

export default {
  name: "PropertyCard",
  components: {
    PriceRating,
    OverallRating,
    NeighbourhoodRating,
    BreakEvenCalculator,
  },
  props: {
    coordinates: { type: String, required: true },
  },
  data() {
    return {
      estimated_price: 1, // Must be set to 1 initially to not break calculations
      rating: 0,
      neighbourhood_rating: 0,
      property: null,
      loading: true,
      break_even_100: null,
      break_even_80: null,
      break_even_60: null,
      price_per_night: null,
    };
  },
  computed: {
    /**
     * Gets the image URL for a street view image of the property
     * @returns {string|null}
     */
    listing_image_url() {
      if (this.property) {
        const lat = this.property.latitude.toFixed(6);
        const lng = this.property.longitude.toFixed(6);
        return `https://maps.googleapis.com/maps/api/streetview?size=500x300&location=${lat},${lng}&fov=120&pitch=15&source=outdoor&key=${API_KEY}`;
      }
      return null;
    },
  },
  watch: {
    // Upon property change, re-fetch all values
    coordinates() {
      this.rating = 0; // Reset rating so the previous property's rating isn't shown
      this.fetchProperty().then(() => {
        this.fetchEstimatedPrice(this.property.id);
        this.fetchBreakEven(this.property.id);
        this.fetchRating(this.property.id);
        this.fetchNeighbourhoodRating(this.property.neighbourhood);
        this.fetchPricePerNight();
      });
    },
  },
  created() {
    this.fetchProperty().then(() => {
      this.fetchEstimatedPrice(this.property.id);
      this.fetchBreakEven(this.property.id);
      this.fetchRating(this.property.id);
      this.fetchNeighbourhoodRating(this.property.neighbourhood);
      this.fetchPricePerNight();
    });
  },
  methods: {
    getBreakEvenString,
    capitalizeWords,

    onHide() {
      this.$emit("hide");
    },

    /**
     * Fetches the property's data
     */
    async fetchProperty() {
      this.loading = true;
      const result = await axios({
        url: BACKEND_URL,
        method: "post",
        data: {
          query: `
            {
              saleByCoordinatesId(id: "${this.coordinates}") {
                id
                idSale
                salePrice
                latitude
                longitude
                address
                yearBuilt
                zipCode
                buildingClassCategory
                neighbourhood
              }
            }
          `,
        },
      });

      this.property = result.data.data.saleByCoordinatesId;
    },

    /**
     * Fetches the property's estimated price
     * @param {string} id - the property's ID
     */
    fetchEstimatedPrice(id) {
      this.loading = true;
      axios({
        url: BACKEND_URL,
        method: "post",
        data: {
          query: `
            {
              estimatedSalePriceById(id: "${id}")
            }
          `,
        },
      }).then((result) => {
        this.estimated_price = result.data.data.estimatedSalePriceById;
      }).finally(() => {
        this.loading = false;
      });
    },

    /**
     * Fetches the property's break-even
     * @param {string} id - the property's ID
     */
    fetchBreakEven(id) {
      this.loading = true;
      // At 100%
      axios({
        url: BACKEND_URL,
        method: "post",
        data: {
          query: `
            {
              calculateBreakEven(id: "${id}", occupancyRate: 1.0)
            }
          `,
        },
      }).then((result) => {
        if (result.data.data.calculateBreakEven) {
          this.break_even_100 = result.data.data.calculateBreakEven;
        }
      });

      // At 80%
      axios({
        url: BACKEND_URL,
        method: "post",
        data: {
          query: `
            {
              calculateBreakEven(id: "${id}", occupancyRate: 0.8)
            }
          `,
        },
      }).then((result) => {
        if (result.data.data.calculateBreakEven) {
          this.break_even_80 = result.data.data.calculateBreakEven;
        }
      });

      // At 60%
      axios({
        url: BACKEND_URL,
        method: "post",
        data: {
          query: `
            {
              calculateBreakEven(id: "${id}", occupancyRate: 0.6)
            }
          `,
        },
      }).then((result) => {
        if (result.data.data.calculateBreakEven) {
          this.break_even_60 = result.data.data.calculateBreakEven;
        }
      });
    },

    /**
     * Fetches the property's computed rating
     * @param {string} id - the property's ID
     */
    fetchRating(id) {
      axios({
        url: BACKEND_URL,
        method: "post",
        data: {
          query: `
            {
              saleById(id: "${id}"){
                score
              }
            }
          `,
        },
      }).then((result) => {
        // Get rating and apply to 1-10 range
        if (result.data.data.saleById.score > 0) {
          this.rating = Math.max(1, Math.round(result.data.data.saleById.score));
        } else {
          // Invalid rating
          this.rating = 11;
        }
      });
    },

    /**
     * Fetches the property's neighbourhood rating
     * @param {string} neighbourhood - the neighbourhood's name
     */
    fetchNeighbourhoodRating(neighbourhood) {
      axios({
        url: BACKEND_URL,
        method: "post",
        data: {
          query: `
            {
              neighbourhoodRating(neighbourhood: "${neighbourhood}")
            }
          `,
        },
      }).then((result) => {
        // Get rating and apply to 1-10 range
        if (result.data.data.neighbourhoodRating > 0) {
          this.neighbourhood_rating = Math.max(1, Math.round(result.data.data.neighbourhoodRating));
        } else {
          // Invalid rating
          this.neighbourhood_rating = 11;
        }
      });
    },

    /**
     * Fetches the property's computed rating
     */
    fetchPricePerNight() {
      axios({
        url: BACKEND_URL,
        method: "post",
        data: {
          query: `
            {
              estimatedPriceByNeighbourhood(neighbourhood: "${this.property.neighbourhood}")
            }
          `,
        },
      }).then((result) => {
        this.price_per_night = result.data.data.estimatedPriceByNeighbourhood;
      });
    },
  },
};
</script>

<style scoped>
  .propertyCard{
    height: calc(100% - 40px);
    width: 500px;
    position: absolute;
    top: 0;
    right: 0;
    margin: 20px;
  }
</style>
