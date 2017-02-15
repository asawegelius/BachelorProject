/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.speedvoter.voteservice.dao;


import org.slf4j.LoggerFactory;
import se.wegelius.speedvoter.voteservice.model.hibernateobjects.VotesHO;

/**
 *
 * @author asawe
 */
public class VotesDao  extends GenericDao<VotesHO, Integer> {
        
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(VotesDao.class);

    public VotesDao(Class<VotesHO> type) {
        super(type);
    }

    /**
     *
     */
    public VotesDao() {
        super(VotesHO.class);
    }
}