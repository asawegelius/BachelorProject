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
public class RolePermission {

    private int rolePermissionId;
    private int permissionId;
    private int roleId;

    public RolePermission() {
    }

    public RolePermission(int rolePermissionId, int permissionId, int roleId) {
        this.rolePermissionId = rolePermissionId;
        this.permissionId = permissionId;
        this.roleId = roleId;
    }

    public int getRolePermissionId() {
        return this.rolePermissionId;
    }

    public void setRolePermissionId(int rolePermissionId) {
        this.rolePermissionId = rolePermissionId;
    }

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

}
