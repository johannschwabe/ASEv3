# ASE AirBnB Property Evaluator

Webpage to evaluate the AirBnB value of properties for sale, developed with VueJS.

## Documentation

[Documentation](https://github.com/johannschwabe/ASEv3/wiki)

## Tests

![Build](https://img.shields.io/badge/Tests-Failing-red)

### Number of tests
![Build](https://img.shields.io/badge/Total-2-lightgrey)
![Build](https://img.shields.io/badge/Failing-1-red)
![Build](https://img.shields.io/badge/Passing-1-brightgreen)

### Coverage

![Line Coverage](https://img.shields.io/badge/LineCoverage-4.32-orange)
![Functions Coverage](https://img.shields.io/badge/FunctionsCoverage-3-orange)
![Statement Coverage](https://img.shields.io/badge/StatementCoverage-4.32-orange)
![Branche Coverage](https://img.shields.io/badge/BranchCoverage-0-red)

## Open Bugs (Failed tests)

### /home/runner/work/ASEv3/ASEv3/frontend/src/test/PropertyCard.test.js

#### propertyCardMounts

- (failed) - **1.1 mounts.**
```javascript
TypeError: Cannot read property toFixed of undefined
    at VueComponent.listing_image_url (/home/runner/work/ASEv3/ASEv3/frontend/src/components/PropertyCard.vue:326:35)
    at Watcher.get (/home/runner/work/ASEv3/ASEv3/frontend/node_modules/vue/dist/vue.common.dev.js:4474:25)
    at Watcher.evaluate (/home/runner/work/ASEv3/ASEv3/frontend/node_modules/vue/dist/vue.common.dev.js:4579:21)
    at VueComponent.computedGetter [as listing_image_url] (/home/runner/work/ASEv3/ASEv3/frontend/node_modules/vue/dist/vue.common.dev.js:4828:17)
    at Object.listing_image_url [as get] (/home/runner/work/ASEv3/ASEv3/frontend/node_modules/vue/dist/vue.common.dev.js:2100:20)
    at Proxy.render (/home/runner/work/ASEv3/ASEv3/frontend/src/components/PropertyCard.vue:430:35)
    at VueComponent.Vue._render (/home/runner/work/ASEv3/ASEv3/frontend/node_modules/vue/dist/vue.common.dev.js:3547:22)
    at VueComponent.updateComponent (/home/runner/work/ASEv3/ASEv3/frontend/node_modules/vue/dist/vue.common.dev.js:4063:21)
    at Watcher.get (/home/runner/work/ASEv3/ASEv3/frontend/node_modules/vue/dist/vue.common.dev.js:4474:25)
    at new Watcher (/home/runner/work/ASEv3/ASEv3/frontend/node_modules/vue/dist/vue.common.dev.js:4463:12)
    at mountComponent (/home/runner/work/ASEv3/ASEv3/frontend/node_modules/vue/dist/vue.common.dev.js:4070:3)
    at VueComponent.Object.<anonymous>.Vue.$mount (/home/runner/work/ASEv3/ASEv3/frontend/node_modules/vue/dist/vue.common.dev.js:9040:10)
    at VueComponent.Object.<anonymous>.Vue.$mount (/home/runner/work/ASEv3/ASEv3/frontend/node_modules/vue/dist/vue.common.dev.js:11940:16)
    at init (/home/runner/work/ASEv3/ASEv3/frontend/node_modules/vue/dist/vue.common.dev.js:3121:13)
    at createComponent (/home/runner/work/ASEv3/ASEv3/frontend/node_modules/vue/dist/vue.common.dev.js:5969:9)
    at createElm (/home/runner/work/ASEv3/ASEv3/frontend/node_modules/vue/dist/vue.common.dev.js:5916:9)
    at VueComponent.patch [as __patch__] (/home/runner/work/ASEv3/ASEv3/frontend/node_modules/vue/dist/vue.common.dev.js:6466:7)
    at VueComponent.Vue._update (/home/runner/work/ASEv3/ASEv3/frontend/node_modules/vue/dist/vue.common.dev.js:3942:19)
    at VueComponent.updateComponent (/home/runner/work/ASEv3/ASEv3/frontend/node_modules/vue/dist/vue.common.dev.js:4063:10)
    at Watcher.get (/home/runner/work/ASEv3/ASEv3/frontend/node_modules/vue/dist/vue.common.dev.js:4474:25)
    at new Watcher (/home/runner/work/ASEv3/ASEv3/frontend/node_modules/vue/dist/vue.common.dev.js:4463:12)
    at mountComponent (/home/runner/work/ASEv3/ASEv3/frontend/node_modules/vue/dist/vue.common.dev.js:4070:3)
    at VueComponent.Object.<anonymous>.Vue.$mount (/home/runner/work/ASEv3/ASEv3/frontend/node_modules/vue/dist/vue.common.dev.js:9040:10)
    at VueComponent.Object.<anonymous>.Vue.$mount (/home/runner/work/ASEv3/ASEv3/frontend/node_modules/vue/dist/vue.common.dev.js:11940:16)
    at mount (/home/runner/work/ASEv3/ASEv3/frontend/node_modules/@vue/test-utils/dist/vue-test-utils.js:13977:21)
    at Object.<anonymous> (/home/runner/work/ASEv3/ASEv3/frontend/src/test/PropertyCard.test.js:6:18)
    at Object.asyncJestTest (/home/runner/work/ASEv3/ASEv3/frontend/node_modules/jest-jasmine2/build/jasmineAsyncInstall.js:106:37)
    at /home/runner/work/ASEv3/ASEv3/frontend/node_modules/jest-jasmine2/build/queueRunner.js:45:12
    at new Promise (<anonymous>)
    at mapper (/home/runner/work/ASEv3/ASEv3/frontend/node_modules/jest-jasmine2/build/queueRunner.js:28:19)
    at /home/runner/work/ASEv3/ASEv3/frontend/node_modules/jest-jasmine2/build/queueRunner.js:75:41
```


