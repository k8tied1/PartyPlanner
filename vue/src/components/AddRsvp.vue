<template>
  <b-container class="greyBackground">
    <h1>ARE YOU IN?</h1>

    <b-form v-on:submit.prevent="addRsvp">
      <b-form-input
        id="name"
        v-model="rsvp.name"
        placeholder="What's your name?"
        required
      ></b-form-input>
      <label for="demo-sb">How many are you bringing?</label>
      <b-form-spinbutton
        id="demo-sb"
        v-model="rsvp.numAttending"
        min="1"
        max="10"
        required
        inline
      ></b-form-spinbutton>
      <b-form-input
        id="email"
        v-model="rsvp.email"
        placeholder="Email address"
        type="email"
      ></b-form-input>
      <b-form-input
        id="name"
        v-model="rsvp.deets"
        placeholder="Anything additional you want us to know"
      ></b-form-input>

      <div id="buttonSpacer">
        <b-button type="submit" variant="dark" v-if="!$store.state.alreadyRSVPd">I'm There, Baby</b-button>

        <b-button variant="dark">
          <add-to-calendar
            title="Chris Dwyer Birthday party"
            location="Villa Hills Civic Club Inc, 729 Rogers Rd, Villa Hills, KY 41017, USA"
            :start="new Date(2021, 10, 13, 20)"
            :end="new Date(2021, 10, 14, 0)"
            details="Talk Thirty To Me for Chris's birthday party"
            inline-template
          >
            <google-calendar id="google-calendar">
              <i class="fa fa-google"></i> Add to Google calendar
            </google-calendar>

                    <!-- just in case we want more...
            <microsoft-calendar id="microsoft-calendar">
              <i class="fa fa-windows"></i> Add to Microsoft live calendar
            </microsoft-calendar>
            
            <office365-calendar id="office365-calendar">
              <i class="fa fa-windows"></i> Add to Office365 outlook calendar
            </office365-calendar>
            -->
          </add-to-calendar>
        </b-button>

         <b-button v-on:click="toggleConfetti" v-if="$store.state.showCelebration" id="stop-confetti-button"
        >FFS Enough with the Confetti!</b-button
      >
      </div>
    </b-form>
  </b-container>
</template>

<script>
import rsvpService from "../services/RsvpService";
export default {
  data() {
    return {
      rsvp: { name: "", numAttending: 1, email: "", deets: "" },
    };
  },
  methods: {
    toggleConfetti() {
      this.$store.commit("TOGGLE_CELEBRATE");
    },
    addRsvp() {
      rsvpService
        .addRsvp(this.$store.state.partyId, this.rsvp)
        .then((response) => {
          console.log(`${response.status} Success!`);
          this.toggleConfetti();
        })
        .catch((error) => {
          console.log(`ERROR: ${error.status}`);
        });
    },
  },
};
</script>



<style scoped>
#buttonSpacer {
  display: flex;
  justify-content: space-around;
}
</style>