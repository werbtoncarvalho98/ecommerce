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
import br.unitins.topicos1.dto.EnderecoDTO;
import br.unitins.topicos1.dto.EnderecoResponseDTO;
import br.unitins.topicos1.service.EnderecoService;

@Path("/enderecos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EnderecoResource {

    @Inject
    EnderecoService telefoneService;

    @GET
    public List<EnderecoResponseDTO> getAll() {
        return telefoneService.getAll();
    }

    @GET
    @Path("/{id}")
    public EnderecoResponseDTO findById(@PathParam("id") Long id) {
        return telefoneService.findById(id);
    }

    @POST
    public Response insert(EnderecoDTO dto) {
        try {
            EnderecoResponseDTO telefone = telefoneService.create(dto);
            return Response.status(Status.CREATED).entity(telefone).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, EnderecoDTO dto) {
        try {
            EnderecoResponseDTO telefone = telefoneService.update(id, dto);
            return Response.status(Status.NO_CONTENT).entity(telefone).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        telefoneService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    public long count() {
        return telefoneService.count();
    }

    @GET
    @Path("/search/{cep}")
    public List<EnderecoResponseDTO> search(@PathParam("cep") String cep) {
        return telefoneService.findByCep(cep);
    }
}