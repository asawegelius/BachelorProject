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
    private String user_name;
    private String passwordSalt;
    private String passwordHash;

    public Logins() {
    }

    public Logins(String user_name, String passwordSalt, String passwordHash) {
        this.user_name = user_name;
        this.passwordSalt = passwordSalt;
        this.passwordHash = passwordHash;
    }

    public Logins(int login_id, String user_name, String passwordSalt, String passwordHash) {
        this.loginsId = login_id;
        this.user_name = user_name;
        this.passwordSalt = passwordSalt;
        this.passwordHash = passwordHash;
    }

    public Integer getLoginsId() {
        return this.loginsId;
    }

    public void setLoginsId(Integer loginsId) {
        this.loginsId = loginsId;
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

}
