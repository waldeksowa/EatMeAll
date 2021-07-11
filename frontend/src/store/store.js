// import json from '../../public/ALL_PRODUCTS.json'
// import Vue from "vue";
// import vueResource from 'vue-resource'
// Vue.use(vueResource)

const state = {
  products:[],
  // products:json,
}
const mutations = {
  FETCH_DATA(state, products) {
    state.products = products
  }
}
const actions = {
  fetchData({ commit }) {
    return new Promise((resolve, reject) => {
      Vue.http.get("http://localhost:8080/api/v1/products")
        .then((response) => {
          commit("FETCH_DATA", response.body);
          resolve();
        })
        .catch((error) => {
          console.log(error.statusText);
        });
    });
  }
}
const getters = {
  products: (state) =>{
    return state.products
  }
}

export default {
  namespaced:true,
  state,
  mutations,
  actions,
  getters
}
