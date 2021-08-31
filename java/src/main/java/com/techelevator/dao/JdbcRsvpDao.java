package com.techelevator.dao;

import com.techelevator.model.Message;
import com.techelevator.model.Rsvp;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcRsvpDao implements  RsvpDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcRsvpDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * get all of the RSVPs for a given party
     *
     * @param partyId
     * @return all of the Rsvps
     */
    @Override
    public List<Rsvp> getAllRSVPsForParty(int partyId) {
        List<Rsvp> list = new ArrayList<>();
        String sql = "SELECT rsvp_id, party_id, name, numattending, email, deets " +
                " FROM rsvps" +
                " WHERE party_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, partyId);
        while(results.next()) {
            list.add(mapRowToRsvp(results));
        }
        return list;
    }


    /**
     * Add an rsvp for a party
     *
     * @param rsvp - must have party id, name and numAttending
     */
    @Override
    public void add(Rsvp rsvp) {
        String sql = "INSERT INTO rsvps (  " +
                "party_id, name, numAttending, email, deets )" +
                " VALUES (?,?,?,?,?) " ;
        jdbcTemplate.update(sql, rsvp.getParty_id(),rsvp.getName(),rsvp.getNumAttending(),rsvp.getEmail(),rsvp.getDeets());
    }

    /**
     * This will delete all of the rsvps in teh list based on their id
     *
     * @param rsvpList
     */
    @Override
    public void deleteListOfRsvps(Rsvp[] rsvpList) {
        String idList="";
        for(Rsvp rsvp : rsvpList){
            idList+=rsvp.getRsvp_id()+",";
        }
        //take off the last comma
        idList = idList.substring(0,idList.length()-1);

        String sql = "DELETE FROM rsvps " +
                "WHERE rsvp_id IN ( "+idList+" )"; //this is bad form but since we know it's all numbers, there can't be a sql injection attack
        jdbcTemplate.update(sql);
    }

    private Rsvp mapRowToRsvp(SqlRowSet rs) {
        Rsvp p = new Rsvp();
        p.setRsvp_id(rs.getInt("rsvp_id"));
        p.setParty_id(rs.getInt("party_id"));
        p.setName(rs.getString("name"));
        p.setNumAttending(rs.getInt("numattending"));
        p.setDeets(rs.getString("deets"));
        p.setEmail(rs.getString("email"));
        return p;
    }
}
