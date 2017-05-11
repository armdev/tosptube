package com.mongo.resources;

import com.codahale.metrics.annotation.Timed;
import com.mongo.dao.SearchDAO;
import com.mongo.models.SearchKeyModel;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/search")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class SearchKeyResource {

    private final SearchDAO searchDAO;

    public SearchKeyResource(SearchDAO searchDAO) {
        this.searchDAO = searchDAO;      
    }

    @Path("/create")
    @POST
    @Timed
    public Response saveSearchKey(@Valid SearchKeyModel data) {        
        searchDAO.save(data);
        return Response.ok().entity("\"Success\"").type(MediaType.APPLICATION_JSON + ";charset=utf-8").build();
    }

    @GET
    @Path("/top/searches")
    public Response getTopSearches() {
        List<SearchKeyModel> list = searchDAO.getTopSearches();       
        return Response.ok(list).type(MediaType.APPLICATION_JSON + ";charset=utf-8").build();
    }

}
