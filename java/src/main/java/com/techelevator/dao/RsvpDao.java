package com.techelevator.dao;

import com.techelevator.model.Rsvp;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RsvpDao {
    /**
     * get all of the RSVPs for a given party
     * @param partyId
     * @return all of the Rsvps
     */
    List<Rsvp> getAllRSVPsForParty(int partyId);

    /**
     * Add an rsvp for a party
     * @param rsvp - must have party id, name and numAttending
     */
    void add(Rsvp rsvp);

    /**
     * This will delete all of the rsvps in teh list based on their id
     * @param rsvpList
     */
    void deleteListOfRsvps(Rsvp[] rsvpList);
}
