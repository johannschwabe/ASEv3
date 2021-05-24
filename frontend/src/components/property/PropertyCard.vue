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

      <q-separator />

      <div style="margin: 10px 10px 20px 10px">
        <strong>Price rating</strong>
      </div>

      <!-- Badge for sales price -->
      <div
        :style="'position: relative; left: calc('+ price_badge_offset + '% - 40px)'"
      >
        <q-badge
          v-if="property"
          color="grey-3"
          :text-color="loading ? 'black' : property.salePrice < estimated_price ? 'positive' : 'negative'"
          :label="loading ? '...' : '$' + property.salePrice.toLocaleString()"
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

      <q-separator />

      <!-- Break Even -->
      <div style="margin: 10px">
        <strong>Break-even</strong>
      </div>
      <q-separator />

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <strong>At 100%</strong>
        </q-card-section>
        <q-separator vertical />
        <q-card-section v-if="has_break_even">
          {{ getBreakEvenString(break_even_100) }}
        </q-card-section>
        <q-card-section v-else>
          <q-spinner-dots v-if="loading" />
        </q-card-section>
      </q-card-section>

      <q-separator />

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <strong>At 80%</strong>
        </q-card-section>
        <q-separator vertical />
        <q-card-section v-if="has_break_even">
          {{ getBreakEvenString(break_even_80) }}
        </q-card-section>
        <q-card-section v-else>
          <q-spinner-dots v-if="loading" />
        </q-card-section>
      </q-card-section>

      <q-separator />

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <strong>At 60%</strong>
        </q-card-section>
        <q-separator vertical />
        <q-card-section v-if="has_break_even">
          {{ getBreakEvenString(break_even_60) }}
        </q-card-section>
        <q-card-section v-else>
          <q-spinner-dots v-if="loading" />
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
        If no break-even duration is given, break-even is not achievable at that occupancy rate.
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
        <q-card-section v-if="price_per_night !== null">
          ${{ Math.round(price_per_night) }}
        </q-card-section>
        <q-card-section v-else>
          <q-spinner-dots v-if="loading" />
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
import BreakEvenCalculator from "./BreakEvenCalculator.vue";
import { SLIDER_COLORS } from "../../constants/COLORS.js";
import { RATINGS } from "../../constants/RATINGS.js";
import {API_KEY, BACKEND_URL} from "../../constants/API.js";
import { capitalizeWords, getBreakEvenString } from "../../data/helpers.js";

export default {
  name: "PropertyCard",
  components: { BreakEvenCalculator },
  props: {
    coordinates: { type: String, required: true },
  },
  data() {
    return {
      estimated_price: 1, // Must be set to 1 initially to not break calculations
      rating: 0,
      property: null,
      loading: true,
      has_break_even: false, // Whether a break-even duration is available/loaded
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

    /**
     * The position of the slider comparing expected and actual price
     */
    price_slider_position() {
      if (this.property) {
        const ratio = this.property.salePrice / this.estimated_price;
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
  watch: {
    // Upon property change, re-fetch all values
    coordinates() {
      this.rating = 0; // Reset rating so the previous property's rating isn't shown
      this.fetchProperty().then(() => {
        this.fetchEstimatedPrice(this.property.id);
        this.fetchDefaultBreakEven(this.property.id);
        this.fetchRating(this.property.id);
        this.fetchPricePerNight();
      });
    },
  },
  created() {
    this.fetchProperty().then(() => {
      this.fetchEstimatedPrice(this.property.id);
      this.fetchDefaultBreakEven(this.property.id);
      this.fetchRating(this.property.id);
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
    fetchDefaultBreakEven(id) {
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
          this.has_break_even = true;
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
        console.log("Rating for", id);
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
    height: calc(100% - 90px);
    width: 500px;
    position: absolute;
    top: 0;
    right: 0;
    margin: 20px;
  }
</style>
