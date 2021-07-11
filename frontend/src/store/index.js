import Vue from 'vue'
import Vuex from 'vuex'

// we first import the module
import products from './store'
Vue.config.devtools = true
Vue.use(Vuex)

export default function (/* { ssrContext } */) {
  const Store = new Vuex.Store({
    modules: {
      products
    },

    strict: process.env.DEV
  })

  // if (process.env.DEV && module.hot) {
  //   module.hot.accept(['./showcase'], () => {
  //     const newShowcase = require('./showcase').default
  //     Store.hotUpdate({ modules: { showcase: newShowcase } })
  //   })
  // }

  return Store
}
