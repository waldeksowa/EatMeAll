<template>
  <q-page>
    <div class="q-gutter-y-md  q-col-gutter-md q-pa-md fit row wrap justify-center items-start content-start" >
      <div class="col-grow col-sm-9">
        <q-form class="q-col-gutter-y-lg">
          <div class="text-h4  text-center">Dame Posiłku</div>
          <q-input filled class="" v-model.trim="recipe.name" label="Nazwa Posiłku" lazy-rules :rules="[val => val && val.length > 0 || 'To pole jest wymagane']" />
          <q-input filled type="number" v-model.number="recipe.time" label="Czas Przygotowania (min)" lazy-rules :rules="[val => !!val || 'To pole jest wymagane']" />
          <q-input filled v-model.trim="recipe.author" label="Autor Przepisu" lazy-rules :rules="[val => !!val || 'To pole jest wymagane']" />
          <q-input filled v-model.trim="recipe.description" label="Dodatkowy Opis" lazy-rules :rules="[val => !!val || 'To pole jest wymagane']" />
          <div class="text-h4  text-center">Pora Posiłku</div>
          <div class="q-pa-lg">
            <div v-for="(box) in checkboxOptions" :key="box.label"  >
              <q-checkbox class="mobile-only text-body1" size='lg' v-model="box.checked" :label="box.label" />
            </div>
            <div class="row justify-between">
              <q-checkbox class="desktop-only text-body1" v-for="(box,index) in checkboxOptions" :key="index" size='lg' v-model="box.checked" :label="box.label" />
            </div>
          </div>
          <div class="text-h4  text-center">Kroki Przygotowania</div>
          <q-checkbox v-model="areStepsHiden" label="Brak krokow przygotwania" class="text-body1" size='lg' @click="!areStepsHiden" />
          <div class="q-col-gutter-y-lg">
            <div v-for="(row,index) in recipe.steps" :key="index">
              <div>
                <q-input filled v-model.trim="row.name" type='text' :label="returnStepLabelWithNumber(index + 1)" :disable="areStepsHiden" :rules="[val => !!val || 'To pole jest wymagane']" >
                  <template v-slot:after>
                    <q-btn flat class="bg-primary full-height text-white btn-h" icon="remove" @click="deleteRow(index)"></q-btn>
                  </template>
                </q-input>
              </div>
            </div>
            <div class="q-mb-lg">
              <q-btn flat class="bg-primary full-width text-white btn-h"  icon="receipt_long" label="Dodaj Krok" @click="addRow()"></q-btn>
            </div>
          </div>

          <div class="q-col-gutter-y-lg q-mt-lg">
            <div class="text-h4 text-center"> Posilek Zawiera</div>
            <div class=" text-body1 ">Kcal (20/{{ returnMealCalories() }})Kcal</div>
            <q-linear-progress stripe rounded size="20px" :value="0.2" color="warning" class="q-mt-sm" />
            <div class="text-body1 ">Węglowodany (40/{{ returnMealCalories() }})g</div>
            <q-linear-progress stripe rounded size="20px" :value="0.4" color="red" class="q-mt-sm" />
            <div class="text-body1 ">Tłuszcze (60/{{ returnMealCalories() }})g</div>
            <q-linear-progress stripe rounded size="20px" :value="0.5" color="green" class="q-mt-sm" />
            <div class="text-body1 ">Białko (10/{{ returnMealCalories() }})g</div>
            <q-linear-progress stripe rounded size="20px" :value="0.1" color="blue" class="q-mt-sm" />
          </div>
          <div class="text-h4 text-center q-mb-lg">Dodaj Produkt</div>
          <div>
            <q-btn color="primary full-width btn-h" label="Dodaj Produkty" @click="dialog = !dialog" icon="shopping_cart"></q-btn>
          </div>
          <div class="q-col-gutter-y-lg">
            <div class="text-h4 text-center q-mt-lg">Skladniki Posilku</div>
              <div class="q-pb-md" v-if="recipe.addedProducts.length !== 0">
                <div v-for="(p,i) in recipe.addedProducts" :key="i">
                  <q-card class="my-card" flat bordered>
                    <q-item class="row" >
                      <q-item-section >
                        <div class="text-body1 q-mt-sm q-mb-xs" >{{ p.product.name }}</div>
                      </q-item-section>
                      <q-item-section class="col-sm-3">
                        <div class="mobile-only">
                          <q-input filled type='number' class="text-capitalize bg-white full-width" v-model.number="p.amount" label="Ilosc" suffix="(G)" :rules="[val => !!val || 'To pole jest wymagane']"/>
                          <q-btn flat class="bg-primary text-white full-width btn-h" :icon='includeProduct(p.product) ? "remove" : "add"' @click='includeProduct(p.product) ?  removeProducts(p.product) : addProductToList(p.product)'></q-btn>
                        </div>
                        <div class="desktop-only">
                          <q-input filled type='number' class="text-capitalize bg-white full-width" v-model.number="p.amount" label="Ilosc" suffix="(G)" :rules="[val => !!val || 'To pole jest wymagane']">
                            <template v-slot:after>
                              <q-btn flat class="bg-primary text-white full-width btn-h" :icon='includeProduct(p.product) ? "remove" : "add"' @click='includeProduct(p.product) ?  removeProducts(p.product) : addProductToList(p.product)'></q-btn>
                            </template>
                          </q-input>
                        </div>
                      </q-item-section>
                    </q-item>
                    <q-separator />
                    <q-item-section class="q-pa-sm bg-grey-3">
                      <div class="text-subtitle2 flex ">Kalorie: {{p.product.calories}}(Kcal) Tłuszcze: {{p.product.fat}}(g) Weglowodany: {{p.product.carbs}}(g) Bialko: {{p.product.protein}}(g)</div>
                    </q-item-section>
                  </q-card>
                </div>
              </div>
            <div class="q-mt-lg">
              <q-btn @click.prevent="post()" icon="add_circle" class=" full-width btn-h bg-primary text-white full-width text-body1 " label="Dodaj Posilek"></q-btn>
            </div>
          </div>
        </q-form>
      </div>
    </div>
    <q-dialog v-model="dialog">
      <q-card style="width:80vw;height:95vh">
        <q-card-section class="bg-secondary text-center">
          <div class="text-h4 q-mb-md">Wybierz produkt</div>
          <div class="row inline full-width justify-center q-mb-md">
            <q-select filled class="text-capitalize bg-white col col-auto col-grow" v-model="sort" label="Sortuj" :options="returnSortLabel()"/>
            <q-select filled class="text-capitalize bg-white col col-auto col-grow" v-model="dropdownType" label="Kategoria" :options="returnProductsTypes()"/>
          </div>
          <q-input filled class="text-capitalize bg-white" v-model.trim="toFind" label="Czego Szukasz"/>
        </q-card-section>
          <q-tab-panels v-model="dropdownType" animated swipeable infinite class="bg-white shadow-2">
            <q-tab-panel v-for="(typeName,index) in returnProductsTypes()"  :key="index" :name="typeName" :label="typeName" >
              <q-card-section>
                <div class="q-mb-lg row justify-between">
                  <q-icon class='text-black' style='font-size: 32px' name="chevron_left"></q-icon>
                  <div class="text-h5 text-capitalize text-center">{{ typeName }}</div>
                  <q-icon class='text-black' style='font-size: 32px' name="chevron_right"></q-icon>
                </div>
                <div v-for="(p,k) in returnProductsBySelectedType()" :key="k">
                  <q-card class="my-card" flat bordered v-if="showProductsToFind(p)" >
                    <div>
                      <q-item>
                        <q-item-section >
                          <div class="text-body1 q-pa-sm " >{{ p.name }}</div>
                        </q-item-section>
                        <q-btn flat class="bg-primary self-center text-white btn-h" :icon='includeProduct(p) ? "remove" : "add"' @click=' includeProduct(p) ?  removeProducts(p) : addProductToList(p)'></q-btn>
                      </q-item>
                      <q-separator />
                      <q-item-section class="q-pa-sm bg-grey-3">
                        <div class="text-subtitle2 flex ">Kalorie: {{p.calories}}(Kcal) Tłuszcze: {{p.fat}}(g) Weglowodany: {{p.carbs}}(g) Bialko: {{p.protein}}(g)</div>
                      </q-item-section>
                    </div>
                  </q-card>
                </div>
              </q-card-section>
            </q-tab-panel>
          </q-tab-panels>
      </q-card>
    </q-dialog>
  </q-page>
