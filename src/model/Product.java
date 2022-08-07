/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Product class. This class contains product values.
 *
 * @author PhillipSanchez
 */
public class Product {
    
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    
    /** This method constructs products.
     * @param id Passes Id.
     * @param name Passes name.
     * @param price Passes price.
     * @param stock Passes stock.
     * @param min Passes min.
     * @param max Passes max.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    
    /** This method gets Id.
     * @return id Returns id.
     */
    public int getId() {
        return id;
    }
    
    /** This method sets Id.
     * @param id Passes id.
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /** This method gets name.
     * @return name Returns name.
     */
    public String getName() {
        return name;
    }
    
    /** This method sets name.
     * @param name Passes name.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /** This method gets Price.
     * @return price Returns price.
     */
    public double getPrice() {
        return price;
    }
    
    /** This method sets price.
     * @param price Passes price.
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /** This method gets stock.
     * @return stock Returns stock.
     */
    public int getStock() {
        return stock;
    }
    
    /** This method sets stock.
     * @param stock Passes stock.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    /** This method gets min.
     * @return min Returns min.
     */
    public int getMin() {
        return min;
    }
    
    /** This method sets min.
     * @param min Passes min.
     */
    public void setMin(int min) {
        this.min = min;
    }
    
    /** This method gets max.
     * @return max Returns max.
     */
    public int getMax() {
        return max;
    }
    /** This method sets max.
     * @param max Passes max.
     */
    public void setMax(int max) {
        this.max = max;
    }
    
    /** This method adds associated parts.
     * @param part Passes part.
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    
     /** This method selects associated parts.
     * @param selectedAssociatedPart Passes selected associated parts.
     */
    public void deleteAssociatedPart(Part selectedAssociatedPart){
        associatedParts.remove(selectedAssociatedPart);
    }
    
     /** This method gets all associated parts.
     * @return associtatedParts Returns associated parts.
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
    
}
