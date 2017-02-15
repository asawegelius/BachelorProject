/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.speedvoter.voteservice.dao;

import org.slf4j.LoggerFactory;
import se.wegelius.speedvoter.voteservice.model.hibernateobjects.OptionsHO;

/**
 *
 * @author asawe
 */
public class OptionsDao  extends GenericDao<OptionsHO, Integer> {
        
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(OptionsDao.class);

    public OptionsDao(Class<OptionsHO> type) {
        super(type);
    }

    /**
     *
     */
    public OptionsDao() {
        super(OptionsHO.class);
    }
}
