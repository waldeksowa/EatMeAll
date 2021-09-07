<template>
  <div class="q-gutter-y-xl">
    <div class="text-body1">
      {{ macrosName.calorific }} ({{ calcMealCalorific() }})Kcal
    </div>
    <qProgressBarLabel
      :props="{
        label: macrosName.carbohydrates,
        mealMacros: calcMealCarbohydrates(),
        mealWeight: calcMealWeight(),
      }"
    ></qProgressBarLabel>
    <qProgressBar
      :props="{
        value: calcCarbohydratesPercentage(),
        color: 'red',
        size: '20px',
      }"
    ></qProgressBar>
    <qProgressBarLabel
      :props="{
        label: macrosName.fat,
        mealMacros: calcMealFat(),
        mealWeight: calcMealWeight(),
      }"
    ></qProgressBarLabel>
    <qProgressBar
      :props="{
        value: calcFatPercentage(),
        color: 'green',
        size: '20px',
      }"
    ></qProgressBar>
    <qProgressBarLabel
      :props="{
        label: macrosName.protein,
        mealMacros: calcMealProtein(),
        mealWeight: calcMealWeight(),
      }"
    ></qProgressBarLabel>
    <qProgressBar
      :props="{
        value: calcProteinPercentage(),
        color: 'blue',
        size: '20px',
      }"
    ></qProgressBar>
  </div>
</template>
<script>
import { mapGetters } from "vuex";
export default {
  props: ["dataArray"],
  data() {
    return {};
  },
  computed: {
    ...mapGetters("store", ["macrosName"]),
  },
  components: {
    qProgressBar: () => import("./QProgressBar.vue"),
    qProgressBarLabel: () => import("./QProgressBarLabel.vue"),
  },
  methods: {
    calcMealCalorific() {
      return this.dataArray
        .map((p) => (p.amount * p.product.calorific) / 100)
        .reduce((a, b) => a + b, 0)
        .toFixed(0);
    },
    calcMealWeight() {
      return this.dataArray
        .map((p) => p.amount)
        .reduce((a, b) => a + b, 0)
        .toFixed(0);
    },
    calcMealCarbohydrates() {
      return this.dataArray
        .map((p) => (p.amount * p.product.carbohydrates) / 100)
        .reduce((a, b) => a + b, 0)
        .toFixed(2);
    },
    calcMealFat() {
      return this.dataArray
        .map((p) => (p.amount * p.product.fat) / 100)
        .reduce((a, b) => a + b, 0)
        .toFixed(2);
    },
    calcMealProtein() {
      return this.dataArray
        .map((p) => (p.amount * p.product.protein) / 100)
        .reduce((a, b) => a + b, 0)
        .toFixed(2);
    },
    calcFatPercentage() {
      return (
        (this.dataArray
          .map((p) => (p.amount * p.product.fat) / 100)
          .reduce((a, b) => a + b, 0) *
          9) /
        this.calcMealCalorific()
      );
    },
    calcProteinPercentage() {
      return (
        (this.dataArray
          .map((p) => (p.amount * p.product.protein) / 100)
          .reduce((a, b) => a + b, 0) *
          4) /
        this.calcMealCalorific()
      );
    },
    calcCarbohydratesPercentage() {
      return (
        (this.dataArray
          .map((p) => (p.amount * p.product.carbohydrates) / 100)
          .reduce((a, b) => a + b, 0) *
          4) /
        this.calcMealCalorific()
      );
    },
  },
};
</script>