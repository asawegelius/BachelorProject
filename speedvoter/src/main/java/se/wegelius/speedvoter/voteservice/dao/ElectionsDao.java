/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.speedvoter.voteservice.dao;

import org.slf4j.LoggerFactory;
import se.wegelius.speedvoter.voteservice.model.hibernateobjects.ElectionsHO;

/**
 *
 * @author asawe
 */
public class ElectionsDao  extends GenericDao<ElectionsHO, Integer> {
        
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ElectionsDao.class);

    public ElectionsDao(Class<ElectionsHO> type) {
        super(type);
    }

    /**
     *
     */
    public ElectionsDao() {
        super(ElectionsHO.class);
    }
}
