<template>
  <div>
    <div class="q-ma-md row justify-center">
      <div class="q-gutter-md">
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
    <div class="q-ma-md row justify-center">
      <div class="q-gutter-md">
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
</template>