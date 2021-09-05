<template>
  <div>
    <div class="text-h4 text-center">{{ header }}</div>
    <q-checkbox
      :value="props.areStepsHidden"
      label="Brak krokow przygotwania"
      class="text-body1"
      size="lg"
      @click.native="invertAreStepsHidden()"
    />
    <div class="q-col-gutter-y-lg">
      <div v-for="(row, index) in props.steps" :key="index">
        <div>
          <q-input
            filled
            type="text"
            :value="row.name"
            :label="returnStepLabelWithNumber(index + 1)"
            :disable="props.areStepsHidden"
            :rules="[(val) => !!val || errorMessage]"
            @input="updateInputValue($event, index)"
          >
            <template v-slot:after>
              <q-btn
                flat
                class="bg-primary full-height text-white btn-h"
                icon="remove"
                @click="deleteRow(index)"
              ></q-btn>
            </template>
          </q-input>
        </div>
      </div>
      <div class="q-mb-lg">
        <q-btn
          flat
          class="bg-primary full-width text-white btn-h"
          icon="receipt_long"
          label="Dodaj Krok"
          @click="addRow()"
        ></q-btn>
      </div>
    </div>
  </div>
</template>
<script>
import { ERROR_RULES } from "src/translate/addMeal/inputLabels.js";
import { MEAL_STEPS } from "../../translate/addMeal/sectionHeaders.js";
export default {
  props: ["props"],
  data() {
    return {
      errorMessage: ERROR_RULES,
      header: MEAL_STEPS,
    };
  },
  methods: {
    returnStepLabelWithNumber(aNUmber) {
      return `Krok nr ${aNUmber}`;
    },
    addRow() {
      this.$emit("addRow", { name: "" });
    },
    deleteRow(aIndex) {
      this.$emit("deleteRow", aIndex);
    },
    updateInputValue(aValue, aIndex) {
      this.$emit("updateInputValue", [aValue, aIndex]);
    },
    invertAreStepsHidden() {
      this.$emit("invertAreStepsHidden", !this.props.areStepsHidden);
    },
  },
};
</script>