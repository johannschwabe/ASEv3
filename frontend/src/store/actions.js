// TODO functions
export function toggleDrawer({ commit }) {
  commit("toggleDrawer");
}

// TODO functions
export function toggleMarkers({ commit }) {
  commit("toggleMarkers");
}

// TODO functions
export function setMapType({ commit }, payload) {
  commit("setMapType", payload);
  commit("setSelectedCoordinates", { selected_coordinates: null });
}
