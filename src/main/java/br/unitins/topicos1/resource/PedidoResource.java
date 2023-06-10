package br.unitins.topicos1.resource;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import br.unitins.topicos1.application.Result;
import br.unitins.topicos1.dto.PedidoDTO;
import br.unitins.topicos1.dto.PedidoResponseDTO;
import br.unitins.topicos1.dto.UsuarioResponseDTO;
import br.unitins.topicos1.service.PedidoService;
import br.unitins.topicos1.service.UsuarioService;
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

@Path("/compras")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PedidoResource {

    @Inject
    PedidoService compraService;

    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioService usuarioService;

    private static final Logger LOG = Logger.getLogger(PedidoResource.class);

    @GET
    @RolesAllowed({ "Admin", "User" })
    public Response getUsuario() {

        // obtendo o login a partir do token
        String login = jwt.getSubject();
        UsuarioResponseDTO usuario = usuarioService.findByLogin(login);

        return Response.ok(usuario).build();
    }

    @GET
    @RolesAllowed({ "Admin" })
    public List<PedidoResponseDTO> getAll() {
        LOG.info("Buscando todas as compras.");
        LOG.debug("ERRO DE DEBUG.");
        return compraService.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public PedidoResponseDTO findById(@PathParam("id") Long id) {
        return compraService.findById(id);
    }

    @POST
    @RolesAllowed({ "Admin" })
    public Response insert(PedidoDTO compradto) {
        LOG.infof("Inserindo uma compra: %s", compradto.getClass());
        Result result = null;
        try {
            PedidoResponseDTO compra = compraService.create(compradto);
            LOG.infof("Pedido (%d) criada com sucesso.", compra.id());
            return Response.status(Status.CREATED).entity(compra).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir uma compra.");
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
    public Response update(@PathParam("id") Long id, PedidoDTO compradto) {
        LOG.infof("Atualizando uma compra: %s", compradto.getClass());
        Result result = null;
        try {
            PedidoResponseDTO compra = compraService.update(id, compradto);
            LOG.infof("Pedido (%d) atualizada com sucesso.", compra.id());
            return Response.status(Status.NO_CONTENT).entity(compra).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao atualizar uma compra.");
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
        compraService.delete(id);
        return Response
                .status(Status.NO_CONTENT)
                .build();

    }

    @GET
    @Path("/search/{id}")
    @RolesAllowed({ "Admin" })
    public PedidoResponseDTO searchId(@PathParam("id") Long id) {
        return compraService.findById(id);
    }

    @GET
    @Path("/search/{nome}")
    @RolesAllowed({ "Admin" })
    public List<PedidoResponseDTO> search(@PathParam("nome") String nome) {
        return compraService.findByNome(nome);
    }

    @GET
    @Path("/count")
    @RolesAllowed({ "Admin" })
    public long count() {
        return compraService.count();
    }
}