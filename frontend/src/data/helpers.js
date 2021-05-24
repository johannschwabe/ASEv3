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
  if (value >= 200) { return "-"; }

  return `${Math.round((value + Number.EPSILON) * 100) / 100} years`;
}
