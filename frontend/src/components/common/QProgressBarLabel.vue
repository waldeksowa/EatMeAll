<template>
  <div class="text-body1">
    {{ props.label }} ({{ props.mealMacros }}/{{ props.mealWeight }})g
  </div>
</template>
<script>
export default {
  props: ["props"],
};
</script>