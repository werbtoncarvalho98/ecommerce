package br.unitins.topicos1.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.topicos1.application.Result;
import br.unitins.topicos1.dto.ClienteDTO;
import br.unitins.topicos1.dto.ClienteResponseDTO;
import br.unitins.topicos1.service.ClienteService;
import jakarta.inject.Inject;
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
public class ClienteResource {

    @Inject
    ClienteService clienteService;

    private static final Logger LOG = Logger.getLogger(ClienteResource.class);

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
        LOG.infof("Inserindo um cliente: %s", dto.nome());
        Result result = null;

        try {
            ClienteResponseDTO cliente = clienteService.create(dto);
            LOG.infof("Cliente (%d) criado com sucesso.", cliente.id());
            return Response.status(Status.CREATED).entity(cliente).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um cliente.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }

        return Response.status(Status.NOT_FOUND).entity(result).build();
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