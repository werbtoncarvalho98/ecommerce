package org.acme.resource;

import java.util.List;

import org.acme.application.Result;
import org.acme.dto.PedidoDTO;
import org.acme.dto.PedidoResponseDTO;
import org.acme.dto.UsuarioResponseDTO;
import org.acme.service.PedidoService;
import org.acme.service.UsuarioService;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import jakarta.annotation.security.RolesAllowed;
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

@Path("/pedidos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PedidoResource {

    @Inject
    PedidoService pedidoService;

    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioService usuarioService;

    private static final Logger LOG = Logger.getLogger(PedidoResource.class);

    @GET
    @RolesAllowed({ "Admin", "User" })
    public Response getUsuario() {
        String login = jwt.getSubject();
        UsuarioResponseDTO usuario = usuarioService.findByLogin(login);

        return Response.ok(usuario).build();
    }

    @GET
    @RolesAllowed({ "Admin" })
    public List<PedidoResponseDTO> getAll() {
        LOG.info("Buscando todas as pedidos.");
        LOG.debug("ERRO DE DEBUG.");
        return pedidoService.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public PedidoResponseDTO findById(@PathParam("id") Long id) {
        return pedidoService.findById(id);
    }

    @POST
    @RolesAllowed({ "Admin" })
    public Response insert(PedidoDTO pedidoDTO) {
        LOG.infof("Inserindo uma pedido: %s", pedidoDTO.getClass());
        Result result = null;
        try {
            PedidoResponseDTO pedido = pedidoService.create(pedidoDTO);
            LOG.infof("Pedido (%d) criada com sucesso.", pedido.id());
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
    public Response update(@PathParam("id") Long id, PedidoDTO pedidoDTO) {
        LOG.infof("Atualizando uma pedido: %s", pedidoDTO.getClass());
        Result result = null;
        try {
            PedidoResponseDTO pedido = pedidoService.update(id, pedidoDTO);
            LOG.infof("Pedido (%d) atualizada com sucesso.", pedido.id());
            return Response.status(Status.NO_CONTENT).entity(pedido).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao atualizar uma pedido.");
            LOG.debug(e.getMessage());
            result = new Result(e.getConstraintViolations());
        } catch (Exception e) {
            LOG.fatal("Erro sem identificacao: " + e.getMessage());
            result = new Result(e.getMessage(), false);
        }
        return Response.status(Status.NOT_FOUND).entity(result).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public Response delete(@PathParam("id") Long id) {
        pedidoService.delete(id);
        return Response
                .status(Status.NO_CONTENT)
                .build();

    }

    @GET
    @Path("/search/{id}")
    @RolesAllowed({ "Admin" })
    public PedidoResponseDTO searchId(@PathParam("id") Long id) {
        return pedidoService.findById(id);
    }

    @GET
    @Path("/search/{nome}")
    @RolesAllowed({ "Admin" })
    public List<PedidoResponseDTO> search(@PathParam("nome") String nome) {
        return pedidoService.findByNome(nome);
    }

    @GET
    @Path("/count")
    @RolesAllowed({ "Admin" })
    public long count() {
        return pedidoService.count();
    }
}