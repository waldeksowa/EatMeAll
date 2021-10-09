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
      <div class="col-4 q-ma-md row justify-center">
        <div class="q-gutter-md">
          <q-icon name="account_circle" class="icon-size"></q-icon>
          <h1 class="text-h5 text-center">Dane Ogólne</h1>
          <q-input
            filled
            class="inputWidth"
            v-model="name"
            label="Imie"
            :disable="isInputDisabled"
          />
          <q-input
            filled
            class="inputWidth"
            v-model="age"
            label="Wiek"
            type="text"
            :disable="isInputDisabled"
            suffix="Lat"
          />
          <q-input
            filled
            class="inputWidth"
            v-model="height"
            label="Wzrost"
            type="text"
            :disable="isInputDisabled"
            suffix="Cm"
          />
          <q-input
            filled
            class="inputWidth"
            v-model="currentWeight"
            label="Aktualna waga"
            type="text"
            :disable="isInputDisabled"
            suffix="Kg"
          />
        </div>
      </div>
      <div class="col-6 q-ma-md q-gutter-sm row justify-center">
        <div class="q-gutter-md q-ma-sm">
          <h1 class="text-h5 text-center">Proporcje ciała</h1>
          <q-input
            filled
            class="inputWidth"
            v-model="currentFat"
            label="Tkanka tłuszczowa"
            type="text"
            :disable="isInputDisabled"
            suffix="Kg"
          />
          <q-input
            filled
            class="inputWidth"
            v-model="currentMussels"
            label="Masa mięśniowa"
            type="text"
            :disable="isInputDisabled"
            suffix="Kg"
          />
          <q-input
            filled
            class="inputWidth"
            v-model="currentWater"
            label="Ilość wody w ciele"
            type="text"
            :disable="isInputDisabled"
            suffix="Kg"
          />
        </div>
        <div class="q-gutter-md q-ma-sm">
          <h1 class="text-h5 text-center">Rekomendowane spozycie</h1>
          <q-input
            filled
            class="inputWidth"
            v-model="recommendedCalories"
            label="Rekomnendowana ilość Kalori"
            type="text"
            :disable="isInputDisabled"
            suffix="Kcal"
          />
          <q-input
            filled
            class="inputWidth"
            v-model="recommendedFat"
            label="Rekomnendowana ilość Tłuszczów"
            type="text"
            :disable="isInputDisabled"
            suffix="g"
          />

          <q-input
            filled
            class="inputWidth"
            v-model="recommendedProtein"
            label="Rekomnendowana ilość Białek"
            type="text"
            :disable="isInputDisabled"
            suffix="g"
          />
          <q-input
            filled
            class="inputWidth"
            v-model="recommendedRoughage"
            label="Rekomnendowana ilość Błonnika"
            type="text"
            :disable="isInputDisabled"
            suffix="g"
          />
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
.inputWidth
  width: 200px
</style>