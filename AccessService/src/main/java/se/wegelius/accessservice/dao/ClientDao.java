/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.accessservice.dao;

import org.slf4j.LoggerFactory;
import se.wegelius.accessservice.model.ho.ClientHO;

/**
 *
 * @author asawe
 */
public class ClientDao  extends GenericDao<ClientHO, Integer> {
        
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ClientDao.class);

    public ClientDao(Class<ClientHO> type) {
        super(type);
    }

    /**
     *
     */
    public ClientDao() {
        super(ClientHO.class);
    }
}
