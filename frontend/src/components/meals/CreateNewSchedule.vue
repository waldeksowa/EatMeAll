<template>
    <div>
        <q-card class="q-pa-md">
          <q-card-section>
            <div class="text-h6">Uwaga</div>
          </q-card-section>

          <q-card-section class="q-pt-none">
            Ta akcja spowoduje usuniecie obecnego tygodnia, czy chcesz wygenerowac nowy plan ?
          </q-card-section>

          <q-card-actions align="right">
            <q-btn flat label="Tak" color="primary" @click="fetchScheduledata()" v-close-popup />
            <q-btn flat label="Nie" color="primary" v-close-popup />
          </q-card-actions>
        </q-card>
    </div>
</template>
<script>
export default{
  methods: {
    fetchScheduledata(){     
      this.$emit('fetchScheduledata')
    },
  },
}
</script>