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
public class Gate  implements java.io.Serializable {


     private Integer gateId;
     private int eventId;
     private int voterId;
     private String accessCode;
     private Boolean active;

    public Gate() {
    }

    public Gate(int eventId, int voterId, String accessCode, Boolean active) {
       this.eventId = eventId;
       this.voterId = voterId;
       this.accessCode = accessCode;
       this.active = active;
    }
   
    public Integer getGateId() {
        return this.gateId;
    }
    
    public void setGateId(Integer gateId) {
        this.gateId = gateId;
    }
    public int getEventId() {
        return this.eventId;
    }
    
    public void setEvents(int eventId) {
        this.eventId = eventId;
    }
    public int getVoterId() {
        return this.voterId;
    }
    
    public void setVoter(int voterId) {
        this.voterId = voterId;
    }
    public String getAccessCode() {
        return this.accessCode;
    }
    
    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }
    public Boolean getActive() {
        return this.active;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    }

}
