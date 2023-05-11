package br.unitins.topicos1.resource;

import java.util.List;

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
import br.unitins.topicos1.application.Result;
import br.unitins.topicos1.dto.ProdutoDTO;
import br.unitins.topicos1.dto.ProdutoResponseDTO;
import br.unitins.topicos1.service.ProdutoService;

@Path("/produtos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProdutoResource {

    @Inject
    ProdutoService produtoService;

    @GET
    public List<ProdutoResponseDTO> getAll() {
        return produtoService.getAll();
    }

    @GET
    @Path("/{id}")
    public ProdutoResponseDTO findById(@PathParam("id") Long id) {
        return produtoService.findById(id);
    }

    @POST
    public Response insert(ProdutoDTO dto) {
        try {
            ProdutoResponseDTO produto = produtoService.create(dto);
            return Response.status(Status.CREATED).entity(produto).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, ProdutoDTO dto) {
        try {
            ProdutoResponseDTO produto = produtoService.update(id, dto);
            return Response.status(Status.NO_CONTENT).entity(produto).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        produtoService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    public long count() {
        return produtoService.count();
    }

    @GET
    @Path("/search/{nome}")
    public List<ProdutoResponseDTO> search(@PathParam("nome") String nome) {
        return produtoService.findByNome(nome);
    }
}