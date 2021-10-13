<template>
  <div>
    <div
      class="
        q-gutter-y-md q-col-gutter-md q-pa-md
        fit
        row
        wrap
        justify-center
        content-center
      "
    >
      <div v-for="(account, index) in membersAccounts" :key="`member-${index}`">
        <div @click="goToMemberSite()" :userData="membersAccounts">
          <img src="../assets/Netflix-avatar.jpg" class="user-icon" />
          <h5 class="text-center">{{ account.name }}</h5>
        </div>
      </div>
      <div @click="isDialogAddNewMemberShowed = !isDialogAddNewMemberShowed">
        <q-card class="user-icon bg-primary">
          <q-icon
            name="add"
            size="64px"
            class="text-white absolute-center"
          ></q-icon>
        </q-card>
      </div>
      <q-dialog v-model="isDialogAddNewMemberShowed" persistent
        ><AddNewMember @addNewUser="addNewUser($event)"></AddNewMember
      ></q-dialog>
    </div>
    <div class="row justify-center">
      <q-btn label="usun kato"></q-btn>
    </div>
  </div>
</template>
<script>
import Member from "./Member.vue";
import AddNewMember from "../components/accountLists/AddNewMember.vue";
export default {
  render(h) {
    return h(Member, { props: membersAccounts });
  },
  data() {
    return {
      isDialogAddNewMemberShowed: false,
      membersAccounts: [
        {
          name: "Jan",
          age: "28",
          height: "175",
          currentWeight: 90,
          currentFat: 10,
          currentMussels: 10,
          currentWater: 10,
          recommendedCalories: 3000,
          recommendedCarbohydrates: 400,
          recommendedFat: 200,
          recommendedProtein: 100,
          recommendedRoughage: 200,
        },
      ],
    };
  },
  methods: {
    goToMemberSite() {
      // popraw na konto
      this.$router.push("/kato");
    },
    addNewUser(data) {
      this.membersAccounts.push(data);
    },
  },
  components: {
    AddNewMember,
  },
};
</script>
<style lang="sass">
.big-faces
  font-size: 12rem
.user-icon
  width: 200px
  height: 200px
.my-card
  width: 100%
  max-width: 250px
</style>