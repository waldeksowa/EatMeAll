<template>
  <q-page class="">
    <div class="">
      <div
        class="full-width bg-primary"
        style="min-height: 20vh; height: 150px"
      >
        <div class="row q-pa-lg justify-end q-gutter-sm">
          <q-btn class="bg-white" label="Generuj Posiłki"></q-btn>
          <q-btn
            class="bg-white"
            label="Wygeneruj Obecny tydzień"
            @click="createScheduleForWeek()"
          ></q-btn>
          <q-btn class="bg-white" label="Kalendarz"></q-btn>
        </div>
      </div>
    </div>
    <div
      v-if="areMealsWasFeached()"
      class="row justify-center q-py-md no-wrap mobile-column col-12"
    >
      <div
        class="col-grow col-shrink q-gutter-sm"
        v-for="(s, j) in mealsSchedule"
        :key="j"
      >
        <div class="text-center text-h5">
          {{ returnCorrectDayLabel(s.day) }}
        </div>
        <div class="q-gutter-sm">
          <div class="" v-for="(d, i) in s.meal" :key="i">
            <div class="text-center text-body1"></div>
            <q-card class="my-card-min">
              <q-card-section>
                <div class="text-center">
                  {{ returnMealTimeLabel(s.date[i]) }}
                </div>
              </q-card-section>
              <q-img
                :src="returnImgByMealTime(s.date[i])"
                class="card-img"
                @click="showDeatilDialog(d)"
              >
                <div
                  class="
                    absolute-full
                    text-subtitle1 text-bold
                    flex flex-center
                    text-center
                  "
                >
                  <q-tooltip>
                    <div class="text-body2">{{ d.name }}</div>
                  </q-tooltip>

                  <div class="long-word">
                    {{ d.name }}
                  </div>
                </div>
              </q-img>

              <q-expansion-item
                expand-separator
                icon="restaurant"
                label="Wartości"
              >
                <div class="text-center">
                  <div>Kalorie: {{ s.meal[i].calorific }}Kcal</div>
                  <div>Tłuszcze: {{ s.meal[i].fat }}(g)</div>
                  <div>Weglodowany: {{ s.meal[i].carbohydrates }}(g)</div>
                  <div>Proteiny: {{ s.meal[i].protein }}(g)</div>
                  <div>Blonnik: {{ s.meal[i].roughage }}(g)</div>
                </div>
              </q-expansion-item>
            </q-card>
          </div>
        </div>
      </div>
      <q-dialog v-model="isDetailInfDialogShow">
        <moreInfoDialog :selectedMeal="selectedMeal"></moreInfoDialog>
      </q-dialog>
      <q-dialog v-model="isScheduleShow">
        <createScheduleDialog
          @fetchScheduledata="fetchScheduledata()"
        ></createScheduleDialog>
      </q-dialog>
    </div>
  </q-page>
</template>

<script>
import { Notify } from "quasar";
import { SCHEDULE } from "../EndpointAddresses";
export default {
  name: "PageIndex",
  data() {
    return {
      isDetailInfDialogShow: false,
      isScheduleShow: false,
      selectedMeal: Object,
      mealsSchedule: [],
      showMealDialog: false,
      dataSelectedDialog: {},
    };
  },
  watch: {
    mealsSchedule: {
      handler(aSchedule) {
        localStorage.mealsSchedule = JSON.stringify(aSchedule);
      },
      deep: true,
    },
  },
  mounted() {
    this.parseLocalStorageValues();
  },
  methods: {
    async fetchScheduledata() {
      this.removeLocalStorage();
      try {
        let responce = await fetch(SCHEDULE);

        if (!responce.ok) {
          this.errorMesage("Ups... Cos poszlo nie tak");
          console.log(responce.status);
        }

        let result = await responce.json();
        this.prepareScheduleaData(result);
      } catch (e) {
        this.errorMesage("Ups... Cos poszlo nie tak");
        console.log(e);
      }
    },
    prepareScheduleaData(aResult) {
      let arr = [];
      for (const [weekDay, mealObject] of Object.entries(aResult)) {
        arr.push({
          day: weekDay,
          date: Object.keys(mealObject),
          meal: Object.values(mealObject),
        });
      }

      this.sortMealDay(arr);
      // this.sortMealTimeAndMealData(arr)

      this.mealsSchedule = arr;
    },
    sortMealDay(Aaray) {
      let ordering = {}, // map for efficient lookup of sortIndex
        sortOrder = [
          "MONDAY",
          "TUESDAY",
          "WEDNESDAY",
          "THURSDAY",
          "FRIDAY",
          "SATURDAY",
          "SUNDAY",
        ];
      for (var i = 0; i < sortOrder.length; i++) ordering[sortOrder[i]] = i;

      Aaray.sort(function (a, b) {
        return ordering[a.day] - ordering[b.day];
      });
    },
    // sortMealTimeAndMealData(aMealSchedule){
    //   let ordering = {},
    //       sortOrder = ['BREAKFAST','SECOND_BREAKFAST','LUNCH','SUPPER','FRIDAY','DINNER'];
    //   for (var i=0; i<sortOrder.length; i++)
    //       ordering[sortOrder[i]] = i;
    //   aMealSchedule.forEach(s => {
    //     return s.date.sort((a, b) => (ordering[a] - ordering[b]))
    //   })
    //   aMealSchedule.forEach(s => {
    //     return s.meal.sort((a, b) =>(ordering[a] - ordering[b]))
    //   })
    // },

    parseLocalStorageValues() {
      if (localStorage.mealsSchedule)
        this.mealsSchedule = JSON.parse(localStorage.mealsSchedule);
    },
    removeLocalStorage() {
      localStorage.removeItem("mealsSchedule");
    },
    showDeatilDialog(aMeal) {
      this.selectedMeal = aMeal;
      this.isDetailInfDialogShow = !this.isDetailInfDialogShow;
    },
    createScheduleForWeek() {
      if (localStorage.mealsSchedule) {
        this.isScheduleShow = !this.isScheduleShow;
      } else {
        this.fetchScheduledata();
      }
    },
    errorMesage(e) {
      Notify.create({
        message: `⚠ ${e}`,
        classes: "full-width text-center bg-negative",
      });
    },
    areMealsWasFeached() {
      if (this.mealsSchedule.length === 0) {
        return false;
      }
      return true;
    },

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
  },

  created() {},
  components: {
    moreInfoDialog: () => import("../components/meals/MoreInfoDialog.vue"),
    createScheduleDialog: () =>
      import("../components/meals/CreateNewSchedule.vue"),
  },
};
</script>
<style lang="sass">
.dialog
  width: 90vw
  height: 90vh

div:first-letter
  text-transform: uppercase

.card-img
  height: 5vh
  @media (max-width:1100px)
    height: 10vh

.my-card-min
  height: 100%
  @media (max-width:1100px)
    width: 90vw

.long-word
  overflow: hidden
  white-space: nowrap
  text-overflow: ellipsis

.mobile-column
  @media (max-width:1100px)
    flex-direction: column
    justify-content: center
    align-items: center

.big-size
  font-size: 48px
</style>
