<template>
  <div>
    <b-form v-on:submit.prevent="addMessage">
      <b-form-input
        id="name"
        v-model="msg.from_name"
        placeholder="What's your name?"
        
      ></b-form-input>
      <b-form-input
        id="message"
        v-model="msg.message"
        placeholder="Tell it like it is, man."
        required
        maxlength=200
      ></b-form-input>
        <b-button type="submit" variant="dark">Do It</b-button>
    </b-form>
  </div>
</template>

<script>
import messageService from "../services/MessageService"
export default {
    data() {
        return {
            msg: {name: '',message:''}
        }
    },
    methods:{
        addMessage() {
            messageService.addMessage(this.$store.state.partyId,this.msg)
            .then(response=>{
                console.log(response.status);
                this.$emit('changed',this.msg)
            })
            .catch(error=>{console.log(`ERROR: ${error.status}`)})

        }
    }
};
</script>

<style>
</style>