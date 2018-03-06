/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpgrowth;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Random; 

/**
 *
 * @author pablo
 */
public class Main {
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Algoritmo algoritmo = new Algoritmo();
        int soporte=0, total=0, periodo=0;
        List<String> prueba=new ArrayList<>();
        
        funcion funcion = new funcion();
       int p=2;
           String aux="";
           String text=leertxt("C:\\Users\\pablo\\Desktop\\Proyecto\\txt.txt");
           for(int i=0;i<text.length();i=i+p){
               for(int j=0;j<p;j++){
                   aux=aux + text.charAt(i+j);
               }
               prueba.add(new String(aux));
               aux="";
           }
                   
          
        
       /* prueba.add("aA");
        prueba.add("bB");
        //0 para simular el salto de linea
        prueba.add("cC");
        prueba.add("aA");
   
        prueba.add("bB");
        prueba.add("dD");

        prueba.add("aA");
        prueba.add("bB");

        prueba.add("cC");
        prueba.add("aA");

        prueba.add("bB");
        prueba.add("dD");

        prueba.add("aA");
        prueba.add("bB");

        prueba.add("cC");
        prueba.add("aA");

        prueba.add("bB");
        prueba.add("cC");

        prueba.add("aA");
        prueba.add("bB");

                prueba.add("cC");
        prueba.add("aA");
               prueba.add("bB");
        prueba.add("dD");*/
        
        /*prueba.add("1");
        prueba.add("2");
        prueba.add("0"); //0 para simular el salto de linea
        prueba.add("1");
        prueba.add("3");
        prueba.add("0");
        prueba.add("1");
        prueba.add("5");
        prueba.add("4");
        prueba.add("0");
        prueba.add("3");
        prueba.add("0");
        prueba.add("1");
        prueba.add("2");
        prueba.add("3");
        prueba.add("0");
        prueba.add("3");
        prueba.add("2");
        prueba.add("0");
        prueba.add("4");
        prueba.add("5");
        prueba.add("2");
        prueba.add("1");
        */
         /*     prueba.add("1");
        prueba.add("5");
        prueba.add("4");
         prueba.add("2");

                      prueba.add("1");
        prueba.add("5");
        prueba.add("4");
         prueba.add("2");

                      prueba.add("1");
        prueba.add("5");
        prueba.add("4");
         prueba.add("2");
  
                      prueba.add("1");
        prueba.add("5");
        prueba.add("4");
         prueba.add("2");
      */
        
        //prueba=ingresarValores();
        //imprimirLista(prueba);
         System.out.println("elementos ingresados");
        System.out.println(prueba);
        soporte=ingresarSoporte();
        periodo=ingresarPeriodo();
        //total=totalDatos(prueba);
        total=prueba.size()/periodo;
        long inicio = System.currentTimeMillis();
        

        algoritmo.CorrerAlgoritmo(prueba, soporte, total, periodo);
        long fin = System.currentTimeMillis();
        System.out.println((fin-inicio)+"   milisegundos");
        //funcion.patrones();
        //funcion.Combinaciones();
        //funcion.mostrarPatrones();
        //funcion.ResultadoFinal();
    //    for(int i=0; i < prueba.size(); i++){
      //  System.out.println(prueba.get(i));
        //}
        // TODO code application logic here
    
      
    
    }
    /*
    public static List ingresarValores(){
        Scanner reader = new Scanner(System.in);
        int i=0; 
        String j="";
        List<String> lista =new ArrayList<>();
        List<String> aux =new ArrayList<>();
        do {		
            System.out.println ("Ingrese la cantidad de patrones a ingresar");
            try {
                i = reader.nextInt();
            }catch (InputMismatchException ime){
                reader.next();
            }
            if (i<=0) System.out.println("¡Error! Solo puedes insertar números enteros. ");
        }while(i<=0);
        String tecla=null;
        System.out.println ("Quieres que los numeros se ingresen aleatoriamente? ej: valores entre 1 y 20\ns)Si\nn)No");
        tecla = reader.nextLine(); tecla = reader.nextLine();
        if (!tecla.equals("s") && !tecla.equals("n")){
            do{
                System.out.println("¡Error! Solo puedes ingresar S o N\nQuieres que los numeros se ingresen aleatoriamente? ej: valores entre 1 y 20\ns)Si\nn)No");
                tecla = reader.nextLine();
            }while(!tecla.equals("s") && !tecla.equals("n")); 
        }
        if (tecla.equals("n")){
            for (int k=0; k<i; k++){
                System.out.println("Ingresando el patron nro "+(k+1));
                System.out.println("Puede ingresar valores hasta ingresar 0");
                do {			
                    try {
                        j = reader.next();
                        if (estaEnLista(aux,j)==false) {
                            lista.add(j);
                            aux.add(j);
                        }
                        else 
                            System.out.println("El valor no se puede repetir");
                    }catch (InputMismatchException ime){
                        System.out.println("¡Error! Solo puedes insertar números enteros.");
                        reader.next();
                    }
                } while (j!="0");
            aux.clear();
            }
        return (lista);
        }
        for (int k=0; k<i; k++){
            int flag=0;//bandera usada para tener 1 numero minimo
            Random random = new Random();
            do{
                if (flag==0){
                    j = random.toString(20)+1;
                    
                    lista.add(j);
                    aux.add(j);
                    flag=1;
                }
                else{
                    j = random.nextInt(20);
                    if (estaEnLista(aux,j)==false) {
                            lista.add(j);
                            aux.add(j);
                    }
                }
            } while (j!=0);
            aux.clear();
        }
        return (lista);        
    };*/
    
    public static void imprimirLista(List<String> lista){
        System.out.println("\nLos valores ingresados son: \n");
        int aux=lista.size();
        for (int i=0; i<aux; i++){
            if (i==0 || lista.get(i-1)=="0") System.out.print("["+lista.get(i));
            else
                if (lista.get(i)!="0" || i==aux-1)
                    System.out.print(","+lista.get(i));
                else
                    System.out.println("]");            
        }
    };
    
    public static int ingresarSoporte(){
        int a=0;
        Scanner reader = new Scanner(System.in);
        System.out.println("\nIngrese el valor del soporte");
        do{
            try {
                    a = reader.nextInt();
                }catch (InputMismatchException ime){
                    reader.next();
                }
            if (a<=0) System.out.println("¡Error! Solo puedes insertar números enteros");
        } while (a<=0);
        return a;  
    };
    
       public static int ingresarPeriodo(){
        int a=0;
        Scanner reader = new Scanner(System.in);
        System.out.println("\nIngrese el valor del periodo");
        do{
            try {
                    a = reader.nextInt();
                }catch (InputMismatchException ime){
                    reader.next();
                }
            if (a<=0) System.out.println("¡Error! Solo puedes insertar números enteros");
        } while (a<=0);
        return a;  
    };
    
    public static int totalDatos(List<String> lista){
        int aux=lista.size(), contador=0;
        for (int i=0; i<aux; i++){
            if (lista.get(i)=="0") contador++;
        }
        return contador;
    };
    
            public static  String  leertxt(String direccion){
        String texto="";
        try{
            BufferedReader bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;    
            while((bfRead = bf.readLine()) != null){
              temp = temp + bfRead;  
            }
            texto=temp;
        }catch(Exception e){
           System.err.println("No se encontro archivo");
        }
     return texto;
    };
        
    public static boolean estaEnLista(List<String> a, String value) {
        for (int i = 0; i < a.size(); i++) {
            if(value == a.get(i)) {
                return true;
            }
        }
        return false;
    }
       
}