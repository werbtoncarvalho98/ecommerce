package br.unitins.topicos1.resource;

import java.io.IOException;
import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import br.unitins.topicos1.application.Result;
import br.unitins.topicos1.dto.HardwareDTO;
import br.unitins.topicos1.dto.HardwareResponseDTO;
import br.unitins.topicos1.dto.UsuarioResponseDTO;
import br.unitins.topicos1.form.ImageForm;
import br.unitins.topicos1.service.FileService;
import br.unitins.topicos1.service.FileServicelmpl;
import br.unitins.topicos1.service.HardwareService;
import io.quarkus.agroal.runtime.DataSourceSupport.Entry;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

@Path("/hardwares")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HardwareResource {

    @Inject
    HardwareService hardwareService;

    @Inject
    FileService fileService;

    @Inject
    JsonWebToken jwt;

    private static final Logger LOG = Logger.getLogger(HardwareResource.class);

    @GET
    @RolesAllowed({ "Admin", "User" })
    public List<HardwareResponseDTO> getAll() {
        return hardwareService.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Admin", "User" })
    public HardwareResponseDTO findById(@PathParam("id") Long id) {
        return hardwareService.findById(id);
    }

    @POST
    @RolesAllowed({ "Admin" })
    public Response insert(HardwareDTO dto) {
        LOG.infof("Inserindo um hardware: %s", dto.modelo());
        Result result = null;

        try {
            HardwareResponseDTO hardware = hardwareService.create(dto);
            LOG.infof("Hardware (%d) criado com sucesso.", hardware.modelo());

            return Response.status(Status.CREATED).entity(hardware).build();
        } catch (ConstraintViolationException e) {
            LOG.error("Erro ao incluir um hardware.");
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
    public Response update(@PathParam("id") Long id, HardwareDTO dto) {
        try {
            HardwareResponseDTO hardware = hardwareService.update(id, dto);
            return Response.status(Status.NO_CONTENT).entity(hardware).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public Response delete(@PathParam("id") Long id) {
        hardwareService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed({ "Admin", "User" })
    public long count() {
        return hardwareService.count();
    }

    @GET
    @Path("/search/{marca}")
    @RolesAllowed({ "Admin", "User" })
    public List<HardwareResponseDTO> search(@PathParam("marca") String marca) {
        return hardwareService.findByMarca(marca);
    }

    // @PATCH
    // @Path("/novaimagem")
    // @RolesAllowed({ "Admin", "User" })
    // @Consumes(MediaType.MULTIPART_FORM_DATA)
    // public Response salvarImagem(@MultipartForm ImageForm form) {
    //     String imagem = "";

    //     try {
    //         imagem = fileService.salvarImagem(form.getImagem(),
    //                 form.getNomeImagem());
    //     } catch (IOException e) {
    //         Result result = new Result(e.getMessage(), false);
    //         return Response.status(Status.CONFLICT).entity(result).build();
    //     }

    //     String marca = jwt.getSubject();
    //     List<HardwareResponseDTO> hardware = hardwareService.findByMarca(marca);

    //     hardware = hardwareService.update(hardware.forEach(), imagem);

    //     return Response.ok(hardware).build();
    // }

    @GET
    @Path("/download/{imagem}")
    @RolesAllowed({ "Admin", "User" })
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("imagem") String imagem) {
        ResponseBuilder response = Response.ok(fileService.download(imagem));
        response.header("Content-Disposition", "attachment;filename=" + imagem);
        return response.build();
    }
}