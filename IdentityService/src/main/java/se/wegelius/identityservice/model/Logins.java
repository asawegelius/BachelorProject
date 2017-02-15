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
public class Logins implements java.io.Serializable {

    private Integer loginsId;
    private int user_id;
    private String passwordSalt;
    private String passwordHash;

    public Logins() {
    }

    public Logins(int user_id, String passwordSalt, String passwordHash) {
        this.user_id = user_id;
        this.passwordSalt = passwordSalt;
        this.passwordHash = passwordHash;
    }

    public Logins(int login_id, int user_id, String passwordSalt, String passwordHash) {
        this.loginsId = login_id;
        this.user_id = user_id;
        this.passwordSalt = passwordSalt;
        this.passwordHash = passwordHash;
    }

    public Integer getLoginsId() {
        return this.loginsId;
    }

    public void setLoginsId(Integer loginsId) {
        this.loginsId = loginsId;
    }

    public int getUsers() {
        return this.user_id;
    }

    public void setUsers(int user_id) {
        this.user_id = user_id;
    }

    public String getPasswordSalt() {
        return this.passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

}
