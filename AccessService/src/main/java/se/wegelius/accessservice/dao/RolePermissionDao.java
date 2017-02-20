/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.accessservice.dao;

import org.slf4j.LoggerFactory;
import se.wegelius.accessservice.model.ho.RolePermissionHO;

/**
 *
 * @author asawe
 */
public class RolePermissionDao  extends GenericDao<RolePermissionHO, Integer> {
        
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RolePermissionDao.class);

    public RolePermissionDao(Class<RolePermissionHO> type) {
        super(type);
    }

    /**
     *
     */
    public RolePermissionDao() {
        super(RolePermissionHO.class);
    }
}

