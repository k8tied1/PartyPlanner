import axios from 'axios';

export default {

  getAllMessagesForParty(partyId) {
    return axios.get(`/${partyId}/messages`)
  },
  addMessage(partyId, message){
      message.party_id = partyId;
      return axios.post(`/${partyId}/messages`,message);
  },
  deleteMsgs(msgList){
    return axios.put(`/messages/deletelist`,msgList);
  }

}
