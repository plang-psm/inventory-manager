/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * OutSoruced Class. This class extends part with Company Name values.
 * @author PhillipSanchez
 */
public class Outsourced extends Part{
    
    private String companyName;
    
     /** This method constructs OutSourced. 
     * @param id Passes Id.
     * @param name Passes name.
     * @param price Passes price.
     * @param stock Passes stock.
     * @param min Passes min.
     * @param max Passes max.
     * @param companyName Passes company name.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** This method gets company name. 
     * @return companyName Returns company name.
     */
    public String getCompanyName() {
        return companyName;
    }

     /** This method sets company name. 
     * @param companyName Passes company name.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
}
