package br.unitins.topicos1.resource;

import java.util.List;

import br.unitins.topicos1.application.Result;
import br.unitins.topicos1.dto.CompraDTO;
import br.unitins.topicos1.dto.CompraResponseDTO;
import br.unitins.topicos1.service.CompraService;
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

@Path("/compras")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CompraResource {

    @Inject
    CompraService compraService;

    @Inject
    Validator validator;

    @GET
    public List<CompraResponseDTO> getAll() {
        return compraService.getAll();
    }

    @GET
    @Path("/{id}")
    public CompraResponseDTO findById(@PathParam("id") Long id) {
        return compraService.findById(id);
    }

    @POST
    public Response insert(CompraDTO dto) {
        try {
            CompraResponseDTO compra = compraService.create(dto);
            return Response.status(Status.CREATED).entity(compra).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, CompraDTO dto) {
        try {
            CompraResponseDTO compra = compraService.update(id, dto);
            return Response.ok(compra).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        compraService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    public long count() {
        return compraService.count();
    }

    @GET
    @Path("/search/{nome}")
    public List<CompraResponseDTO> search(@PathParam("nome") String nome) {
        return compraService.findByNome(nome);
    }
}