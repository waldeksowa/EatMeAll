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
  </div>
</template>
<script>
import { MEMBER } from "../EndpointAddresses";
import { mapGetters } from "vuex";
import AddNewMember from "../components/accountLists/AddNewMember.vue";
export default {
  data() {
    return {
      isDialogAddNewMemberShowed: false,
      membersAccounts: [],
      memberUrl: MEMBER,
    };
  },
  computed: {
    ...mapGetters("store", ["jwt"]),
  },
  methods: {
    fetchData() {
      var myHeaders = new Headers();
      myHeaders.append("Authorization", `Bearer ${this.jwt}`);

      var requestOptions = {
        method: "GET",
        headers: myHeaders,
        redirect: "follow",
      };

      fetch(this.memberUrl, requestOptions)
        .then((response) => response.json())
        .then((result) => (this.membersAccounts = result))
        .catch((error) => console.log("error", error));
    },
    goToMemberSite() {
      this.$router.push("/konto");
    },
    addNewUser(data) {
      var myHeaders = new Headers();
      myHeaders.append("Authorization", `Bearer ${this.jwt}`);
      myHeaders.append("Content-Type", "application/json");

      var raw = JSON.stringify(data);

      var requestOptions = {
        method: "POST",
        headers: myHeaders,
        body: raw,
        redirect: "follow",
      };

      fetch(this.memberUrl, requestOptions)
        .then((response) => response.text())
        .then((result) => console.log(result))
        .catch((error) => console.log("error", error));

      this.fetchData();
    },
  },
  components: {
    AddNewMember,
  },
  mounted() {
    this.fetchData();
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