<template>
  <q-card class="q-pa-lg dialog">
    <div class="text-center text-h4">{{ selectedMeal.name }}</div>
    <div class="q-col-gutter-y-lg q-mt-lg">
      <div class="text-body1">Kalorie: {{ selectedMeal.calorific }} Kcal</div>
      <div class="text-body1">
        Węglowodany ({{ selectedMeal.carbohydrates }}/100)g
      </div>
      <q-linear-progress
        stripe
        rounded
        size="20px"
        :value="calcValueforProgressBar(selectedMeal.carbohydrates)"
        color="red"
        class="q-mt-sm"
      />
      <div class="text-body1">Tłuszcze ({{ selectedMeal.fat }}/100)g</div>
      <q-linear-progress
        stripe
        rounded
        size="20px"
        :value="calcValueforProgressBar(selectedMeal.fat)"
        color="green"
        class="q-mt-sm"
      />
      <div class="text-body1">Białko ({{ selectedMeal.protein }}/100)g</div>
      <q-linear-progress
        stripe
        rounded
        size="20px"
        :value="calcValueforProgressBar(selectedMeal.protein)"
        color="blue"
        class="q-mt-sm"
      />
      <div class="text-body1">Błonnik ({{ selectedMeal.roughage }}/100)g</div>
      <q-linear-progress
        stripe
        rounded
        size="20px"
        :value="calcValueforProgressBar(selectedMeal.roughage)"
        color="orange"
        class="q-mt-sm"
      />
    </div>
    <div></div>
  </q-card>
</template>
<script>
export default {
  props: ["selectedMeal"],
  methods: {
    calcValueforProgressBar(aVal) {
      return aVal / 100;
    },
  },
};
</script>
<style>
</style>