<template>
  <div>
    <div class="text-h4 text-center">{{ header }}</div>
    <qInputRule
      :props="{
        value: props.recipeData.name,
        label: props.inputLabel.name,
        type: 'text',
      }"
      @updateRecipeInputValue="
        updateRecipeInputValue($event, props.inputLabel.name)
      "
    >
    </qInputRule>
    <qInputRule
      :props="{
        value: props.recipeData.author,
        label: props.inputLabel.author,
        type: 'text',
      }"
      @updateRecipeInputValue="
        updateRecipeInputValue($event, props.inputLabel.author)
      "
    >
    </qInputRule>
    <qInputRule
      :props="{
        value: props.recipeData.time,
        label: props.inputLabel.time,
        type: 'number',
      }"
      @updateRecipeInputValue="
        updateRecipeInputValue($event, props.inputLabel.time)
      "
    >
    </qInputRule>
    <qInputRule
      :props="{
        value: props.recipeData.description,
        label: props.inputLabel.description,
        type: 'text',
      }"
      @updateRecipeInputValue="
        updateRecipeInputValue($event, props.inputLabel.description)
      "
    >
    </qInputRule>
  </div>
</template>
<script>
import { MEAL_DATA } from "../../translate/addMeal/sectionHeaders.js";
export default {
  props: ["props"],
  data() {
    return { header: MEAL_DATA };
  },
  components: {
    qInputRule: () => import("../common/QInputWithCustoRules.vue"),
  },
  methods: {
    updateRecipeInputValue(aUpdate, aLabel) {
      this.$emit("updateRecipeInputValue", [aUpdate, aLabel]);
    },
  },
};
</script>