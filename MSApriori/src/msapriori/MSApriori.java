/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msapriori;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Vector;
import java.text.DecimalFormat;

/**
 *
 * @author Mark
 */
public class MSApriori {
    
    Vector<TablaMSApriori> tablas = new Vector();
    Vector<TablaMSApriori> general= new Vector();
    Vector<TablaMSApriori> nofrecuente = new Vector();
    Vector<TablaFinal> resultado = new Vector();
    Scanner reader = new Scanner(System.in);
    DecimalFormat porciento = new DecimalFormat("0.###");
    DecimalFormat tiempo = new DecimalFormat("0.###");
        
    public MSApriori(){
            System.out.println("Iniciando el algoritmo MSApriori");
            double LS, B;
            String archivo=null;
            
            System.out.println("Ingrese el valor para el calculo del soporte especial (B), el valor debe estar entre 0 y 1");
            B=ingresarSoporte();
            
            System.out.println("Ingrese el valor del soporte general a utilizar (LS), el valor debe estar entre 0 y 1");
            LS=ingresarSoporte();
            
            archivo=archivo();
            leertxt(archivo,LS,B);
            
            long Inicio = System.currentTimeMillis();
            
            tablafinal(LS,B);
            
            long Fin= System.currentTimeMillis();
            imprimirResultadoFinal();
            System.out.println("\nTiempo de ejecución: ");            
            System.out.println(((Fin-Inicio))+" Milisegundos");
            System.out.println(tiempo.format((Fin-Inicio)*1.0/1000.0)+" Segundos");
    }
    
    public String archivo(){
        String archivo=null;
        System.out.println("Ingrese el nombre del archivo a ingresar");
        do{
            try {
                    archivo = reader.next();
                }catch (InputMismatchException ime){
                    reader.next();
                }
        }while (archivo==null);
        if (archivo.contains(".txt")==false)
            archivo+=".txt";
        return archivo;
    }
    
    public void leertxt(String archivo, double LS, double B){
        String texto=null;
        try{
            BufferedReader bf = new BufferedReader(new FileReader(archivo));
            String temp =null;
            String bfRead;    
            while((bfRead = bf.readLine()) != null){
                if (temp==null) temp=bfRead;
                else temp+=bfRead;  
            }
            texto=temp;
        }catch(Exception e){
           System.out.println("No se encontro archivo");
           System.exit(0);
        }
        
        
        String aux=null;
        
        int largo=2; //Representa el largo de las palabras en este caso 2 por aA
        int periodo=periodo();//Representa el largo de las combinaciones por ejemplo aAbB
        int auxper=0;//auxiliar para ver si cumple el periodo
        Vector<String> prueba = new Vector();
        
        for(int i=0;i<texto.length();i=i+largo){
               for(int j=0;j<largo;j++){
                   if (aux==null) aux=String.valueOf(texto.charAt(i+j));
                   else aux+= texto.charAt(i+j);
               }
               prueba.add(aux);
               auxper+=1;
               aux=null;
               if (auxper==periodo){
                   tablas.add(new TablaMSApriori (new Vector<String>(prueba),B,LS,0));
                   System.out.println("Cadena ingresada: "+prueba);
                   prueba.clear();
                   auxper=0;
               }
        }
    }
    
    public int periodo(){
        int periodo=0;
        System.out.println("Ingrese el periodo, debe ser un valor natural");
        do{
            try {
                periodo = reader.nextInt();
            }catch (InputMismatchException ime){
                reader.next();
            }
            if (periodo<=0) System.out.println("Error, solo se aceptan números naturales");
        } while(periodo<=0);
        return periodo;
    }
    
    public void tablafinal(double LS, double B){        

        System.out.println("\nCalculando...");
        
        Vector <String> auxiliar= new Vector();
        Vector <String> auxiliar2= new Vector();
        for (int i=0; i<tablas.size(); i++){
            for (int j=0; j<tablas.get(i).patron.size(); j++){
                if (existeValor( tablas.get(i).patron.get(j), auxiliar)==false){
                    auxiliar.add(new String (tablas.get(i).patron.get(j)));
                    auxiliar2.add(new String (tablas.get(i).patron.get(j)));
                    ingresarMIS(auxiliar2,LS,B);
                    auxiliar2.clear();
                }
            }
        }
        if (general.isEmpty()) return;
        combinatoria(LS,B);
    }
    
