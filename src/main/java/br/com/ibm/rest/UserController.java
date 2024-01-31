package br.com.ibm.rest;

import br.com.ibm.exception.NotFoundException;
import br.com.ibm.persistence.dto.AddUserDto;
import br.com.ibm.persistence.dto.LoginDto;
import br.com.ibm.persistence.dto.PutUserDto;
import br.com.ibm.persistence.model.User;
import br.com.ibm.services.UserService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;
import java.util.Optional;

@Path("/v1/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {
    @Inject
    UserService service;

    @GET
    public Response listUsers() {
        List<User> user = this.service.getUsers();
        return Response.status(Response.Status.OK).entity(user).build();
    }

    @POST
    public Response addUser(@Valid AddUserDto userData) {
        this.service.addUser(userData);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/login")
    public Response loginUser(@Valid LoginDto loginDto) {
        User authUser = this.service.loginUser(loginDto).orElseThrow(() ->
                new NotFoundException("Usuário não encontrado ou senha incorreta"));
        return Response.status(Response.Status.OK).entity(authUser).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id) {
        this.service.deleteUser(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateUser(@Valid @PathParam("id") Long id, PutUserDto userData) {
        this.service.updatedUser(id, userData);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/{id}/user")
    public Response getUserInfoById(@PathParam("id") Long id) {
        Optional<User> userOptional = this.service.getUserInfoById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return Response.status(Response.Status.OK).entity(user).build();
        } else {
            throw new NotFoundException("Informações do usuário não encontradas para o ID: " + id);
        }
    }

}
