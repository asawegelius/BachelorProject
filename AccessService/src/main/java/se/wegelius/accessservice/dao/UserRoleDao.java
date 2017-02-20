/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.accessservice.dao;

import org.slf4j.LoggerFactory;
import se.wegelius.accessservice.model.ho.UserRoleHO;

/**
 *
 * @author asawe
 */
public class UserRoleDao  extends GenericDao<UserRoleHO, Integer> {
        
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserRoleDao.class);

    public UserRoleDao(Class<UserRoleHO> type) {
        super(type);
    }

    /**
     *
     */
    public UserRoleDao() {
        super(UserRoleHO.class);
    }
}
