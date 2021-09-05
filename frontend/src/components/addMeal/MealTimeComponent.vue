<template>
  <div>
    <div class="text-h4 text-center">{{ header }}</div>
    <div class="q-pa-lg container-checkbox">
      <div v-for="(box, index) in checkboxOptions" :key="index">
        <q-checkbox
          class="text-body1"
          size="lg"
          :value="box.checked"
          :label="box.label"
          @click.native="changeChecboxValue(!box.checked, index)"
        />
      </div>
    </div>
  </div>
</template>
<script>
import { MEAL_TIME } from "../../translate/addMeal/sectionHeaders.js";
export default {
  props: ["checkboxOptions"],
  data() {
    return {
      header: MEAL_TIME,
    };
  },
  methods: {
    changeChecboxValue(aValue, aIndex) {
      this.$emit("changeChecboxValue", [aValue, aIndex]);
    },
  },
};
</script>
<style lang="sass">
.container-checkbox
  @media (min-width:1100px)
    display: flex
    justify-content: space-between
</style>