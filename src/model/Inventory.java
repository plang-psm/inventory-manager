/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory class. Holds methods for parts and products.
 * @author PhillipSanchez
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** This method adds parts. 
     * @param part Passes part to add.
     */
    public static void addPart(Part part){
        allParts.add(part);
    }
    
    /** This method adds products. 
     * @param product Passes product to add.
     */
    public static void addProduct(Product product){
        allProducts.add(product);
    }
    
    /** This method looks up parts by part IDs. 
     * @param partId Passes the part IDs.
     * @return part Returns part if matched.
     */
    public static Part lookUpPart(int partId){
        for(Part part : Inventory.getAllParts())
        {
            if(part.getId() == partId)
                return part;
        } return null;
    }
    
    /** This method looks up products by product IDs. 
     * @param productId Passes the product IDs.
     * @return product Returns product if matched.
     */
    public static Product lookUpProduct(int productId){
        for(Product product : Inventory.getAllProducts())
        {
            if(product.getId() == productId)
                return product;
        } return null;
    }
    
    /** This method is an Observable List that looks up parts by part names. 
     * @param partName Passes the part name.
     * @return part Returns part if matched.
     */
    public static ObservableList<Part> lookUpPart(String partName){
        for(Part part : Inventory.getAllParts())
        {
            if(part.getName() == partName)
                return (ObservableList<Part>) part;
        }return null;
    }
    
    /** This method is an Observable List that looks up products by product names. 
     * @param productName Passes the product name.
     * @return product Returns product if matched.
     */
    
    public static ObservableList<Product> lookUpProduct(String productName){
        for(Product product : Inventory.getAllProducts())
        {
            if(product.getName() == productName)
                return (ObservableList<Product>) product;
        }return null;
    }
    
    /** This method updates parts by part selection. 
     * @param index Passes index.
     * @param selectedPart Passes selected part.
     */
    public static void updatePart(int index, Part selectedPart){
        Inventory.getAllParts().set(index, selectedPart);
    }
    
    /** This method updates products by product selection.
     * @param index Passes index.
     * @param selectedProduct Passes selected product.
     */
    public static void updateProduct(int index, Product selectedProduct){
        Inventory.getAllProducts().set(index, selectedProduct);
    }
    
    /** This method deletes parts by part selection.
     * @param selectedPart  Passes the selected part.
     * @return Inventory.getAllParts().remove(selectedPart) Returns the removal of the selected part.
     */
    public static boolean deletePart(Part selectedPart){
        
        for(Part part : Inventory.getAllParts()){
            if(part == selectedPart){
                return Inventory.getAllParts().remove(selectedPart);
            }
        } return false;
    }
    
    /** This method deletes products by product selection. 
     * @param selectedProduct Passes selected product.
     * @return Inventory.getAllProducts().remove(selectedPart) Returns the removal of the selected product.
     */
    public static boolean deleteProduct(Product selectedProduct){
        
        for(Product product : Inventory.getAllProducts()){
            if(product == selectedProduct){
                return Inventory.getAllProducts().remove(selectedProduct);
            }
        }return false;
    }
    
    /** This method gets all products.
     * @return allProducts Returns all products.
    */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
    
    /** This method gets all parts. 
    * @return allParts Returns all parts.
    */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
  
    
}
