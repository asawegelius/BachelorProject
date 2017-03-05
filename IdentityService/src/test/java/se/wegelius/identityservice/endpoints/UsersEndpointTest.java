/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.identityservice.endpoints;

import com.jayway.restassured.http.ContentType;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import se.wegelius.identityservice.model.Users;
import static com.jayway.restassured.RestAssured.given;

/**
 *
 * @author asawe
 */
public class UsersEndpointTest {

    public UsersEndpointTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        RestUtil.setBaseURI("http://localhost:8080/IdentityService/rest");
        RestUtil.setBasePath("/users"); //Setup Base Path
        RestUtil.setContentType(ContentType.JSON); //Setup Content Type
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getJson method, of class UsersEndpoint.
     */
    @Test
    public void testGetJson_0args() {
        given().when().then().statusCode(200).body(containsString("Wegelius"));
    }

    /**
     * Test of getJson method, of class UsersEndpoint.
     */
    @Test
    public void testGetJson_int() {
        given().
                pathParam("id", 2).
                when().
                get("/{id}").
                then().statusCode(200).
                body("usersId", equalTo(2)).
                //body("firstName", equalTo("Åsa")).
                body("lastName", equalTo("Wegelius")).
                body("email", equalTo("asa.wegelius@gmail.com"));
    }

    /**
     * Test of createJson method, of class UsersEndpoint.
     *
     * @Test public void testCreateJson() { given(). queryParam("first_name",
     * "Thomas"). queryParam("last_name", "Bisballe"). queryParam("email",
     * "thomas.bisballe@gmail.com"). queryParam("phone", 52525252).
     * when().post("/create").then(). statusCode(200); }
     */
    /**
     * Test of updateJson method, of class UsersEndpoint.
     *
     * @throws java.io.IOException
     */
    @Test
    public void testUpdateJson() throws IOException {
        Users[] allUsers = given().when().get().as(Users[].class);
        System.out.println("number of users in testUpdate " + allUsers.length);
        int id = -1;
        for (Users u : allUsers) {
            System.out.println("last name =  " + u.getLastName());
            if (u.getLastName().equals("Bisballe")) {
                id = u.getUsersId();
            }
        }

        System.out.println("id =  " + id);

        given()
                .queryParam("user_id", id)
                .queryParam("first_name", "Thomas")
                .queryParam("last_name", "Bisballe")
                .queryParam("email", "thomasbisballe@gmail.com")
                .queryParam("mobile", 23132312)
                .when().put("/update").then()
                .statusCode(200)
                .body("email", equalTo("thomasbisballe@gmail.com"));

    }

    /**
     * Test of delete method, of class UsersEndpoint.
     *
     * @throws java.io.IOException
     *
     * @Test public void testDelete() throws IOException { Users[] allUsers =
     * given().when().get().as(Users[].class);
     *
     * System.out.println("number of users in testDelete" + allUsers.length);
     * int id = -1; for (Users u : allUsers) { if
     * (u.getLastName().equals("Bisballe")) { id = u.getUsersId(); } }
     * given().pathParam("id", id) .when().delete("/delete/{id}")
     * .then().statusCode(200); }
     */
    /**
     * Test of validateEmail method, of class UsersEndpoint.
     */
    @Test
    public void testValidateEmail() {
        System.out.println("validateEmail");
        UsersEndpoint endpoint = new UsersEndpoint();
        String validEmail = "thomas.bisballe@gmail.com";
        String invalidEmail = "asa.wegeliusgmail.com";
        assertTrue(endpoint.validateEmail(validEmail));
        assertFalse(endpoint.validateEmail(invalidEmail));
    }

    /**
     * Test of validatePhone method, of class UsersEndpoint.
     */
    @Test
    public void testValidatePhone() {
        System.out.println("validatePhone");
        UsersEndpoint endpoint = new UsersEndpoint();
        String validPhone = "52525252";
        String invalidPhone = "5252";
        assertTrue(endpoint.validatePhone(validPhone));
        assertFalse(endpoint.validatePhone(invalidPhone));
    }

}
