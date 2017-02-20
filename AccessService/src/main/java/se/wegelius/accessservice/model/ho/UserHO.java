package se.wegelius.accessservice.model.ho;
// Generated Feb 18, 2017 12:40:42 AM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * UserHO generated by hbm2java
 */
public class UserHO  implements java.io.Serializable {


     private Integer userId;
     private String userName;
     private Set<ClientHO> clients = new HashSet<ClientHO>(0);
     private Set<UserRoleHO> userRoles = new HashSet<UserRoleHO>(0);

    public UserHO() {
    }

    public UserHO(String userName, Set<ClientHO> clients, Set<UserRoleHO> userRoles) {
       this.userName = userName;
       this.clients = clients;
       this.userRoles = userRoles;
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
    public Set<ClientHO> getClients() {
        return this.clients;
    }
    
    public void setClients(Set<ClientHO> clients) {
        this.clients = clients;
    }
    public Set<UserRoleHO> getUserRoles() {
        return this.userRoles;
    }
    
    public void setUserRoles(Set<UserRoleHO> userRoles) {
        this.userRoles = userRoles;
    }




}

