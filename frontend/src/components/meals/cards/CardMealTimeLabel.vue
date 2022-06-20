<template>
  <p>
    {{ returnMealTimeLabel(mealTimeLabel) }}
  </p>
</template>

<script>
export default {
  props: { mealTimeLabel: String },
  methods: {
    returnMealTimeLabel(aMealTime) {
      let mealTimeValues = [
        { label: "Śniadanie", value: "breakfast" },
        { label: "2 Śniadanie", value: "secondBreakfast" },
        { label: "Obiad", value: "lunch" },
        { label: "Kolacja", value: "supper" },
        { label: "Podwieczorek", value: "dinner" },
      ];
      for (const { value, label } of mealTimeValues) {
        if (aMealTime === value) return label;
      }
    },
  },
};
</script>