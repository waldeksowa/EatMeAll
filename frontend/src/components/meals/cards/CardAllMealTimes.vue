<template>
  <div>
    <h1 class="text-h6 text-center">Pory posiłku</h1>
    <div class="">
      <p v-for="(label, i) in mealTimeLabels" :key="`label-${i}`">
        {{ label }}
      </p>
    </div>
  </div>
</template>
<script>
export default {
  props: { mealTimes: Array },
  data() {
    return {
      mealTimeLabels: [],
    };
  },
  mounted() {
    this.returnMealTimes();
  },
  methods: {
    returnMealTimes() {
      let arr = [];
      let mealTimeValues = [
        { label: "Śniadanie", value: "BREAKFAST" },
        { label: "2 Śniadanie", value: "SECOND_BREAKFAST" },
        { label: "Obiad", value: "LUNCH" },
        { label: "Podwieczorek", value: "DINNER" },
        { label: "Kolacja", value: "SUPPER" },
      ];

      this.mealTimes.forEach((item) => {
        for (const { value, label } of mealTimeValues) {
          if (item === value) arr.push(label);
        }
      });
      this.mealTimeLabels = arr;
    },
  },
};
</script>