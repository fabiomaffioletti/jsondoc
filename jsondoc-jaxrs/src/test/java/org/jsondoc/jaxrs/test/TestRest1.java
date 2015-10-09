package org.jsondoc.jaxrs.test;

import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.annotation.ApiResponseObject;

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

    @ApiMethod(description = "Find all pets")
    @Path("pets")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponseObject(clazz = Pet.class)
    public Response findAll() {
        return null;
    }

    @ApiMethod(description = "Find pet with given id")
    @Path("pets/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponseObject(clazz = Pet.class)
    public Response findOne(
            @ApiPathParam(name = "id", description = "The id of the pet") @PathParam("id") long id
    ) {
        return null;
    }

    @Path("pets/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public long create(Pet pet) {
        return 0l;
    }
}
