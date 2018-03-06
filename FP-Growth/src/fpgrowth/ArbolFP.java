package fpgrowth;



import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.text.DecimalFormat;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 
 * @author pablo
 */

    public class ArbolFP{
	Nodo raiz = new Nodo();

        List<String> patron = new ArrayList<>();
        
        Vector<Tabla> tablas = new Vector();
        Vector<Patron> patron_parcial = new Vector();
        Vector<Patron> patron_soporte = new Vector();
        Vector<Patron> patron_frecuente = new Vector();
        Vector<Patron> patron_frecuente_orden = new Vector();
        
        DecimalFormat porciento = new DecimalFormat("#.00"); 
        
    public void LlenarTabla(List<String> bd){
        
        
        tablas.add(new Tabla("0",0,null,null));        
        for(int f=0; f < bd.size(); f++){
            for(int i = 0; i < tablas.size(); i++){
         	if(tablas.get(i).item.equals(bd.get(f))==true ){
         		tablas.get(i).count = tablas.get(i).count + 1;
                                  i=tablas.size();
         	}
                else{
                    if(i == tablas.size() - 1){
                        tablas.add(new Tabla(bd.get(f),1,null,null));
                        i=tablas.size();
                    }
                }
            }
        }
       /* for(int i = 0; i < tablas.size(); i++){
            System.out.println("item tabla"); 
            System.out.println(tablas.get(i).item); 
            System.out.println("tamaño count");
            System.out.println(tablas.get(i).count); 
        }*/
    }

    public void TablaSoporte(int soporte){
        //System.out.println("tabla soporte"); 
        for(int i = 0; i<tablas.size(); i++){
            if(    tablas.get(i).item.equals("0")==true || tablas.get(i).count < soporte){
       	  	tablas.get(i).item = "";
       	  	tablas.get(i).count = 0;
            }
        }
        for(int i = 0; i < tablas.size(); i++){
            for(int j=i+1; j < tablas.size(); j++){
                if(tablas.get(i).count < tablas.get(j).count ){
                    String aux = tablas.get(i).item;
                    int aux2 = tablas.get(i).count;
                    tablas.get(i).item = tablas.get(j).item;
                    tablas.get(i).count = tablas.get(j).count;
                    tablas.get(j).item = aux;
                    tablas.get(j).count = aux2;
		}
            }
	}
      /*  for(int i=0;i<tablas.size();i++){
            System.out.println("item tabla"); 
            System.out.println(tablas.get(i).item);
            System.out.println("count tabla"); 
            System.out.println(tablas.get(i).count);
        }*/
    }
  
    public void OrdenarTransaccion(List<String> td){
        List<String> orden=new ArrayList<>();

        for(int i=0; i<tablas.size();i++){
            for(int j=0; j<td.size(); j++){
                if(td.get(j).equals(tablas.get(i).item)==true){
                    orden.add(td.get(j));    
                }
            }
        }
        td.clear();
        for(int k=0; k<orden.size();k++){
            td.add(orden.get(k));
        }
    }

    public void IngresarTransaccion(List<String> transaccion){
        Nodo NodoActual = raiz;
        for(String item : transaccion){
            Nodo hijo = NodoActual.getHijoConItem(item);
            if(hijo == null){
		Nodo NuevoNodo = new Nodo();
		NuevoNodo.item = item;
		NuevoNodo.padre = NodoActual;
		NodoActual.hijos.add(NuevoNodo); 
		NodoActual = NuevoNodo;	
		AgregarLinkTabla(item, NuevoNodo);
                             
            }
            else{
           	hijo.count++;
           	NodoActual = hijo;   
            }
	}
    }

    public void AgregarLinkTabla(String item, Nodo nodo){
        Nodo aux=null;
        for(int i = 0; i < tablas.size(); i++){
            if(    tablas.get(i).item.equals(item)==true){           
                if(tablas.get(i).link == null){
                    tablas.get(i).link = nodo;
                    tablas.get(i).ultimo_link = nodo; 
                }else{ 
                    aux=tablas.get(i).ultimo_link;
                    aux.link_sig=nodo;
               	    tablas.get(i).ultimo_link = nodo; 
                }
            }
        }
    }

    public void PatronesParciales(int soporte, int total, Vector<ArrayList<String>> lista, int periodo){
        Nodo NodoPatrones;
        Nodo aux;
        int sup=soporte;
        int count=0;
        String item=""; 
        double porcentaje=0;
        porcentaje=soporte*100.0/total;
        System.out.println("\nLos patrones frecuentes con soporte "+soporte+" ("+porciento.format(porcentaje)+"%) son :\n" );
            for (int i=0;i<tablas.size()-1;i++){
                if (tablas.get(i).item!=""){
                    porcentaje=tablas.get(i).count*100.0/total;
                    System.out.println("Patron :["+tablas.get(i).item+"], Soporte :"+tablas.get(i).count+" ("+porciento.format(porcentaje)+"%)");
                }
            }
        for(int i=tablas.size()-1;i>=0;i--){

            if(tablas.get(i).link != null){

                NodoPatrones=tablas.get(i).link;

                while(NodoPatrones != null){
                    count=NodoPatrones.count;
                    aux=NodoPatrones.padre;
                    item=NodoPatrones.item;
 
                    while(aux !=raiz){
                        Nodo aux2=null;
                        patron.add(aux.item);
                        aux2=aux.padre;
                        aux=aux2;
                        if(aux == raiz){
                            if(patron.size()> 0){
                                PatronOrden(patron, count, item);
                                patron.clear();
                                count=0;
                            }
                        }
                    }
                aux=NodoPatrones.link_sig;
                NodoPatrones=aux;
                }
            }
        PatronAgrupado(sup);
        PatronesFrecuentes(item);
        Orden_bd(periodo,lista);
        ResultadoFinal(total);
        patron_parcial.clear();
        patron_soporte.clear();
        patron_frecuente.clear();
        patron_frecuente_orden.clear();
        }
    }
  
  //ordenar patron que se obtiene del arbol
    public void PatronOrden(List<String> patronparcial, int count, String item){
        List<String> patroncount=new ArrayList<>();
        //System.out.println("item");
        //System.out.println(item);
        //System.out.println("patron parcial");
    
        for(int i=patronparcial.size()-1; i>=0; i--){
            patroncount.add(patronparcial.get(i));
          //  System.out.println(patronparcial.get(i)); 
        }
        //System.out.println("count");
        //System.out.println(count);
        patronparcial.clear();
        for(int i=0; i<patroncount.size(); i++){
        patronparcial.add(patroncount.get(i));
        }
        patron_parcial.add(new Patron(new ArrayList<String>(patronparcial), count));
  } 

//elementos comunes entre patrones parciales y sumar el contador  
    public void PatronAgrupado(int soporte){
     List<String> aux=new ArrayList<>();
     List<String> aux2=new ArrayList<>();
     List<String> aux3=new ArrayList<>();
     int count=0;
     int count2=0;
     int ctotal=0;
     int f=0;
     if(patron_parcial.size()==1){
         aux=patron_parcial.get(0).items;
         count=patron_parcial.get(0).count;
         if(count>=soporte){
         patron_soporte.add(new Patron(new ArrayList<String>(aux), count));
         }
     }else{
            for(int i=0;i<patron_parcial.size();i++){
                                      if(patron_parcial.get(i).count >=soporte){
                      patron_soporte.add(new Patron(new ArrayList<String>(patron_parcial.get(i).items),patron_parcial.get(i).count));
            }
            }
      for(int i=0;i<patron_parcial.size()-1;i++){
          aux=patron_parcial.get(i).items;
          count=patron_parcial.get(i).count;
          for(int l=i+1;l<patron_parcial.size();l++){
              aux2=patron_parcial.get(l).items;
              count2=patron_parcial.get(l).count;
              for(int j=0;j<aux.size();j++){
            
                  for(int k=0;k<aux2.size();k++){
                
                      if(aux.get(j) == aux2.get(k)){
                      aux3.add(aux.get(j));
                  }
              }
          }
      if(aux3.size()>0){
          ctotal=count+count2; 
          if(ctotal>=soporte){
              for(int t=0;t<patron_soporte.size();t++){
                   f=0;
                  if(patron_soporte.get(t).items.equals(aux3)==true && ctotal==patron_soporte.get(t).count ){
                      f=1;
                      t=patron_soporte.size();
                  }
              }
              if(f==0){
                  patron_soporte.add(new Patron(new ArrayList<String>(aux3), ctotal));
              }
          
                             }
          }
      aux3.clear();
          }
      }
     }

  }
  
  
 //combinar los elementos de los patrones parciales agrupados con el item para obtener patrones frecuentes
  
  public void PatronesFrecuentes(String item){
        List<String> aux=new ArrayList<>();
        List<String> aux2=new ArrayList<>();
        List<String> aux3=new ArrayList<>();
        List<String> ptotal=new ArrayList<>();
        List<String> contador_p=new ArrayList<>();
        int c=0;
        int c2=0;
        int contador;
        int ba=0;
        int tamaño=0;
        aux.clear();
        aux2.clear();
        for(int i=0;i<patron_soporte.size();i++){
            //System.out.println("Empieza la vuelta nro "+ (i+1) +" de "+patron_soporte.size());
            //System.out.println("Elemento: "+item +", patron de soporte: "+patron_soporte.get(i).items);
            aux=new ArrayList<String>(patron_soporte.get(i).items);
            
            c=patron_soporte.get(i).count;
            contador=patron_frecuente.size();
            for(int k=0;k<aux.size();k++){
                ptotal.add(aux.get(k));
            }
            ptotal.add(item);
            contador_p=ptotal;
            tamaño=ptotal.size();
            patron_frecuente.add(new Patron(new ArrayList<String>(ptotal),patron_soporte.get(i).count));
            contador_p=new ArrayList<String>(ptotal);
            ptotal.clear();
            if(aux.size()>1){
            for (int j=0; j<aux.size();j++){ 
            	if (j==0) {
					for (int k=0; k<aux.size();k++){	            		
                                aux2.add(aux.get(k));
                                aux2.add(item);
            			patron_frecuente.add(new Patron(new ArrayList<String>(aux2),c));
                                aux2.clear();
            		}
            	}
            	else {	
                    int ayuda=patron_frecuente.size();
                    for (int m=contador; m<ayuda; m++){
                    	for (int k=1; k<aux.size();k++){
                        	aux2=new ArrayList<String>(patron_frecuente.get(contador).items);
                        	if (estaEnLista(aux2, aux.get(k), contador_p)==false){
                                    aux2.add(aux.get(k));
                                    for(int q=0;q<patron_frecuente.size();q++){
                                        ba=0;
                                        aux3=new ArrayList<String>(patron_frecuente.get(q).items);
                                        c2=patron_frecuente.get(q).count;
                                        if(Contiene(aux3,aux2)==true && aux3.size()==aux2.size()){
                                            ba=1;
                                            q=patron_frecuente.size();
                                        }
                                        if(aux2.size()>=tamaño){
                                            ba=1;
                                            q=patron_frecuente.size();
                                        }
                                    }
                                    if(ba==0){
                                   
                                    patron_frecuente.add(new Patron(new ArrayList<String>(aux2),c));
                                    }
                                }           
                                aux2.clear();
                   	 	}
                    contador++;
                    }
                }

            }

        } 
 
        }

    }
  

    
    public boolean Contiene(List<String> a, List<String> b){
        String item="";
        int count=0;
        int count2=0;
        if(a.size() != b.size()){
            return false;
        }
        for(int i=0;i<a.size();i++){
            item=a.get(i);
            for(int j=0;j<a.size();j++){
                if(a.get(j).equals(item)){
                    count++;
                }
            }
            for(int k=0;k<b.size();k++){
                if(b.get(k).equals(item)){
                    count2++;
                }
            }
            if(count != count2){
             return false;
        }
        }
        return true;
    }
  
    public boolean estaEnLista(List<String> a, String value, List<String> c) {
        int count=0;
        int count2=0;
       for (int i = 0; i < c.size(); i++) {
            if(value.equals(c.get(i))) {
                count++;
            }
        }
        for (int j = 0; j < a.size(); j++) {
            if(value.equals(a.get(j))) {
                count2++;
            }
        }
        if(count>count2){
         return false; 
        }
        return true;
    }
  
      public void Orden_bd(int p, Vector<ArrayList<String>> lista){
      List<String> aux=new ArrayList<>();
      List<String> aux2=new ArrayList<>();
      List<String> aux3=new ArrayList<>();
      int c=0;
      for(int i=0; i<lista.size();i++){
          aux=lista.get(i);
          for(int j=0;j<patron_frecuente.size();j++){
              aux2=patron_frecuente.get(j).items;
              c=patron_frecuente.get(j).count;
               if(aux.size()==aux2.size()){
                    if(Contiene(aux,aux2)==true){
                        if(PatronEstaEnLista(aux,c)==0){
                            patron_frecuente_orden.add(new Patron(new ArrayList<String>(aux),c));   
                         }
                     }
                    }else{
                   for(int k=0;k<aux.size();k++){
                       if(k+aux2.size()<=aux.size()){
                      for(int l=0;l<aux2.size();l++){
                          aux3.add(aux.get(k+l));
                      }

                      if(Contiene(aux3,aux2)==true){
                        if(PatronEstaEnLista(aux3,c)==0){
                            patron_frecuente_orden.add(new Patron(new ArrayList<String>(aux3),c));   
                            aux3.clear();
                         }
                     }
                   }
                       aux3.clear();
                   }        
                 } 
          }
      }
  }
      
      
    public int PatronEstaEnLista(List<String> a, int contador){
      int bandera=0;
      for(int i=0; i<patron_frecuente_orden.size();i++){
           if(a.equals(patron_frecuente_orden.get(i).items)){
               if(patron_frecuente_orden.get(i).count>=contador){
                   bandera=1;

               }
               if(patron_frecuente_orden.get(i).count<contador){
                  patron_frecuente_orden.get(i).items=new ArrayList<String> (a);
                  patron_frecuente_orden.get(i).count=contador;
                  bandera=1;
                   
               }
               
           }
      }
      return bandera;
    }  
    
  public void MostrarPatrones(){
            List<String> aux=new ArrayList<>();
      int c=0;
      for(int i=0;i<patron_frecuente.size();i++){
          aux=patron_frecuente.get(i).items;
          c=patron_frecuente.get(i).count;
                System.out.println("patron frecuente");
          for(int j=0;j<aux.size();j++){
      System.out.println(aux.get(j));
          }
       System.out.println("count");
             System.out.println(c);
          
          
      }
      
  }
  
    public void ResultadoFinal(int total){
        
        List<String> aux2=new ArrayList<>();
        for (int i=0;i<patron_frecuente.size();i++){
            aux2=patron_frecuente.get(i).items;
            int c=patron_frecuente.get(i).count;
            System.out.println("Patron :"+aux2+", Soporte :"+c+" ("+porciento.format(c*100.0/total)+"%)");
        }
        
    }
 
}
