<template>
  <q-input
    filled
    lazy-rules
    :type="props.type"
    :value="props.value"
    :label="props.label"
    :rules="[(val) => (val && val.length > 0) || this.errorMessage]"
    @input="$emit('updateRecipeInputValue', $event)"
  />
</template>
<script>
import { ERROR_RULES } from "src/translate/addMeal/inputLabels.js";
export default {
  props: ["props"],
  data() {
    return {
      errorMessage: ERROR_RULES,
    };
  },
};
</script>