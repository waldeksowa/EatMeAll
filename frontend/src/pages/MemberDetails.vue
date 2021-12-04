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
      <div class="q-ma-md row justify-center">
        <div class="q-gutter-md q-pa-md">
          <q-img src="../assets/Netflix-avatar.jpg" />
          <h1 class="text-h5 text-center">Dane Ogólne</h1>
          <q-input
            filled
            class="input-width"
            v-model="memberData.name"
            label="Imie"
            :disable="isInputDisabled"
          />
          <q-input
            filled
            class="input-width"
            v-model.number="memberData.age"
            label="Wiek"
            type="number"
            :disable="isInputDisabled"
            suffix="Lat"
          />
          <q-input
            filled
            class="input-width"
            v-model.number="memberData.height"
            label="Wzrost"
            type="number"
            :disable="isInputDisabled"
            suffix="Cm"
          />
          <q-input
            filled
            class="input-width"
            v-model.number="memberData.currentWeight"
            label="Aktualna waga"
            type="number"
            :disable="isInputDisabled"
            suffix="Kg"
          />
        </div>
        <div class="q-ma-md row justify-center">
          <div class="q-gutter-md q-py-md q-pa-md">
            <h1 class="text-h5 text-center">Proporcje ciała</h1>
            <q-input
              filled
              class="input-width"
              v-model.number="memberData.currentFat"
              label="Tkanka tłuszczowa"
              type="number"
              :disable="isInputDisabled"
              suffix="Kg"
            />
            <q-input
              filled
              class="input-width"
              v-model.number="memberData.currentMussels"
              label="Masa mięśniowa"
              type="number"
              :disable="isInputDisabled"
              suffix="Kg"
            />
            <q-input
              filled
              class="input-width"
              v-model.number="memberData.currentWater"
              label="Ilość wody w ciele"
              type="number"
              :disable="isInputDisabled"
              suffix="Kg"
            />
          </div>
          <div class="q-gutter-md q-py-md">
            <h1 class="text-h5 text-center">Rekomendacje</h1>
            <q-input
              filled
              class="input-width"
              v-model.number="memberData.recommendedCalories"
              label="Rekomnendowana ilość Kalori"
              type="number"
              :disable="isInputDisabled"
              suffix="Kcal"
            />
            <q-input
              filled
              class="input-width"
              v-model.number="memberData.recommendedFat"
              label="Rekomnendowana ilość Tłuszczów"
              type="number"
              :disable="isInputDisabled"
              suffix="g"
            />

            <q-input
              filled
              class="input-width"
              v-model.number="memberData.recommendedProtein"
              label="Rekomnendowana ilość Białek"
              type="number"
              :disable="isInputDisabled"
              suffix="g"
            />
            <q-input
              filled
              class="input-width"
              v-model.number="memberData.recommendedRoughage"
              label="Rekomnendowana ilość Błonnika"
              type="number"
              :disable="isInputDisabled"
              suffix="g"
            />
          </div>
        </div>
      </div>
    </div>
    <div class="row justify-center q-gutter-md q-ma-md">
      <q-btn
        class="bg-primary text-white"
        size="lg"
        @click="isInputDisabled = false"
      >
        Edytuj
      </q-btn>
      <q-btn
        class="bg-primary text-white"
        :disable="isInputDisabled"
        size="lg"
        @click="postUpdatedData()"
      >
        Zapisz
      </q-btn>
      <q-btn
        class="bg-negative text-white"
        size="lg"
        @click="deleteAccountDialogisShowed = !deleteAccountDialogisShowed"
      >
        Usuń Kąto
      </q-btn>
    </div>
    <q-dialog v-model="deleteAccountDialogisShowed">
      <q-card class="q-pa-md">
        <q-card-section>
          <div class="text-h6">Uwaga</div>
        </q-card-section>

        <q-card-section class="q-pt-none">
          Ta akcja spowoduje usuniecie na stałe kąta tego użytkownika, czy
          jesteś tego pewny?
        </q-card-section>
        <q-card-section>
          <q-btn
            flat
            label="Tak"
            color="primary"
            @click="removeAccount()"
            v-close-popup
          />
          <q-btn flat label="Nie" color="primary" v-close-popup />
        </q-card-section>
      </q-card>
    </q-dialog>
  </q-page>
</template>
<script>
import { MEMBER } from "../EndpointAddresses";
import { mapGetters, mapActions } from "vuex";
export default {
  data() {
    return {
      isInputDisabled: true,
      deleteAccountDialogisShowed: false,
      memberData: [],
    };
  },
  mounted() {},
  computed: {
    ...mapGetters("store", ["memberIdToShowAccountDetail", "jwt"]),
  },
  methods: {
    ...mapActions("store", ["errorMesage", "notifySucessful"]),
    fetchMemberData() {
      var myHeaders = new Headers();
      myHeaders.append("Authorization", `Bearer ${this.jwt}`);

      var requestOptions = {
        method: "GET",
        headers: myHeaders,
        redirect: "follow",
      };
      const url = `${MEMBER}/${this.memberIdToShowAccountDetail}`;
      fetch(url, requestOptions)
        .then((response) => response.json())
        .then((result) => (this.memberData = result))
        .catch((error) => {
          console.log(error);
          this.errorMesage("Ups... pobrać danych o użytkowniku");
        });
    },
    postUpdatedData() {
      var myHeaders = new Headers();
      myHeaders.append("Authorization", `Bearer ${this.jwt}`);
      myHeaders.append("Content-Type", "application/json");

      var raw = JSON.stringify({
        name: this.memberData.name,
        age: this.memberData.age,
        height: this.memberData.height,
        currentWeight: this.memberData.currentWeight,
        currentFat: this.memberData.currentFat,
        currentMussels: this.memberData.currentMussels,
        currentWater: this.memberData.currentWater,
        recommendedCalories: this.memberData.recommendedCalories,
        recommendedCarbohydrates: this.memberData.recommendedCarbohydrates,
        recommendedFat: this.memberData.recommendedFat,
        recommendedProtein: this.memberData.recommendedProtein,
        recommendedRoughage: this.memberData.recommendedRoughage,
      });

      var requestOptions = {
        method: "PUT",
        headers: myHeaders,
        body: raw,
        redirect: "follow",
      };

      const url = `${MEMBER}/${this.memberIdToShowAccountDetail}`;
      fetch(url, requestOptions)
        .then((response) => response.json())
        .then((result) => result)
        .catch((error) => {
          console.log(error);
          this.errorMesage("Ups... pojawił sie błąd podczas wysyłania danych");
        });

      this.isInputDisabled = true;
      this.notifySucessful("Dane zostały pomyślnie zmienione");
      this.removeAccount();
    },
    removeAccount() {
      var myHeaders = new Headers();
      myHeaders.append("Authorization", `Bearer ${this.jwt}`);

      var requestOptions = {
        method: "DELETE",
        headers: myHeaders,
        redirect: "follow",
      };

      const url = `${MEMBER}/${this.memberIdToShowAccountDetail}`;
      fetch(url, requestOptions)
        .then((response) => {
          if (response.ok) {
            this.$router.push("/uzytkownicy");
            return response.text();
          }
        })
        .then((result) => result)
        .catch((error) => {
          console.log(error);
          this.errorMesage("Ups... nie udało sie usunąć kąta");
        });
    },
  },
  mounted() {
    this.fetchMemberData();
  },
};
</script>
<style lang="sass">
.icon-size
  font-size: 180px
.input-width
  width: 200px
</style>