/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.speedvoter.voteservice;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;



/**
 *
 * @author asawe
 */
@ApplicationPath("/rest")
public class MyApplication extends ResourceConfig {

    public MyApplication() {
        packages(this.getClass().getPackage().getName());
    }

}
