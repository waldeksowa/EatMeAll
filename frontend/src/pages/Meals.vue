<template>
  <q-page>
    <div class="full-width bg-primary" style="min-height: 20vh; height: 150px">
      <div class="row q-pa-lg justify-end q-gutter-sm">
        <q-btn class="bg-white" label="Generuj Posiłki"></q-btn>
        <q-btn
          class="bg-white"
          label="Wygeneruj Obecny tydzień"
          @click="isScheduleShow = !isScheduleShow"
        ></q-btn>
        <q-btn class="bg-white" label="Kalendarz"></q-btn>
        <q-btn
          class="bg-white"
          label="Zapisz"
          @click="postMemberScheduleToServer()"
        ></q-btn>
      </div>
    </div>
    <div class="row justify-end">
      <div
        v-for="(account, index) in membersAccounts"
        :key="`member-${index}`"
        :class="
          memberIdToShowSchedule === account.id ? 'bg-accent' : 'bg-white'
        "
        class="member-width"
        @click="showUserSchedule(account.id)"
      >
        <div class="q-my-sm">
          <center>
            <q-img src="../assets/Netflix-avatar.jpg" class="tumbnail" />
            <p class="big-first-letter text-center q-ma-sm long-word">
              {{ account.name }}
            </p>
          </center>
        </div>
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
        @fetchMemberSheduleData="fetchMemberSheduleData()"
      ></createScheduleDialog>
    </q-dialog>
  </q-page>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import { MEMBER } from "../EndpointAddresses";
import {
  RANDOMSCHEDULE,
  SCHEDULE,
  MEMBER_SCHEDULE,
} from "../EndpointAddresses";
import { date } from "quasar";
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
  computed: {
    ...mapGetters("store", ["jwt", "memberIdToShowSchedule"]),
  },
  mounted() {
    this.fetchMembersAccountData();
    this.fetchMemberSheduleData();
  },
  methods: {
    ...mapActions("store", ["updateMemberIdToShowSchedule", "errorMesage"]),
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
        .catch((error) => {
          this.errorMesage("Ups... Cos poszlo nie tak");
          console.log(error);
        });
    },
    showUserSchedule(aMemberId) {
      this.updateMemberIdToShowSchedule(aMemberId);
      this.fetchMemberSheduleData();
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
        let breakfast = day.mealTime.indexOf("BREAKFAST");
        let secondBreakfast = day.mealTime.indexOf("SECOND_BREAKFAST");
        let lunch = day.mealTime.indexOf("LUNCH");
        let dinner = day.mealTime.indexOf("DINNER");
        let supper = day.mealTime.indexOf("SUPPER");
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
    fetchMemberSheduleData() {
      var myHeaders = new Headers();
      myHeaders.append("Authorization", `Bearer ${this.jwt}`);

      var requestOptions = {
        method: "GET",
        headers: myHeaders,
        redirect: "follow",
      };
      const url = `${MEMBER_SCHEDULE}${this.memberIdToShowSchedule}`;
      fetch(RANDOMSCHEDULE, requestOptions)
        .then((response) => {
          if (!response.ok) {
            this.errorMesage("Ups... Cos poszlo nie tak");
          }
          return response.json();
        })
        .then((result) => {
          console.log("~ result", result);
          this.mealsSchedule = result;
        })
        .catch((error) => {
          this.errorMesage("Ups... Cos poszlo nie tak");
          console.log(error);
        });
    },
    showDeatilDialog(aMeal) {
      this.selectedMeal = aMeal;
      this.isDetailInfDialogShow = true;
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
.member-width
  width: 90px
.tumbnail
  width: 40px
  border-radius: 100px
  &:hover
    transition-property: background-color font-size transform color
    transition-timing-function: ease-in-out
    transition-duration: 3s
    border-radius: 0px
</style>
