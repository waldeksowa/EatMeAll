<template>
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
    <registerForm></registerForm>
  </div>
</template>
<script>
export default {
  components: {
    registerForm: () => import("../components/register/registerForm.vue"),
  },
};
</script>