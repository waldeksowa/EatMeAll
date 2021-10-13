import { KILOCALORIES, CARBOHYDRATES, FATS, PROTEINS } from '../translate/common/macrosName.js'
const state = {
  macrosName: {
    calorific: KILOCALORIES,
    carbohydrates: CARBOHYDRATES,
    fat: FATS,
    protein: PROTEINS
  },
  jwt: "1",

}
const mutations = {
  updateJwt(state, payload) {
    state.jwt = payload;
  }
}
const actions = {
  updateJwt({ commit }, payload) {
    commit('updateJwt', payload)
  }
}
const getters = {
  macrosName: (state) => { return state.macrosName },
  jwt: (state) => { return state.jwt },
  isJwtTokenDefined: (state) => { if (state.jwt) { return true } return false }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters
}
