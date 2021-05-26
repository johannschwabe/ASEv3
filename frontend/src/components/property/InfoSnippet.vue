<template>
  <q-card-section horizontal>
    <q-card-section class="col-3">
      <strong>{{ title }}</strong>
    </q-card-section>
    <q-separator vertical />
    <q-card-section>
      <q-spinner-dots v-if="loading" />
      <div v-else>
        {{ content ? content.toString() : "" }}
      </div>
    </q-card-section>
  </q-card-section>
</template>

<script>
export default {
  name: "InfoSnippet",
  props: {
    title: { type: String, required: true },
    content: { required: true},
    loading: { type: Boolean, required: true },
  },
};
</script>
