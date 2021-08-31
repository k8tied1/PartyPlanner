package com.techelevator.dao;

public interface PartyDao {
    /**
     * Checks the database to see if the username given is the owner of the party
     * @param username - username of the user
     * @param partyId - party id
     * @return - if the id corresponding to the username is the owner of the party
     */
    boolean isUserOwner(String username, int partyId);
}
