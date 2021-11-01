<template>
  <q-card style="width: 80vw; height: 95vh">
    <q-card-section class="bg-secondary text-center">
      <div class="text-h4 q-mb-md">{{ header }}</div>
      <div class="row inline full-width justify-center q-mb-md">
        <q-select
          filled
          class="text-capitalize bg-white col col-auto col-grow"
          v-model="sort"
          label="Sortuj"
          :options="returnSortLabel()"
        />
        <q-select
          filled
          class="text-capitalize bg-white col col-auto col-grow"
          v-model="dropdownType"
          label="Kategoria"
          :options="returnProductsTypes()"
        />
      </div>
      <q-input
        filled
        class="text-capitalize bg-white"
        v-model.trim="toFind"
        label="Czego Szukasz"
      />
    </q-card-section>
    <q-tab-panels
      v-model="dropdownType"
      animated
      swipeable
      infinite
      class="bg-white shadow-2"
    >
      <q-tab-panel
        v-for="(typeName, index) in returnProductsTypes()"
        :key="index"
        :name="typeName"
        :label="typeName"
      >
        <q-card-section>
          <div class="q-mb-lg row justify-between">
            <q-icon
              class="text-black"
              style="font-size: 32px"
              name="chevron_left"
            ></q-icon>
            <div class="text-h5 text-capitalize text-center">
              {{ typeName }}
            </div>
            <q-icon
              class="text-black"
              style="font-size: 32px"
              name="chevron_right"
            ></q-icon>
          </div>
          <div v-for="(p, k) in returnProductsBySelectedType()" :key="k">
            <q-card
              class="full-width"
              flat
              bordered
              v-if="showProductsToFind(p)"
            >
              <div>
                <q-item>
                  <q-item-section>
                    <div class="text-body1 q-pa-sm">{{ p.name }}</div>
                  </q-item-section>
                  <q-btn
                    flat
                    class="bg-primary self-center text-white btn-h"
                    :icon="includeProduct(p) ? 'remove' : 'add'"
                    @click="
                      includeProduct(p)
                        ? removeProducts(p)
                        : addProductToList(p)
                    "
                  ></q-btn>
                </q-item>
                <q-separator />
                <q-item-section class="q-pa-sm bg-grey-3">
                  <div class="text-subtitle2 flex">
                    {{ macrosName.calorific }}: {{ p.calorific }}(Kcal)
                    {{ macrosName.fat }}: {{ p.fat }}(g)
                    {{ macrosName.carbohydrates }}: {{ p.carbohydrates }}(g)
                    {{ macrosName.protein }}: {{ p.protein }}(g)
                  </div>
                </q-item-section>
              </div>
            </q-card>
          </div>
        </q-card-section>
      </q-tab-panel>
    </q-tab-panels>
  </q-card>
</template>
<script>
import { MEAL_ADD_PRODUCTS } from "../../translate/addMeal/sectionHeaders.js";
import { mapGetters } from "vuex";
import { ALL_PRODUCTS } from "../../EndpointAddresses.js";
import { mapActions } from "vuex";
export default {
  props: ["props"],
  data() {
    return {
      productsUrl: ALL_PRODUCTS,
      products: "",
      header: MEAL_ADD_PRODUCTS,
      sortOptions: [
        { label: "Najwiecej Białka", val: 0 },
        { label: "Najwiecej Weglowodanow", val: 1 },
        { label: "Najwiecej Tłuszczów", val: 2 },
        { label: "Najmniej Białka", val: 3 },
        { label: "Najmniej Weglowodanow", val: 4 },
        { label: "Najmniej Tłuszczów", val: 5 },
      ],
      dropdownType: "wszystko",
      sort: "Najwiecej Białka",
      toFind: "",
      addedProducts: this.props.addedProducts,
      page: 1,
    };
  },
  computed: {
    ...mapGetters("store", ["macrosName"]),
  },
  mounted() {
    this.fetchProducts();
  },
  methods: {
    ...mapActions("store", ["notifyError"]),
    fetchProducts() {
      fetch(`${this.productsUrl}`)
        .then((response) => response.json())
        .then((data) => (this.products = data))
        .catch((error) => {
          this.notifyError("Ups... cos poszlo nie tak");
          console.log(error);
        });
    },
    returnSortLabel() {
      let sortedlabels = [];
      for (const { label } of this.sortOptions) {
        sortedlabels.push(label);
      }
      return sortedlabels;
    },
    returnProductsTypes() {
      let productsTypes = [];
      for (const { type } of this.products) {
        productsTypes.push(type);
      }
      productsTypes.push("wszystko");
      return productsTypes;
    },
    returnProductsBySelectedType() {
      let selectedProducts = [];
      for (const { type, products } of this.products) {
        if (this.dropdownType === type) return products;
        if (this.dropdownType === "wszystko")
          selectedProducts.push(...products);
      }
      return selectedProducts;
    },
    showProductsToFind(aP) {
      return aP.name
        .toLowerCase()
        .trim()
        .includes(this.toFind.toLowerCase().trim());
    },
    includeProduct(aP) {
      let includedProducts = [];
      for (const { product } of this.addedProducts) {
        includedProducts.push(product);
      }
      return includedProducts.includes(aP);
    },
    removeProducts(aP) {
      this.$emit("removeProducts", aP);
    },
    addProductToList(aP) {
      this.$emit("addProductToList", {
        product: aP,
        amount: 100,
      });
    },
  },
};
</script>