/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.identityservice.model;

/**
 *
 * @author asawe
 */
public class Users implements java.io.Serializable {

    private Integer usersId;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;

    public Users() {
    }

    public Users(String firstName, String lastName, String email, String mobile) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
    }

    public Users(int usersId, String firstName, String lastName, String email, String mobile) {
        this.usersId = usersId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
    }

    public Integer getUsersId() {
        return this.usersId;
    }

    public void setUsersId(Integer usersId) {
        this.usersId = usersId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
