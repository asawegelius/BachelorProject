/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.speedvoter.voteservice.model;

import se.wegelius.speedvoter.voteservice.model.hibernateobjects.UsersHO;

/**
 *
 * @author asawe
 */
public class UsersUserrights {

    private Integer usersUserrightsId;
    private int userrightsId;
    private int usersId;

    public UsersUserrights() {
    }

    public UsersUserrights(int userrightsId, int usersId) {
        this.userrightsId = userrightsId;
        this.usersId = usersId;
    }

    public Integer getUsersUserrightsId() {
        return this.usersUserrightsId;
    }

    public void setUsersUserrightsId(Integer usersUserrightsId) {
        this.usersUserrightsId = usersUserrightsId;
    }

    public int getUserrightsId() {
        return this.userrightsId;
    }

    public void setUserrightsId(int userrightsId) {
        this.userrightsId = userrightsId;
    }

    public int getUsersId() {
        return this.usersId;
    }

    public void setUsersId(int usersId) {
        this.usersId = usersId;
    }

}
