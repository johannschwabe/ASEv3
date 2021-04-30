<template>
  <q-card
    v-scroll
    class="propertyCard overflow-hidden"
  >
    <div style="position: absolute; top: 0; right: 0; z-index: 1">
      <q-btn
        flat
        icon="mdi-close"
        @click="onHide"
      />
    </div>
    <q-scroll-area style="height: 100%">
      <!-- Card header: Name & host -->
      <q-card-section class="bg-primary text-white no-padding">
        <img
          :src="listing_image_url"
          alt="listing image"
        >
        <div
          class="row justify-between"
          style="padding: 20px"
        >
          <q-spinner-dots v-if="loading" />
          <div
            v-else-if="property"
            class="text-h6"
          >
            {{ property.name }}
          </div>

          <div v-if="property">
            <q-icon
              v-for="i in property.computed_rating"
              :key="i"
              name="star"
              color="white"
              size="24px"
            />
          </div>
        </div>
      </q-card-section>

      <!-- Host title -->
      <q-card-section class="bg-grey-3">
        <div class="text-bold">
          Host
        </div>
      </q-card-section>
      <q-card-section>
        <q-item>
          <q-item-section avatar>
            <q-avatar
              color="grey-3"
              :icon="host_image_url ? undefined : 'person'"
              text-color="primary"
              size="60px"
            >
              <img
                v-if="host_image_url"
                :src="host_image_url"
                alt="host image"
              >
            </q-avatar>
          </q-item-section>
          <q-item-section>
            <q-spinner-dots v-if="loading" />
            <div v-else>
              <div class="text-bold">
                {{ property ? property.hostName : "" }}
              </div>
              {{ property ? `${property.calculatedHostListingsCount} listing(s)` : "" }}
              <br>
              More host info may go here.
            </div>
          </q-item-section>
        </q-item>
      </q-card-section>

      <!-- Info title -->
      <q-card-section class="bg-grey-3">
        <div class="text-bold">
          Info
        </div>
      </q-card-section>

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <strong>Type</strong>
        </q-card-section>
        <q-separator vertical />
        <q-card-section class="col-8">
          <q-spinner-dots v-if="loading" />
          <div v-else>
            {{ property ? property.roomType : "" }}
          </div>
        </q-card-section>
      </q-card-section>

      <q-separator />

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <strong>Area</strong>
        </q-card-section>
        <q-separator vertical />
        <q-card-section>
          <q-spinner-dots v-if="loading" />
          <div v-else>
            {{ property ? property.neighbourhoodGroup : "" }}
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
            {{ property ? property.neighbourhood : "" }}
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
            {{ property ? `${property.latitude}, ${property.longitude}` : "" }}
          </div>
        </q-card-section>
      </q-card-section>

      <!-- Pricing title -->
      <q-card-section class="bg-grey-3">
        <div class="text-bold">
          Pricing
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
            {{ property ? `$${property.price}` : "" }}
          </div>
        </q-card-section>
      </q-card-section>

      <q-separator />

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <strong>Min. nights</strong>
        </q-card-section>
        <q-separator vertical />
        <q-card-section>
          <q-spinner-dots v-if="loading" />
          <div v-else>
            {{ property ? `${property.minimumNights} ($${property.minimumNights * property.price})` : "" }}
          </div>
        </q-card-section>
      </q-card-section>

      <!-- Rating title -->
      <q-card-section class="bg-grey-3">
        <div class="text-bold">
          Rating
        </div>
      </q-card-section>

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <strong>Reviews</strong>
        </q-card-section>
        <q-separator vertical />
        <q-card-section>
          <q-spinner-dots v-if="loading" />
          <div v-else>
            {{ property ? `${property.numberOfReviews} (${property.reviewsPerMonth} per month)` : "" }}
          </div>
        </q-card-section>
      </q-card-section>

      <q-separator />

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <strong>Availability</strong>
        </q-card-section>
        <q-separator vertical />
        <q-card-section>
          <q-spinner-dots v-if="loading" />
          <div v-else>
            {{ property ? `${property.availability365} days per year (${365-property.availability365} days booked)` : "" }}
          </div>
        </q-card-section>
      </q-card-section>

      <q-separator />

      <!-- Action buttons TODO -->
      <q-card-actions align="center">
        <q-btn flat>
          Favorite
        </q-btn>
        <q-btn flat>
          Show all by host
        </q-btn>
        <q-btn flat>
          Hide
        </q-btn>
      </q-card-actions>
    </q-scroll-area>
  </q-card>
</template>

<script>
import axios from "axios";
import { API_KEY } from "../constants/API.js";

export default {
  name: "AirbnbCard",
  props: {
    coordinates: { type: String, required: true },
  },
  data: () => ({
    property: null,
    loading: true,
  }),
  computed: {
    host_image_url() {
      // TODO
      return "https://picsum.photos/100";
    },

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
    coordinates() {
      this.fetchProperty();
    },
  },
  created() {
    this.fetchProperty();
  },
  methods: {
    onHide() {
      this.$emit("hide");
    },

    fetchProperty() {
      this.loading = true;
      axios({
        url: "http://localhost:8282/graphql",
        method: "post",
        data: {
          query: `
            {
              airbnbById(id: "${this.coordinates}") {
                id
                name
                hostName
                calculatedHostListingsCount
                roomType
                neighbourhoodGroup
                neighbourhood
                latitude
                longitude
                price
                minimumNights
                numberOfReviews
                reviewsPerMonth
                availability365
              }
            }
          `,
        },
      }).then((result) => {
        this.property = result.data.data.airbnbById;
        // TODO remove fake
        this.property.computed_rating = 3;
      }).finally(() => {
        this.loading = false;
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
