package com.techelevator.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class Message {

    private int message_id;

    @Min(value=1,message = "You must include a party id!") //can't use NotNull here because it will default to 0
    private int party_id;

    private String from_name;//this defaults to 'Anonymous Chris Admirer'

    @Size(min=1, max=200, message="Must be between 1 and 200 characters!")
    private String message;

    private LocalDate created; //database will default this to current date/time

    public Message(){}

    public Message(int message_id, int party_id, String from_name, String message, LocalDate created) {
        this.message_id = message_id;
        this.party_id = party_id;
        this.from_name = from_name;
        this.message = message;
        this.created = created;
    }

    /*Basic Getters and Setters*/
    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public int getParty_id() {
        return party_id;
    }

    public void setParty_id(int party_id) {
        this.party_id = party_id;
    }

    public String getFrom_name() {
        return from_name;
    }

    public void setFrom_name(String from_name) {
        this.from_name = from_name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }
}
