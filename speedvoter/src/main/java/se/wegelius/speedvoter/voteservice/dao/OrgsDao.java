/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.speedvoter.voteservice.dao;

import org.slf4j.LoggerFactory;
import se.wegelius.speedvoter.voteservice.model.hibernateobjects.OrgsHO;

/**
 *
 * @author asawe
 */
public class OrgsDao extends GenericDao<OrgsHO, Integer> {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(OrgsDao.class);

    public OrgsDao(Class<OrgsHO> type) {
        super(type);
    }

    /**
     *
     */
    public OrgsDao() {
        super(OrgsHO.class);
    }
}
