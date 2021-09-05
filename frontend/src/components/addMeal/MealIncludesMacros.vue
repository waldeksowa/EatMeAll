<template>
  <div>
    <div class="q-col-gutter-y-lg q-mt-lg">
      <div class="text-h4 text-center">{{ header }}</div>
      <macrosDiagram :dataArray="dataArray"></macrosDiagram>
    </div>
  </div>
</template>
<script>
import { MEAL_INCLUDES } from "../../translate/addMeal/sectionHeaders.js";
export default {
  props: ["dataArray"],
  data() {
    return {
      header: MEAL_INCLUDES,
    };
  },
  components: {
    macrosDiagram: () => import("../common/MacrosDiagram.vue"),
  },
};
</script>