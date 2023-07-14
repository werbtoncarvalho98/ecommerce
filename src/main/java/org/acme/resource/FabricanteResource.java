package org.acme.resource;

import java.util.List;

import org.acme.application.Result;
import org.acme.dto.FabricanteDTO;
import org.acme.dto.FabricanteResponseDTO;
import org.acme.service.FabricanteService;
import org.jboss.logging.Logger;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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

@Path("/fabricantes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FabricanteResource {

    @Inject
    FabricanteService fabricanteService;

    private static final Logger LOG = Logger.getLogger(FabricanteResource.class);

    @GET
    @RolesAllowed({ "Admin", "User" })
    public List<FabricanteResponseDTO> getAll() {
        return fabricanteService.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Admin", "User" })
    public FabricanteResponseDTO findById(@PathParam("id") Long id) {
        return fabricanteService.findById(id);
    }

    @POST
    @RolesAllowed({ "Admin" })
    public Response insert(FabricanteDTO dto) {
        LOG.infof("Inserindo um fabricante: %s", dto.nome());
        Result result = null;

        try {
            FabricanteResponseDTO fabricante = fabricanteService.create(dto);
            LOG.infof("Fabricante (%d) criado com sucesso.", fabricante.nome());

            return Response.status(Status.CREATED).entity(fabricante).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um fabricante.");
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
    public Response update(@PathParam("id") Long id, FabricanteDTO dto) {
        try {
            FabricanteResponseDTO fabricante = fabricanteService.update(id, dto);
            return Response.status(Status.NO_CONTENT).entity(fabricante).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public Response delete(@PathParam("id") Long id) {
        fabricanteService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed({ "Admin", "User" })
    public long count() {
        return fabricanteService.count();
    }

    @GET
    @Path("/search/{nome}")
    @RolesAllowed({ "Admin", "User" })
    public List<FabricanteResponseDTO> search(@PathParam("nome") String nome) {
        return fabricanteService.findByNome(nome);
    }
}