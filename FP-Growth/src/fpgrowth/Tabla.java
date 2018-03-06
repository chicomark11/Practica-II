package fpgrowth;

import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pablo
 */
public class Tabla{
  String item = "";
  int count = 0;
  Nodo link = null;
  Nodo ultimo_link = null;
  

 public Tabla(){ 

        this.item="0";
        this.count=0;
        this.link=null;
        this.ultimo_link=null;
 }
 
     public Tabla(String item, int count, Nodo link, Nodo ultimo_link){
        this.item=item;
        this.count=count;
        this.link=link;
        this.ultimo_link=ultimo_link;
    }

}
