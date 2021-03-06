package resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.*;
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
@Path("/users")
@Api("User")
@Produces("application/json")
public class UserResource {
    private static final Logger log = LoggerFactory.getLogger(UserResource.class);

    @GET
    @Path("{_id}")
    @ApiOperation(value = "Get a user by his object_id", response = User.class)
    @ApiResponses( value = {
            @ApiResponse(code = 404, message = "User not found."),
            @ApiResponse(code = 500, message = "Can't retrieve user on id.")
    })
    public Response getUser(@ApiParam(value = "Object id") @PathParam("_id") String _id){
        try {
            User user = Database.get().getDatastore().find(User.class,"objectId",new ObjectId(_id))
                    .retrievedFields(false,"password").get();
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
    @ApiOperation(value = "Try to log in a user.", response = Token.class)
    @ApiResponses(value = {
        @ApiResponse(code = 404, message = "User not found !"),
        @ApiResponse(code = 500, message = "Can't retrieve user.")
    })
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
        return Response.ok(new HttpResponse(500,"Can't retrieve user. Internal error server.")).status(500).build();
    }


    @GET
    @Path("token/{token}")
    @ApiOperation(value = "Check the validity of a token.", response = Claims.class)
    @ApiResponses( value = {
            @ApiResponse(code = 400, message = "Token non valid or expired."),
            @ApiResponse(code = 500, message = "Can't verify the token.")
    })
    public Response verifyToken(@PathParam("token") String token){
        try {
            Claims body = Jwts.parser().setSigningKey(CONFIG.getString("token.secret")).parseClaimsJws(token).getBody();
            return Response.ok(MAPPER.writeValueAsString(body)).status(200).build();
        } catch (JsonProcessingException e) {
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());
            return Response.ok(new HttpResponse(400,"Token non valid or expired")).status(500).build();
        } catch (Exception e) {
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());
        }
        return Response.ok(new HttpResponse(500,"Can't verify token.")).status(500).build();
    }



    @POST
    @Path("requestPassword")
    @Consumes("application/json")
    @ApiOperation(value = "Create a forgotten password request.", response = ForgottenPasswordResponse.class)
    @ApiResponses(value={
            @ApiResponse(code = 404, message = "User not found with email."),
            @ApiResponse(code = 500, message = "Unable to create a forgotten request.")
    })
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
    @ApiOperation(value = "Check the validity of a forgotten password request.", response = HttpResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "The timestamp has expired. Recreate another one."),
            @ApiResponse(code = 404, message = "Request not found."),
            @ApiResponse(code = 500, message = "Cannot verify the request.")
    })
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
    @ApiOperation(value = "Update a password", response = HttpResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "The timestamp has expired."),
            @ApiResponse(code = 400, message = "Passwords seems to be different or/and are same of the old password."),
            @ApiResponse(code = 500, message = "Cannot update your password. Try again later.")

    })
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
