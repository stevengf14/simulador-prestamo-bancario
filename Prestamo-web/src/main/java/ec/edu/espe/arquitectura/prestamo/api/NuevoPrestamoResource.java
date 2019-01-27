/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.prestamo.api;

import ec.edu.espe.arquitectura.prestamo.Entidades.Producto;
import ec.edu.espe.arquitectura.prestamo.Entidades.Tabla_Amortizacion;
import ec.edu.espe.arquitectura.prestamo.Entidades.TipoProducto;
import ec.edu.espe.arquitectura.prestamo.Entidades.Total;
import ec.edu.espe.arquitectura.prestamo.rest.msg.TiposPrestamo;
import ec.edu.espe.arquitectura.prestamo.Modelo.Bean_NuevoPrestamoLocal;
import ec.edu.espe.arquitectura.prestamo.rest.msg.TipoPrestamo;
import ec.edu.espe.arquitectura.prestamo.rest.msg.nuevoPrestamo;
import java.util.ArrayList;
import java.util.List;
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
    public Response postJson(nuevoPrestamo np) {
        int numPrestamo=0;
        numPrestamo = bean_nuevoPrestamo.ExtraerNumPrestamo();
        np.setMontoFinal(np.getMonto()-(np.getMonto()*0.15));
        bean_nuevoPrestamo.insertarPrestamo(numPrestamo + "", np.getCli_id() + "", bean_nuevoPrestamo.EncontrarIdPrestamo(np.getTipoPrestamo()) + "", np.getFechaCreacion(), np.getFechaConsecion(), np.getFechaDesembolso(), np.getMonto() + "", np.getPlazo() + "", np.getTipoPrestamo(), "0.15", np.getMontoFinal() + "", "act");
        ArrayList<Tabla_Amortizacion> amortizacion = new ArrayList<Tabla_Amortizacion>();
        amortizacion=CargarTabla(np.getTipoPrestamo(),np.getMonto(),np.getPlazo());
        for (int i = 1; i < amortizacion.size(); i++) {
            bean_nuevoPrestamo.InsertarAmortizacion(numPrestamo, amortizacion.get(i).getCapital(), amortizacion.get(i).getInteres(), amortizacion.get(i).getValor_cuota(), amortizacion.get(i).getFecha_amortizacion(), amortizacion.get(i).getEstado(), amortizacion.get(i).getNumero(), amortizacion.get(i).getCapital());
        }
        return Response.ok(np).build();
    }
    
    public ArrayList<Tabla_Amortizacion> CargarTabla(String tipo, double monto,int plazo) {
        ArrayList<Total> lista_total = new ArrayList<Total>();
        double interes_prestamo;
        ArrayList<Tabla_Amortizacion> amortizacion = new ArrayList<Tabla_Amortizacion>();
        lista_total.clear();
        Tabla_Amortizacion ta;
        interes_prestamo=bean_nuevoPrestamo.encontrarInteres(tipo);
        double interes_anual = interes_prestamo;
        double interes_mensual = interes_anual / 12 / 100;
        double interes = 0;
        double valor_cuota = monto * (interes_mensual * Math.pow(1 + interes_mensual, plazo)) / (Math.pow(interes_mensual + 1, plazo) - 1);
        double capital = 0;
        double saldo = monto;
        List<String> lista_fecha = bean_nuevoPrestamo.GenerarFechas(plazo);
        for (int i = 0; i <= plazo; i++) {

            if (i == 0) {
                ta = new Tabla_Amortizacion(i, 0, 0, 0, bean_nuevoPrestamo.Convertir(saldo), lista_fecha.get(i), "");
            } else {
                interes = saldo * ((interes_prestamo / 12) / 100);
                capital = valor_cuota - interes;
                saldo = saldo - capital;
                ta = new Tabla_Amortizacion(i, bean_nuevoPrestamo.Convertir(capital), bean_nuevoPrestamo.Convertir(interes), bean_nuevoPrestamo.Convertir(capital + interes), bean_nuevoPrestamo.Convertir(saldo), lista_fecha.get(i), "Pendiente");
            }
            amortizacion.add(ta);
        }
        Total tot = new Total("Total", bean_nuevoPrestamo.Convertir(monto), bean_nuevoPrestamo.Convertir(monto * interes_anual / 100), bean_nuevoPrestamo.Convertir(valor_cuota * plazo), bean_nuevoPrestamo.Convertir(saldo), "", "");
        lista_total.add(tot);
        return amortizacion;
    }
}
