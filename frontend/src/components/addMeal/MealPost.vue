<template>
  <div>
    <div class="q-mt-lg">
      <q-btn
        :disabeld="buttonWasClicked"
        @click.prevent="post()"
        icon="add_circle"
        class="full-width btn-h bg-primary text-white full-width text-body1"
        label="Dodaj Posilek"
      ></q-btn>
    </div>
  </div>
</template>
<script>
import { mapActions, mapGetters } from "vuex";
import { Notify } from "quasar";
import { MEALS } from "src/EndpointAddresses";
export default {
  props: ["props"],
  data() {
    return {
      buttonWasClicked: false,
    };
  },
  computed: {
    ...mapGetters("store", ["jwt"]),
  },
  methods: {
    ...mapActions("store", ["notifyError", "notifySucessful"]),
    checkMealData() {
      try {
        const errorMissingMealData = "Nie uzupełniles danych posiłku";

        if (
          !this.props.recipeData.name ||
          !this.props.recipeData.author ||
          !this.props.recipeData.description ||
          !parseInt(this.props.recipeData.time)
        )
          throw new Error(errorMissingMealData);
        return true;
      } catch (e) {
        this.notifyError(e.message);
        return false;
      }
    },
    checkProducts() {
      try {
        const errorMissingAmount = "Niepoprawna ilość produktu";
        const errorMissingProducts = "Nie dodaleś produktow do posiłku";
        let productsIdAndAmount = [];

        for (const { amount, product } of this.props.recipeData.addedProducts) {
          if (parseInt(amount) <= 0) throw new Error(errorMissingAmount);

          if (product)
            productsIdAndAmount.push({ amount: amount, id: product.id });
        }

        if (productsIdAndAmount.length === 0)
          throw new Error(errorMissingProducts);

        return [productsIdAndAmount, null];
      } catch (e) {
        this.notifyError(e.message);
        return [null, e];
      }
    },
    checkMealTime() {
      try {
        const errorMissingMealTime = "Nie uzupełniles pory posiłku";
        let mealTiemArr = [];

        for (const { value, checked } of this.props.checkboxOptions) {
          if (checked) mealTiemArr.push(value);
        }
        if (mealTiemArr.length === 0) throw new Error(errorMissingMealTime);
        return [mealTiemArr, null];
      } catch (e) {
        this.notifyError(e.message);
        return [null, e];
      }
    },
    checkMealSteps() {
      try {
        const errorMissingSteps = "Nie podales krokow do przygotowania posiłku";
        let stepsArr = [];

        if (!this.props.areStepsHidden) {
          for (const { name } of this.props.recipeData.steps) {
            if (!name) {
              throw new Error(errorMissingSteps);
            } else {
              stepsArr.push(name);
            }
          }
          if (stepsArr.length === 0) throw new Error(errorMissingSteps);
        }
        return [stepsArr, null];
      } catch (e) {
        this.notifyError(e.message);
        return [null, e];
      }
    },
    checkForm() {
      const mealData = this.checkMealData();
      const [productsIdAndAmount, errorProductsIdAndAmount] =
        this.checkProducts();
      const [mealTiemArr, errorMealTiemArr] = this.checkMealTime();
      const [stepsArr, errorStepsArr] = this.checkMealSteps();

      if (
        !errorProductsIdAndAmount &&
        !errorMealTiemArr &&
        !errorStepsArr &&
        mealData
      )
        return [productsIdAndAmount, mealTiemArr, stepsArr];
    },
    restartFormData() {
      this.$emit("restartFormData");
    },
    post() {
      const [productsIdAndAmount, mealTiemArr, stepsArr] = this.checkForm();
      const sucessMessage = "Twój posiłek został dodany";

      fetch("http://localhost:8080/api/v2/meals", requestOptions)
        .then((response) => response.text())
        .then((result) => console.log(result))
        .catch((error) => console.log("error", error));

      const raw = JSON.stringify({
        name: this.props.recipeData.name,
        author: this.props.recipeData.author,
        description: this.props.recipeData.description,
        prepareTime: this.props.recipeData.time,
        mealTime: mealTiemArr,
        steps: stepsArr,
        products: productsIdAndAmount,
      });

      var myHeaders = new Headers();
      myHeaders.append("Authorization", `Bearer ${this.jwt}`);
      myHeaders.append("Content-Type", "application/json");

      var requestOptions = {
        method: "POST",
        headers: myHeaders,
        redirect: "follow",
        body: raw,
      };

      fetch(MEALS, requestOptions)
        .then((r) => r.json())
        .then((d) => {
          this.postId = d.id;
          this.notifySucessful(sucessMessage);
          this.restartFormData();
        })
        .catch((error) => {
          console.error(error);
          this.notifyError(error);
        });
    },
  },
};
</script>