/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.accessservice.dao;

import org.slf4j.LoggerFactory;
import se.wegelius.accessservice.model.ho.RoleHO;

/**
 *
 * @author asawe
 */
public class RoleDao  extends GenericDao<RoleHO, Integer> {
        
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RoleDao.class);

    public RoleDao(Class<RoleHO> type) {
        super(type);
    }

    /**
     *
     */
    public RoleDao() {
        super(RoleHO.class);
    }
}

