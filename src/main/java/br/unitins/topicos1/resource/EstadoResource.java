package br.unitins.topicos1.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.topicos1.application.Result;
import br.unitins.topicos1.dto.EstadoDTO;
import br.unitins.topicos1.dto.EstadoResponseDTO;
import br.unitins.topicos1.service.EstadoService;
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

@Path("/estados")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EstadoResource {

    @Inject
    EstadoService estadoService;

    private static final Logger LOG = Logger.getLogger(ClienteResource.class);

    @GET
    @RolesAllowed({"Admin","User"})
    public List<EstadoResponseDTO> getAll() {
        return estadoService.getAll();
    }

    @GET
    @Path("/{id}")
    public EstadoResponseDTO findById(@PathParam("id") Long id) {
        return estadoService.findById(id);
    }

    @POST
    public Response insert(EstadoDTO dto) {
        LOG.infof("Inserindo um estado: %s", dto.nome());
        Result result = null;

        try {
            EstadoResponseDTO estado = estadoService.create(dto);
            LOG.infof("Estado (%d) criado com sucesso.", estado.id());

            return Response.status(Status.CREATED).entity(estado).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um estado.");
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
    public Response update(@PathParam("id") Long id, EstadoDTO dto) {
        try {
            EstadoResponseDTO estado = estadoService.update(id, dto);
            return Response.status(Status.NO_CONTENT).entity(estado).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        estadoService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed({"Admin"})
    public long count() {
        return estadoService.count();
    }

    @GET
    @Path("/search/{sigla}")
    public List<EstadoResponseDTO> search(@PathParam("sigla") String sigla) {
        return estadoService.findBySigla(sigla);
    }
}