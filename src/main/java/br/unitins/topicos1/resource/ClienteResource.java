package br.unitins.topicos1.resource;

import java.util.List;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import br.unitins.topicos1.application.Result;
import br.unitins.topicos1.dto.ClienteDTO;
import br.unitins.topicos1.dto.ClienteResponseDTO;
import br.unitins.topicos1.service.ClienteService;

@Path("/clientes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    ClienteService clienteService;

    @GET
    public List<ClienteResponseDTO> getAll() {
        return clienteService.getAll();
    }

    @GET
    @Path("/{id}")
    public ClienteResponseDTO findById(@PathParam("id") Long id) {
        return clienteService.findById(id);
    }

    @POST
    public Response insert(ClienteDTO dto) {
        try {
            ClienteResponseDTO cliente = clienteService.create(dto);
            return Response.status(Status.CREATED).entity(cliente).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, ClienteDTO dto) {
        try {
            ClienteResponseDTO cliente = clienteService.update(id, dto);
            return Response.status(Status.NO_CONTENT).entity(cliente).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        clienteService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    public long count() {
        return clienteService.count();
    }

    @GET
    @Path("/search/{cpf}")
    public List<ClienteResponseDTO> search(@PathParam("cpf") String cpf) {
        return clienteService.findByCpf(cpf);
    }
}