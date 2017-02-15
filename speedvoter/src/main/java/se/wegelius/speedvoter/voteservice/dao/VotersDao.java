/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.speedvoter.voteservice.dao;

import org.slf4j.LoggerFactory;
import se.wegelius.speedvoter.voteservice.model.hibernateobjects.VoterHO;

/**
 *
 * @author asawe
 */
public class VotersDao  extends GenericDao<VoterHO, Integer> {
        
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(VotersDao.class);

    public VotersDao(Class<VoterHO> type) {
        super(type);
    }

    /**
     *
     */
    public VotersDao() {
        super(VoterHO.class);
    }
}
