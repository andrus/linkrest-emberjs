package org.objectstyle.linkrest.emberjs.example.api;

import com.nhl.link.rest.DataResponse;
import com.nhl.link.rest.LinkRest;
import com.nhl.link.rest.SimpleResponse;
import org.objectstyle.linkrest.emberjs.cayenne.Article;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("article")
@Produces(MediaType.APPLICATION_JSON)
public class ArticleApi {

    @Context
    private Configuration config;

    @GET
    public DataResponse<Article> getAll(@Context UriInfo uriInfo) {
        return LinkRest.select(Article.class, config).uri(uriInfo).select();
    }

    @GET
    @Path("{articleId}")
    public DataResponse<Article> getOne(@PathParam("articleId") int id, @Context UriInfo uriInfo) {
        return LinkRest.select(Article.class, config).byId(id).uri(uriInfo).select();
    }

    @POST
    public SimpleResponse create(String data) {
        return LinkRest.create(Article.class, config).sync(data);
    }

    @PUT
    public SimpleResponse createOrUpdate(String data) {
        return LinkRest.createOrUpdate(Article.class, config).sync(data);
    }


}
