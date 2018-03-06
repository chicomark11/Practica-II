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
public class Nodo {
	String item;
	int count = 1;

	Nodo padre = null;
	List<Nodo> hijos = new ArrayList<Nodo>();
	Nodo link_sig = null;

    public Nodo(){ }

    public Nodo getHijoConItem(String item) {

		for(Nodo hijo : hijos){

			if(hijo.item.equals(item)==true){

				return hijo;
			}
		}
		return null;
	}

}
