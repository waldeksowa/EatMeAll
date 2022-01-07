<template>
  <p>
    {{ mealName }}
  </p>
</template>
<script>
export default {
  props: {
    mealName: String,
  },
};
</script>