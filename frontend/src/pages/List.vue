<template>
  <q-page>
    <div>
      <div v-if="showWeekView">
        <div class="q-gutter-y-lg" v-for="m in meals" :key="m.id">
          <div class="row no-wrap q-gutter-md justify-center mobile-column">
            <div class="col-grow col-shrink" v-for="(t, j) in 5" :key="j">
              <q-card class="my-card" v-if="m.mealTime[j]">
                <q-img
                  :src="returnImgByMealTime(m.mealTime[j])"
                  style="height: 10vh"
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
                      <div class="text-body2">{{ m.name }}</div>
                    </q-tooltip>
                    <div class="long-word">
                      {{ m.name }}
                    </div>
                  </div>
                </q-img>
                <q-card-section class="q-pt-md bg-grey-3">
                  <q-chip :label="m.author" icon="person">
                    <q-tooltip>
                      <div class="text-body2">Autor</div>
                    </q-tooltip>
                  </q-chip>
                  <q-chip
                    :label="returnPreparationTime(m.prepareTime)"
                    icon="timer"
                  >
                    <q-tooltip>
                      <div class="text-body2">Czas przygotowywania</div>
                    </q-tooltip>
                  </q-chip>
                  <q-chip :label="returnMealTime(m.mealTime[j])" icon="cake">
                    <q-tooltip>
                      <div class="text-body2">Pora Posiłku</div>
                    </q-tooltip>
                  </q-chip>
                  <div class="absolute-bottom-right q-ma-sm">
                    <q-btn
                      class="bg-primary text-white"
                      round
                      icon="visibility"
                      @click="showDialog(m, m.mealTime[j])"
                    >
                      <q-tooltip>
                        <div class="text-body2">Podgląd</div>
                      </q-tooltip>
                    </q-btn>
                  </div>
                </q-card-section>
                <!-- <q-card-section class="q-gutter-y-lg">
                        <div class="q-gutter-xs column" style="max-width: 300px" >
                        <q-chip v-for="(p,j) in m.products" :key="j" :label="p.product.name">
                            <q-tooltip>
                            <div class="text-body2">{{ p.product.name }}</div>
                            </q-tooltip>
                        </q-chip>
                        </div>
                    </q-card-section> -->
              </q-card>
              <q-card class="my-card bg-primary" v-else>
                <q-item
                  to="/dodajposilek"
                  name="dodaj"
                  class="full-width full-height"
                >
                  <q-tooltip>
                    <div class="text-body2">Dodaj posiłek</div>
                  </q-tooltip>
                  <q-item-section class="absolute-center">
                    <q-icon name="add" class="text-white big-size"></q-icon>
                  </q-item-section>
                </q-item>
              </q-card>
            </div>
          </div>
          <q-dialog v-model="showMealDialog">
            <q-card style="width: 80vw; height: 80vh" class="q-pa-lg">
              <q-card-section class="q-gutter-y-lg">
                <q-input
                  filled
                  type="text"
                  v-model="dataSelectedDialog.name"
                  label="Nazwa Przepisu"
                  :disable="disableEditing"
                  lazy-rules
                  :rules="[(val) => !!val || 'To pole jest wymagane']"
                />
                <q-input
                  filled
                  type="text"
                  v-model="dataSelectedDialog.prepareTime"
                  label="Czas Przygotowania"
                  :disable="disableEditing"
                  lazy-rules
                  :rules="[(val) => !!val || 'To pole jest wymagane']"
                />
                <q-input
                  filled
                  type="text"
                  v-model="dataSelectedDialog.author"
                  label="Autor"
                  :disable="disableEditing"
                  lazy-rules
                  :rules="[(val) => !!val || 'To pole jest wymagane']"
                />
                <q-input
                  filled
                  type="text"
                  v-model="dataSelectedDialog.description"
                  label="Opis"
                  :disable="disableEditing"
                  lazy-rules
                  :rules="[(val) => !!val || 'To pole jest wymagane']"
                />
                <div class="q-col-gutter-y-lg q-mt-lg">
                  <div class="text-h4 text-center">Posilek Zawiera</div>
                  <div class="text-body1">
                    Kalorie: {{ dataSelectedDialog.calorific }} Kcal
                  </div>
                  <div class="text-body1">
                    Węglowodany ({{ dataSelectedDialog.carbohydrates }}/100)g
                  </div>
                  <q-linear-progress
                    stripe
                    rounded
                    size="20px"
                    :value="
                      calcValueforProgressBar(dataSelectedDialog.carbohydrates)
                    "
                    color="red"
                    class="q-mt-sm"
                  />
                  <div class="text-body1">
                    Tłuszcze ({{ dataSelectedDialog.fat }}/100)g
                  </div>
                  <q-linear-progress
                    stripe
                    rounded
                    size="20px"
                    :value="calcValueforProgressBar(dataSelectedDialog.fat)"
                    color="green"
                    class="q-mt-sm"
                  />
                  <div class="text-body1">
                    Białko ({{ dataSelectedDialog.protein }}/100)g
                  </div>
                  <q-linear-progress
                    stripe
                    rounded
                    size="20px"
                    :value="calcValueforProgressBar(dataSelectedDialog.protein)"
                    color="blue"
                    class="q-mt-sm"
                  />
                  <div class="text-body1">
                    Błonnik ({{ dataSelectedDialog.roughage }}/100)g
                  </div>
                  <q-linear-progress
                    stripe
                    rounded
                    size="20px"
                    :value="
                      calcValueforProgressBar(dataSelectedDialog.roughage)
                    "
                    color="orange"
                    class="q-mt-sm"
                  />
                </div>

                <div class="text-subtitle2">Skladniki</div>
                <div class="q-gutter-xs" style="max-width: 300px">
                  <q-chip
                    v-for="(p, j) in dataSelectedDialog.products"
                    :key="j"
                    :label="returnPrdoductData(p)"
                  >
                    <q-tooltip>
                      <div class="text-body2">{{ p.product.name }}</div>
                    </q-tooltip>
                  </q-chip>
                </div>
              </q-card-section>
            </q-card>
            <!-- <q-card>
                    <mealForm></mealForm>
                </q-card> -->
          </q-dialog>
        </div>
      </div>
    </div>
  </q-page>
