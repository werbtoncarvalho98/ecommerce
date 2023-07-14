package org.acme.resource;

import java.util.List;

import org.acme.application.Result;
import org.acme.dto.TelefoneDTO;
import org.acme.dto.TelefoneResponseDTO;
import org.acme.service.TelefoneService;
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

@Path("/telefones")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TelefoneResource {

    @Inject
    TelefoneService telefoneService;

    private static final Logger LOG = Logger.getLogger(TelefoneResource.class);

    @GET
    @RolesAllowed({ "Admin", "User" })
    public List<TelefoneResponseDTO> getAll() {
        return telefoneService.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Admin", "User" })
    public TelefoneResponseDTO findById(@PathParam("id") Long id) {
        return telefoneService.findById(id);
    }

    @POST
    @RolesAllowed({ "Admin" })
    public Response insert(TelefoneDTO dto) {
        LOG.infof("Inserindo um telefone: %s", dto.numero());
        Result result = null;

        try {
            TelefoneResponseDTO telefone = telefoneService.create(dto);
            LOG.infof("Telefone (%d) criado com sucesso.", telefone.numero());

            return Response.status(Status.CREATED).entity(telefone).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um telefone.");
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
    public Response update(@PathParam("id") Long id, TelefoneDTO dto) {
        try {
            TelefoneResponseDTO telefone = telefoneService.update(id, dto);
            return Response.status(Status.NO_CONTENT).entity(telefone).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public Response delete(@PathParam("id") Long id) {
        telefoneService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed({ "Admin", "User" })
    public long count() {
        return telefoneService.count();
    }

    @GET
    @Path("/search/{numero}")
    @RolesAllowed({ "Admin", "User" })
    public List<TelefoneResponseDTO> search(@PathParam("numero") String numero) {
        return telefoneService.findByNumero(numero);
    }
}