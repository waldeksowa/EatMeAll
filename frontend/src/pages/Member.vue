<template>
  <q-page>
    <div
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
      <div class="q-ma-md row justify-center">
        <div class="q-gutter-md">
          <q-icon name="account_circle" class="icon-size" />
          <h1 class="text-h5 text-center">Dane Ogólne</h1>
          <q-input
            filled
            class="input-width"
            v-model="name"
            label="Imie"
            :disable="isInputDisabled"
          />
          <q-input
            filled
            class="input-width"
            v-model.number="age"
            label="Wiek"
            type="number"
            :disable="isInputDisabled"
            suffix="Lat"
          />
          <q-input
            filled
            class="input-width"
            v-model.number="height"
            label="Wzrost"
            type="number"
            :disable="isInputDisabled"
            suffix="Cm"
          />
          <q-input
            filled
            class="input-width"
            v-model.number="currentWeight"
            label="Aktualna waga"
            type="number"
            :disable="isInputDisabled"
            suffix="Kg"
          />
        </div>
        <div class="q-ma-md row justify-center">
          <div class="q-gutter-md q-py-md">
            <h1 class="text-h5 text-center">Proporcje ciała</h1>
            <q-input
              filled
              class="input-width"
              v-model.number="currentFat"
              label="Tkanka tłuszczowa"
              type="number"
              :disable="isInputDisabled"
              suffix="Kg"
            />
            <q-input
              filled
              class="input-width"
              v-model.number="currentMussels"
              label="Masa mięśniowa"
              type="number"
              :disable="isInputDisabled"
              suffix="Kg"
            />
            <q-input
              filled
              class="input-width"
              v-model.number="currentWater"
              label="Ilość wody w ciele"
              type="number"
              :disable="isInputDisabled"
              suffix="Kg"
            />
          </div>
        </div>
        <div class="q-ma-md row justify-center">
          <div class="q-gutter-md q-py-md">
            <h1 class="text-h5 text-center">Rekomendacje</h1>
            <q-input
              filled
              class="input-width"
              v-model.number="recommendedCalories"
              label="Rekomnendowana ilość Kalori"
              type="number"
              :disable="isInputDisabled"
              suffix="Kcal"
            />
            <q-input
              filled
              class="input-width"
              v-model.number="recommendedFat"
              label="Rekomnendowana ilość Tłuszczów"
              type="number"
              :disable="isInputDisabled"
              suffix="g"
            />

            <q-input
              filled
              class="input-width"
              v-model.number="recommendedProtein"
              label="Rekomnendowana ilość Białek"
              type="number"
              :disable="isInputDisabled"
              suffix="g"
            />
            <q-input
              filled
              class="input-width"
              v-model.number="recommendedRoughage"
              label="Rekomnendowana ilość Błonnika"
              type="number"
              :disable="isInputDisabled"
              suffix="g"
            />
          </div>
        </div>
      </div>
    </div>
    <div class="row justify-center q-gutter-md q-ma-md">
      <q-btn
        class="bg-primary text-white"
        size="lg"
        @click="isInputDisabled = false"
      >
        Edytuj
      </q-btn>
      <q-btn class="bg-primary text-white" size="lg" @click="postUpdatedData()">
        Zapisz
      </q-btn>
    </div>
  </q-page>
</template>
<script>
import { Notify } from "quasar";
export default {
  props: ["userData"],
  data() {
    return {
      name: "Jan",
      age: "28",
      height: "175",
      currentWeight: 90,
      currentFat: 10,
      currentMussels: 10,
      currentWater: 10,
      recommendedCalories: 3000,
      recommendedCarbohydrates: 400,
      recommendedFat: 200,
      recommendedProtein: 100,
      recommendedRoughage: 200,
      isInputDisabled: true,
    };
  },

  methods: {
    errorMesage(e) {
      Notify.create({
        message: `⚠ ${e}`,
        classes: "full-width text-center bg-negative",
      });
    },
    notifySucessful(e) {
      Notify.create({
        message: `${e}`,
        classes: "full-width text-center bg-positive",
      });
    },

    postUpdatedData() {
      this.isInputDisabled = true;
      this.notifySucessful("Dane zostały pomyślnie zmienione");
    },
  },
};
</script>
<style lang="sass">
.icon-size
  font-size: 180px
.input-width
  width: 200px
</style>