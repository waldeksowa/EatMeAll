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
        <div
          @click="goToMemberSite(account.id)"
          :userData="membersAccounts"
          class="member-container"
        >
          <center>
            <q-img src="../assets/Netflix-avatar.jpg" class="user-icon" />
          </center>
          <h5 class="text-center long-word big-first-letter">
            {{ account.name }}
          </h5>
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
import { mapActions } from "vuex";
import AddNewMember from "../components/accountLists/AddNewMember.vue";
export default {
  data() {
    return {
      isDialogAddNewMemberShowed: false,
      membersAccounts: [],
    };
  },
  computed: {
    ...mapGetters("store", ["jwt"]),
  },
  methods: {
    ...mapActions("store", ["updateMemberIdToShow", "errorMesage"]),
    fetchData() {
      var myHeaders = new Headers();
      myHeaders.append("Authorization", `Bearer ${this.jwt}`);

      var requestOptions = {
        method: "GET",
        headers: myHeaders,
        redirect: "follow",
      };

      fetch(MEMBER, requestOptions)
        .then((response) => response.json())
        .then((result) => (this.membersAccounts = result))
        .catch((error) => {
          console.log(error);
          this.errorMesage("Ups... nie udało się pobrać danych o użytkowniku");
        });
    },
    goToMemberSite(aMemberIdToShowAccountDetail) {
      this.updateMemberIdToShow(aMemberIdToShowAccountDetail);
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

      fetch(MEMBER, requestOptions)
        .then((response) => response.json())
        .then((result) => result)
        .catch((error) => {
          console.log(error);
          this.errorMesage("Ups... nie udało sie dodać nowego użytkownika");
        });

      this.$router.go(0);
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
.member-container
  width: 200px
.big-faces
  font-size: 12rem
.user-icon
  width: 200px
  height: 200px
.my-card
  width: 100%
  max-width: 250px
</style>