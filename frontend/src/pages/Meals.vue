<template>
  <q-page>
    <div class="full-width bg-primary" style="min-height: 20vh; height: 150px">
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
    <mealTable
      @showDeatilDialog="showDeatilDialog($event)"
      :mealsSchedule="mealsSchedule"
    />
    <q-dialog v-model="isDetailInfDialogShow">
      <moreInfoDialog :selectedMeal="selectedMeal"></moreInfoDialog>
    </q-dialog>
    <q-dialog v-model="isScheduleShow">
      <createScheduleDialog
        @fetchScheduledata="fetchScheduledata()"
      ></createScheduleDialog>
    </q-dialog>
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
    showDeatilDialog(aMeal) {
      this.selectedMeal = aMeal;
      this.isDetailInfDialogShow = true;
    },
    parseLocalStorageValues() {
      if (localStorage.mealsSchedule)
        this.mealsSchedule = JSON.parse(localStorage.mealsSchedule);
    },
    removeLocalStorage() {
      localStorage.removeItem("mealsSchedule");
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
  },
  components: {
    moreInfoDialog: () => import("../components/meals/MoreInfoDialog.vue"),
    mealTable: () => import("../components/meals/MealTable.vue"),
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
</style>
