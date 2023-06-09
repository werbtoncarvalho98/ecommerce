package br.unitins.topicos1.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.topicos1.application.Result;
import br.unitins.topicos1.dto.CreditoDTO;
import br.unitins.topicos1.dto.CreditoResponseDTO;
import br.unitins.topicos1.service.CreditoService;
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

@Path("/creditos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CreditoResource {

    @Inject
    CreditoService creditoService;

    private static final Logger LOG = Logger.getLogger(CreditoResource.class);

    @Inject
    Validator validator;

    @GET
    @RolesAllowed({ "Admin", "User" })
    public List<CreditoResponseDTO> getAll() {
        return creditoService.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public CreditoResponseDTO findById(@PathParam("id") Long id) {
        return creditoService.findById(id);
    }

    @POST
    @RolesAllowed({ "Admin" })
    public Response insert(CreditoDTO dto) {
        // LOG.infof("Inserindo um credito: %s", dto.totalCredito());
        Result result = null;

        try {
            CreditoResponseDTO credito = creditoService.create(dto);
            LOG.infof("Credito (%d) criado com sucesso.", credito.titular());

            return Response.status(Status.CREATED).entity(credito).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um credito.");
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
    public Response update(@PathParam("id") Long id, CreditoDTO dto) {
        try {
            CreditoResponseDTO credito = creditoService.update(id, dto);
            return Response.ok(credito).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public Response delete(@PathParam("id") Long id) {
        creditoService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed({ "Admin" })
    public long count() {
        return creditoService.count();
    }

    @GET
    @Path("/search/{status}")
    @RolesAllowed({ "Admin" })
    public List<CreditoResponseDTO> search(@PathParam("status") String status) {
        return creditoService.findByTitular(status);
    }
}