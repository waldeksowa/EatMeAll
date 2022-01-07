<template>
  <p class="text-center text-h5">
    {{ returnCorrectDayLabel(weekDay) }}
  </p>
</template>
<script>
import { date } from "quasar";
export default {
  props: { weekDay: String },
  methods: {
    returnCorrectDayLabel(aMealDay) {
      let dayTimeValues = [
        { label: "Poniedziałek", value: 1 },
        { label: "Wtorek", value: 2 },
        { label: "Środa", value: 3 },
        { label: "Czwartek", value: 4 },
        { label: "Piatek", value: 5 },
        { label: "Sobota", value: 6 },
        { label: "Niedziela", value: 7 },
      ];
      for (const { value, label } of dayTimeValues) {
        if (date.getDayOfWeek(aMealDay) === value) return label;
      }
    },
  },
};
</script>