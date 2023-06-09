package br.unitins.topicos1.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.topicos1.application.Result;
import br.unitins.topicos1.dto.DebitoDTO;
import br.unitins.topicos1.dto.DebitoResponseDTO;
import br.unitins.topicos1.service.DebitoService;
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

@Path("/debitos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DebitoResource {

    @Inject
    DebitoService debitoService;

    private static final Logger LOG = Logger.getLogger(DebitoResource.class);

    @Inject
    Validator validator;

    @GET
    @RolesAllowed({ "Admin", "User" })
    public List<DebitoResponseDTO> getAll() {
        return debitoService.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public DebitoResponseDTO findById(@PathParam("id") Long id) {
        return debitoService.findById(id);
    }

    @POST
    @RolesAllowed({ "Admin" })
    public Response insert(DebitoDTO dto) {
        // LOG.infof("Inserindo um debito: %s", dto.totalDebito());
        Result result = null;

        try {
            DebitoResponseDTO debito = debitoService.create(dto);
            LOG.infof("Debito (%d) criado com sucesso.", debito.titular());

            return Response.status(Status.CREATED).entity(debito).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um debito.");
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
    public Response update(@PathParam("id") Long id, DebitoDTO dto) {
        try {
            DebitoResponseDTO debito = debitoService.update(id, dto);
            return Response.ok(debito).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public Response delete(@PathParam("id") Long id) {
        debitoService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed({ "Admin" })
    public long count() {
        return debitoService.count();
    }

    @GET
    @Path("/search/{status}")
    @RolesAllowed({ "Admin" })
    public List<DebitoResponseDTO> search(@PathParam("status") String status) {
        return debitoService.findByTitular(status);
    }
}