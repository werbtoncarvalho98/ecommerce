package br.unitins.topicos1.resource;

import java.util.List;

import javax.inject.Inject;
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
import br.unitins.topicos1.dto.HardwareDTO;
import br.unitins.topicos1.dto.HardwareResponseDTO;
import br.unitins.topicos1.service.HardwareService;

@Path("/hardwares")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HardwareResource {

    @Inject
    HardwareService hardwareService;

    @GET
    public List<HardwareResponseDTO> getAll() {
        return hardwareService.getAll();
    }

    @GET
    @Path("/{id}")
    public HardwareResponseDTO findById(@PathParam("id") Long id) {
        return hardwareService.findById(id);
    }

    @POST
    public Response insert(HardwareDTO dto) {
        try {
            HardwareResponseDTO hardware = hardwareService.create(dto);
            return Response.status(Status.CREATED).entity(hardware).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, HardwareDTO dto) {
        try {
            HardwareResponseDTO hardware = hardwareService.update(id, dto);
            return Response.ok(hardware).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        hardwareService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    public long count() {
        return hardwareService.count();
    }

    @GET
    @Path("/search/{marca}")
    public List<HardwareResponseDTO> search(@PathParam("marca") String marca) {
        return hardwareService.findByMarca(marca);
    }
}