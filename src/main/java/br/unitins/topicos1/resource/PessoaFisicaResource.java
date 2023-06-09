package br.unitins.topicos1.resource;

import java.util.List;

import br.unitins.topicos1.application.Result;
import br.unitins.topicos1.dto.PessoaFisicaDTO;
import br.unitins.topicos1.dto.PessoaFisicaResponseDTO;
import br.unitins.topicos1.service.PessoaFisicaService;
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

@Path("/pessoasfisicas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PessoaFisicaResource {

    @Inject
    PessoaFisicaService pessoaFisicaService;

    @GET
    public List<PessoaFisicaResponseDTO> getAll() {
        return pessoaFisicaService.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Admin", "User" })
    public PessoaFisicaResponseDTO findById(@PathParam("id") Long id) {
        return pessoaFisicaService.findById(id);
    }

    @POST
    @RolesAllowed({ "Admin" })
    public Response insert(PessoaFisicaDTO dto) {
        try {
            PessoaFisicaResponseDTO pessoafisica = pessoaFisicaService.create(dto);
            return Response.status(Status.CREATED).entity(pessoafisica).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public Response update(@PathParam("id") Long id, PessoaFisicaDTO dto) {
        try {
            pessoaFisicaService.update(id, dto);
            return Response.status(Status.NO_CONTENT).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public Response delete(@PathParam("id") Long id) {
        pessoaFisicaService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed({ "Admin" })
    public long count() {
        return pessoaFisicaService.count();
    }

    @GET
    @Path("/search/{nome}")
    @RolesAllowed({ "Admin" })
    public List<PessoaFisicaResponseDTO> search(@PathParam("nome") String nome) {
        return pessoaFisicaService.findByNome(nome);
    }
}