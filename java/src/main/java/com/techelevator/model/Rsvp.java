package com.techelevator.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class Rsvp {
    private int rsvp_id;
    private int party_id; //this is coming from the URL so no need to be required
    @NotBlank(message="Name is required")
    private String name;
    @Min(value=1, message = "Must RSVP for at least 1 person.")
    private int numAttending;
    private String email; //allowed to be blank
    private String deets; //allowed to be blank

    public int getRsvp_id() {
        return rsvp_id;
    }

    public void setRsvp_id(int rsvp_id) {
        this.rsvp_id = rsvp_id;
    }

    public int getParty_id() {
        return party_id;
    }

    public void setParty_id(int party_id) {
        this.party_id = party_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumAttending() {
        return numAttending;
    }

    public void setNumAttending(int numAttending) {
        this.numAttending = numAttending;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeets() {
        return deets;
    }

    public void setDeets(String deets) {
        this.deets = deets;
    }
}
