<template>
  <q-page>
    <div class="row justify-center q-gutter-sm q-ma-md">
      <q-btn class="bg-primary text-white" label="Generuj Posiłki"></q-btn>
      <q-btn
        class="bg-primary text-white"
        label="Wygeneruj Obecny tydzień"
        @click="openDialog()"
      ></q-btn>
      <q-btn class="bg-primary text-white" label="Kalendarz"></q-btn>
      <q-btn
        class="bg-primary text-white"
        label="Zapisz"
        @click="postMemberScheduleToServer()"
      ></q-btn>
    </div>

    <div class="row justify-center q-py-md no-wrap mobile-column col-12">
      <div
        class="col-grow col-shrink"
        v-for="(mealData, i) in mealsSchedule"
        :key="`mealForDayInWeek-${i}`"
      >
        <div>
          <p class="text-center text-h5">
            {{ returnCorrectDayLabel(mealData.date) }}
          </p>
          <div v-for="(meal, key, j) in mealData.meals" :key="`mealData-${j}`">
            <div class="mobile-column">
              <q-card class="const-width q-ma-sm">
                <div class="text-center">
                  <p>
                    {{ returnMealTimeLabel(key) }}
                  </p>
                  <p>Nazwa: {{ meal.name }}</p>
                </div>
                <q-img :src="returnImgByMealTime(key)" class="card-img" />
                <q-expansion-item
                  expand-separator
                  label="Wartości"
                  class="const-width text-center"
                >
                  <p>Czas przygotowania: {{ meal.prepareTime }}</p>
                  <p>Kalorie: {{ meal.calorific }}</p>
                  <p>
                    Węglowodany:
                    {{ meal.carbohydrates }}
                  </p>
                  <p>Tłuszcze: {{ meal.fat }}</p>
                  <p>
                    Białko:
                    {{ meal.protein }}
                  </p>
                  <p>
                    Błonnik:
                    {{ meal.roughage }}
                  </p>
                </q-expansion-item>
              </q-card>
            </div>
          </div>
        </div>
      </div>
    </div>
  </q-page>
</template>
<script>
import { SCHEDULE } from "../../EndpointAddresses";
import { date } from "quasar";
export default {
  props: ["mealsSchedule", "isScheduleShow"],
  methods: {
    openDialog() {
      this.$emit("openDialog");
    },
    postMemberScheduleToServer() {
      var myHeaders = new Headers();
      myHeaders.append("Authorization", `Bearer ${this.jwt}`);
      myHeaders.append("Content-Type", "application/json");

      let scheduleToPost = this.createParsedSchedule();
      var raw = JSON.stringify({
        schedule: scheduleToPost,
        memberId: this.memberIdToShowSchedule,
      });

      var requestOptions = {
        method: "POST",
        headers: myHeaders,
        body: raw,
        redirect: "follow",
      };

      fetch(SCHEDULE, requestOptions)
        .then((response) => response.json())
        .then((result) => console.log(result))
        .catch((error) => console.log("error", error));
    },
    returnMealsId() {
      let meals = [];
      for (const [key, val] of Object.entries(this.mealsSchedule)) {
        meals.push({
          mealTime: Object.keys(val.meals),
          mealValues: Object.values(val.meals),
        });
      }
      return meals;
    },
    createParsedSchedule() {
      let meals = this.returnMealsId();
      let postSchedule = [];
      const timeStamp = Date.now();
      const formattedString = date.formatDate(timeStamp, "YYYY-MM-DD");
      meals.forEach((day) => {
        let breakfast = day.mealTime.indexOf("breakfast");
        let secondBreakfast = day.mealTime.indexOf("secondbreakfast");
        let lunch = day.mealTime.indexOf("lunch");
        let dinner = day.mealTime.indexOf("dinner");
        let supper = day.mealTime.indexOf("supper");
        postSchedule.push({
          date: formattedString,
          ...(breakfast >= 0
            ? { breakfast: day.mealValues[breakfast].id }
            : {}),
          ...(secondBreakfast >= 0
            ? { secondBreakfast: day.mealValues[secondBreakfast].id }
            : {}),
          ...(lunch >= 0 ? { lunch: day.mealValues[lunch].id } : {}),
          ...(dinner >= 0 ? { dinner: day.mealValues[dinner].id } : {}),
          ...(supper >= 0 ? { supper: day.mealValues[supper].id } : {}),
        });
      });
      console.log("~ postSchedule", postSchedule);
      return postSchedule;
    },
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
    returnMealTimeLabel(aMealTime) {
      let mealTimeValues = [
        { label: "Śniadanie", value: "breakfast" },
        { label: "2 Śniadanie", value: "secondbreakfast" },
        { label: "Obiad", value: "lunch" },
        { label: "Kolacja", value: "supper" },
        { label: "Podwieczorek", value: "dinner" },
      ];
      for (const { value, label } of mealTimeValues) {
        if (aMealTime === value) return label;
      }
    },
    returnImgByMealTime(aMealTime) {
      switch (aMealTime) {
        case "breakfast":
          return "https://images.unsplash.com/photo-1525351484163-7529414344d8?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80";
        case "secondbreakfast":
          return "https://images.unsplash.com/photo-1497888329096-51c27beff665?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1351&q=80";
        case "lunch":
          return "https://images.unsplash.com/photo-1563897539633-7374c276c212?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=943&q=80";
        case "dinner":
          return "https://images.unsplash.com/photo-1414235077428-338989a2e8c0?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80";
        case "supper":
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
