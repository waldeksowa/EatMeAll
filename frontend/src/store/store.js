import { KILOCALORIES, CARBOHYDRATES, FATS, PROTEINS } from '../translate/common/macrosName.js'
import { Notify } from "quasar";
const state = {
  macrosName: {
    calorific: KILOCALORIES,
    carbohydrates: CARBOHYDRATES,
    fat: FATS,
    protein: PROTEINS
  },
  memberIdToShowSchedule: '',
  jwt: "",
  memberIdToShow: ""
}
const mutations = {
  updateJwt(state, payload) {
    state.jwt = payload;
  },
  updateMemberIdToShowSchedule(state, payload) {
    state.memberIdToShowSchedule = payload;
  },
  updateMemberIdToShow(state, payload) {
    state.memberIdToShow = payload;
  }
}
const actions = {
  updateJwt({ commit }, payload) {
    commit('updateJwt', payload)
  },
  updateMemberIdToShowSchedule({ commit }, payload) {
    commit('updateMemberIdToShowSchedule', payload)
  },
  updateMemberIdToShow({ commit }, payload) {
    commit('updateMemberIdToShow', payload)
  },
  errorMesage({ commit }, e) {
    Notify.create({
      message: `⚠ ${e}`,
      classes: "full-width text-center bg-negative",
    });
  },
  notifySucessful({ commit }, e) {
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
  memberIdToShowSchedule: (state) => { return state.memberIdToShowSchedule },
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
