/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.accessservice.model;

/**
 *
 * @author asawe
 */
public class User {

    private Integer userId;
    private String userName;

    public User() {
    }

    public User(String userName) {
        this.userName = userName;
    }

    public User(Integer userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
