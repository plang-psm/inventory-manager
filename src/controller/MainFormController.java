/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

/**
 * Main form controller class. This class is the main form.
    G2.) A compatible feature suitable to your application that would extend functionality to the next version if you were to update the application
        
        -A feature that I would love to update is removing the search button and have the the search text field search for the part/product name as the user inputs text.
            I believe it would create a much more user friendly experience for anyone using the Inventory Management System.
 * @author PhillipSanchez
 */
public class MainFormController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInventoryCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableView<Product> proTableView;

    @FXML
    private TableColumn<Product, Integer> proIdCol;

    @FXML
    private TableColumn<Product, String> proNameCol;

    @FXML
    private TableColumn<Product, Integer> proInventoryCol;
    
    @FXML
    private TableColumn<Product, Double> proPriceCol;
    @FXML
    private TextField partSearchTxt;
    @FXML
    private TextField productSearchTxt;
    
    /** Searches for parts, clears when text field is empty, and alerts user if part name not found. */
    @FXML
    void onActionPartSearchBtn(ActionEvent event) {
        
        String search = partSearchTxt.getText();
        
        ObservableList<Part> parts = searchByPartName(search);
        try{
            if(parts.size() == 0){

                int id = Integer.parseInt(search);
                Part part = getPartWithId(id);
                
                if(part != null)
                    parts.add(part);
                
                if(part == null){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error Dialog.");
                    alert.setContentText("Part ID not found");
                    alert.showAndWait();  
                }
            }
        } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog.");
                alert.setContentText("Part name not found");
                alert.showAndWait();  
    }
        partTableView.setItems(parts);
        
    }
    /** Loops through part name. Returns if found in search text field or returns error message if not found-- see onActionPartSearchBtn. 
     *  @param partialName Passes part name
     *  @return namedParts Returns named parts related to part name.
     */
    private ObservableList<Part> searchByPartName(String partialName){
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        
        ObservableList<Part> allParts = Inventory.getAllParts();
        
        for(Part part : allParts){
            if (part.getName().toLowerCase().contains(partialName.toLowerCase())){
                namedParts.add(part);
            }
        }
        
        return namedParts;
    }
    /** Loops through part ID. Returns if found in search text field or returns error message if not found-- see onActionPartSearchBtn. 
     *  @param id Passes part id
     *  @return part Returns id parts related to part id.
     */
    private Part getPartWithId(int id){
        ObservableList<Part> allParts = Inventory.getAllParts();
 
        for(Part part : allParts){
            if(part.getId() == id)
                return part;
        }
        return null;
    }
    
    /** Searches for products, clears when text field is empty, and alerts user if product name not found. */
    @FXML
    void onActionProductSearchBtn(ActionEvent event) {
        
        String search = productSearchTxt.getText();
        
        ObservableList<Product> products = searchByProdName(search);
        try{
            if(products.size() == 0){

                int id = Integer.parseInt(search);
                Product product = getProdWithId(id);
                if(product != null)
                    products.add(product);
                if(product == null){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error Dialog.");
                    alert.setContentText("Product ID not found");
                    alert.showAndWait();  
                }
            }
        }catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog.");
                alert.setContentText("Product name not found");
                alert.showAndWait();  
    }
        proTableView.setItems(products);
        
    }
    
    /** Loops through product name. Returns if found in search text field or returns error message if not found-- see onActionPartSearchBtn. 
     *  @param partialName partialName passes the product's name
     * @return namedProducts Returns named product related to product name.
     */
    private ObservableList<Product> searchByProdName(String partialName){
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();
        
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        
        for(Product product : allProducts){
            if (product.getName().toLowerCase().contains(partialName.toLowerCase())){
                namedProducts.add(product);
            }
        }
        
        return namedProducts;
    }
    
    /** Loops through product ID. Returns if found in search text field or returns error message if not found-- see onActionPartSearchBtn. 
     *  @param id id passes the product's ID
     *  @return product Returns id product related to product id.
     */
    private Product getProdWithId(int id){
        ObservableList<Product> allProducts = Inventory.getAllProducts();
 
        for(Product product : allProducts){
            if(product.getId() == id)
                return product;
        }
        return null;
    }

    /** Closes the application. */
    @FXML
    void onActionCloseTheApplication(ActionEvent event) 
    {
        System.exit(0);
    }
    
    /** Allows the user to delete parts from the part table. */
    @FXML
    void onActionDeleteFromParts(ActionEvent event) 
    {
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete? All data will be erased .");
       Optional<ButtonType> result = alert.showAndWait();
       
       if(result.isPresent() && result.get() == ButtonType.OK)
       {
                ObservableList<Part> selectedRows, allParts;
                allParts = partTableView.getItems();

                selectedRows = partTableView.getSelectionModel().getSelectedItems();

                for(Part part : selectedRows){
                    allParts.remove(part);
                }
        }
            
    }
    
    Product product;
    
    /** Allows the user to delete products from the product table.
        An error will display if the product is associated with a part. 
    */
    @FXML
    void onActionDeleteFromProductsForm(ActionEvent event) 
    {
        Product selectedRow = (Product)proTableView.getSelectionModel().getSelectedItem();
        
        for(Product product : Inventory.getAllProducts())    
            
            if (product.getAllAssociatedParts().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete? All data will be erased .");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK)
                    Inventory.deleteProduct(selectedRow);
                
            }else if (!(product.getAllAssociatedParts().isEmpty())){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog.");
                alert.setContentText("Product with associated parts cannot be deleted. Please remove parts from product.");
                alert.showAndWait();
            }
        }
    
    /** Leads the user to the add parts form. */
    @FXML
    void onActionDisplayAddPartsForm(ActionEvent event) throws IOException 
    {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    /** Leads the user to the add products form. */
    @FXML
    void onActionDisplayAddProductsForm(ActionEvent event) throws IOException 
    {
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        
    }
    
    /** Leads the user to the modify parts form. Transfers parts data the modify form screen. */
    @FXML
    void onActionDisplayModifyPartsForm(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyPartForm.fxml"));
        loader.load();
        
    
        ModifyPartFormController MPFController = loader.getController();
        MPFController.transferPart(partTableView.getSelectionModel().getSelectedItem());
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Leads the user and transfers product data to the modify products form. */
    @FXML
    void onActionDisplayModifyProductsForm(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyProductForm.fxml"));
        loader.load();
        
        ModifyProductFormController MPRFController = loader.getController();
        MPRFController.transferProduct(proTableView.getSelectionModel().getSelectedItem());
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

     /**
     * Initializes the controller class.
     * @param url Passes URL.
     * @param rb Passes RB.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        /** Sets data into the parts table. */
        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        /** Sets data into the products table. */
        proTableView.setItems(Inventory.getAllProducts());
        proIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        proNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        proInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        proPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
    }    
    
}
