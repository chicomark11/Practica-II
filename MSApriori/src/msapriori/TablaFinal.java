/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msapriori;

import java.util.Vector;

/**
 *
 * @author Mark
 */
public class TablaFinal {
    Vector<String> transaccion;
    double MIS;
    int frecuencia;

    public TablaFinal (){ 
        this.transaccion=null;
        this.MIS=0;
        this.frecuencia=0;
    }
    
    public TablaFinal (Vector<String> transaccion, double MIS, int frecuencia){ 
        this.transaccion=transaccion;
        this.MIS=MIS;
        this.frecuencia=frecuencia;
    }
    
}
