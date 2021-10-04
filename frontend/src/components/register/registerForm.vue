<template>
  <q-form class="col-grow col-sm-9">
    <div>
      <h4>Rejestracja</h4>
    </div>
    <QInputWithCustoRules
      @updateRecipeInputValue="updateLogin($event)"
      :props="{ label: 'Login', value: login, type: 'text' }"
    ></QInputWithCustoRules>
    <QInputWithCustoRules
      @updateRecipeInputValue="updatePassword($event)"
      :props="{ label: 'Haslo', value: password, type: 'password' }"
    ></QInputWithCustoRules>
    <QInputWithCustoRules
      @updateRecipeInputValue="updateSecondPassword($event)"
      :props="{
        label: 'Powtorz hasło',
        value: secondPassword,
        type: 'password',
      }"
    ></QInputWithCustoRules>
    <div class="q-gutter-md">
      <q-btn class="text-white bg-primary" label="Dodaj kato" />
      <q-btn
        class="text-white bg-primary"
        label="Zaloguj się"
        @click="redirectToLoginForm()"
      />
    </div>
  </q-form>
</template>
<script>
export default {
  data() {
    return {
      login: "",
      password: "",
      secondPassword: "",
    };
  },
  methods: {
    redirectToLoginForm() {
      this.$router.push({ path: "/" });
    },
    updateLogin(update) {
      this.login = update;
    },
    updatePassword(update) {
      this.password = update;
    },
    updateSecondPassword(update) {
      this.secondPassword = update;
    },
  },
  components: {
    QInputWithCustoRules: () => import("../common/QInputWithCustoRules.vue"),
  },
};
</script>