package com.techelevator.dao;

import com.techelevator.model.Message;
import com.techelevator.model.Rsvp;
import com.techelevator.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component //use me for dependency injection of MessageDao
public class JdbcMessageDao implements  MessageDao{

    private JdbcTemplate jdbcTemplate;
    private final String allMessageColumns = " message_id, party_id, from_name, message, created_at ";
    private final String newMessageReqColumns = " party_id, message ";

    public JdbcMessageDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Retrieves a list of all of the messages for a specific party.
     *
     * @param partyId
     * @return List of Messages, sorted by created timestamp most recent first
     */
    @Override
    public List<Message> getAllMessagesForParty(int partyId) {
        List<Message> list = new ArrayList<>();
        String sql = "SELECT  " +
                allMessageColumns +
                " FROM messages" +
                " WHERE party_id = ? " +
                " ORDER BY created_at DESC";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, partyId);
        while(results.next()) {
            list.add(mapRowToMessage(results));
        }
        return list;
    }

    /**
     * Add a new message for a party
     *
     * @param partyId
     * @param newMessage - the message should contain the party id it's for, who created it and the actual message
     * @return the new message again with the id and created_id set
     */
    @Override
    public Message addNewMessageForParty(int partyId, Message newMessage) {
        int newMessageId = -1;

        //if the message doesn't include who it's from, use the default
        if (newMessage.getFrom_name()==null){
            String sql = "INSERT INTO messages(  " +
                    newMessageReqColumns + ") " + //party_id,  message
                    " VALUES (?,?) returning message_id";
            newMessageId = jdbcTemplate.queryForObject(sql, Integer.class,partyId,newMessage.getMessage());

        } else {
            String newMessageColumns = newMessageReqColumns + ", from_name ";
            String sql = "INSERT INTO messages(  " +
                    newMessageColumns + ") " + //party_id,  message, from_name
                    " VALUES (?,?,?) returning message_id";
            newMessageId = jdbcTemplate.queryForObject(sql, Integer.class,partyId, newMessage.getMessage(), newMessage.getFrom_name());
        }
        return getMessageById(newMessageId);
    }

    /**
     * Delete a specific message
     *
     * @param partyId   - the party the message is for
     * @param messageId - the message id to delete
     */
    @Override
    public void deleteMessageForParty(int partyId, int messageId) {
        String sql = "DELETE FROM messages  " +
                " WHERE message_id = ?";
        jdbcTemplate.update(sql,messageId);

    }

    /**
     * Delete all of the messages for a party
     *
     * @param partyId
     */
    @Override
    public void deleteAllMessagesForParty(int partyId) {
        String sql = "DELETE FROM messages  " +
                " WHERE party_id = ?";
        jdbcTemplate.update(sql,partyId);
    }

    /**
     * Deletes an array of messages by message id
     *
     * @param array
     */
    @Override
    public void deleteListOfMessages(Message[] array) {
        String idList="";
        for(Message m : array){
            idList+=m.getMessage_id()+",";
        }
        //take off the last comma
        idList = idList.substring(0,idList.length()-1);

        String sql = "DELETE FROM messages " +
                "WHERE message_id IN ( "+idList+" )"; //this is bad form but since we know it's all numbers, there can't be a sql injection attack
        jdbcTemplate.update(sql);
    }


    private Message getMessageById(int newMessageId) {

        String sql = "SELECT  " +
                allMessageColumns +
                " FROM messages " +
                " WHERE message_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, newMessageId);
        if (results.next()) {
            return mapRowToMessage(results);
        }
        return null;
    }


    private Message mapRowToMessage(SqlRowSet rs) {
        Message m = new Message();
        m.setMessage_id(rs.getInt("message_id"));
        m.setParty_id(rs.getInt("party_id"));
        m.setFrom_name(rs.getString("from_name"));
        m.setMessage(rs.getString("message"));
        m.setCreated(rs.getDate("created_at").toLocalDate());
        return m;
    }
}
