import { KILOCALORIES, CARBOHYDRATES, FATS, PROTEINS } from '../translate/common/macrosName.js'
const state = {
  macrosName: {
    calorific: KILOCALORIES,
    carbohydrates: CARBOHYDRATES,
    fat: FATS,
    protein: PROTEINS
  },

}
const mutations = {

}
const actions = {

}
const getters = {
  macrosName: state => state.macrosName,
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters
}
