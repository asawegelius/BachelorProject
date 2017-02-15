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
public class Options  implements java.io.Serializable {


     private Integer optionId;
     private int electionId;
     private String theOption;

    public Options() {
    }
    
    public Options(int electionId) {
        this.electionId = electionId;
    }
    public Options(int electionId, String theOption) {
       this.electionId = electionId;
       this.theOption = theOption;
    }
   
    public Integer getOptionId() {
        return this.optionId;
    }
    
    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }
    public int getElectionId() {
        return this.electionId;
    }
    
    public void setElectionId(int electionId) {
        this.electionId = electionId;
    }
    public String getTheOption() {
        return this.theOption;
    }
    
    public void setTheOption(String theOption) {
        this.theOption = theOption;
    }
 
}
