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
public class Permission {
     private Integer permissionId;
     private int clientId;
     private String object;
     private String target;

    public Permission() {
    }

	
    public Permission(int clientId) {
        this.clientId = clientId;
    }
    public Permission(int clientId, String object, String target) {
       this.clientId = clientId;
       this.object = object;
       this.target = target;
    }
   
    public Integer getPermissionId() {
        return this.permissionId;
    }
    
    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getObject() {
        return this.object;
    }
    
    public void setObject(String object) {
        this.object = object;
    }
    public String getTarget() {
        return this.target;
    }
    
    public void setTarget(String target) {
        this.target = target;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
   
}
