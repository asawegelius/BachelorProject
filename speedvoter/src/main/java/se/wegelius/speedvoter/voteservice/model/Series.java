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
public class Series {

    private Integer seriesId;
    private String accessCode;
    private String serie;
    private Boolean vacant;

    public Series() {
    }

    public Series(String accessCode, String serie, Boolean vacant) {
        this.accessCode = accessCode;
        this.serie = serie;
        this.vacant = vacant;
    }

    public Integer getSeriesId() {
        return this.seriesId;
    }

    public void setSeriesId(Integer seriesId) {
        this.seriesId = seriesId;
    }

    public String getAccessCode() {
        return this.accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getSerie() {
        return this.serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Boolean getVacant() {
        return this.vacant;
    }

    public void setVacant(Boolean vacant) {
        this.vacant = vacant;
    }

}
