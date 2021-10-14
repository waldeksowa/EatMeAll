import { KILOCALORIES, CARBOHYDRATES, FATS, PROTEINS } from '../translate/common/macrosName.js'
const state = {
  macrosName: {
    calorific: KILOCALORIES,
    carbohydrates: CARBOHYDRATES,
    fat: FATS,
    protein: PROTEINS
  },
  jwt: "382095b6-9222-48f3-95c3-e5c5d0881499",
  loggedMemberId: ""

}
const mutations = {
  updateJwt(state, payload) {
    state.jwt = payload;
  },
  updateLoggedMemberId(state, payload) {
    state.loggedMemberId = payload;
  }
}
const actions = {
  updateJwt({ commit }, payload) {
    commit('updateJwt', payload)
  },
  updateLoggedMemberId({ commit }, payload) {
    commit('updateLoggedMemberId', payload)
  }
}
const getters = {
  macrosName: (state) => { return state.macrosName },
  loggedMemberId: (state) => { return state.loggedMemberId },
  jwt: (state) => { return state.jwt },
  isJwtTokenDefined: (state) => {
    if (state.jwt) {
      return true
    }
    return false
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters
}
