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
public class Client {

    private int clientId;
    private int userId;
    private String clientName;
    private String clientDescription;
    private String redirectUri;

    public Client() {
    }

    public Client(int clientId) {
        this.clientId = clientId;
    }

    public Client(int clientId, int userId, String clientName, String clientDescription, String redirectUri) {
        this.clientId = clientId;
        this.userId = userId;
        this.clientName = clientName;
        this.clientDescription = clientDescription;
        this.redirectUri = redirectUri;
    }

    public int getClientId() {
        return this.clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }




    public String getClientDescription() {
        return this.clientDescription;
    }

    public void setClientDescription(String clientDescription) {
        this.clientDescription = clientDescription;
    }

    public String getRedirectUri() {
        return this.redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }


}
