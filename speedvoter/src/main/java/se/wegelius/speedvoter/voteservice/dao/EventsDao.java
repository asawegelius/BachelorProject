/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.speedvoter.voteservice.dao;

import org.slf4j.LoggerFactory;
import se.wegelius.speedvoter.voteservice.model.hibernateobjects.EventsHO;

/**
 *
 * @author asawe
 */
public class EventsDao extends GenericDao<EventsHO, Integer> {
    
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(EventsDao.class);

    public EventsDao(Class<EventsHO> type) {
        super(type);
    }

    /**
     *
     */
    public EventsDao() {
        super(EventsHO.class);
    }
}
