/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.accessservice.dao;

import org.slf4j.LoggerFactory;
import se.wegelius.accessservice.model.ho.PermissionHO;

/**
 *
 * @author asawe
 */
public class PermissionDao extends GenericDao<PermissionHO, Integer> {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PermissionDao.class);

    public PermissionDao(Class<PermissionHO> type) {
        super(type);
    }

    /**
     *
     */
    public PermissionDao() {
        super(PermissionHO.class);
    }
}
