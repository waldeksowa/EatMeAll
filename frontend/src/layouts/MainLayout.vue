<template>
  <q-layout view="lHh Lpr lFf">
    <q-header elevated>
      <q-toolbar>
        <q-avatar >
          <img src="https://cdn.quasar.dev/logo-v2/svg/logo-mono-white.svg"/>
        </q-avatar>
        <q-toolbar-title>EatMeAll</q-toolbar-title>
      </q-toolbar>

      <q-tabs v-model="tab" inline-label align="left" class="text-white desktop-only" >
        <q-route-tab v-ripple v-for="(nav,index) in navs" exect :key="index" :to="nav.to" :icon="nav.icon" :label="nav.label" :name="nav.label"></q-route-tab>
      </q-tabs>
      <q-img class='header-image absolute-top' src="../assets/navbarphoto.jpg"></q-img>
    </q-header>
    <q-footer  >
        <q-tabs v-model="tab" dense align="center" class="text-white mobile-only">
          <q-route-tab v-ripple v-for="(nav,index) in navs" exect :key="index"  :to="nav.to" :icon="nav.icon" :label="nav.label" :name="nav.label"></q-route-tab>
        </q-tabs>
    </q-footer>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script>
import { date } from 'quasar'

export default {
  name: 'MainLayout',
  data () {
    return {
      leftDrawerOpen: false,
      tab: 'home',
      value:70,
      rightListOpen:false,
      navs:[
        {label:'home', icon:'home', to:'/'},
        {label:'lista', icon:'list', to:'/list'},
        {label:'dodaj', icon:'add', to:'/dodajPosilek'},
        {label:'o nas', icon:'info', to:'/info'}
      ]
    }
  },
  computed:{
    todaysDate(){
      let timeStamp = Date.now()
      return date.formatDate(timeStamp, 'DD.MM.YYYY')
    },
  },
  watch: {
    '$route':function () {
      console.log(this.$router.currentRoute)
    }
  },

}
</script>
<style type="scss">
.header-image{
  height: 100%;
  z-index: -1;
  opacity: 0.3;
  filter: grayscale(100%);
}

</style>
