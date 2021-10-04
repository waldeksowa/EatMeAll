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
      <div class="column">
        <q-input v-model="email" label="Email" disable />
        <q-input v-model="login" label="Login" disable />
        <q-input v-model="password" label="Haslo" type="password" disable />
      </div>
      <div><q-icon name="account_circle" class="icon-size"></q-icon></div>
    </div>
    <q-separator inset />
    <div
      class="
        q-gutter-y-md q-col-gutter-md q-pa-md
        fit
        row
        wrap
        items-start
        content-start
      "
    >
      <h1 class="text-h5">Posilki</h1>
    </div>
  </q-page>
</template>
<script>
export default {
  data() {
    return {
      email: "email@email.com",
      login: "Login1234",
      password: "Password",
    };
  },
};
</script>
<style lang="sass">
.icon-size
    font-size: 180px
</style>