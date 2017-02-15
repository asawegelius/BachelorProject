/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.speedvoter.voteservice.dao;

import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import se.wegelius.speedvoter.voteservice.model.hibernateobjects.OrgsHO;

/**
 *
 * @author asawe
 */
public class OrgsDaoTest {
    private static OrgsDao dao;
    
    public OrgsDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {       
        dao = new OrgsDao();
    }
    
    @AfterClass
    public static void tearDownClass() {
        Set<OrgsHO> list = dao.getAll();
        for(OrgsHO ho : list){
            dao.delete(ho);
        }
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void createTest() {
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    
    @Test
    public void findTest() {
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    
    @Test
    public void deleteTest() {
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
