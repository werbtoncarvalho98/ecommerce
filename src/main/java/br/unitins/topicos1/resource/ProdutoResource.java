// package br.unitins.topicos1.resource;

// import java.io.IOException;
// import java.util.List;

// import org.eclipse.microprofile.jwt.JsonWebToken;
// import org.jboss.logging.Logger;
// import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

// import br.unitins.topicos1.application.Result;
// import br.unitins.topicos1.dto.ProdutoDTO;
// import br.unitins.topicos1.dto.ProdutoResponseDTO;
// import br.unitins.topicos1.dto.UsuarioResponseDTO;
// import br.unitins.topicos1.form.ImageForm;
// import br.unitins.topicos1.service.FileService;
// import br.unitins.topicos1.service.ProdutoService;
// import br.unitins.topicos1.service.UsuarioService;
// import jakarta.annotation.security.RolesAllowed;
// import jakarta.inject.Inject;
// import jakarta.validation.ConstraintViolationException;
// import jakarta.ws.rs.Consumes;
// import jakarta.ws.rs.DELETE;
// import jakarta.ws.rs.GET;
// import jakarta.ws.rs.PATCH;
// import jakarta.ws.rs.POST;
// import jakarta.ws.rs.PUT;
// import jakarta.ws.rs.Path;
// import jakarta.ws.rs.PathParam;
// import jakarta.ws.rs.Produces;
// import jakarta.ws.rs.core.MediaType;
// import jakarta.ws.rs.core.Response;
// import jakarta.ws.rs.core.Response.ResponseBuilder;
// import jakarta.ws.rs.core.Response.Status;

// @Path("/produtos")
// @Consumes(MediaType.APPLICATION_JSON)
// @Produces(MediaType.APPLICATION_JSON)
// public class ProdutoResource {

//     @Inject
//     ProdutoService produtoService;

//     @Inject
//     FileService fileService;

//     @Inject
//     UsuarioService usuarioService;

//     @Inject
//     JsonWebToken jwt;

//     private static final Logger LOG = Logger.getLogger(ProdutoResource.class);

//     @GET
//     @RolesAllowed({ "Admin", "User" })
//     public List<ProdutoResponseDTO> getAll() {
//         return produtoService.getAll();
//     }

//     @GET
//     @Path("/{id}")
//     @RolesAllowed({ "Admin", "User" })
//     public ProdutoResponseDTO findById(@PathParam("id") Long id) {
//         return produtoService.findById(id);
//     }

//     @POST
//     @RolesAllowed({ "Admin" })
//     public Response insert(ProdutoDTO dto) {
//         LOG.infof("Inserindo um produto: %s", dto.nome());
//         Result result = null;

//         try {
//             ProdutoResponseDTO produto = produtoService.create(dto);
//             LOG.infof("Produto (%d) criado com sucesso.", produto.nome());

//             return Response.status(Status.CREATED).entity(produto).build();
//         } catch (ConstraintViolationException e) {
//             LOG.error("Erro ao incluir um produto.");
//             LOG.debug(e.getMessage());

//             result = new Result(e.getConstraintViolations());
//         } catch (Exception e) {
//             LOG.fatal("Erro sem identificacao: " + e.getMessage());

//             result = new Result(e.getMessage(), false);
//         }

//         return Response.status(Status.NOT_FOUND).entity(result).build();
//     }

//     @PUT
//     @Path("/{id}")
//     @RolesAllowed({ "Admin", "User" })
//     public Response update(@PathParam("id") Long id, ProdutoDTO dto) {
//         try {
//             ProdutoResponseDTO produto = produtoService.update(id, dto);
//             return Response.status(Status.NO_CONTENT).entity(produto).build();
//         } catch (ConstraintViolationException e) {
//             Result result = new Result(e.getConstraintViolations());
//             return Response.status(Status.NOT_FOUND).entity(result).build();
//         }
//     }

//     @DELETE
//     @Path("/{id}")
//     @RolesAllowed({ "Admin", "User" })
//     public Response delete(@PathParam("id") Long id) {
//         produtoService.delete(id);
//         return Response.status(Status.NO_CONTENT).build();
//     }

//     @GET
//     @Path("/count")
//     @RolesAllowed({ "Admin" })
//     public long count() {
//         return produtoService.count();
//     }

//     @GET
//     @Path("/search/{nome}")
//     @RolesAllowed({ "Admin" })
//     public List<ProdutoResponseDTO> search(@PathParam("nome") String nome) {
//         return produtoService.findByNome(nome);
//     }

//     @GET
//     @RolesAllowed({ "Admin", "User" })
//     public Response getUsuario() {
//         String login = jwt.getSubject();
//         UsuarioResponseDTO usuario = usuarioService.findByLogin(login);

//         return Response.ok(usuario).build();
//     }

//     @PATCH
//     @Path("/novaimagem")
//     @RolesAllowed({ "Admin", "User" })
//     @Consumes(MediaType.MULTIPART_FORM_DATA)
//     public Response salvarImagem(@MultipartForm ImageForm form) {
//         String imagem = "";

//         try {
//             imagem = fileService.salvarImagemUsuario(form.getImagem(), form.getNomeImagem());
//         } catch (IOException e) {
//             Result result = new Result(e.getMessage(), false);
//             return Response.status(Status.CONFLICT).entity(result).build();
//         }

//         String login = jwt.getSubject();
//         UsuarioResponseDTO usuario = usuarioService.findByLogin(login);

//         usuario = usuarioService.update(usuario.id(), imagem);

//         return Response.ok(usuario).build();

//     }

//     @GET
//     @Path("/download/{imagem}")
//     @RolesAllowed({ "Admin", "User" })
//     @Produces(MediaType.APPLICATION_OCTET_STREAM)
//     public Response download(@PathParam("imagem") String imagem) {
//         ResponseBuilder response = Response.ok(fileService.download(imagem));
//         response.header("Content-Disposition", "attachment;filename=" + imagem);
//         return response.build();
//     }
// }