package br.unitins.topicos1.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.topicos1.application.Result;
import br.unitins.topicos1.dto.BoletoDTO;
import br.unitins.topicos1.dto.BoletoResponseDTO;
import br.unitins.topicos1.service.BoletoService;
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

@Path("/boletos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BoletoResource {

    @Inject
    BoletoService boletoService;

    private static final Logger LOG = Logger.getLogger(BoletoResource.class);

    @Inject
    Validator validator;

    @GET
    @RolesAllowed({ "Admin", "User" })
    public List<BoletoResponseDTO> getAll() {
        return boletoService.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public BoletoResponseDTO findById(@PathParam("id") Long id) {
        return boletoService.findById(id);
    }

    @POST
    @RolesAllowed({ "Admin" })
    public Response insert(BoletoDTO dto) {
        // LOG.infof("Inserindo um boleto: %s", dto.totalBoleto());
        Result result = null;

        try {
            BoletoResponseDTO boleto = boletoService.create(dto);
            LOG.infof("Boleto (%d) criado com sucesso.", boleto.codigo());

            return Response.status(Status.CREATED).entity(boleto).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um boleto.");
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
    public Response update(@PathParam("id") Long id, BoletoDTO dto) {
        try {
            BoletoResponseDTO boleto = boletoService.update(id, dto);
            return Response.ok(boleto).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public Response delete(@PathParam("id") Long id) {
        boletoService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed({ "Admin" })
    public long count() {
        return boletoService.count();
    }

    @GET
    @Path("/search/{status}")
    @RolesAllowed({ "Admin" })
    public List<BoletoResponseDTO> search(@PathParam("status") String status) {
        return boletoService.findByCodigo(status);
    }
}