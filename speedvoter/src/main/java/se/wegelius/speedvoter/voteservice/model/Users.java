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
public class Users {

    private Integer userId;
    private String email;
    private String userpassword;
    private String name;

    public Users() {
    }

    public Users(String email, String userpassword) {
        this.email = email;
        this.userpassword = userpassword;
    }

    public Users(String email, String userpassword, String name) {
        this.email = email;
        this.userpassword = userpassword;
        this.name = name;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserpassword() {
        return this.userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
