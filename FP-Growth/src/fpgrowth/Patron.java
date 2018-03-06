/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpgrowth;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pablo
 */
public class Patron {
    List<String> items = new ArrayList<>();
    int count=0;
    
public Patron(){
    this.items=null;
    this.count=0;
}

public Patron(List<String> items, int count){
    this.items=items;
    this.count=count;
    
}
}
