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
public class VotersVotes {

    private Integer votersVoteId;
    private int gateId;
    private int votesId;

    public VotersVotes() {
    }

    public VotersVotes(int gateId, int votesId) {
        this.gateId = gateId;
        this.votesId = votesId;
    }

    public Integer getVotersVoteId() {
        return this.votersVoteId;
    }

    public void setVotersVoteId(Integer votersVoteId) {
        this.votersVoteId = votersVoteId;
    }

    public int getGateId() {
        return this.gateId;
    }

    public void setGateId(int gateId) {
        this.gateId = gateId;
    }

    public int getVotesId() {
        return this.votesId;
    }

    public void setVotesId(int votesId) {
        this.votesId = votesId;
    }

}
