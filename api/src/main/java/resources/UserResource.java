package resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.*;
import models.Token;
import models.User;
import mongo.Database;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import requests.UserLoginRequest;
import utils.HttpError;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Date;

import static conf.Configuration.CONFIG;

/**
 * Created by thomas on 19/05/16.
 */
@Path("/users")
@Api("User")
@Produces("application/json")
public class UserResource {
    private static final Logger log = LoggerFactory.getLogger(UserResource.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

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
            if(user== null) return Response.ok(new HttpError(404,"User not found !")).status(404).build();
            return Response.ok(MAPPER.writeValueAsString(user)).status(200).build();
        }catch (Exception e){
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());
        }
        return Response.ok(new HttpError(500,"Can't retrieve user on id " + _id + ". Internal error server.")).status(500).build();
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
                    .filter("login =",userLoginRequest.login)
                    .filter("password =",User.encrypt(userLoginRequest.password)).get();

            if(user== null) return Response.ok(new HttpError(404,"User not found !")).status(404).build();

            String token = Jwts.builder()
                    .setSubject(MAPPER.writeValueAsString(user))
                    .setExpiration(new Date(new Date().getTime() + CONFIG.getLong("token.timeout")))
                    .signWith(SignatureAlgorithm.forName(CONFIG.getString("token.algorithm")), CONFIG.getString("token.secret"))
                    .compact();
            return Response.ok(MAPPER.writeValueAsString(new Token(token))).status(200).build();
        }catch (Exception e){
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());
        }
        return Response.ok(new HttpError(500,"Can't retrieve user. Internal error server.")).status(500).build();
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
            return Response.ok(new HttpError(400,"Token non valid or expired")).status(500).build();
        } catch (Exception e) {
            if(log.isDebugEnabled())e.printStackTrace();
            log.error(e.getMessage());
        }
        return Response.ok(new HttpError(500,"Can't verify token.")).status(500).build();
    }


}
