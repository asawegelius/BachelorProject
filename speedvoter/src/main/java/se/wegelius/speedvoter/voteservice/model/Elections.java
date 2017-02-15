/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.speedvoter.voteservice.model;

/**
 *
 * @author asawe
 */
import java.util.Date;
import javax.validation.constraints.NotNull;

public class Elections implements java.io.Serializable {

    private Integer electionId;
    @NotNull(message = "You need to state the event id. ")
    private int eventId;
    private String post;
    private Date date;
    private Integer minVotes;
    private Integer maxVotes;
    private Boolean secret;
    private Boolean active;

    public Elections() {
    }

    public Elections(int eventId) {
        this.eventId = eventId;
    }

    public Elections(int electionId, int eventId, String post, Date date, Integer minVotes, Integer maxVotes, Boolean secret, Boolean active) {
        this.electionId = electionId;
        this.eventId = eventId;
        this.post = post;
        this.date = date;
        this.minVotes = minVotes;
        this.maxVotes = maxVotes;
        this.secret = secret;
        this.active = active;
    }

    public Integer getElectionId() {
        return this.electionId;
    }

    public void setElectionId(Integer electionId) {
        this.electionId = electionId;
    }

    public int getEventId() {
        return this.eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getPost() {
        return this.post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getMinVotes() {
        return this.minVotes;
    }

    public void setMinVotes(Integer minVotes) {
        this.minVotes = minVotes;
    }

    public Integer getMaxVotes() {
        return this.maxVotes;
    }

    public void setMaxVotes(Integer maxVotes) {
        this.maxVotes = maxVotes;
    }

    public Boolean getSecret() {
        return this.secret;
    }

    public void setSecret(Boolean secret) {
        this.secret = secret;
    }

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}
