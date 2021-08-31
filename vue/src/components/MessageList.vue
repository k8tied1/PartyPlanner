<template>
  <div>
    <b-container class="scroll" id="messageContainer">
      <h4>What's everyone saying?</h4>
      <div
        class="listitem"
        v-for="message in messages"
        v-bind:key="message.message_id"
      >
        <p>
          <span class="bold">{{ message.from_name }}</span> said:
          {{ message.message }} {{message.created=="just now"? "":"on"}} {{ message.created }}
        </p>
      </div>
      <b-button
        v-if="!showAddMessage"
        v-on:click="showAddMessage = true"
        variant="dark"
      >
        Add a message
      </b-button>
      <add-message v-else v-on:changed="newMessage" />
    </b-container>
  </div>
</template>

<script>
import messageService from "../services/MessageService";
import AddMessage from "./AddMessage.vue";

export default {
  components: {
    AddMessage,
  },
  data() {
    return {
      messages: [],
      showAddMessage: false,
    };
  },
  created() {
    messageService
      .getAllMessagesForParty(this.$store.state.partyId)
      .then((response) => {
        this.messages = response.data;
      });
  },
  methods: {
    newMessage(msg) {
      if (!msg.from_name) {
        msg.from_name = "too chicken to identify";
      }
      msg.created = "just now";
      this.messages.unshift(msg);
      this.showAddMessage = false;
      var myDiv = document.getElementById("messageContainer");
      myDiv.scrollTop = 0;
    },
  },
};
</script>

<style scoped>
div.listitem {
  border: 1px solid black;
  border-radius: 3px 3px;
}
div.listitem:nth-child(odd) {
  background-color: whitesmoke;
}
div.listitem p {
  margin: 0;
}
.bold {
  font-weight: bold;
}
</style>