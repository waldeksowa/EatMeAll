<template>
  <div class="row justify-center q-py-md no-wrap mobile-column col-12">
    <div class="q-gutter-md row">
      <div
        class="col-grow col-shrink"
        v-for="(mealData, mealDay, j) in mealsSchedule"
        :key="`mealForDayInWeek-${j}`"
      >
        <div>
          <p class="text-center text-h5">
            {{ returnCorrectDayLabel(mealDay) }}
          </p>
          <div class="q-gutter-md">
            <div
              v-for="(macrosValues, mealTime, i) in mealData.meals"
              :key="`mealData-${i}`"
            >
              <div class="mobile-column">
                <q-card class="const-width">
                  <div class="text-center">
                    <p>
                      {{ returnMealTimeLabel(mealTime) }}
                    </p>
                    <p>Nazwa: {{ macrosValues.name }}</p>
                  </div>
                  <q-img
                    :src="returnImgByMealTime(mealTime)"
                    class="card-img"
                  />
                  <q-expansion-item
                    expand-separator
                    label="Wartości"
                    class="const-width text-center"
                  >
                    <p>Czas przygotowania: {{ macrosValues.prepareTime }}</p>
                    <p>Kalorie: {{ macrosValues.calorific }}</p>
                    <p>
                      Węglowodany:
                      {{ macrosValues.carbohydrates }}
                    </p>
                    <p>Tłuszcze: {{ macrosValues.fat }}</p>
                    <p>
                      Białko:
                      {{ macrosValues.protein }}
                    </p>
                    <p>
                      Błonnik:
                      {{ macrosValues.roughage }}
                    </p>
                  </q-expansion-item>
                </q-card>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: ["mealsSchedule"],
  methods: {
    returnCorrectDayLabel(aMealDay) {
      let dayTimeValues = [
        { label: "Poniedziałek", value: "MONDAY" },
        { label: "Wtorek", value: "TUESDAY" },
        { label: "Środa", value: "WEDNESDAY" },
        { label: "Czwartek", value: "THURSDAY" },
        { label: "Piatek", value: "FRIDAY" },
        { label: "Sobota", value: "SATURDAY" },
        { label: "Niedziela", value: "SUNDAY" },
      ];
      for (const { value, label } of dayTimeValues) {
        if (aMealDay === value) return label;
      }
    },
    returnMealTimeLabel(aMealTime) {
      let mealTimeValues = [
        { label: "Śniadanie", value: "BREAKFAST" },
        { label: "2 Śniadanie", value: "SECOND_BREAKFAST" },
        { label: "Obiad", value: "LUNCH" },
        { label: "Kolacja", value: "SUPPER" },
        { label: "Podwieczorek", value: "DINNER" },
      ];
      for (const { value, label } of mealTimeValues) {
        if (aMealTime === value) return label;
      }
    },
    returnImgByMealTime(aMealTime) {
      switch (aMealTime) {
        case "BREAKFAST":
          return "https://images.unsplash.com/photo-1525351484163-7529414344d8?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80";
        case "SECOND_BREAKFAST":
          return "https://images.unsplash.com/photo-1497888329096-51c27beff665?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1351&q=80";
        case "LUNCH":
          return "https://images.unsplash.com/photo-1563897539633-7374c276c212?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=943&q=80";
        case "DINNER":
          return "https://images.unsplash.com/photo-1414235077428-338989a2e8c0?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80";
        case "SUPPER":
          return "https://images.unsplash.com/photo-1598515213345-d710d121c709?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80";
        default:
          return "https://images.unsplash.com/photo-1575282247585-d56c93eb0d8b?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1489&q=80";
      }
    },
    showDeatilDialog(aMeal) {
      this.$emit("showDeatilDialog", aMeal);
    },
  },
};
</script>
<style lang="sass">
.const-width
  width: 150px
.card-img
  width: 100%
  height: 10vh
  @media (max-width:1100px)
    height: 10vh

.my-card-min
  height: 100%
  @media (max-width:1100px)
    width: 90vw
.mobile-column
  @media (max-width:1100px)
    flex-direction: column
    justify-content: center
    align-items: center
.big-size
  font-size: 48px
</style>
