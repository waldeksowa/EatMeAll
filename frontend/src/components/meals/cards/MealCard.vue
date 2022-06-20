<template>
  <q-card class="const-width q-ma-sm q-py-md relative-position">
    <CardMealTimeLabel :mealTimeLabel="mealTimeLabel" class="text-center" />
    <CardMealName :mealName="mealName" class="text-center" />
    <CardToolsList
      class="full-height full-width absolute-center"
      @replaceMeal="replaceMeal($event)"
    />
  </q-card>
</template>
<script>
import CardMealTimeLabel from "./CardMealTimeLabel.vue";
import CardMealName from "./CardMealName.vue";
import CardToolsList from "./CardToolsList.vue";
export default {
  components: { CardToolsList, CardMealName, CardMealTimeLabel },
  props: { mealTimeLabel: String, mealName: String },
  methods: {
    replaceMeal(aData) {
      this.$emit("replaceMeal", aData);
    },
  },
};
</script>