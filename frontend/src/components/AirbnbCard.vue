<template>
  <q-card
    v-scroll
    class="propertyCard overflow-hidden"
  >
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
          <div class="text-h6">
            {{ property.name }}
          </div>

          <div>
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
            <div class="text-bold">
              {{ property.host_name }}
            </div>
            {{ property.calculated_host_listings_count }} listing(s)
            <br>
            More host info may go here.
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
          <b>Type</b>
        </q-card-section>
        <q-separator vertical />
        <q-card-section class="col-8">
          {{ property.room_type }}
        </q-card-section>
      </q-card-section>

      <q-separator />

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <b>Area</b>
        </q-card-section>
        <q-separator vertical />
        <q-card-section>
          {{ property.neighbourhood_group }}
        </q-card-section>
      </q-card-section>

      <q-separator />

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <b>Neighbourhood</b>
        </q-card-section>
        <q-separator vertical />
        <q-card-section>
          {{ property.neighbourhood }}
        </q-card-section>
      </q-card-section>

      <q-separator />

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <b>Coordinates</b>
        </q-card-section>
        <q-separator vertical />
        <q-card-section>
          {{ property.latitude }}, {{ property.longitude }}
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
          <b>Price</b>
        </q-card-section>
        <q-separator vertical />
        <q-card-section>
          ${{ property.price }}
        </q-card-section>
      </q-card-section>

      <q-separator />

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <b>Min. nights</b>
        </q-card-section>
        <q-separator vertical />
        <q-card-section>
          {{ property.minimum_nights }} (${{ property.minimum_nights * property.price }})
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
          <b>Reviews</b>
        </q-card-section>
        <q-separator vertical />
        <q-card-section>
          {{ property.number_of_reviews }} ({{ property.reviews_per_month }} per month)
        </q-card-section>
      </q-card-section>

      <q-separator />

      <q-card-section horizontal>
        <q-card-section class="col-3">
          <b>Availability</b>
        </q-card-section>
        <q-separator vertical />
        <q-card-section>
          {{ property.availability_365 }} days per year ({{ 365-property.availability_365 }} days booked)
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
export default {
  name: "AirbnbCard",
  props: {
    property: { type: Object, required: true },
  },
  computed: {
    host_image_url() {
      // TODO
      return "https://picsum.photos/100";
    },

    listing_image_url() {
      // TODO
      return "https://picsum.photos/500/300";
    },
  },
  created() {
    // Fill missing data TODO remove once backend gives
    if (!this.property.reviews_per_month) {
      this.property.reviews_per_month = 0;
    }

    // TODO remove fake
    this.property.computed_rating = Math.floor(Math.random() * 4) + 1;
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
