package br.unitins.topicos1.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.topicos1.application.Result;
import br.unitins.topicos1.dto.EnderecoDTO;
import br.unitins.topicos1.dto.EnderecoResponseDTO;
import br.unitins.topicos1.service.EnderecoService;
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

@Path("/enderecos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EnderecoResource {

    @Inject
    EnderecoService enderecoService;

    private static final Logger LOG = Logger.getLogger(UsuarioResource.class);

    @GET
    @RolesAllowed({ "Admin", "User" })
    public List<EnderecoResponseDTO> getAll() {
        return enderecoService.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Admin", "User" })
    public EnderecoResponseDTO findById(@PathParam("id") Long id) {
        return enderecoService.findById(id);
    }

    @POST
    @RolesAllowed({ "Admin" })
    public Response insert(EnderecoDTO dto) {
        LOG.infof("Inserindo um endereco: %s", dto.cep());
        Result result = null;

        try {
            EnderecoResponseDTO endereco = enderecoService.create(dto);
            LOG.infof("Endereco (%d) criado com sucesso.", endereco.id());

            return Response.status(Status.CREATED).entity(endereco).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um endereco.");
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
    public Response update(@PathParam("id") Long id, EnderecoDTO dto) {
        try {
            EnderecoResponseDTO endereco = enderecoService.update(id, dto);
            return Response.status(Status.NO_CONTENT).entity(endereco).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public Response delete(@PathParam("id") Long id) {
        enderecoService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed({ "Admin", "User" })
    public long count() {
        return enderecoService.count();
    }

    @GET
    @Path("/search/{cep}")
    @RolesAllowed({ "Admin", "User" })
    public List<EnderecoResponseDTO> search(@PathParam("cep") String cep) {
        return enderecoService.findByCep(cep);
    }
}