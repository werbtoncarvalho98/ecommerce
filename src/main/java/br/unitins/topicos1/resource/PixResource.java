package br.unitins.topicos1.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.topicos1.application.Result;
import br.unitins.topicos1.dto.PixDTO;
import br.unitins.topicos1.dto.PixResponseDTO;
import br.unitins.topicos1.service.PixService;
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

@Path("/pixs")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PixResource {

    @Inject
    PixService pixService;

    private static final Logger LOG = Logger.getLogger(PixResource.class);

    @Inject
    Validator validator;

    @GET
    public List<PixResponseDTO> getAll() {
        return pixService.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Admin", "User" })
    public PixResponseDTO findById(@PathParam("id") Long id) {
        return pixService.findById(id);
    }

    @POST
    @RolesAllowed({ "Admin" })
    public Response insert(PixDTO dto) {
        // LOG.infof("Inserindo um pix: %s", dto.totalPix());
        Result result = null;

        try {
            PixResponseDTO pix = pixService.create(dto);
            LOG.infof("Pix (%d) criado com sucesso.", pix.chave());

            return Response.status(Status.CREATED).entity(pix).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um pix.");
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
    public Response update(@PathParam("id") Long id, PixDTO dto) {
        try {
            PixResponseDTO pix = pixService.update(id, dto);
            return Response.ok(pix).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public Response delete(@PathParam("id") Long id) {
        pixService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed({ "Admin" })
    public long count() {
        return pixService.count();
    }

    @GET
    @Path("/search/{chave}")
    @RolesAllowed({ "Admin" })
    public List<PixResponseDTO> search(@PathParam("chave") String chave) {
        return pixService.findByChave(chave);
    }
}