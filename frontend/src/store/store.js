import { KILOCALORIES, CARBOHYDRATES, FATS, PROTEINS } from '../translate/common/macrosName.js'
import { Notify } from "quasar";
import {
  Loading,
  QSpinnerGears
} from 'quasar'
const state = {
  macrosName: {
    calorific: KILOCALORIES,
    carbohydrates: CARBOHYDRATES,
    fat: FATS,
    protein: PROTEINS
  },
  memberIdToShowSchedule: '',
  jwt: "",
  memberIdToShowAccountDetail: ""
}
const mutations = {
  updateJwt(state, payload) {
    state.jwt = payload;
  },
  updateMemberIdToShowSchedule(state, payload) {
    state.memberIdToShowSchedule = payload;
  },
  updateMemberIdToShow(state, payload) {
    state.memberIdToShowAccountDetail = payload;
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
  notifyError({ commit }, e) {
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
  showLoading({ commit }, e) {
    Loading.show();

    // hiding in 2s
    this.timer = setTimeout(() => {
      Loading.hide();
      this.timer = void 0;
    }, 2000);
  },
}
const getters = {
  objectJwt: (state) => { return state.macrosName },
  macrosName: (state) => { return state.macrosName },
  memberIdToShowAccountDetail: (state) => { return state.memberIdToShowAccountDetail },
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
