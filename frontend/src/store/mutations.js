/* eslint-disable no-param-reassign */

export function toggleDrawer(state) {
  state.drawer_open = !state.drawer_open;
}

export function toggleMarkers(state) {
  state.show_markers = !state.show_markers;
}

export function toggleHeatmap(state) {
  state.show_heatmap = !state.show_heatmap;
}

export function setMapsApi(state, payload) {
  console.log("Set API to", payload.maps_api);
  state.maps_api = payload.maps_api;
}
