/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.accessservice.dao;

import org.slf4j.LoggerFactory;
import se.wegelius.accessservice.model.ho.UserHO;

/**
 *
 * @author asawe
 */
public class UserDao  extends GenericDao<UserHO, Integer> {
        
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserDao.class);

    public UserDao(Class<UserHO> type) {
        super(type);
    }

    /**
     *
     */
    public UserDao() {
        super(UserHO.class);
    }
}

