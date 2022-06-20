<template>
  <div>
    <q-icon
      size="md"
      name="list"
      @click="isShowAllMealsDialogShowed = !isShowAllMealsDialogShowed"
    >
      <q-tooltip class="text-body2" :offset="[10, 10]">
        Wybierz posiłek z listy
      </q-tooltip>
    </q-icon>
    <q-dialog v-model="isShowAllMealsDialogShowed" class="full-width">
      <q-card
        div
        class="
          q-gutter-y-md q-col-gutter-md q-pa-md
          fit
          row
          wrap
          justify-center
          items-start
          content-start
        "
      >
        <div class="col-grow col-sm-9">
          <h1 class="text-h3 text-center">Zamień posiłek na...</h1>
          <div v-for="(meal, i) in allMeals" :key="`mealCard-${i}`">
            <q-card
              class="const-width q-ma-sm q-pa-md relative-position"
              @click="replaceMeal(meal.id)"
            >
              <h2 class="text-h5">
                <CardMealName :mealName="meal.name" class="text-center" />
              </h2>
              <CardAllMealTimes :mealTimes="meal.mealTime" />
            </q-card>
          </div>
        </div>
      </q-card>
    </q-dialog>
  </div>
</template>
<script>
import { MEALS } from "../../../EndpointAddresses";
import { mapGetters, mapActions } from "vuex";
// import CardMealTimeLabel from "../cards/CardMealTimeLabel.vue";
import CardAllMealTimes from "../cards/CardAllMealTimes.vue";
import CardMealName from "../cards/CardMealName.vue";
export default {
  components: { CardMealName, CardAllMealTimes },
  data() {
    return {
      isShowAllMealsDialogShowed: false,
      allMeals: [],
    };
  },
  computed: {
    ...mapGetters("store", ["jwt"]),
  },
  created() {
    this.fetchAllMeals();
  },
  methods: {
    ...mapActions("store", ["notifyError", "notifySucessful"]),
    replaceMeal(aId) {
      this.isShowAllMealsDialogShowed = false;
      this.$emit("replaceMeal", aId);
    },
    parseMeals(aResult) {
      // console.log("~ aResult", aResult);
      aResult.forEach(async (e) => {
        this.allMeals.push(await this.fetchMealDetails(e.id));
      });
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
    fetchAllMeals() {
      var myHeaders = new Headers();
      myHeaders.append("Authorization", `Bearer ${this.jwt}`);

      var requestOptions = {
        method: "GET",
        headers: myHeaders,
        redirect: "follow",
      };

      fetch(MEALS, requestOptions)
        .then((response) => response.json())
        .then((result) => {
          // console.log("~ parseInt(result.id)", result);
          this.parseMeals(result);
        })
        .catch((error) => {
          console.error(error);
          this.notifyError(error);
        });
    },
  },
};
</script>