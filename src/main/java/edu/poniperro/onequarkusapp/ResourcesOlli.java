package edu.poniperro.onequarkusapp;

import edu.poniperro.onequarkusapp.dominio.Item;
import edu.poniperro.onequarkusapp.dominio.Orden;
import edu.poniperro.onequarkusapp.dominio.Usuaria;
import jdk.jfr.ContentType;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/")
public class ResourcesOlli {
    @Inject
    ServiceOlli service;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("wellcome")
    public String wellcome() {
        return "Wellcome Ollivanders!";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("usuaria/{nombre}")
    public Response getUsuaria(@PathParam("nombre") String nombre) {
        Usuaria usuaria = service.cargaUsuaria(nombre);

        return !usuaria.getNombre().isEmpty()
                ? Response.ok(usuaria).build()
                : Response.status(404).build();
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("ordena")
    public Response createOrden(Orden ord) {
        Orden orden = service.comanda(ord.getUser().getNombre(), ord.getItem().getNombre());
        return orden != null
                ? Response.status(201).entity(orden).build()
                : Response.noContent().status(404).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("pedidos/{usuaria}")
    public Response getPedidosByUsuaria(@PathParam("usuaria") String nombreUsuaria) {
        List<Orden> ordenes = service.cargaOrden(nombreUsuaria);

        return Response.ok(ordenes).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("item/{nombre}")
    public Response getItem(@PathParam("nombre") String nombre) {
        Item item = service.cargaItem(nombre);

        return !item.getNombre().isEmpty()
                ? Response.ok(item).build()
                : Response.status(404).build();
    }
}
