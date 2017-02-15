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
public class Userrights {

    private Integer userrightsId;
    private int level;
    private Integer org;
    private Boolean active;

    public Userrights() {
    }

    public Userrights(int level) {
        this.level = level;
    }

    public Userrights(int level, Integer org, Boolean active) {
        this.level = level;
        this.org = org;
        this.active = active;
    }

    public Integer getUserrightsId() {
        return this.userrightsId;
    }

    public void setUserrightsId(Integer userrightsId) {
        this.userrightsId = userrightsId;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Integer getOrg() {
        return this.org;
    }

    public void setOrg(Integer org) {
        this.org = org;
    }

    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}
