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
    <div class="row q-gutter-md">
      <div
        @click="goToMemberSite(account.id)"
        :userData="membersAccounts"
        v-for="(account, index) in membersAccounts"
        :key="`member-${index}`"
      >
        <q-img src="../assets/Netflix-avatar.jpg" class="tumbnail q-pa-md" />
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
        @fetchScheduleData="fetchScheduleData()"
      ></createScheduleDialog>
    </q-dialog>
  </q-page>
</template>

<script>
import { mapGetters } from "vuex";
import { MEMBER } from "../EndpointAddresses";
import { Notify } from "quasar";
import { RANDOMSCHEDULE } from "../EndpointAddresses";
export default {
  name: "PageIndex",
  data() {
    return {
      isDetailInfDialogShow: false,
      isScheduleShow: false,
      showMealDialog: false,
      selectedMeal: Object,
      mealsSchedule: [],
      dataSelectedDialog: {},
      membersAccounts: [],
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
  computed: {
    ...mapGetters("store", ["jwt"]),
  },
  mounted() {
    this.fetchMembersAccountData();
    this.fetchScheduleData();
    this.parseLocalStorageValues();
  },
  methods: {
    fetchMembersAccountData() {
      var myHeaders = new Headers();
      myHeaders.append("Authorization", `Bearer ${this.jwt}`);

      var requestOptions = {
        method: "GET",
        headers: myHeaders,
        redirect: "follow",
      };
      fetch(MEMBER, requestOptions)
        .then((response) => response.json())
        .then((result) => (this.membersAccounts = result))
        .catch((error) => console.log("error", error));
    },
    fetchScheduleData() {
      this.removeLocalStorage();

      var myHeaders = new Headers();
      myHeaders.append("Authorization", `Bearer ${this.jwt}`);

      var requestOptions = {
        method: "GET",
        headers: myHeaders,
        redirect: "follow",
      };
      fetch(RANDOMSCHEDULE, requestOptions)
        .then((response) => {
          console.log("~ response", response);
          if (!response.ok) {
            this.errorMesage("Ups... Cos poszlo nie tak");
          }
          return response.json();
        })
        .then((result) => {
          console.log("~ result", result);
          this.prepareScheduleaData(result);
        })
        .catch((e) => {
          this.errorMesage("Ups... Cos poszlo nie tak");
          console.log(e);
        });
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
        this.fetchScheduleData();
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

.tumbnail
  width: 25px
</style>
