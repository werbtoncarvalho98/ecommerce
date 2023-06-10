package br.unitins.topicos1.resource;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import br.unitins.topicos1.application.Result;
import br.unitins.topicos1.dto.ItemPedidoDTO;
import br.unitins.topicos1.dto.ItemPedidoResponseDTO;
import br.unitins.topicos1.dto.UsuarioResponseDTO;
import br.unitins.topicos1.service.ItemPedidoService;
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

@Path("/itemcompras")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ItemPedidoResource {

    @Inject
    ItemPedidoService itemPedidoService;

    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioService usuarioService;

    private static final Logger LOG = Logger.getLogger(ItemPedidoResource.class);

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
    public List<ItemPedidoResponseDTO> getAll() {
        LOG.info("Buscando todas as compras.");
        LOG.debug("ERRO DE DEBUG.");
        return itemPedidoService.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public ItemPedidoResponseDTO findById(@PathParam("id") Long id) {
        return itemPedidoService.findById(id);
    }

    @POST
    @RolesAllowed({ "Admin" })
    public Response insert(ItemPedidoDTO itemPedidoDTO) {
        LOG.infof("Inserindo um item compra: %s", itemPedidoDTO.getClass());
        Result result = null;
        try {
            ItemPedidoResponseDTO itemPedido = itemPedidoService.create(itemPedidoDTO);
            LOG.infof("Brinquedo (%d) criado com sucesso.", itemPedido.id());
            return Response.status(Status.CREATED).entity(itemPedido).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um item compra.");
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
    public Response update(@PathParam("id") Long id, ItemPedidoDTO itemPedidoDTO) {
        LOG.infof("Atualizando um item compra: %s", itemPedidoDTO.getClass());
        Result result = null;
        try {
            ItemPedidoResponseDTO itemPedido = itemPedidoService.update(id, itemPedidoDTO);
            LOG.infof("Brinquedo (%d) atualizado com sucesso.", itemPedido.id());
            return Response.status(Status.NO_CONTENT).entity(itemPedido).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao atualizar um brinquedo.");
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
        itemPedidoService.delete(id);
        return Response
                .status(Status.NO_CONTENT)
                .build();

    }

    @GET
    @Path("/search/{id}")
    @RolesAllowed({ "Admin" })
    public ItemPedidoResponseDTO searchId(@PathParam("id") Long id) {
        return itemPedidoService.findById(id);
    }

    @GET
    @Path("/search/{nome}")
    @RolesAllowed({ "Admin" })
    public List<ItemPedidoResponseDTO> search(@PathParam("nome") String nome) {
        return itemPedidoService.findByNome(nome);
    }

    @GET
    @Path("/count")
    @RolesAllowed({ "Admin" })
    public long count() {
        return itemPedidoService.count();
    }
}
