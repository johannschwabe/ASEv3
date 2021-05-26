/* eslint-disable no-param-reassign */

export function toggleDrawer(state) {
  state.drawer_open = !state.drawer_open;
}

export function toggleTable(state) {
  state.table_open = !state.table_open;
}

export function toggleMarkers(state) {
  state.show_markers = !state.show_markers;
}

export function toggleHeatmap(state) {
  state.show_heatmap = !state.show_heatmap;
}

export function toggleNeighbourhoods(state) {
  state.show_neighbourhoods = !state.show_neighbourhoods;
}

export function setMapsApi(state, payload) {
  state.maps_api = payload.maps_api;
}

export function setMapType(state, payload) {
  state.map_type = payload.map_type;
}

export function setHeatmapRadius(state, payload) {
  state.heatmap_radius = payload.heatmap_radius;
}

export function setSelectedId(state, payload) {
  state.selected_id = payload.selected_id;
}