</template>
<script>
import { Notify } from 'quasar'
import json from './../assets/ALL_PRODUCTS.json'
import {MEALS} from "src/EndpointAddresses";

export default {
  name: 'PageIndex',
  data(){
    return{
      products:json,
      areStepsHiden:false,
      dropdownType:"zboża",
      sort:"Najwiecej Białka",
      dialog:false,
      toFind:"",
      sortOptions:[
        {label:'Najwiecej Białka',val:0},
        {label:'Najwiecej Weglowodanow',val:1},
        {label:'Najwiecej Tłuszczów',val:2},
        {label:'Najmniej Białka',val:3},
        {label:'Najmniej Weglowodanow',val:4},
        {label:'Najmniej Tłuszczów',val:5},
      ],
      recipe:{
        name:'',
        time:null,
        author:'',
        description:'',
        addedProducts:[],
        steps:[],
      },
      checkboxOptions: [
        {label: 'Śniadanie', value:"BREAKFAST", checked:false, cal:250},
        {label: '2 Śniadanie', value:"SECOND_BREAKFAST", checked:false, cal:100},
        {label: 'Obiad', value:"LUNCH", checked:false, cal:350},
        {label: 'Podwieczorek', value:"DINNER", checked:false, cal:200},
        {label: 'Kolacja', value:"SUPPER", checked:false, cal:100}
      ],

    }
  },
  methods: {
    formFieldAreEmptyNotify(e){
      Notify.create({
        message: `⚠ ${e}`,
        classes:'full-width text-center bg-negative'
      })
    },
    sucessfulNotify(){
      Notify.create({
        message: `Twój posiłek został dodany`,
        classes:'full-width text-center bg-positive'
      })
    },
    returnStepLabelWithNumber(aNUmber){
      return `Krok nr ${aNUmber}`
    },
    addRow: function() {
      this.recipe.steps.push({'name': ''});
    },
    deleteRow: function(aIndex) {
      this.recipe.steps.splice(aIndex,1);
    },
    returnSortLabel(){
      let arr = [];
      for(const {label} of this.sortOptions){
        arr.push(label)
      }
      return arr
    },
    returnProductsTypes(){
      let arr = [];
      for(const {type} of this.products){
        arr.push(type)
      }
      arr.push('wszystko')
      return arr
    },
    returnProductsBySelectedType(){
      let arr=[]
      for(const {type,products} of this.products){
        if(this.dropdownType === type ) return products
        if(this.dropdownType === 'wszystko') arr.push(...products)
      }
      return arr
    },
    removeProducts(aP){
      this.recipe.addedProducts.forEach((item,index) => {
        if(item.product === aP) this.recipe.addedProducts.splice(index,1)
      })
    },
    returnMealCalories(){
      let max = 100
      for(const{checked,cal} of this.checkboxOptions) {
        if(checked && cal > max) max = cal
      }
      return max
    },
    showProductsToFind(aP){
      return aP.name.toLowerCase().trim().includes(this.toFind.toLowerCase().trim());
    },
    includeProduct(aP){
      let arr=[]
      for(const {product} of this.recipe.addedProducts){
        arr.push(product)
      }
      return arr.includes(aP)
    },
    addProductToList(aP){
      this.recipe.addedProducts.push({
        product:aP,
        amount:100
      })
    },
    checkMealData(){
      try{
        const errorMissingMealData = "Nie uzupełniles danych posiłku"

        if(!this.recipe.name || !this.recipe.author || !this.recipe.description || !parseInt(this.recipe.time))
          throw new Error(errorMissingMealData)
      }catch(e){
        this.formFieldAreEmptyNotify(e.message)
      }
    },
    checkProducts(){
      try{
        const errorMissingAmount = "Niepoprawna ilosc posilku"
        const errorMissingProducts = "Nie dodales produktow do posiłku"
        let productsIdAndAmount = []

        for(const {amount, product} of this.recipe.addedProducts) {
          if(parseInt(amount) <= 0) throw new Error(errorMissingAmount)

          if(product)
            productsIdAndAmount.push({amount:amount,id:product.id})
        }

        if(productsIdAndAmount.length === 0) throw new Error(errorMissingProducts)

        return [productsIdAndAmount,null]
      }catch(e){
        this.formFieldAreEmptyNotify(e.message)
        return [null,e]
      }
    },
    chechkMealTime(){
      try{
        const errorMissingMealTime = "Nie uzupełniles pory posiłku"
        let mealTiemArr = []

        for(const {value,checked} of this.checkboxOptions){
          if(checked) mealTiemArr.push(value)
        }
        if(mealTiemArr.length === 0) throw new Error(errorMissingMealTime)
        return [mealTiemArr,null]
      }catch(e){
        this.formFieldAreEmptyNotify(e.message)
        return [null,e]
      }
    },
    checkMealSteps(){
      try{
        const errorMissingSteps = "Nie podales krokow do przygotowania posiłku"
        let stepsArr = []

        if(!this.areStepsHiden) {
          for(const {name} of this.recipe.steps){
            if(!name) {
              throw new Error(errorMissingSteps)
            } else {
              stepsArr.push(name)
            }
          }
          if(stepsArr.length === 0) throw new Error(errorMissingSteps)
        }
        return[stepsArr,null]
      }catch(e){

        this.formFieldAreEmptyNotify(e.message)
        return[null,e]
      }
    },
    checkForm(){
      this.checkMealData()
      const [productsIdAndAmount,errorProductsIdAndAmount] = this.checkProducts()
      const [mealTiemArr,errorMealTiemArr] = this.chechkMealTime()
      const [stepsArr,errorStepsArr] = this.checkMealSteps()

      if(!errorProductsIdAndAmount && !errorMealTiemArr && !errorStepsArr)
        return [productsIdAndAmount,mealTiemArr,stepsArr]
    },
    post(){
      const [productsIdAndAmount,mealTiemArr,stepsArr] = this.checkForm();

      const requestData = {
        name:this.recipe.name,
        author:this.recipe.author,
        description:this.recipe.description,
        prepareTime:this.recipe.time,
        mealTime: mealTiemArr,
        steps: stepsArr,
        products:productsIdAndAmount
      }

      const requestOptions = {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(requestData)
      };
      fetch(MEALS,requestOptions)
      .then(r => r.json())
      .then(d => (this.postId = d.id))
      this.sucessfulNotify()
    },

  },
  created() {
    // this.$store.dispatch("products/fetchData", { self: this })
    // fetch('http://localhost:8080/api/v1/products').then(r => r.json()).then(d => this.products.push(d));
  },
  // computed:{
  //   ...mapGetters('products',['products']),
  // },
  // components:{
  //   MealForm : () => import('../../components/MealForm.vue')
  // }
}
</script>
<style>
.btn-h{
    height: 50px;
}
</style>
