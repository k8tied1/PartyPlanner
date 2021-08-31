package com.techelevator.dao;

import com.techelevator.model.Message;

import java.util.List;

public interface MessageDao {
    /**
     * Retrieves a list of all of the messages for a specific party.
     * @param partyId
     * @return List of Messages, sorted by created timestamp most recent first
     */
    List<Message> getAllMessagesForParty(int partyId);

    /**
     * Add a new message for a party
     * @param partyId
     * @param newMessage - the message should contain the party id it's for, who created it and the actual message
     * @return the new message again with the id and created_id set
     */
    Message addNewMessageForParty(int partyId, Message newMessage);

    /**
     * Delete a specific message
     * @param partyId - the party the message is for
     * @param messageId - the message id to delete
     */
    void deleteMessageForParty(int partyId, int messageId);

    /**
     * Delete all of the messages for a party
     * @param partyId
     */
    void deleteAllMessagesForParty(int partyId);

    /**
     * Deletes an array of messages by message id
     * @param array
     */
    void deleteListOfMessages(Message[] array);
}
