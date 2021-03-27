const resolve = require('path').resolve;

module.exports = {
  root: true,
  env: {
    browser: true,
    node: true,
    es6: true,
    "jest/globals": true
  },
  parserOptions: {
    "parser": "babel-eslint",
    "ecmaVersion": 2018,
    "sourceType": "module"
  },
  extends: [
    "airbnb-base",
    "eslint:recommended",
    "plugin:vue/recommended",
  ],
  "plugins": [
    "vue",
    "jest"
  ],
  "rules": {
    "linebreak-style": 0,
    "quotes": ["error", "double"],
    "no-extend-native": 0,
    "max-len": 0,
    "camelcase": 0,
    "func-names": 0,
    "no-underscore-dangle": 0,
    "no-use-before-define": 0,
    "stric": 0,
    "yoda": 0,
    "no-return-await": 0,
    "no-restricted-globals": 0,
    "prefer-promise-reject-errors": 0,
    "object-curly-spacing": 0,
    "max-classes-per-file": 0,
    "strict": 0,
    "no-mixed-operators": 0,
    "prefer-destructuring": 0,
    "curly": ["error", "all"],
    "object-shorthand": ["error", "methods"], //ensures that no property or method shorthand is used in any object literal.
    "arrow-body-style": 0,
    "vue/prop-name-casing": ["error", "snake_case"],
    "no-prototype-builtins": 0,
    "import/extensions": ["error", "always"],
    "no-plusplus": 0,
    "no-case-declarations": 0,
    "no-bitwise": 0,
  },
  "globals": {
    "Atomics": "readonly",
    "SharedArrayBuffer": "readonly"
  },
  settings: {
    'import/resolver': {
      webpack: {
        config: {
          resolve: {
            alias: {
              '~': __dirname,
              '@': __dirname,
              '@/themes': resolve(__dirname, 'themes'),
              '@/mixins': resolve(__dirname, 'mixins'),
              '@/static': resolve(__dirname, 'static'),
              '@/assets': resolve(__dirname, 'assets'),
              '@/plugins': resolve(__dirname, 'plugins'),
              '@/pages': resolve(__dirname, 'pages'),
              '@/components': resolve(__dirname, 'components')
            }
          }
        }
      }
    }
  }
};
