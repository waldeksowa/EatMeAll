<template>
  <q-page>
    <div
      class="
        full
        q-gutter-y-md q-col-gutter-md q-pa-md
        fit
        row
        wrap
        justify-center
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
            disable
          />
          <q-input
            filled
            class="inputWidth"
            v-model="age"
            label="Wiek"
            type="text"
            disable
            suffix="Lat"
          />
          <q-input
            filled
            class="inputWidth"
            v-model="height"
            label="Wzrost"
            type="text"
            disable
            suffix="Cm"
          />
          <q-input
            filled
            class="inputWidth"
            v-model="currentWeight"
            label="Aktualna waga"
            type="text"
            disable
            suffix="Kg"
          />
        </div>
      </div>
      <div class="col-6 q-ma-md wrap row q-gutter-sm">
        <div class="q-gutter-md q-ma-sm">
          <h1 class="text-h5 text-center">Proporcje ciała</h1>
          <q-input
            filled
            class="inputWidth"
            v-model="currentFat"
            label="Tkanka tłuszczowa"
            type="text"
            disable
            suffix="Kg"
          />
          <q-input
            filled
            class="inputWidth"
            v-model="currentMussels"
            label="Masa mięśniowa"
            type="text"
            disable
            suffix="Kg"
          />
          <q-input
            filled
            class="inputWidth"
            v-model="currentWater"
            label="Ilość wody w ciele"
            type="text"
            disable
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
            disable
            suffix="Kcal"
          />
          <q-input
            filled
            class="inputWidth"
            v-model="recommendedFat"
            label="Rekomnendowana ilość Tłuszczów"
            type="text"
            disable
            suffix="g"
          />

          <q-input
            filled
            class="inputWidth"
            v-model="recommendedProtein"
            label="Rekomnendowana ilość Białek"
            type="text"
            disable
            suffix="g"
          />
          <q-input
            filled
            class="inputWidth"
            v-model="recommendedRoughage"
            label="Rekomnendowana ilość Błonnika"
            type="text"
            disable
            suffix="g"
          />
        </div>
      </div>
    </div>
  </q-page>
</template>
<script>
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
    };
  },
  methods: {
    errorMesage(e) {
      Notify.create({
        message: `⚠ ${e}`,
        classes: "full-width text-center bg-negative",
      });
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