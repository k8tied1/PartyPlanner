package com.techelevator.controller;

import com.techelevator.dao.MessageDao;
import com.techelevator.dao.PartyDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Message;
import com.techelevator.model.Rsvp;
import com.techelevator.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class MessageController {

    private MessageDao messageDao;
    private UserDao userDao;
    private PartyDao partyDao;

    public MessageController(MessageDao messageDao, UserDao userDao, PartyDao partyDao) {
        this.messageDao = messageDao;
        this.userDao = userDao;
        this.partyDao = partyDao;
    }

    /**
     * API endpoint to retrieve all the messages for a given party. Will be ordered most recent to least recent
     * @param partyId - the id of the party to retrive messages for
     * @return a list of messages ordered by date with most recent first
     */
    @RequestMapping(value = "/{partyId}/messages", method = RequestMethod.GET)
    public List<Message> getAllMessagesForParty(@PathVariable int partyId) {
        return messageDao.getAllMessagesForParty(partyId);
    }

    /**
     * API endpoint to add a new message for a party
     * @param partyId - the party id
     * @param newMessage - the message, must include the actual message. if the name of the person creating it as blank, it will default to
     *                   anonymous chris admirer.
     * @return - the new message with the id set
     */
    @RequestMapping(value = "/{partyId}/messages", method = RequestMethod.POST)
    public Message addMessage(@PathVariable int partyId, @Valid @RequestBody Message newMessage) {
        return messageDao.addNewMessageForParty(partyId,newMessage);
    }

    /**
     * API endpoint to delete a specific message for a party
     * @param partyId
     * @param messageId
     */
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/{partyId}/messages/{messageId}", method = RequestMethod.DELETE)
    public void deleteMessage(@PathVariable int partyId, @PathVariable int messageId, Principal principal) throws Exception {
        if (canUserAccess(partyId, principal.getName())) {
            messageDao.deleteMessageForParty(partyId, messageId);
        } else {
            throw new Exception("You must be the creator of the party or an admin to delete messages.");
        }
    }

    /**
     * API endpoint to delete all the messages for a party
     * @param partyId
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/{partyId}/messages", method = RequestMethod.DELETE)
    public void deleteAllMessages(@PathVariable int partyId, Principal principal) throws Exception {
        messageDao.deleteAllMessagesForParty(partyId);
    }

    /**
     * So I don't love this. In order to send a list to delete, I have to use a put since a delete
     * doesn't have a body
     * @param array - arrays of Messages to delete
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/messages/deletelist", method = RequestMethod.PUT)
    public void deleteABunchOfMessages( @RequestBody Message[] array) {
        messageDao.deleteListOfMessages(array);
    }

    private boolean canUserAccess(int partyId, String username){
        return isAdmin(username) || partyDao.isUserOwner(username,partyId);
    }

    private boolean isAdmin(String username){
        User user = userDao.findByUsername(username);
        return (user.hasRole("ROLE_ADMIN"));
    }
}

