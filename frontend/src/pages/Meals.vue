<template>
  <q-page>
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
      @openDialog="openDialog"
      :mealsSchedule="mealsSchedule"
      :isScheduleShow="isScheduleShow"
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
  MEALS,
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
  created() {
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
    openDialog() {
      this.isScheduleShow = true;
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
    fetchMemberSheduleData() {
      // var myHeaders = new Headers();
      // myHeaders.append("Authorization", `Bearer ${this.jwt}`);

      // var requestOptions = {
      //   method: "GET",
      //   headers: myHeaders,
      //   redirect: "follow",
      // };
      // const url = `${MEMBER_SCHEDULE}${this.memberIdToShowSchedule}`;
      // fetch(RANDOMSCHEDULE, requestOptions)
      //   .then((response) => {
      //     if (!response.ok) {
      //       this.errorMesage("Ups... Cos poszlo nie tak");
      //     }
      //     return response.json();
      //   })
      //   .then((result) => {
      //     console.log("~ random", result);
      //     this.mealsSchedule = result;
      //   })
      //   .catch((error) => {
      //     this.errorMesage("Ups... Cos poszlo nie tak");
      //     console.log(error);
      //   });

      let a = {
        createdAt: "2021-11-06T11:03:15.917+0000",
        updatedAt: "2021-11-06T11:03:15.917+0000",
        version: 0,
        scheduleDate: "2021-09-24",
        schedule: [
          {
            date: "2021-09-24",
            breakfast: 1,
            secondBreakfast: 1,
            lunch: 1,
            dinner: 1,
            supper: 1,
            prepareTime: 55,
            calorific: 33.4,
            protein: 1.1,
            fat: 0.2,
            carbohydrates: 7.1,
            roughage: 0.4,
          },
          {
            date: "2021-09-25",
            breakfast: 1,
            secondBreakfast: 1,
            lunch: 1,
            dinner: 1,
            supper: 1,
            prepareTime: 55,
            calorific: 33.4,
            protein: 1.1,
            fat: 0.2,
            carbohydrates: 7.1,
            roughage: 0.4,
          },
          {
            date: "2021-09-26",
            breakfast: 1,
            secondBreakfast: 1,
            lunch: 1,
            dinner: 1,
            supper: 1,
            prepareTime: 55,
            calorific: 33.4,
            protein: 1.1,
            fat: 0.2,
            carbohydrates: 7.1,
            roughage: 0.4,
          },
          {
            date: "2021-09-27",
            breakfast: 1,
            secondBreakfast: 1,
            lunch: 1,
            dinner: 1,
            supper: 1,
            prepareTime: 55,
            calorific: 33.4,
            protein: 1.1,
            fat: 0.2,
            carbohydrates: 7.1,
            roughage: 0.4,
          },
          {
            date: "2021-09-28",
            breakfast: 1,
            secondBreakfast: 1,
            lunch: 1,
            dinner: 1,
            supper: 1,
            prepareTime: 55,
            calorific: 33.4,
            protein: 1.1,
            fat: 0.2,
            carbohydrates: 7.1,
            roughage: 0.4,
          },
          {
            date: "2021-09-29",
            breakfast: 1,
            secondBreakfast: 1,
            lunch: 1,
            dinner: 1,
            supper: 1,
            prepareTime: 55,
            calorific: 33.4,
            protein: 1.1,
            fat: 0.2,
            carbohydrates: 7.1,
            roughage: 0.4,
          },
          {
            date: "2021-09-30",
            breakfast: 1,
            secondBreakfast: 1,
            lunch: 1,
            dinner: 1,
            supper: 1,
            prepareTime: 55,
            calorific: 33.4,
            protein: 1.1,
            fat: 0.2,
            carbohydrates: 7.1,
            roughage: 0.4,
          },
        ],
        memberId: 1,
        id: 49,
      };
      this.parseMemberScheduleData(a);
    },
    async parseMemberScheduleData(aSchedule) {
      let arr = [];

      for (let i = 0; i < aSchedule.schedule.length; i++) {
        arr.push({
          date: aSchedule.schedule[i].date,
          meals: {
            breakfast: await this.fetchMealDetails(
              aSchedule.schedule[i].breakfast
            ),
            secondbreakfast: await this.fetchMealDetails(
              aSchedule.schedule[i].secondBreakfast
            ),
            dinner: await this.fetchMealDetails(aSchedule.schedule[i].dinner),
            lunch: await this.fetchMealDetails(aSchedule.schedule[i].lunch),
            supper: await this.fetchMealDetails(aSchedule.schedule[i].supper),
          },
          mealsId: {
            breakfast: aSchedule.schedule[i].breakfast,
            secondbreakfast: aSchedule.schedule[i].secondBreakfast,
            dinner: aSchedule.schedule[i].dinner,
            lunch: aSchedule.schedule[i].lunch,
            supper: aSchedule.schedule[i].supper,
          },
        });
      }
      this.mealsSchedule = arr;
    },
    async fetchMealDetails(aId) {
      let requestOptions = {
        method: "GET",
        redirect: "follow",
      };
      try {
        let responce = await fetch(`${MEALS}${aId}`, requestOptions);
        let result = await responce.json();
        return result;
      } catch (err) {
        console.log(err);
      }
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