</template>

<script>
import { mapActions } from "vuex";
import { date } from "quasar";
import { Notify } from "quasar";
import { mapGetters } from "vuex";
import mealForm from "./AddMeal.vue";
import { MEALS } from "src/EndpointAddresses";

export default {
  name: "PageIndex",
  data() {
    return {
      showWeekView: true,
      disableEditing: true,
      meals: [],
      showMealDialog: false,
      dataSelectedDialog: {},
      mealTimeValues: [
        { label: "Śniadanie", value: "BREAKFAST", checked: false, cal: 250 },
        {
          label: "2 Śniadanie",
          value: "SECOND_BREAKFAST",
          checked: false,
          cal: 100,
        },
        { label: "Obiad", value: "LUNCH", checked: false, cal: 350 },
        { label: "Kolacja", value: "SUPPER", checked: false, cal: 100 },
        { label: "Podwieczorek", value: "DINNER", checked: false, cal: 200 },
      ],
    };
  },
  methods: {
    returnPrdoductData(aP) {
      return `${aP.product.name} - ${aP.amount}g`;
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
    calcValueforProgressBar(aVal) {
      return aVal / 100;
    },
    showDialog(aMeal, aMealTime) {
      this.showMealDialog = !this.showMealDialog;
      this.dataSelectedDialog = aMeal;
      this.dataSelectedDialog.cardMealTime = aMealTime;
    },
    returnPreparationTime(aPreparationTime) {
      return `${aPreparationTime} min`;
    },
    formatDate(time) {
      return date.formatDate(new Date(time), "DD.MM.YYYY HH:mm");
    },
    returnMealTime(aMealTime) {
      for (const { label, value } of this.mealTimeValues) {
        if (aMealTime === value) return label;
      }
    },
    sortMealTime(aMealTime) {
      let ordering = {};
      let sortOrder = [];
      for (const { value } of this.mealTimeValues) {
        sortOrder.push(value);
      }
      for (var i = 0; i < sortOrder.length; i++) ordering[sortOrder[i]] = i;

      aMealTime.sort(function (a, b) {
        return ordering[a] - ordering[b] || a.localeCompare(b);
      });
    },
    async fetchMealdata() {
      let responce = await fetch(MEALS);

      if (!responce.ok) {
        this.errorMesage("Ups... Cos poszlo nie tak");
      }

      let result = await responce.json();
      for (const { id } of result.reverse()) {
        let detailResponce = await fetch(MEALS + { id });
        let detailResult = await detailResponce.json();
        this.meals.push(detailResult);
      }
      for (const { mealTime } of this.meals) {
        this.sortMealTime(mealTime);
      }
    },
    errorMesage(e) {
      Notify.create({
        message: `⚠ ${e}`,
        classes: "full-width text-center bg-negative",
      });
    },
  },
  computed: {
    ...mapGetters(["mealTime"]),
  },
  // components:{
  //   mealForm: mealForm,
  // },
  created() {
    this.fetchMealdata();
  },
};
</script>
<style lang="scss">
.my-card {
  width: 200px;
  height: 100%;
  @media (max-width: 1100px) {
    width: 90vw;
  }
}
.long-word {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.mobile-column {
  @media (max-width: 1100px) {
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
}
.big-size {
  font-size: 48px;
}
</style>
