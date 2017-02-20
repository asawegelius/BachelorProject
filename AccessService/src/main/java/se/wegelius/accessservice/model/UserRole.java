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
public class UserRole {

    private Integer userRoleId;
    private int roleId;
    private int userId;

    public UserRole() {
    }

    public UserRole(int roleId, int userId) {
        this.roleId = roleId;
        this.userId = userId;
    }

    public Integer getUserRoleId() {
        return this.userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
