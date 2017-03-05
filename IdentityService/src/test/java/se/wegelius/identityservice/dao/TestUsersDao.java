/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.identityservice.dao;

import java.sql.Connection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import se.wegelius.identityservice.model.HO.UsersHO;

/**
 *
 * @author asawe
 */
public class TestUsersDao {

    @Test
    public void testIt() throws Exception {

        UsersDao dao = new UsersDao();

        UsersHO user1 = new UsersHO();
        user1.setEmail("anna.andersen@gamil.com");
        user1.setFirstName("Anna");
        user1.setLastName("Andersen");

        UsersHO user2 = new UsersHO();
        user2.setEmail("bert.andersen@gamil.com");
        user2.setFirstName("Bert");
        user2.setLastName("Andersen");

        dao.save(user1);
        dao.save(user2);
        /*
        Map<String, Object> u1 = new HashMap<String, Object>();
        u1.put("first_name", "Anna");
        Map<String, Object> u2 = new HashMap<String, Object>();
        u2.put("first_name", "Bert");

        String hql = "from Users u where u.first_name = ?";

        HashSet<UsersHO> h1set = (HashSet<UsersHO>) dao.query(hql, u1);
        HashSet<UsersHO> h2set = (HashSet<UsersHO>) dao.query(hql, u2);

        UsersHO h1 = h1set.iterator().next();
        UsersHO h2 = h2set.iterator().next();
*/
        assertEquals("Anna", dao.findByID(user1.getUsersId()).getFirstName());
        assertEquals("Bert", dao.findByID(user2.getUsersId()).getFirstName());

        assertEquals("Anna", dao.findByID(user1.getUsersId()).getFirstName());
        assertEquals("Bert", dao.findByID(user2.getUsersId()).getFirstName());
/*
        user1.setFirstName("Anders");
        user2.setFirstName("Berit");

        dao.update(user1);
        dao.update(user2);

        assertEquals("Anders", dao.findByID(user1.getUsersId()).getFirstName());
        assertEquals("Berit", dao.findByID(user2.getUsersId()).getFirstName());
*/
        dao.delete(user1);
        dao.delete(user2);

        assertNull(dao.findByID(user1.getUsersId()));
        assertNull(dao.findByID(user2.getUsersId()));

    }

}
