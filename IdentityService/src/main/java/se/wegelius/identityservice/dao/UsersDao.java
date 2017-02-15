/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.identityservice.dao;

import org.slf4j.LoggerFactory;
import se.wegelius.identityservice.model.HO.UsersHO;

/**
 *
 * @author asawe
 */
public class UsersDao extends GenericDao<UsersHO, Integer> {
    
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UsersDao.class);

    public UsersDao(Class<UsersHO> type) {
        super(type);
    }

    /**
     *
     */
    public UsersDao() {
        super(UsersHO.class);
    }
}
