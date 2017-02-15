/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.speedvoter.voteservice.dao;


import org.slf4j.LoggerFactory;
import se.wegelius.speedvoter.voteservice.model.hibernateobjects.SpeakersHO;

/**
 *
 * @author asawe
 */
public class SpeakersDao  extends GenericDao<SpeakersHO, Integer> {
        
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SpeakersDao.class);

    public SpeakersDao(Class<SpeakersHO> type) {
        super(type);
    }

    /**
     *
     */
    public SpeakersDao() {
        super(SpeakersHO.class);
    }
}