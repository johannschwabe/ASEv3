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
  state.maps_api = payload.maps_api;
}

export function setHeatmapType(state, payload) {
  state.heatmap_type = payload.heatmap_type;
}

export function setHeatmapRadius(state, payload) {
  state.heatmap_radius = payload.heatmap_radius;
}
