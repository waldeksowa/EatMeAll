<template>
  <q-page>
    <div v-if="membersAccounts.length" class="row justify-end">
      <div
        v-for="(account, index) in membersAccounts"
        :key="`member-${index}`"
        :class="
          memberIdToShowSchedule === account.id ? 'bg-accent' : 'bg-white'
        "
        class="member-width"
        @click="renderScheduleForMember(account.id)"
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
    <div v-else class="row justify-center">
      <h5 class="text-center">Nie dodaleś mamberów</h5>
    </div>

    <mealTable
      @showDialogToGenerateNewWeekSchedule="
        showDialogToGenerateNewWeekSchedule()
      "
      @replaceMeal="replaceMeal($event)"
      :mealsSchedule="mealsSchedule"
      :isSaveChangesButtonShow="isSaveChangesButtonShow"
      :isScheduleShow="isDialogToGenerateNewWeekSchedule"
    />
    <q-dialog v-model="isDialogToGenerateNewWeekSchedule">
      <createScheduleDialog
        @fetchRandomSheduleData="fetchRandomSheduleData()"
      ></createScheduleDialog>
    </q-dialog>
  </q-page>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import { MEMBER } from "../EndpointAddresses";
import { RANDOMSCHEDULE, MEMBER_SCHEDULE, MEALS } from "../EndpointAddresses";
export default {
  name: "PageIndex",
  data() {
    return {
      isDialogToGenerateNewWeekSchedule: false,
      isSaveChangesButtonShow: false,
      mealsSchedule: [],
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
    ...mapActions("store", [
      "updateMemberIdToShowSchedule",
      "notifyError",
      "showLoading",
    ]),
    async parseMemberScheduleData(aSchedule) {
      let arr = [];
      for (let i = 0; i < aSchedule.schedule.length; i++) {
        arr.push({
          date: aSchedule.schedule[i].date,
          meals: {
            breakfast: await this.fetchMealDetails(
              aSchedule.schedule[i].breakfast
            ),
            secondBreakfast: await this.fetchMealDetails(
              aSchedule.schedule[i].secondBreakfast
            ),
            dinner: await this.fetchMealDetails(aSchedule.schedule[i].dinner),
            lunch: await this.fetchMealDetails(aSchedule.schedule[i].lunch),
            supper: await this.fetchMealDetails(aSchedule.schedule[i].supper),
          },
          mealsId: {
            breakfast: aSchedule.schedule[i].breakfast,
            secondBreakfast: aSchedule.schedule[i].secondBreakfast,
            dinner: aSchedule.schedule[i].dinner,
            lunch: aSchedule.schedule[i].lunch,
            supper: aSchedule.schedule[i].supper,
          },
        });
      }
      this.mealsSchedule = arr;
    },
    async fetchMealDetails(aId) {
      var myHeaders = new Headers();
      myHeaders.append("Authorization", `Bearer ${this.jwt}`);
      let requestOptions = {
        method: "GET",
        headers: myHeaders,
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
          this.notifyError(
            "Ups... Nie mogę pobrać danych o kątach użytkowników"
          );
          console.log(error);
        });
    },
    fetchRandomSheduleData() {
      this.showLoading();
      var myHeaders = new Headers();
      myHeaders.append("Authorization", `Bearer ${this.jwt}`);

      var requestOptions = {
        method: "GET",
        headers: myHeaders,
        redirect: "follow",
      };
      fetch(RANDOMSCHEDULE, requestOptions)
        .then((response) => {
          if (!response.ok) {
            console.log("~ response", response);
            this.notifyError("Ups... Nie mogę wylosować planu diety");
          }
          return response.json();
        })
        .then((result) => {
          console.log("~ random", result);
          this.parseMemberScheduleData(result);
        })
        .catch((error) => {
          this.notifyError("Ups... Nie mogę wylosować planu diety");
          console.log(error);
        });
    },
    fetchMemberSheduleData() {
      this.showLoading();
      var myHeaders = new Headers();
      myHeaders.append("Authorization", `Bearer ${this.jwt}`);

      var requestOptions = {
        method: "GET",
        headers: myHeaders,
        redirect: "follow",
      };
      const url = `${MEMBER_SCHEDULE}${this.memberIdToShowSchedule}`;
      fetch(url, requestOptions)
        .then((response) => {
          if (!response.ok) {
            this.notifyError(
              "Ups... Nie mogę pobrać planu diety dla użytkownika"
            );
          }
          return response.json();
        })
        .then((result) => {
          this.parseMemberScheduleData(result);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    replaceMeal(aData) {
      const { id, clickedCardData: c } = aData;
      this.mealsSchedule[c.day].mealsId[c.mealTime] = id;
      this.isSaveChangesButtonShow = true;
    },
    showDialogToGenerateNewWeekSchedule() {
      this.isDialogToGenerateNewWeekSchedule = true;
    },
    renderScheduleForMember(aMemberId) {
      this.updateMemberIdToShowSchedule(aMemberId);
      this.fetchMemberSheduleData();
    },
  },
  components: {
    // moreInfoDialog: () => import("../components/meals/MoreInfoDialog.vue"),
    mealTable: () => import("../components/meals/mealTable/MealTable.vue"),
    createScheduleDialog: () =>
      import("../components/meals/dialogs/CreateNewSchedule.vue"),
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
</style>
