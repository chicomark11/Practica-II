package fpgrowth;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pablo
 */
    
    public class Algoritmo {
        
        List<String> transaccion  = new ArrayList<>();
        ArbolFP arbol = new ArbolFP();
        Vector<ArrayList<String>> lista_td = new Vector();

    
    public void CorrerAlgoritmo(List<String> bd, int soporte, int total, int p){
        String aux = "";
        int sup=soporte;
        

     	arbol.LlenarTabla(bd);
     	arbol.TablaSoporte(soporte);
        System.out.println("segmentos");
       for(int i=0;i<bd.size();i=i+p ){
       aux=bd.get(i);
       if(i+p<=bd.size()){
       for(int j=0;j<p;j++){

           transaccion.add(bd.get(i+j));
       }
       System.out.println(transaccion);
       lista_td.add(new ArrayList<String> (transaccion));
          arbol.OrdenarTransaccion(transaccion);  
         arbol.IngresarTransaccion(transaccion);

             
          transaccion.clear();
       }
        transaccion.clear();       
       
 }
       /*for(int i=0;i<bd.size();i++ ){
            aux=bd.get(i);
            if(aux!=""){
                transaccion.add(bd.get(i));
            }
            if(aux == "0" || i == bd.size() - 1){
                arbol.OrdenarTransaccion(transaccion);
                arbol.IngresarTransaccion(transaccion);
                //aux=0;   
                transaccion.clear();
            }
        }*/
        arbol.PatronesParciales(sup, total, lista_td, p);

    }

}