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
public class TablaMSApriori {
    Vector <String> patron;
    double B;
    double LS;
    double MIS;

    public TablaMSApriori (){ 
        this.patron=null;
        this.B=0;
        this.LS=0;
        this.MIS=0;
    }
    
    public TablaMSApriori (Vector<String> patron, double B, double LS, double MIS){ 
        this.patron=patron;
        this.B=B;
        this.LS=LS;
        this.MIS=MIS;
    }
    
    public TablaMSApriori (TablaMSApriori clon){
        this.patron=patron;
        this.B=B;
        this.LS=LS;
        this.MIS=MIS;
    }
}