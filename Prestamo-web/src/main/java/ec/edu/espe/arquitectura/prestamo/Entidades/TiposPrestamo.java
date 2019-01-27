/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.prestamo.rest.msg;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Steven
 */
public class TiposPrestamo {
    List<String> tipo = new ArrayList<String>();

    public List<String> getTipo() {
        return tipo;
    }

    public void setTipo(List<String> tipo) {
        this.tipo = tipo;
    }
    
}
