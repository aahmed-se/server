package resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.*;
import models.ResetPassword;
import models.Token;
import models.User;
import mongo.Database;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import requests.UpdatePasswordRequest;
import requests.UserForgottenPasswordRequest;
import requests.UserLoginRequest;
import responses.ForgottenPasswordResponse;
import utils.HttpResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.util.Date;

import static conf.Configuration.CONFIG;
import static models.Model.MAPPER;

/**
 * Created by thomas on 19/05/16.
 */
@Path("/user")
@Produces("application/json")
public class UserResource {
    private static final Logger log = LoggerFactory.getLogger(UserResource.class);

    @GET
    @Path("{id}")
    public Response getUser(@PathParam("id") String _id){
        try {
            User user = Database.get().getDatastore().get(User.class,new ObjectId(_id));
            if(user== null) return Response.ok(new HttpResponse(404,"User not found !")).status(404).build();
            return Response.ok(MAPPER.writeValueAsString(user)).status(200).build();
        }catch (Exception e){
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());
        }
        return Response.ok(new HttpResponse(500,"Can't retrieve sent on id " + _id + ". Internal error server.")).status(500).build();
    }

    @POST
    @Path("login")
    @Consumes("application/json")
    public Response login(UserLoginRequest userLoginRequest){
        try {
            User user = Database.get().getDatastore().find(User.class)
                    .field("login").equalIgnoreCase(userLoginRequest.login)
                    .filter("password =",User.encrypt(userLoginRequest.password))
                    .retrievedFields(false,"password","email").get();

            if(user== null) return Response.ok(new HttpResponse(404,"User not found !")).status(404).build();

            String token = Jwts.builder()
                    .setSubject(MAPPER.writeValueAsString(user))
                    .setExpiration(new Date(new Date().getTime() + CONFIG.getLong("token.timeout")*1000))
                    .signWith(SignatureAlgorithm.forName(CONFIG.getString("token.algorithm")), CONFIG.getString("token.secret"))
                    .compact();
            return Response.ok(MAPPER.writeValueAsString(new Token(token))).status(200).build();
        }catch (Exception e){
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());
        }
        return Response.ok(new HttpResponse(500,"Can't retrieve sent on id . Internal error server.")).status(500).build();
    }


    @GET
    @Path("token/{token}")
    public Response verifyToken(@PathParam("token") String token){
        try {

            Claims body = Jwts.parser().setSigningKey(CONFIG.getString("token.secret")).parseClaimsJws(token).getBody();

            return Response.ok(body.getSubject()).status(200).build();
        } catch (JwtException e ){
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());
            return Response.ok(new HttpResponse(400,"Bad token.")).status(400).build();
        }catch (Exception e) {
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());
        }
        return Response.ok(new HttpResponse(500,"Token non valid")).status(500).build();
    }



    @POST
    @Path("requestPassword")
    @Consumes("application/json")
    public Response createResetRequest(UserForgottenPasswordRequest forgottenPasswordRequest){
        try {
            User user = Database.get().getDatastore().find(User.class).field("email").equal(forgottenPasswordRequest.email).get();

            if(user == null) return Response.ok(new HttpResponse(404,"User not found with email " + forgottenPasswordRequest.email)).status(404).build();

            ResetPassword resetPassword = new ResetPassword(user);
            resetPassword.save();

            return Response.ok(MAPPER.writeValueAsString(new ForgottenPasswordResponse(true,"An email has been sent."))).status(200).build();

        }catch (Exception e){
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());
        }
        return Response.ok(new HttpResponse(500,"Unable to create a forgotten request. Try again later.")).status(500).build();
    }


    @GET
    @Path("requestPassword/{key}")
    public Response validResetRequest(@PathParam("key") String key){
        try {
            Query<ResetPassword> passwordQuery = Database.get().getDatastore().find(ResetPassword.class).filter("key =",key);
            ResetPassword resetPassword = passwordQuery.get();
            if(resetPassword == null) return Response.ok(new HttpResponse(404, "Request not found")).status(404).build();
            if(!resetPassword.isValid()) {
                Database.get().getDatastore().findAndDelete(passwordQuery);
                return Response.ok(new HttpResponse(400, "The timestamp has expired. Recreate another one.")).status(400).build();
            }
            return Response.ok(new HttpResponse(200,"The request is still valid.")).status(200).build();
        }catch (Exception e){
            if(log.isDebugEnabled()) e.printStackTrace();
            log.error(e.getMessage());
        }
        return Response.ok(new HttpResponse(500,"Cannot verify the request. Try again later.")).status(500).build();
    }


    @POST
    @Path("updatePassword")
    @Consumes("application/json")
    public Response updatePassword(UpdatePasswordRequest updatePasswordRequest){
        try {
            //search the request
            Query<ResetPassword> passwordQuery = Database.get().getDatastore().find(ResetPassword.class).filter("key =",updatePasswordRequest.key);
            ResetPassword resetPassword = passwordQuery.get();

            if(resetPassword == null) return Response.ok(new HttpResponse(404, "Request not found")).status(404).build();
            //check is timestamp not expired
            if(!resetPassword.isValid()) {
                Database.get().getDatastore().findAndDelete(passwordQuery);
                return Response.ok(new HttpResponse(400, "The timestamp has expired. Recreate another one.")).status(400).build();
            }

            User user = resetPassword.getUser();
            //check if password and confirm are same and if they are different from the old password
            if(updatePasswordRequest.password.equals(updatePasswordRequest.passwordConfirm) && User.encrypt(updatePasswordRequest.password) != user.getPassword())
                user.setPassword(updatePasswordRequest.password).save();
            else
                return Response.ok(new HttpResponse(400,"Passwords seems to be different or/and are same of the old password.")).status(400).build();

            //if password updated, we delete the request then return all is ok !
            Database.get().getDatastore().findAndDelete(passwordQuery);
            return Response.ok(new HttpResponse(200,"The password has been modified correctly")).status(200).build();
        }catch (Exception e){
            if(log.isDebugEnabled()) e.printStackTrace();
            log.error(e.getMessage());
        }
        return Response.ok(new HttpResponse(500,"Cannot update your password. Try again later.")).status(500).build();
    }
}
