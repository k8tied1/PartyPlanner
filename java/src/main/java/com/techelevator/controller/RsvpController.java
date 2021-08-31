package com.techelevator.controller;


import com.techelevator.dao.PartyDao;
import com.techelevator.dao.RsvpDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Rsvp;
import com.techelevator.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class RsvpController {

    private PartyDao partyDao;
    private UserDao userDao;
    private RsvpDao rsvpDao;

    public RsvpController(PartyDao partyDao, UserDao userDao, RsvpDao rsvpDao) {
        this.partyDao = partyDao;
        this.userDao = userDao;
        this.rsvpDao = rsvpDao;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{partyId}/rsvp", method = RequestMethod.GET)
    public List<Rsvp> getAllRsvps(@PathVariable int partyId, Principal principal) throws Exception {
        if (canUserAccess(partyId,principal.getName())){
            return rsvpDao.getAllRSVPsForParty(partyId);
        }
        else {
            throw new Exception("You must be the creator of the party or an admin to see the RSVPs.");
        }
    }
    @RequestMapping(value = "/{partyId}/rsvp", method = RequestMethod.POST)
    public void addRsvp(@PathVariable int partyId, @Valid @RequestBody Rsvp rsvp) throws Exception {
        rsvp.setParty_id(partyId);
        rsvpDao.add(rsvp);
    }

    /**
     * So I don't love this. In order to send a list to delete, I have to use a put since a delete
     * doesn't have a body
     * @param array - arrays of Rsvps to delete
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/rsvp/deletelist", method = RequestMethod.PUT)
    public void deleteABunchOfRsvps( @RequestBody Rsvp[] array) {

        rsvpDao.deleteListOfRsvps(array);
    }

    private boolean canUserAccess(int partyId, String username){
        return isAdmin(username) || partyDao.isUserOwner(username,partyId);
    }

    private boolean isAdmin(String username){
        User user = userDao.findByUsername(username);
        return (user.hasRole("ADMIN"));
    }
}
