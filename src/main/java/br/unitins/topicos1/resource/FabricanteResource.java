package br.unitins.topicos1.resource;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import br.unitins.topicos1.application.Result;
import br.unitins.topicos1.dto.FabricanteDTO;
import br.unitins.topicos1.dto.FabricanteResponseDTO;
import br.unitins.topicos1.service.FabricanteService;

@Path("/fabricantes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FabricanteResource {

    @Inject
    FabricanteService fabricanteService;

    @GET
    public List<FabricanteResponseDTO> getAll() {
        return fabricanteService.getAll();
    }

    @GET
    @Path("/{id}")
    public FabricanteResponseDTO findById(@PathParam("id") Long id) {
        return fabricanteService.findById(id);
    }

    @POST
    public Response insert(FabricanteDTO dto) {
        try {
            FabricanteResponseDTO fabricante = fabricanteService.create(dto);
            return Response.status(Status.CREATED).entity(fabricante).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        } catch (Exception e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
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
    public Response delete(@PathParam("id") Long id) {
        fabricanteService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    public long count() {
        return fabricanteService.count();
    }

    @GET
    @Path("/search/{nome}")
    public List<FabricanteResponseDTO> search(@PathParam("nome") String nome) {
        return fabricanteService.findByNome(nome);
    }
}