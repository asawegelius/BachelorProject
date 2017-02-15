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
public class Voter {

    private Integer voterId;
    private int orgsId;
    private String memberCode;
    private String name;
    private String mail;
    private String mobil;

    public Voter() {
    }

    public Voter(int orgsId, String memberCode, String name, String mail, String mobil) {
        this.orgsId = orgsId;
        this.memberCode = memberCode;
        this.name = name;
        this.mail = mail;
        this.mobil = mobil;
    }

    public Integer getVoterId() {
        return this.voterId;
    }

    public void setVoterId(Integer voterId) {
        this.voterId = voterId;
    }

    public int getOrgsId() {
        return this.orgsId;
    }

    public void setOrgsId(int orgsId) {
        this.orgsId = orgsId;
    }

    public String getMemberCode() {
        return this.memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMobil() {
        return this.mobil;
    }

    public void setMobil(String mobil) {
        this.mobil = mobil;
    }

}
