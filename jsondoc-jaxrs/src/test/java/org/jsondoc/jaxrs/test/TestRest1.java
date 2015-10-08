package org.jsondoc.jaxrs.test;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Arne Bosien
 */
@Path("pets/1.0")
public class TestRest1 {

    @Path("pets")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        return null;
    }

    @Path("pets/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findOne(@PathParam("id") long id) {
        return null;
    }

    @Path("pets/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public long create(Pet pet) {
        return 0l;
    }
}
