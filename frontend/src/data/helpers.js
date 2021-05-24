import {MAP_TYPES} from "../constants/OPTIONS.js";

/**
 * Capitalizes all words in a given string
 * @param {string} input - the input text
 */
export function capitalizeWords(input) {
  const words = input.split(" ");

  for (let i = 0; i < words.length; i++) {
    if (words[i]) {
      words[i] = words[i][0].toUpperCase() + words[i].substr(1);
    }
  }

  return words.join(" ");
}

/**
 * Determines the break-even string to show for a given duration
 * @param {Number} value - the break-even duration in years
 */
export function getBreakEvenString(value) {
  if (value >= 200 || value === null || value === undefined) { return "N/A"; }

  return `${Math.round((value + Number.EPSILON) * 100) / 100} years`;
}

/**
 * Get the actual point radius to apply on the heatmap, depending on map mode
 * @param {string} type - map type (AIRBNB or PROPERTY)
 * @param {Number} radius - the radius in px
 * @returns {Number}
 */
export function getAdjustedRadius(type, radius) {
  let output_radius = radius;

  // For properties, increase weight of each data point
  if (type === MAP_TYPES.PROPERTY) {
    output_radius *= 3;
  }

  return output_radius;
}

/**
 * Sets the heatmap point radius
 * @param {string} type - map type (AIRBNB or PROPERTY)
 * @param {Number} radius - the radius in px
 */
export function setHeatmapRadius(type, radius) {
  const output_radius = getAdjustedRadius(type, radius);
  this.$store.commit("setHeatmapRadius", {heatmap_radius: output_radius});
}
