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
public class Votes {

    private Integer voteId;
    private int electionId;
    private int optionId;

    public Votes() {
    }

    public Votes(int electionId, int optionId) {
        this.electionId = electionId;
        this.optionId = optionId;
    }
    
    public Votes(int voteId, int electionId, int optionId){
        this.voteId = voteId;
        this.electionId = electionId;
        this.optionId = optionId;
    }

    public Integer getVoteId() {
        return this.voteId;
    }

    public void setVoteId(Integer voteId) {
        this.voteId = voteId;
    }

    public int getElectionId() {
        return this.electionId;
    }

    public void setElectionId(int electionId) {
        this.electionId = electionId;
    }

    public int getOptionId() {
        return this.optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

}
