package com.mongo.resources;

import com.auth.service.token.JWTSignerService;
import com.codahale.metrics.annotation.Timed;
import com.mongo.constants.UserState;
import static com.mongo.constants.UserState.ACTIVATED;

import com.mongo.dao.UserDAO;

import com.mongo.models.UserModel;
import com.mongo.utils.CommonUtils;
import java.util.Optional;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class UserResource {

    private final UserDAO userDao;

    public UserResource(UserDAO userDao) {
        this.userDao = userDao;
    }

    @Path("/register")
    @POST
    @Timed
    public Response saveUser(@Valid UserModel data) {
        boolean check = userDao.checkEmail(data.getEmail());
        boolean checkUsername = userDao.checkUsername(data.getUsername());
        UUID userId = UUID.randomUUID();
        if (!check && !checkUsername) {          
            data.setUserId(userId.toString());
            UUID profileLink = UUID.randomUUID();
            data.setProfileLink(profileLink.toString());
            data.setStatus(ACTIVATED);
            data.setRole(UserState.ROLE_SIMPLE_USER);
            data.setPassword(CommonUtils.hashPassword(data.getPassword()));
            userDao.save(data);
        } else {
            return Response.serverError().entity("\"notok\"").type(MediaType.APPLICATION_JSON).build();
        }
        data.setUserId(userId.toString());
        return Response.ok().entity(data).type(MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/login")
    public Response userAuth(@Valid UserModel data) {
        Optional<UserModel> model = userDao.login(data.getEmail(), data.getPassword());
        if (model.isPresent()) {
            System.out.println("user is present");                    
            String token = JWTSignerService.createJWTToken(model.get().getUserId(), model.get().getEmail(), model.get().getEmail(), System.currentTimeMillis());
            model.get().setToken(token);
            return Response.ok().entity(model.get()).type(MediaType.APPLICATION_JSON + ";charset=utf-8").build();            
        }else  if (!model.isPresent()) {
            return Response.serverError().entity("\"nouser\"").type(MediaType.APPLICATION_JSON).build();
        }else{
           return Response.serverError().entity("\"error\"").type(MediaType.APPLICATION_JSON).build();
        }        
    }

    @GET
    @Path("/find/{userId}")
    public Response findUser(@PathParam("userId") String userId) {
        UserModel model = userDao.findById(userId);
        if (model == null) {
            return Response.serverError().entity("\"failed\"").type(MediaType.APPLICATION_JSON).build();
        }
        return Response.ok(model).build();
    }

    @GET
    @Path("/find/email/{email}")
    public Response findUserByEmail(@PathParam("email") String email) {
        UserModel model = userDao.findByEmail(email);
        if (model == null) {
            return Response.serverError().entity("\"failed\"").type(MediaType.APPLICATION_JSON).build();
        }
        return Response.ok(model).build();
    }

    @GET
    @Path("/find/username/{username}")
    public Response findUserByUsername(@PathParam("username") String username) {
        UserModel model = userDao.findUserByUsername(username);
        if (model == null) {
            return Response.serverError().entity("\"failed\"").type(MediaType.APPLICATION_JSON).build();
        }
        return Response.ok(model).build();
    }

}
