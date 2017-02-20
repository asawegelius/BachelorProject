
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author asawe
 */
public class MyApplication extends ResourceConfig {
    public MyApplication() {
        super(ResourceConfig.class);
        register(RolesAllowedDynamicFeature.class);
    }
}