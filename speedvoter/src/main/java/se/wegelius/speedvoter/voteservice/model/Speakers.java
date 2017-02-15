/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.speedvoter.voteservice.model;

import java.util.Date;

/**
 *
 * @author asawe
 */
public class Speakers {

    private Integer speakersId;
    private int eventId;
    private int gateId;
    private Date time;

    public Speakers() {
    }

    public Speakers(int eventId) {
        this.eventId = eventId;
    }

    public Speakers(int eventId, int gateId, Date time) {
        this.eventId = eventId;
        this.gateId = gateId;
        this.time = time;
    }

    public Integer getSpeakersId() {
        return this.speakersId;
    }

    public void setSpeakersId(Integer speakersId) {
        this.speakersId = speakersId;
    }

    public int getEventId() {
        return this.eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getGateId() {
        return this.gateId;
    }

    public void setGateId(int gateId) {
        this.gateId = gateId;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

}
