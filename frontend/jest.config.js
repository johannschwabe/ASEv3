module.exports = {
  moduleNameMapper: {
    "^@/(.*)$": "<rootDir>/$1",
    "^~/(.*)$": "<rootDir>/$1",
    "^vue$": "vue/dist/vue.common.js",
  },
  moduleFileExtensions: [
    "js",
    "vue",
    "json",
  ],
  transform: {
    "^.+\\.js$": "babel-jest",
    ".*\\.(vue)$": "vue-jest",
  },


  collectCoverage: true,
  collectCoverageFrom: [
    "<rootDir>/src/components/**/*.vue",
    "<rootDir>/src/pages/**/*.vue",
    "<rootDir>/src/store/**/*.js",
  ],
  coverageReporters: [
    "html",
    "json-summary",
    "json-summary",
    "text",
    "lcov",
  ],
  coverageDirectory: "./reports/coverage/",
};
