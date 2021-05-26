export function toggleDrawer({ commit }) {
  commit("toggleDrawer");
}

export function toggleTable({ commit }) {
  commit("toggleTable");
}

export function toggleMarkers({ commit }) {
  commit("toggleMarkers");
}

export function setMapType({ commit }, payload) {
  commit("setMapType", payload);
  commit("setSelectedCoordinates", { selected_coordinates: null });
}
