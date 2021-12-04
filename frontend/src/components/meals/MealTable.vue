<template>
  <q-page>
    <div>
      <div class="row justify-center q-gutter-sm q-ma-md">
        <q-btn
          class="bg-primary text-white"
          label="Wygeneruj Obecny tydzień"
          @click="showDialogToGenerateNewWeekSchedule()"
        ></q-btn>
      </div>
      <div v-if="mealsSchedule.length">
        <div v-if="scheduleCreteActions">
          <h4 class="text-center">Co zrobić z wygenerowanym planem?</h4>
          <div class="row justify-center q-gutter-md">
            <q-btn
              class="bg-primary text-white"
              label="Generuj dalej"
              @click="showDialogToGenerateNewWeekSchedule()"
            ></q-btn>
            <q-btn
              class="bg-primary text-white"
              label="Zapisz"
              @click="postMemberScheduleToServer()"
            ></q-btn>
          </div>
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
              <div
                v-for="(meal, key, j) in mealData.meals"
                :key="`mealData-${j}`"
              >
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
      </div>
      <div v-else>
        <h5 class="text-center">Utwórz najpierw utwórz plan diety</h5>
        <center>
          <q-img src="../../assets/missing.svg" class="error"></q-img>
        </center>
      </div>
    </div>
  </q-page>
</template>
<script>
import { mapGetters, mapActions } from "vuex";
import { SCHEDULE } from "../../EndpointAddresses";
import { date } from "quasar";
export default {
  props: ["mealsSchedule", "isScheduleShow"],
  computed: {
    ...mapGetters("store", ["jwt", "memberIdToShowSchedule"]),
  },
  date() {
    return {
      scheduleCreteActions: false,
    };
  },
  methods: {
    ...mapActions("store", ["notifySucessful"]),
    showDialogToGenerateNewWeekSchedule() {
      this.scheduleCreteActions = true;
      this.$emit("showDialogToGenerateNewWeekSchedule");
    },
    parseDataToSend() {
      let object = [];
      this.mealsSchedule.forEach((e, i) => {
        object.push({
          date: e.date,
          breakfast: e.mealsId.breakfast,
          secondBreakfast: e.mealsId.secondBreakfast,
          lunch: e.mealsId.lunch,
          dinner: e.mealsId.dinner,
          supper: e.mealsId.supper,
        });
      });
      return object;
    },
    postMemberScheduleToServer() {
      this.scheduleCreteActions = false;
      var myHeaders = new Headers();
      myHeaders.append("Authorization", `Bearer ${this.jwt}`);
      myHeaders.append("Content-Type", "application/json");

      var raw = JSON.stringify({
        schedule: this.parseDataToSend(),
        memberId: this.memberIdToShowSchedule,
      });

      var requestOptions = {
        method: "POST",
        headers: myHeaders,
        body: raw,
        redirect: "follow",
      };

      fetch(SCHEDULE, requestOptions)
        .then((response) => {
          return response.json();
        })
        .then((result) => {
          console.log(result);
          return result;
        })
        .catch((error) => {
          console.error("error", error);
        });
      this.notifySucessful("Plan diety zostal zapisany");
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
        { label: "2 Śniadanie", value: "secondBreakfast" },
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
        case "secondBreakfast":
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

.error
  width: 60%
</style>
