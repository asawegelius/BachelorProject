/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.identityservice.dao;

import org.slf4j.LoggerFactory;
import se.wegelius.identityservice.model.HO.LoginsHO;

/**
 *
 * @author asawe
 */
public class LoginsDao extends GenericDao<LoginsHO, Integer> {
    
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LoginsDao.class);

    public LoginsDao(Class<LoginsHO> type) {
        super(type);
    }

    /**
     *
     */
    public LoginsDao() {
        super(LoginsHO.class);
    }
}
