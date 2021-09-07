<template>
  <div>
    <div class="text-h4 text-center q-mb-lg">{{ header }}</div>
    <div>
      <q-btn
        color="primary full-width btn-h"
        :label="header"
        @click="dialog = !dialog"
        icon="shopping_cart"
      ></q-btn>
    </div>
    <q-dialog v-model="dialog">
      <mealDialog
        :props="{
          addedProducts: props.addedProducts,
        }"
        @removeProducts="removeProducts($event)"
        @addProductToList="addProductToList($event)"
      ></mealDialog>
    </q-dialog>
  </div>
</template>
<script>
import { MEAL_ADD_PRODUCTS } from "../../translate/addMeal/sectionHeaders.js";
export default {
  props: ["props"],
  data() {
    return {
      header: MEAL_ADD_PRODUCTS,
      dialog: false,
    };
  },
  methods: {
    removeProducts(aP) {
      this.$emit("removeProducts", aP);
    },
    addProductToList(aP) {
      this.$emit("addProductToList", aP);
    },
  },
  components: {
    mealDialog: () => import("./MealDialog.vue"),
  },
};
</script>