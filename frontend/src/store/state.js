import * as OPTIONS from "../data/OPTIONS.js";

export default {
  mapsApi: null,
  show_markers: false,
  show_heatmap: true,
  show_neighbourhoods: true,
  drawer_open: false,
  map_type: OPTIONS.MAP_TYPES.PROPERTY,
  heatmap_radius: 10,
  selected_property: null,
};
