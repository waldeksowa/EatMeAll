<template>
  <div class="q-col-gutter-y-lg">
    <div class="text-h4 text-center q-mt-lg">{{ header }}</div>
    <div class="q-pb-md" v-if="addedProducts.length !== 0">
      <div v-for="(p, i) in addedProducts" :key="i">
        <q-card class="my-card full-width" flat bordered>
          <q-item class="row">
            <q-item-section>
              <div class="text-body1 q-mt-sm q-mb-xs">
                {{ p.product.name }}
              </div>
            </q-item-section>
            <q-item-section class="col-sm-3">
              <div class="container-weight-btn">
                <q-btn
                  flat
                  class="bg-primary text-white full-width btn-h"
                  :icon="includeProduct(p.product) ? 'remove' : 'add'"
                  @click="
                    includeProduct(p.product)
                      ? removeProducts(p.product)
                      : addProductToList(p.product)
                  "
                ></q-btn>
                <qInputProductWeight
                  :props="{
                    amount: p.amount,
                    rules: errorMessage,
                  }"
                  @updateProductWeightValue="
                    updateProductWeightValue($event, i)
                  "
                ></qInputProductWeight>
              </div>
            </q-item-section>
          </q-item>
          <q-separator />
          <q-item-section class="q-pa-sm bg-grey-3">
            <div class="text-subtitle2 flex">
              {{ macrosName.calorific }}: {{ p.product.calorific }}(Kcal)
              {{ macrosName.fat }}: {{ p.product.fat }}(g)
              {{ macrosName.carbohydrates }}: {{ p.product.carbohydrates }}(g)
              {{ macrosName.protein }}: {{ p.product.protein }}(g)
            </div>
          </q-item-section>
        </q-card>
      </div>
    </div>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
import { MEAL_INGREDIENTS } from "../../translate/addMeal/sectionHeaders.js";
import { ERROR_RULES } from "../../translate/addMeal/inputLabels.js";
export default {
  props: ["addedProducts"],
  data() {
    return {
      header: MEAL_INGREDIENTS,
      errorMessage: ERROR_RULES,
    };
  },
  components: {
    qInputProductWeight: () => import("../common/QInputProductWeight.vue"),
  },
  computed: {
    ...mapGetters("store", ["macrosName"]),
  },
  methods: {
    includeProduct(aP) {
      let arr = [];
      for (const { product } of this.addedProducts) {
        arr.push(product);
      }
      return arr.includes(aP);
    },
    updateProductWeightValue(aUpdate, aIndex) {
      this.$emit("updateProductWeightValue", [aUpdate, aIndex]);
    },
    removeProducts(aP) {
      this.$emit("removeProducts", aP);
    },
  },
};
</script>