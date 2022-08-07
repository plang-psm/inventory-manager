/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * InHouse Class. This class extends part with Machine Id values.
 * @author PhillipSanchez
 */
public class InHouse extends Part{
    
    private int machineId;
    
    /** This method constructs InHouse.
     * @param id Passes Id.
     * @param name Passes name.
     * @param price Passes price.
     * @param stock Passes stock.
     * @param min Passes min.
     * @param max Passes max.
     * @param machineId Passes machine id.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
    /** This method gets the Machine Id.
     * @return machineId Returns the machine id.
     */
    public int getMachineId() {
        return machineId;
    }
    
    /** This method sets the machine Id. 
     * @param machineId Passes machine id.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
    
}
