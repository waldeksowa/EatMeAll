<template>
  <div>
    <div
      class="
        row
        justify-center
        items-center
        full-height full-width
        show-tools-list
      "
    >
      <Copy />
      <Reroll />
      <Chose @replaceMeal="replaceMeal($event)" />
      <!-- <Info /> -->
    </div>
  </div>
</template>
<script>
import Copy from "../cardTooltips/Copy.vue";
import Chose from "../cardTooltips/Chose.vue";
import Reroll from "../cardTooltips/Reroll.vue";
// import Info from "../cardTooltips/Info.vue";
export default {
  props: { index: Number },
  components: { Copy, Reroll, Chose },
  data() {
    return {
      showIndex: null,
    };
  },
  methods: {
    replaceMeal(aData) {
      this.$emit("replaceMeal", aData);
    },
  },
};
</script>
<style lang="sass">
.show-tools-list
  opacity: 0%
  &:hover
    animation: show-tools 0.3s
    animation-fill-mode: forwards
@keyframes show-tools
  from
    opacity: 0%
    background-color: hsla(0,0%,100%,0.5)
  to
    opacity: 100%
    background-color: hsla(0,0%,50%,1)
</style>