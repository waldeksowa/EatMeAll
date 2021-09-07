<template>
  <q-page>
    <div
      class="
        q-gutter-y-md q-col-gutter-md q-pa-md
        fit
        row
        wrap
        justify-center
        items-start
        content-start
      "
    >
      <div class="col-grow col-sm-9">
        <q-form class="q-col-gutter-y-lg">
          <mealDataComponent
            :props="{
              recipeData: recipeData,
              inputLabel: inputLabel,
            }"
            @updateRecipeInputValue="updateRecipeInputValue($event)"
          ></mealDataComponent>
          <mealTimeComponent
            :checkboxOptions="checkboxOptions"
            @changeChecboxValue="changeChecboxValue($event)"
          ></mealTimeComponent>
          <mealPreparationSteps
            :props="{ steps: recipeData.steps, areStepsHidden: areStepsHidden }"
            @addRow="addRow($event)"
            @deleteRow="deleteRow($event)"
            @updateInputValue="updateInputValue($event)"
            @invertAreStepsHidden="invertAreStepsHidden($event)"
          ></mealPreparationSteps>
          <mealIncludesMacros
            :dataArray="recipeData.addedProducts"
          ></mealIncludesMacros>
          <mealAddProduct
            :props="{
              addedProducts: recipeData.addedProducts,
            }"
            @removeProducts="removeProducts($event)"
            @addProductToList="addProductToList($event)"
          ></mealAddProduct>
          <mealIngredients
            :addedProducts="recipeData.addedProducts"
            @removeProducts="removeProducts($event)"
            @updateProductWeightValue="updateProductWeightValue($event)"
          ></mealIngredients>
          <mealPost
            :props="{
              checkboxOptions: checkboxOptions,
              areStepsHidden: areStepsHidden,
              recipeData: recipeData,
            }"
            @restartFormData="restartFormData()"
          ></mealPost>
        </q-form>
      </div>
    </div>
  </q-page>
</template>
<script>
import {
  INPUT_NAME,
  INPUT_TIME,
  INPUT_AUTHOR,
  INPUT_DESCRIPTION,
} from "src/translate/addMeal/inputLabels.js";
import {
  BREAKFAST,
  SECOND_BREAKFAST,
  LUNCH,
  DINNER,
  SUPPER,
} from "../translate/common/mealTimes.js";
export default {
  name: "PageIndex",
  data() {
    return {
      areStepsHidden: false,
      recipeData: {
        name: "",
        time: null,
        author: "",
        description: "",
        addedProducts: [],
        steps: [],
      },
      inputLabel: {
        name: INPUT_NAME,
        time: INPUT_TIME,
        author: INPUT_AUTHOR,
        description: INPUT_DESCRIPTION,
      },
      checkboxOptions: [
        { label: BREAKFAST, value: "BREAKFAST", checked: false, cal: 250 },
        {
          label: SECOND_BREAKFAST,
          value: "SECOND_BREAKFAST",
          checked: false,
          cal: 100,
        },
        { label: LUNCH, value: "LUNCH", checked: false, cal: 350 },
        { label: DINNER, value: "DINNER", checked: false, cal: 200 },
        { label: SUPPER, value: "SUPPER", checked: false, cal: 100 },
      ],
    };
  },
  methods: {
    restartFormData() {
      this.recipeData = {
        name: "",
        time: null,
        author: "",
        description: "",
        addedProducts: [],
        steps: [],
      };
      this.checkboxOptions = [
        { label: BREAKFAST, value: "BREAKFAST", checked: false, cal: 250 },
        {
          label: SECOND_BREAKFAST,
          value: "SECOND_BREAKFAST",
          checked: false,
          cal: 100,
        },
        { label: LUNCH, value: "LUNCH", checked: false, cal: 350 },
        { label: DINNER, value: "DINNER", checked: false, cal: 200 },
        { label: SUPPER, value: "SUPPER", checked: false, cal: 100 },
      ];
      this.areStepsHidden = false;
    },
    removeProducts(aP) {
      this.recipeData.addedProducts.forEach((item, index) => {
        if (item.product === aP) this.recipeData.addedProducts.splice(index, 1);
      });
    },
    addProductToList(aP) {
      this.recipeData.addedProducts.push(aP);
    },
    updateRecipeInputValue([aUpdate, aLabel]) {
      if (aLabel === this.inputLabel.name) this.recipeData.name = aUpdate;
      else if (aLabel === this.inputLabel.time) this.recipeData.time = aUpdate;
      else if (aLabel === this.inputLabel.author)
        this.recipeData.author = aUpdate;
      else if (aLabel === this.inputLabel.description)
        this.recipeData.description = aUpdate;
    },
    changeChecboxValue([aUpdate, aIndex]) {
      this.checkboxOptions[aIndex].checked = aUpdate;
    },
    addRow(aUpdata) {
      this.recipeData.steps.push(aUpdata);
    },
    deleteRow(aIndex) {
      this.recipeData.steps.splice(aIndex, 1);
    },
    updateInputValue([aUpdate, aIndex]) {
      this.recipeData.steps[aIndex].name = aUpdate;
    },
    invertAreStepsHidden(aUpdate) {
      this.areStepsHidden = aUpdate;
    },
    updateProductWeightValue([aUpdate, index]) {
      this.recipeData.addedProducts[index].amount = parseInt(aUpdate);
    },
    notifyError(e) {
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
  },
  components: {
    mealDataComponent: () => import("components/addMeal/MealDataComponent.vue"),
    mealAddProduct: () => import("components/addMeal/MealAddProduct.vue"),
    mealTimeComponent: () => import("components/addMeal/MealTimeComponent.vue"),
    mealPreparationSteps: () =>
      import("components/addMeal/MealPreparationSteps.vue"),
    mealIncludesMacros: () =>
      import("components/addMeal/MealIncludesMacros.vue"),
    mealIngredients: () => import("components/addMeal/MealIngredients.vue"),
    mealPost: () => import("components/addMeal/MealPost.vue"),
  },
};
</script>
<style lang="sass">
.btn-h
  height: 50px
.container-weight-btn
  @media only screen and (min-width: 1000px)
    display: flex
</style>
