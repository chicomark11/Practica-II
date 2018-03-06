/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpgrowth;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author pablo
 */
public class funcion {
    int c1=0;
    int c2=0;
    int c3=0;
    String item="5";
    
    List<String> lista1=new ArrayList<>();
    List<String> lista2=new ArrayList<>();
    List<String> lista3=new ArrayList<>();
    List<String> lista4=new ArrayList<>();
    List<String> listaaux=new ArrayList<>();
      
    List<String> aux=new ArrayList<>();
    List<String> aux2=new ArrayList<>();
    List<String> aux3=new ArrayList<>();
    List<String> aux4=new ArrayList<>();
    List<String> aux5=new ArrayList<>();

      
    Vector<Patron> patrones = new Vector();
    Vector<Patron> patronsup = new Vector();
    Vector<Patron> patronfre = new Vector();
      
    public funcion(){  
        lista1.add("1");
        lista1.add("2");
        lista1.add("3");
        lista1.add("4");
        c1=3;
      
        lista2.add("1");
        lista2.add("2");
        lista2.add("4");

        c2=1;
      
        lista3.add("1");
        lista3.add("3");
        lista3.add("4");

        c3=4;
     
        patrones.add(new Patron(lista1,c1));
        patrones.add(new Patron(lista2,c2));
        patrones.add(new Patron(lista3,c3));
      
    }
 
    public void patrones(){
        int count;
        int count2;
        int ctotal;
        
        System.out.println("tamaño vector patrones");
        System.out.println(patrones.size());
            
        for(int i=0;i<patrones.size()-1;i++){
            aux=patrones.get(i).items;
            count=patrones.get(i).count;
            for(int l=i+1;l<patrones.size();l++){
                aux2=patrones.get(l).items;
                count2=patrones.get(l).count;
                for(int j=0;j<aux.size();j++){
                    for(int k=0;k<aux2.size();k++){
                        if(aux.get(j) == aux2.get(k)){
                            aux3.add(aux.get(j));
                        }
                    }
                }
                ctotal=count+count2;
                //guardar elementos comunes y count total
                System.out.println("elementos aux3");
                for(int q=0;q<aux3.size();q++){
                    System.out.println(aux3.get(q));
                }
                //patronsup.add(new Patron(aux3,ctotal));
                patronsup.add(new Patron(new ArrayList<String>(aux3), ctotal));
                System.out.println("tamaño comparacion");
                int t=aux3.size();
                System.out.println(t);

        /*      for(int z=t-1;z>=0;z--){
                        aux3.remove(z);

        }*/
                aux3.clear();
            }
        }
    }
 
    public void mostrarPatrones(){
        
        for(int i=0;i<patronfre.size();i++){        
            lista4=patronfre.get(i).items;
            System.out.println("items");
            for(int j=0;j<lista4.size();j++){
                System.out.println(lista4.get(j));
            }
            System.out.println("count");
            System.out.println(patronfre.get(i).count);
        }
    }

    public void Combinaciones(){
        System.out.println("aca empieza las combinaciones");
        int c=0;
        int c2=0;
        int contador;
        int ba=0;
        aux2.clear();
        for(int i=0;i<patronsup.size();i++){
            System.out.println("Empieza la vuelta nro "+ (i+1) +" de "+patronsup.size());
            System.out.println("Elemento: "+item +", patron de soporte: "+patronsup.get(i).items);
            aux=new ArrayList<String>(patronsup.get(i).items);
            c=patronsup.get(i).count;
            contador=patronfre.size();
            for (int j=0; j<aux.size();j++){ 
            	if (j==0) {
					for (int k=0; k<aux.size();k++){
            			aux2.add(item);
	            		aux2.add(aux.get(k));
            			patronfre.add(new Patron(new ArrayList<String>(aux2),c));
            			aux2.clear();
            		}
            	}
            	else {	
                    int ayuda=patronfre.size();
                    for (int m=contador; m<ayuda; m++){
                    	for (int k=0; k<aux.size();k++){
                        	aux2=new ArrayList<String>(patronfre.get(contador).items);
                        	if (estaEnLista(aux2, aux.get(k))==false){
                                    aux2.add(aux.get(k));
                                    for(int q=0;q<patronfre.size();q++){
                                        ba=0;
                                        aux3=new ArrayList<String>(patronfre.get(q).items);
                                        c2=patronfre.get(q).count;
                                        if(aux3.containsAll(aux2) && c2==c){
                                            ba=1;
                                            q=patronfre.size();
                                        }
                                    }
                                    if(ba==0){
                                    patronfre.add(new Patron(new ArrayList<String>(aux2),c));
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
    
    public boolean estaEnLista(List<String> a, String value) {
        for (int i = 0; i < a.size(); i++) {
            if(value == a.get(i)) {
                return true;
            }
        }
        return false;
    }
    
     public void ResultadoFinal(){
        List<String> aux=new ArrayList<>();
        System.out.println("Los patrones frecuentes son :" );
        for (int i=0;i<patronfre.size();i++){
            aux=patronfre.get(i).items;
            int c=patronfre.get(i).count;
            System.out.println("Patron :"+aux+", Contador :"+c);
        }
     }
    
}
