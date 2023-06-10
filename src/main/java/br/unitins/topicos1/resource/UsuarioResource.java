package br.unitins.topicos1.resource;

import java.util.List;

import br.unitins.topicos1.application.Result;
import br.unitins.topicos1.dto.UsuarioDTO;
import br.unitins.topicos1.dto.UsuarioResponseDTO;
import br.unitins.topicos1.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/clientes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService clienteService;

    @GET
    @RolesAllowed({ "Admin", "User" })
    public List<UsuarioResponseDTO> getAll() {
        return clienteService.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Admin", "User" })
    public UsuarioResponseDTO findById(@PathParam("id") Long id) {
        return clienteService.findById(id);
    }

    @POST
    @Transactional
    @RolesAllowed({ "Admin" })
    public Response insert(UsuarioDTO clienteDTO) {
        try {
            UsuarioResponseDTO cliente = clienteService.create(clienteDTO);
            return Response.status(Status.CREATED).entity(cliente).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(@PathParam("id") Long id, UsuarioDTO clienteDTO) {
        try {
            UsuarioResponseDTO cliente = clienteService.update(id, clienteDTO);
            return Response.ok(cliente).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public Response delete(@PathParam("id") Long id) {
        clienteService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed({ "Admin", "User" })
    public long count() {
        return clienteService.count();
    }

    @GET
    @Path("/search/{nome}")
    @RolesAllowed({ "Admin", "User" })
    public List<UsuarioResponseDTO> search(@PathParam("nome") String nome) {
        return clienteService.findByNome(nome);
    }
}