<template>
  <div v-if="showMessageToGenerateNewSchedule">
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
</template>
<script>
import { mapGetters, mapActions } from "vuex";
import { SCHEDULE } from "../../../EndpointAddresses";
export default {
  props: { showMessageToGenerateNewSchedule: Boolean, mealsSchedule: Array },
  computed: {
    ...mapGetters("store", ["jwt", "memberIdToShowSchedule"]),
  },
  methods: {
    ...mapActions("store", ["notifySucessful"]),
    showDialogToGenerateNewWeekSchedule() {
      this.$emit("showDialogToGenerateNewWeekSchedule");
    },
    hideDialogToGenerateNewWeekSchedule() {
      this.$emit("hideDialogToGenerateNewWeekSchedule");
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
          this.notifySucessful("Plan diety zostal zapisany");
          this.hideDialogToGenerateNewWeekSchedule();
          return result;
        })
        .catch((error) => {
          console.error("error", error);
        });
    },
  },
};
</script>