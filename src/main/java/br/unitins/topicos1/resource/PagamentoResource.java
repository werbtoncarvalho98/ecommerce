package br.unitins.topicos1.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.unitins.topicos1.application.Result;
import br.unitins.topicos1.dto.PagamentoDTO;
import br.unitins.topicos1.dto.PagamentoResponseDTO;
import br.unitins.topicos1.service.PagamentoService;
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

@Path("/pagamentos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PagamentoResource {

    @Inject
    PagamentoService pagamentoService;

    private static final Logger LOG = Logger.getLogger(MunicipioResource.class);

    @Inject
    Validator validator;

    @GET
    public List<PagamentoResponseDTO> getAll() {
        return pagamentoService.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Admin", "User" })
    public PagamentoResponseDTO findById(@PathParam("id") Long id) {
        return pagamentoService.findById(id);
    }

    @POST
    @RolesAllowed({ "Admin" })
    public Response insert(PagamentoDTO dto) {
        //LOG.infof("Inserindo um pagamento: %s", dto.totalPagamento());
        Result result = null;

        try {
            PagamentoResponseDTO pagamento = pagamentoService.create(dto);
            LOG.infof("Pagamento (%d) criado com sucesso.", pagamento.valor());

            return Response.status(Status.CREATED).entity(pagamento).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um pagamento.");
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
    public Response update(@PathParam("id") Long id, PagamentoDTO dto) {
        try {
            PagamentoResponseDTO pagamento = pagamentoService.update(id, dto);
            return Response.ok(pagamento).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public Response delete(@PathParam("id") Long id) {
        pagamentoService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed({ "Admin" })
    public long count() {
        return pagamentoService.count();
    }

    @GET
    @Path("/search/{valor}")
    @RolesAllowed({ "Admin" })
    public List<PagamentoResponseDTO> search(@PathParam("valor") Double valor) {
        return pagamentoService.findByValor(valor);
    }
}