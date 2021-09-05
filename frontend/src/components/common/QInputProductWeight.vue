<template>
  <q-input
    filled
    type="number"
    class="text-capitalize bg-white full-width"
    :value="props.amount"
    @input="$emit('updateProductWeightValue', $event)"
    label="Ilosc"
    suffix="(G)"
    :rules="[(val) => !!val || 'To pole jest wymagane']"
  />
</template>
<script>
export default {
  props: ["props"],
};
</script>