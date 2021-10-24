import { KILOCALORIES, CARBOHYDRATES, FATS, PROTEINS } from '../translate/common/macrosName.js'
const state = {
  macrosName: {
    calorific: KILOCALORIES,
    carbohydrates: CARBOHYDRATES,
    fat: FATS,
    protein: PROTEINS
  },
  jwt: "",
  objectJwt: '',
  memberIdToShow: ""
}
const mutations = {
  updateJwt(state, payload) {
    state.jwt = payload;
  },
  updateMemberIdToShow(state, payload) {
    state.memberIdToShow = payload;
  }
}
const actions = {
  updateJwt({ commit }, payload) {
    commit('updateJwt', payload)
  },
  updateMemberIdToShow({ commit }, payload) {
    commit('updateMemberIdToShow', payload)
  },
  errorMesage(e) {
    Notify.create({
      message: `⚠ ${e}`,
      classes: "full-width text-center bg-negative",
    });
  },
  notifySucessful(e) {
    Notify.create({
      message: `${e}`,
      classes: "full-width text-center bg-positive",
    });
  },
}
const getters = {
  objectJwt: (state) => { return state.macrosName },
  macrosName: (state) => { return state.macrosName },
  memberIdToShow: (state) => { return state.memberIdToShow },
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
