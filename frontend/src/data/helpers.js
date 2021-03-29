// eslint-disable-next-line import/prefer-default-export
export function capitalizeWords(input) {
  const words = input.split(" ");

  for (let i = 0; i < words.length; i++) {
    if (words[i]) {
      words[i] = words[i][0].toUpperCase() + words[i].substr(1);
    }
  }

  return words.join(" ");
}
