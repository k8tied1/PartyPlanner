<template>
  <div class="salmonbackground">
    <h1>Admin Page</h1>
    <b-container>
      <h2>RSVPs Total: {{ totalCount }}</h2>
      <b-table
        striped
        hover
        :items="rsvps"
        :fields="rsvpfields"
        select-mode="multi"
        responsive="sm"
        ref="selectableTable"
        selectable
        @row-selected="onRowSelected"
      >
        <template #cell(selected)="{ rowSelected }">
          <template v-if="rowSelected">
            <span aria-hidden="true">&check;</span>
            <span class="sr-only">Selected</span>
          </template>
          <template v-else>
            <span aria-hidden="true">&nbsp;</span>
            <span class="sr-only">Not selected</span>
          </template>
        </template>
      </b-table>
      <b-button @click="deleteRsvps"> Delete Selected RSVPs </b-button>
    </b-container>
    <b-container>
      <h2>Messages</h2>
      <b-table
        striped
        hover
        :items="messages"
        :fields="msgfields"
        select-mode="multi"
        responsive="sm"
        ref="selectableTable"
        selectable
        @row-selected="onMsgSelected"
      >
        <template #cell(selected)="{ rowSelected }">
          <template v-if="rowSelected">
            <span aria-hidden="true">&check;</span>
            <span class="sr-only">Selected</span>
          </template>
          <template v-else>
            <span aria-hidden="true">&nbsp;</span>
            <span class="sr-only">Not selected</span>
          </template>
        </template>
      </b-table>
      <b-button v-on:click="deleteMsgs"> Delete Selected Messages </b-button>
    </b-container>
  </div>
</template>

<script>
import rsvpService from "@/services/RsvpService.js";
import messageService from "@/services/MessageService.js";
export default {
  name: "admin",
  data() {
    return {
      rsvps: [],
      rsvpselected: [],
      rsvpfields: ["selected", "name", "numAttending", "email", "deets"],
      messages: [],
      msgselected: [],
      msgfields: ["selected", "from_name", "message", "created"],
    };
  },
  created() {
    this.initRsvps();
    this.initMsgs();
  },
  computed: {
    totalCount() {
      return this.rsvps.reduce((sum, current_rsvp) => {
        return sum + current_rsvp.numAttending;
      }, 0);
    },
  },
  methods: {
    initRsvps() {
      rsvpService
        .getAllRsvps(this.$store.state.partyId)
        .then((response) => {
          this.rsvps = response.data;
        })
        .catch((err) => {
          console.log(`Error fetching rsvps: ${err.status}`);
        });
    },
    initMsgs() {
      messageService
        .getAllMessagesForParty(this.$store.state.partyId)
        .then((response) => {
          this.messages = response.data;
        })
        .catch((err) => {
          console.log(`Error fetching messages: ${err.status}`);
        });
    },
    onRowSelected(items) {
      this.rsvpselected = items;
    },
    onMsgSelected(items) {
      this.msgselected = items;
    },
    deleteRsvps() {
      rsvpService
        .deleteRsvps(this.rsvpselected)
        .then((response) => {
          console.log(`Success! ${response.status}`);
          this.initRsvps();
        })
        .catch((err) => {
          console.log(`Error deleting RSVPs ${err.statusText}`);
        });
    },
    deleteMsgs() {
      messageService
        .deleteMsgs(this.msgselected)
        .then((response) => {
          console.log(`Success! ${response.status}`);
          this.initMsgs();
        })
        .catch((err) => {
          console.log(`Error deleting Messages ${err.statusText}`);
        });
    },
  },
};
</script>

<style scoped>

</style>