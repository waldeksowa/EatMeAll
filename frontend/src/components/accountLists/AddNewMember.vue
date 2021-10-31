<template>
  <q-card>
    <div class="q-ma-md row justify-center">
      <div class="q-gutter-md">
        <h1 class="text-h5 text-center">Dane Ogólne</h1>
        <q-input filled class="inputWidth" v-model="name" label="Imie" />
        <q-input
          filled
          class="inputWidth"
          v-model.number="age"
          label="Wiek"
          type="number"
          suffix="Lat"
        />
        <q-input
          filled
          class="inputWidth"
          v-model.number="height"
          label="Wzrost"
          type="number"
          suffix="Cm"
        />
        <q-input
          filled
          class="inputWidth"
          v-model.number="currentWeight"
          label="Aktualna waga"
          type="number"
          suffix="Kg"
        />
      </div>
      <div class="q-gutter-md q-ma-sm">
        <h1 class="text-h5 text-center">Proporcje ciała</h1>
        <q-input
          filled
          class="inputWidth"
          v-model.number="currentFat"
          label="Tkanka tłuszczowa"
          type="number"
          suffix="Kg"
        />
        <q-input
          filled
          class="inputWidth"
          v-model.number="currentMussels"
          label="Masa mięśniowa"
          type="number"
          suffix="Kg"
        />
        <q-input
          filled
          class="inputWidth"
          v-model.number="currentWater"
          label="Ilość wody w ciele"
          type="number"
          suffix="Kg"
        />
      </div>
      <div class="q-gutter-md q-ma-sm">
        <h1 class="text-h5 text-center">Rekomendacje</h1>
        <q-input
          filled
          class="inputWidth"
          v-model.number="recommendedCalories"
          label="Rekomnendowana ilość Kalori"
          type="number"
          suffix="Kcal"
        />
        <q-input
          filled
          class="inputWidth"
          v-model.number="recommendedFat"
          label="Rekomnendowana ilość Tłuszczów"
          type="number"
          suffix="g"
        />
        <q-input
          filled
          class="inputWidth"
          v-model.number="recommendedProtein"
          label="Rekomnendowana ilość Białek"
          type="number"
          suffix="g"
        />
        <q-input
          filled
          class="inputWidth"
          v-model.number="recommendedRoughage"
          label="Rekomnendowana ilość Błonnika"
          type="number"
          suffix="g"
        />
      </div>
    </div>
    <div class="row justify-center q-pa-md q-gutter-lg">
      <div @click="addNewUser()">
        <q-btn size="lg" class="bg-primary text-white">Dodaj</q-btn>
      </div>
      <q-btn class="bg-primary text-white" size="lg" v-close-popup
        >Zamknij okno</q-btn
      >
    </div>
  </q-card>
</template>
<script>
import { mapActions } from "vuex";
export default {
  data() {
    return {
      name: "",
      age: null,
      height: null,
      currentWeight: null,
      currentFat: null,
      currentMussels: null,
      currentWater: null,
      recommendedCalories: null,
      recommendedCarbohydrates: null,
      recommendedFat: null,
      recommendedProtein: null,
      recommendedRoughage: null,
    };
  },
  methods: {
    ...mapActions("store", ["errorMesage", "notifySucessful"]),
    addNewUser() {
      if (
        this.name ||
        this.age ||
        this.height ||
        this.currentWeight ||
        this.currentFat ||
        this.currentMussels ||
        this.currentWater ||
        this.recommendedCalories ||
        this.recommendedCarbohydrates ||
        this.recommendedFat ||
        this.recommendedProtein ||
        this.recommendedRoughage
      ) {
        this.$emit("addNewUser", {
          name: this.name,
          age: this.age,
          height: this.height,
          currentWeight: this.currentWeight,
          currentFat: this.currentFat,
          currentMussels: this.currentMussels,
          currentWater: this.currentWater,
          recommendedCalories: this.recommendedCalories,
          recommendedCarbohydrates: this.recommendedCarbohydrates,
          recommendedFat: this.recommendedFat,
          recommendedProtein: this.recommendedProtein,
          recommendedRoughage: this.recommendedRoughage,
        });
        this.name = null;
        this.age = null;
        this.height = null;
        this.currentWeight = null;
        this.currentFat = null;
        this.currentMussels = null;
        this.currentWater = null;
        this.recommendedCalories = null;
        this.recommendedCarbohydrates = null;
        this.recommendedFat = null;
        this.recommendedProtein = null;
        this.recommendedRoughage = null;
        this.notifySucessful("Użytkownik został dodany pomyślnie");
      } else {
        this.errorMesage("Uzupelnij wszystkie pola");
      }
    
    },
  },
};
</script>
<style lang="sass">
.col
  flex-direction: columno
.inputWidth
  width: 300px
</style>