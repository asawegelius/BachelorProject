/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.identityservice.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import se.wegelius.identityservice.model.HO.UsersHO;

/**
 *
 * @author asawe
 */
public class UsersDaoTest {
    public UsersDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
        
        UsersDao dao = new UsersDao();
        Set<UsersHO> testUsers = dao.getAll();
        for (UsersHO b : testUsers) {
            if (b.getEmail().equals("anna.andersen@gamil.com")) {
                dao.delete(b);
            }
        }
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     *
     */
    @Test
    public void testAddMailList() {
        UsersDao dao = new UsersDao();
        // create a test branch...
        int sum = dao.count();
        UsersHO user = new UsersHO();
        user.setEmail("anna.andersen@gamil.com");
        user.setFirstName("Anna");
        user.setLastName("Andersen");
        dao.saveOrUpdate(user);
        int newSum = dao.count();
        System.out.println("newSum = " + newSum + " sum = " + sum);
        assertTrue(sum == (newSum - 1));
    }

    /**
     *
     */
    @Test
    public void testGetAll() {
        UsersDao dao = new UsersDao();
        int sum = dao.count();
        Set<UsersHO> testList = dao.getAll();
        assertTrue(testList.size() == sum);
    }

    /**
     *
     */
    @Test
    public void testFindById() {
        UsersDao dao = new UsersDao();
        // get a random randUser from all mails
        List<UsersHO> list = new ArrayList<>(dao.getAll());
        int size = list.size();
        int item = new Random().nextInt(size);
        UsersHO randUser = list.get(item);
        UsersHO testFind = dao.findByID(randUser.getUsersId());
        assertTrue(testFind.getEmail().equals(randUser.getEmail()));
        assertTrue(testFind.getFirstName().equals(randUser.getFirstName()));

    }

}