    public boolean existeValor(String valor, Vector<String> lista){
        for (int i=0; i<lista.size();i++){
            if (valor.equals(lista.get(i))) return true;
        }
        return false;
    } 
    
    public void combinatoria(double LS, double B){
        
        Vector<TablaMSApriori> original = new Vector(general);
        int max=tamanoMaximo();
        
        int contador=0;
        
        for (int tamano=0; tamano<(max-1) ;tamano++){
            int ayuda=general.size();
            for(int i=contador; i<ayuda;i++){
                for(int j=0; j<original.size();j++){
                    
                    Vector<String> auxiliar = new Vector();
                    for (int k=0;k<general.get(i).patron.size();k++){
                        auxiliar.add(general.get(i).patron.get(k));
                    }
                    
                    for (int k=0;k<original.get(j).patron.size();k++){
                        auxiliar.add(original.get(j).patron.get(k));
                        //System.out.println("Valor del auxiliar "+auxiliar);
                        if (noFrecuente(auxiliar)==false)
                            if (esPatronValido(auxiliar, LS, B)==true)
                                ingresarMIS(auxiliar, LS, B);
                    }       
                }
                contador=ayuda;
            }
        }  
    }
    
    public boolean esPatronValido (Vector<String> cadena, double LS, double B){
        //System.out.println("Entrada a comparacion de subpatron");
        //System.out.println("Valor de la cadena a comparar "+cadena);
        for (int i=0; i<tablas.size(); i++){
            //System.out.println("Comparando si "+cadena+" es sub patron de patron nro "+(i+1)+" : "+tablas.get(i).patron);
            for (int j=0; j<tablas.get(i).patron.size(); j++){
                if (cadena.get(0).equals(tablas.get(i).patron.get(j))){
                    //System.out.println("El elemento "+cadena.get(0)+" se encontro en el patron "+tablas.get(i).patron+" en el puesto "+(j+1));
                    int contador=1;
                    if (j+cadena.size()<=tablas.get(i).patron.size()){
                        for (int k=1; k<cadena.size();k++){
                            //System.out.println("¿Es ["+cadena.get(k)+"] posicion "+(k+1)+" en "+cadena+" igual a ["+tablas.get(i).patron.get(j+k)+"] posicion "+(k+j+1)+" en "+tablas.get(i).patron+"?");
                            if (cadena.get(k).equals(tablas.get(i).patron.get(j+k))) {
                                //System.out.println("Si lo es");
                                contador++;
                            }
                            else {
                                //System.out.println("No lo es");
                                k=cadena.size();
                            }
                        }
                    }
                    if (contador==cadena.size()) {
                        //System.out.println("La cadena "+cadena+" es subpatron de "+tablas.get(i).patron);
                        return true;
                    }
                }
            }
        }
        //System.out.println("La cadena "+cadena+" no es subpatron de ninguna cadena ingresada");
        nofrecuente.add(new TablaMSApriori (new Vector<String>(cadena),B,LS,0));
        return false;
    }

    public boolean noFrecuente (Vector<String> cadena){
        //System.out.println("Entrada a comparacion de subpatron");
        //System.out.println("Valor de la cadena a consultar si tiene un subpatron que no es frecuente  "+cadena);
        for (int i=0; i<nofrecuente.size(); i++){
            //System.out.println("Comparando si "+nofrecuente.get(i).patron+" es subpatron de : "+cadena);
            for (int j=0; j<cadena.size(); j++){
                if (nofrecuente.get(i).patron.get(0).equals(cadena.get(j))){
                    //System.out.println("El elemento "+nofrecuente.get(i).patron.get(0)+" se encontro en el puesto "+(j+1)+" de "+cadena);
                    int contador=1;
                    if (j+nofrecuente.get(i).patron.size()<=cadena.size()){
                        for (int k=1; k<nofrecuente.get(i).patron.size();k++){
                            //System.out.println("¿Es ["+nofrecuente.get(i).patron.get(k)+"] posicion "+(k+1)+" en "+nofrecuente.get(i).patron+" igual a ["+cadena.get(j+k)+"] posicion "+(k+j+1)+" en "+cadena+"?");
                            if (nofrecuente.get(i).patron.get(k).equals(cadena.get(j+k))) {
                                //System.out.println("Si lo es");
                                contador++;
                            }
                            else {
                                //System.out.println("No lo es");
                                k=cadena.size();
                            }
                        }
                    }
                    if (contador==nofrecuente.get(i).patron.size()) {
                        //System.out.println("La cadena no frecuente "+nofrecuente.get(i).patron+" es subpatron de "+cadena+", asi que se descarta");
                        return true;
                    }
                }
            }
        }
        return false;
    }
   
