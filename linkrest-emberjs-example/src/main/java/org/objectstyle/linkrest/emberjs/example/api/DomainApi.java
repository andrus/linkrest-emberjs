package org.objectstyle.linkrest.emberjs.example.api;

import com.nhl.link.rest.DataResponse;
import com.nhl.link.rest.LinkRest;
import com.nhl.link.rest.SimpleResponse;
import org.objectstyle.linkrest.emberjs.cayenne.Domain;

import javax.ws.rs.*;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("domain")
@Produces(MediaType.APPLICATION_JSON)
public class DomainApi {

    @Context
    private Configuration config;

    @GET
    public DataResponse<Domain> getAll(@Context UriInfo uriInfo) {
        return LinkRest.select(Domain.class, config).uri(uriInfo).select();
    }

    @GET
    @Path("{domainId}")
    public DataResponse<Domain> getOne(@PathParam("domainId") int id, @Context UriInfo uriInfo) {
        return LinkRest.select(Domain.class, config).byId(id).uri(uriInfo).selectOne();
    }

    @POST
    public SimpleResponse create(String data) {

        // 'data' is a single object or an array of objects..

        return LinkRest.create(Domain.class, config).sync(data);
    }

    @PUT
    public SimpleResponse createOrUpdate(String data) {

        // 'data' is a single object or an array of objects... Objects without
        // IDs will be treated as "new". LinkRest will try to locate objects
        // with IDs, and update them if found, or create if not

        return LinkRest.createOrUpdate(Domain.class, config).sync(data);
    }
}
