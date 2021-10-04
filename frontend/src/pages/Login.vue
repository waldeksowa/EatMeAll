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
      <loginForm></loginForm>
    </div>
  </q-page>
</template>
<script>
export default {
  components: {
    loginForm: () => import("../components/login/loginForm.vue"),
  },
};
</script>
<style lang="sass">
</style>