    public void ingresarMIS(Vector <String> cadena, double LS, double B){
        int frecuencia=calcularFrecuencia(cadena);
        //if (frecuencia!=0) System.out.println("El patron "+cadena+" tiene una frecuencia de "+frecuencia+" ("+porciento.format(frecuencia*100.00/tablas.size())+"%)");
        double MIS=calcularMIS(LS,frecuencia,B);
        if (frecuencia*1.00/tablas.size()>=MIS){
            general.add(new TablaMSApriori (new Vector<String>(cadena),B,LS,MIS));
            resultado.add(new TablaFinal (new Vector<String>(cadena),MIS,frecuencia));
        }
    }
    
    public int calcularFrecuencia (Vector<String> cadena){
        //System.out.println("Entrada a comparacion de subpatron");
        //System.out.println("Valor de la cadena a comparar "+cadena);
        int frecuencia=0;
        for (int i=0; i<tablas.size(); i++){
            //System.out.println("Comparando si "+cadena+" es sub patron de patron nro "+(i+1)+" : "+tablas.get(i).patron);
            for (int j=0; j<tablas.get(i).patron.size(); j++){
                if (cadena.get(0).equals(tablas.get(i).patron.get(j))){
                    //System.out.println("El elemento "+cadena.get(0)+" se encontro en el patron "+tablas.get(i).patron+" en el puesto "+(j+1));
                    int contador=1;
                    if (j+cadena.size()<=tablas.get(i).patron.size()){
                        for (int k=1; k<cadena.size();k++){
                            //System.out.println("¿Es ["+cadena.get(k)+"] posicion "+(k+1)+" en "+cadena+" igual a ["+tablas.get(i).patron.get(j+k)+"] posicion "+(k+j+1)+" en "+tablas.get(i).patron+"?");
                            if (cadena.get(k).equals(tablas.get(i).patron.get(j+k))) {
                                //System.out.println("Si lo es");
                                contador++;
                            }
                            else {
                                //System.out.println("No lo es");
                                k=cadena.size();
                            }
                        }
                    }
                    if (contador==cadena.size()) {
                        //System.out.println("Se encontro "+cadena+" en "+tablas.get(i).patron);
                        frecuencia++;
                        j=tablas.get(i).patron.size();
                    }
                }
            }
        }
        //System.out.println("El valor "+cadena+"tiene una frecuencia de "+frecuencia);
        return frecuencia;
    }
    
    public double calcularMIS(double LS, int frecuencia, double B){
        if (LS > frecuencia*B/tablas.size())
            return LS;
        else 
            return frecuencia*B/tablas.size();
    }
    
    public int tamanoMaximo(){
        int max=0;
        for (int i=0; i<tablas.size();i++)
            if (tablas.get(i).patron.size()>max) max=tablas.get(i).patron.size();
        return max;
    }
    
    public double ingresarSoporte(){
        double soporte=-1;
        do{
            try {
                    soporte = reader.nextDouble();
                }catch (InputMismatchException ime){
                    reader.next();
                }
            if (soporte<0 || soporte>1) System.out.println("Error, ingrese un numero entre 0 y 1");
        } while (soporte<0 || soporte>1);
        return soporte;  
    }
    
    public void imprimirResultadoFinal(){
        if (resultado.size()>0){
            System.out.println("\nLos patrones frecuentes son :");
            for (int i=0; i<resultado.size();i++){
                System.out.print("Patron :"+resultado.get(i).transaccion);
                System.out.print(", con soporte : "+porciento.format(resultado.get(i).MIS));
                System.out.println(", y frecuencia : "+resultado.get(i).frecuencia+" ("+porciento.format(resultado.get(i).frecuencia*100.00/tablas.size())+"%)");
            }
        }
        else{
            System.out.println("No se encontraron patrones frecuentes");
        }
    }
    
}