<template>
    <q-linear-progress stripe rounded :size="props.size" :value="props.value" :color="props.color" class="q-mt-sm"/>
</template>
<script>
export default {
    props:['props'],
}
</script>