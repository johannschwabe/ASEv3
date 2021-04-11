module.exports = {
  presets: [
    ["@babel/preset-env", {
      useBuiltIns: "usage",
      targets: {
        browsers: [
          "> 1%",
          "not dead",
          "last 2 versions",
          "ie > 8",
        ],
      },
      corejs: 3,
    }],
  ],
  plugins: [
    "@babel/plugin-syntax-object-rest-spread",
    "@babel/plugin-proposal-object-rest-spread",
    "@babel/plugin-transform-runtime",
    "@babel/plugin-transform-modules-commonjs",
  ],
};
