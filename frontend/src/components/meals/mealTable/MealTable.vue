<template>
  <q-page>
    <div>
      <div class="row justify-center q-gutter-sm q-ma-md">
        <q-btn
          class="bg-primary text-white"
          label="Wygeneruj Obecny tydzień"
          @click="showDialogToGenerateNewWeekSchedule()"
        ></q-btn>
        <q-btn
          class="bg-primary text-white"
          label="Czy zapisać zmiany ?"
          v-if="isSaveChangesButtonShow"
          @click="postSchedule()"
        ></q-btn>
      </div>
      <div v-if="mealsSchedule.length">
        <SaveScheduleDialog
          :showMessageToGenerateNewSchedule="showMessageToGenerateNewSchedule"
          :mealsSchedule="mealsSchedule"
          @showDialogToGenerateNewWeekSchedule="
            showDialogToGenerateNewWeekSchedule()
          "
          @hideDialogToGenerateNewWeekSchedule="
            hideDialogToGenerateNewWeekSchedule()
          "
        />
        <div class="row justify-center q-py-md no-wrap mobile-column col-12">
          <div
            class="col-grow col-shrink"
            v-for="(mealData, i) in mealsSchedule"
            :key="`mealForDayInWeek-${i}`"
          >
            <DayOfTheWeek :weekDay="mealData.date" />
            <div
              v-for="(meal, key, j) in mealData.meals"
              :key="`mealData-${j}`"
              @click="changeClickedCard(i, key)"
            >
              <MealCard
                :mealTimeLabel="key"
                :mealName="meal.name"
                @replaceMeal="replaceMeal($event)"
              />
            </div>
          </div>
        </div>
      </div>
      <div v-else>
        <ErrorMessageWhenScheduleIsNotCreated />
      </div>
    </div>
  </q-page>
</template>
<script>
import { date } from "quasar";
import { mapActions, mapGetters } from "vuex";
import SaveScheduleDialog from "./SaveScheduleDialog.vue";
import DayOfTheWeek from "./DayOfTheWeek.vue";
import ErrorMessageWhenScheduleIsNotCreated from "./ErrorMessageWhenScheduleIsNotCreated.vue";
import MealCard from "../cards/MealCard.vue";
import { SCHEDULE } from "../../../EndpointAddresses";
export default {
  components: {
    SaveScheduleDialog,
    DayOfTheWeek,
    MealCard,
    ErrorMessageWhenScheduleIsNotCreated,
  },
  props: ["mealsSchedule", "isSaveChangesButtonShow"],
  data() {
    return {
      clickedCard: null,
      showMessageToGenerateNewSchedule: false,
    };
  },
  computed: { ...mapGetters("store", ["jwt", "memberIdToShowSchedule"]) },
  methods: {
    ...mapActions("store", ["notifyError", "notifySucessful"]),
    parseMealScheduleToPost() {
      const timeStamp = Date.now();
      const formattedDate = date.formatDate(timeStamp, "YYYY-MM-DD");
      let arr = [];
      this.mealsSchedule.forEach((item) => {
        item.mealsId.date = formattedDate;
        arr.push(item.mealsId);
      });
      return arr;
    },
    postSchedule() {
      var myHeaders = new Headers();
      myHeaders.append("Authorization", `Bearer ${this.jwt}`);
      myHeaders.append("Content-Type", "application/json");

      var raw = JSON.stringify({
        schedule: this.parseMealScheduleToPost(),
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
        .catch((error) => {
          console.error(error);
          this.notifyError(error.messag);
        });
    },
    changeClickedCard(aCol, aMealTime) {
      this.clickedCard = { day: aCol, mealTime: aMealTime };
    },
    showDialogToGenerateNewWeekSchedule() {
      this.showMessageToGenerateNewSchedule = true;
      this.$emit("showDialogToGenerateNewWeekSchedule");
    },
    hideDialogToGenerateNewWeekSchedule() {
      this.showMessageToGenerateNewSchedule = false;
    },
    replaceMeal(aData) {
      console.log("~ this.clickedCard", this.clickedCard);
      const clicked = { ...this.clickedCard };
      this.$emit("replaceMeal", {
        id: aData,
        clickedCardData: clicked,
      });
    },
  },
};
</script>
<style lang="sass" scoped>
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
