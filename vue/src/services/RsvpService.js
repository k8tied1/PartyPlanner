import axios from 'axios';

export default {


  addRsvp(partyId, rsvp){
    rsvp.party_id = partyId;
      return axios.post(`/${partyId}/rsvp`,rsvp);
  },

  getAllRsvps(partyId){
    return axios.get(`/${partyId}/rsvp`);
  },
  deleteRsvps(rsvpList){
    return axios.put(`/rsvp/deletelist`,rsvpList);
  }

}
