package org.acme.resource;

import java.io.IOException;

import org.acme.application.Result;
import org.acme.dto.UsuarioResponseDTO;
import org.acme.form.ImageForm;
import org.acme.service.FileService;
import org.acme.service.UsuarioService;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

@Path("/usuariologado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioLogadoResource {
    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioService usuarioService;

    @Inject
    FileService fileService;

    @GET
    @RolesAllowed({ "Admin", "User" })
    public Response getUsuario() {
        String login = jwt.getSubject();
        UsuarioResponseDTO usuarioResponseDTO = usuarioService.findByLogin(login);

        return Response.ok(usuarioResponseDTO).build();
    }

    @PATCH
    @Path("/novaimagem")
    @RolesAllowed({ "Admin", "User" })
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response salvarImagem(@MultipartForm ImageForm form) {
        String nomeImagem = "";

        try {
            nomeImagem = fileService.salvarImagem(form.getImagem(), form.getNomeImagem());

        } catch (IOException e) {
            Result result = new Result(e.getMessage());
            return Response.status(Status.CONFLICT).entity(result).build();

        }

        String login = jwt.getSubject();
        UsuarioResponseDTO usuario = usuarioService.findByLogin(login);

        usuario = usuarioService.update(usuario.id(), nomeImagem);

        return Response.ok(usuario).build();
    }

    @GET
    @Path("/download/{nomeImagem}")
    @RolesAllowed({ "Admin", "User" })
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("nomeImagem") String nomeImagem) {
        ResponseBuilder response = Response.ok(fileService.download(nomeImagem));
        response.header("Content-Disposition", "attachment;filename=" + nomeImagem);
        return response.build();
    }
}