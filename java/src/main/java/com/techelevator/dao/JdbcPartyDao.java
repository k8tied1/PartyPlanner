package com.techelevator.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcPartyDao implements PartyDao{
    private  JdbcTemplate jdbcTemplate;

    public JdbcPartyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Checks the database to see if the username given is the owner of the party
     *
     * @param username - username of the user
     * @param partyId  - party id
     * @return - if the id corresponding to the username is the owner of the party
     */
    @Override
    public boolean isUserOwner(String username, int partyId) {
        String sql = "SELECT COUNT(*) FROM parties p " +
                "INNER JOIN users u ON u.user_id = p.owner_id " +
                "WHERE u.username = ? " +
                "  AND p.party_id = ?";

        int count = jdbcTemplate.queryForObject(sql, Integer.class, username, partyId);
        return count==1; //there should only ever be at most one since partyId is the PK for parties
    }
}
