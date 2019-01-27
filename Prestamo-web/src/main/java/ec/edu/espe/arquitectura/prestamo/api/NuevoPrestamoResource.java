/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.prestamo.api;

import ec.edu.espe.arquitectura.prestamo.Entidades.Producto;
import ec.edu.espe.arquitectura.prestamo.Entidades.TipoProducto;
import ec.edu.espe.arquitectura.prestamo.rest.msg.TiposPrestamo;
import ec.edu.espe.arquitectura.prestamo.Modelo.Bean_NuevoPrestamoLocal;
import ec.edu.espe.arquitectura.prestamo.rest.msg.TipoPrestamo;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Steven
 */
@Path("nuevoPrestamo")
@RequestScoped
public class NuevoPrestamoResource {

    @EJB
    Bean_NuevoPrestamoLocal bean_nuevoPrestamo;
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of NuevoPrestamoResource
     */
    public NuevoPrestamoResource() {
    }

    /**
     * Retrieves representation of an instance of ec.edu.espe.arquitectura.prestamo.api.NuevoPrestamoResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson() {
        TiposPrestamo tipo = new TiposPrestamo();
        tipo.setTipo(bean_nuevoPrestamo.cargarListaPrestamos());
        return Response.ok(tipo.getTipo()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{tipo}")
    public Response getJson(@PathParam("tipo") String tipo) {
        TipoPrestamo tp = new TipoPrestamo();
        Producto pro = bean_nuevoPrestamo.valoresTipoPrestamo(tipo);
        TipoProducto tipoP = bean_nuevoPrestamo.valoresTipoProducto(tipo);
        tp.setId(pro.getProId());
        tp.setDescripcion(tipo);
        tp.setEstado(pro.getProEstado());
        tp.setTipo_cliente(pro.getProTipoCliente());
        tp.setMontoMin(tipoP.getMontoMinimo());
        tp.setMontoMax(tipoP.getMontoMaximo());
        tp.setPlazoMin(tipoP.getPlazoMin());
        tp.setPlazoMax(tipoP.getPlazoMax());
        return Response.ok(tp).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void postJson(String content) {
        int numPrestamo=0;
        numPrestamo = bean_nuevoPrestamo.ExtraerNumPrestamo();
        /*bean_nuevoPrestamo.insertarPrestamo(numPrestamo + "", bean_nuevoPrestamo.EncontrarClienteId(cedula) + "", bean_nuevoPrestamo.EncontrarIdPrestamo(tipo) + "", this.fechaCreacion, this.fechaConcesion, this.fechaDesembolso, this.monto + "", this.plazo + "", tipo, "0.15", this.montoFinal + "", "act");
        for (int i = 1; i < amortizacion.size(); i++) {
            bean_nuevoPrestamo.InsertarAmortizacion(numPrestamo, amortizacion.get(i).getCapital(), amortizacion.get(i).getInteres(), amortizacion.get(i).getValor_cuota(), amortizacion.get(i).getFecha_amortizacion(), amortizacion.get(i).getEstado(), amortizacion.get(i).getNumero(), amortizacion.get(i).getCapital());
        }*/
    }
}
