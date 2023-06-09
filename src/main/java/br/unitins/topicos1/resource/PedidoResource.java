package br.unitins.topicos1.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.topicos1.application.Result;
import br.unitins.topicos1.dto.PedidoDTO;
import br.unitins.topicos1.dto.PedidoResponseDTO;
import br.unitins.topicos1.service.PedidoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
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

@Path("/pedidos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PedidoResource {

    @Inject
    PedidoService pedidoService;

    private static final Logger LOG = Logger.getLogger(PedidoResource.class);

    @Inject
    Validator validator;

    @GET
    public List<PedidoResponseDTO> getAll() {
        return pedidoService.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Admin", "User" })
    public PedidoResponseDTO findById(@PathParam("id") Long id) {
        return pedidoService.findById(id);
    }

    @POST
    @RolesAllowed({ "Admin" })
    public Response insert(PedidoDTO dto) {
        //LOG.infof("Inserindo um pedido: %s", dto.totalPedido());
        Result result = null;

        try {
            PedidoResponseDTO pedido = pedidoService.create(dto);
            LOG.infof("Pedido (%d) criada com sucesso.", pedido.data());

            return Response.status(Status.CREATED).entity(pedido).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir uma pedido.");
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
    @RolesAllowed({ "Admin" })
    public Response update(@PathParam("id") Long id, PedidoDTO dto) {
        try {
            PedidoResponseDTO pedido = pedidoService.update(id, dto);
            return Response.ok(pedido).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public Response delete(@PathParam("id") Long id) {
        pedidoService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed({ "Admin" })
    public long count() {
        return pedidoService.count();
    }

    @GET
    @Path("/search/{status}")
    @RolesAllowed({ "Admin" })
    public List<PedidoResponseDTO> search(@PathParam("status") String status) {
        return pedidoService.findByStatus(status);
    }
}