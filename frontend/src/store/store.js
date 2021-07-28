// import json from '../../public/ALL_PRODUCTS.json'
// import Vue from "vue";
// import vueResource from 'vue-resource'
// Vue.use(vueResource)

import {ALL_PRODUCTS, MEALS} from "src/EndpointAddresses";

const state = {
  meals:[],
  products:[],
  // products:json,
  mealTime: [
    {label: 'Śniadanie', value:"BREAKFAST", checked:false, cal:250},
    {label: '2 Śniadanie', value:"SECOND_BREAKFAST", checked:false, cal:100},
    {label: 'Obiad', value:"LUNCH", checked:false, cal:350},
    {label: 'Podwieczorek', value:"DINNER", checked:false, cal:200},
    {label: 'Kolacja', value:"SUPPER", checked:false, cal:100}
  ],
}
const mutations = {
  FETCH_DATA(state, products) {
    state.products = products
  },
}
const actions = {
  fetchData({ commit }) {
    return new Promise((resolve, reject) => {
      Vue.http.get(ALL_PRODUCTS)
        .then((response) => {
          commit("FETCH_DATA", response.body);
          resolve();
        })
        .catch((error) => {
          console.log(error.statusText);
        });
    });
  },
  fetchDataMeals({ commit }) {
    fetch(MEALS)
    .then(r => r.json())
    .then(d => {
      for(const {id} of d){
        fetch(MEALS + {id})
        .then(r => r.json())
        .then(d => state.meals = d);
      }
    });
  },
  // fetchMealschedule(){
  //   let response
  //   try {
  //       response = await Vue.http.get(SCHEDULE)
  //   } catch (ex) {
  //       return
  //   }
  //   const data = response.body
  // }
}
const getters = {
  products: (state) =>{
    return state.products
  },
  meals: (state) =>{
    return state.meals
  },
  mealTime: (state) =>{
    return state.mealTime
  }
}

export default {
  namespaced:true,
  state,
  mutations,
  actions,
  getters
}
