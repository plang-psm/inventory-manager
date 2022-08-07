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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

/**
 * FXML Controller class - Modify product controller class. Allows user to modify products.           
    G1. a detailed description of a logical or runtime error that you corrected in the code 
        and a detailed description of how you corrected it above the line of code you are referring to
            
            -A logical error I corrected was the code below. I struggled to grab the data from the associated table saved 
            in the the add product form and continuously maintain it once saved in the modify product form. At first,
            the program would run but I was unable to gather any data from the associated parts table. Once I was able
            successfully transfer the data from the table, I faced two new errors. I struggled to consistently maintain 
            the data after modifying the product multiple times--see example below or the product duplicated on the main
            form table view once saved.
            
            IE: If I modified the product once, it would update. But if I modified the product a second time, my associated
            parts would no longer appear in the associated parts table. 
            
            This led me to create a new product and create an ObservableList named "bottomParts" that stored the associated
            parts attached to the original product. I would then get the data from bottomParts and add them to the new product 
            through a for loop. This for loop grabbed all elements stored in bottomParts(the original parts associated values)
            and associated them to the newProduct. Once the new product was associated to the previous products associated values, 
            I added the new product to the Inventory and removed the old product from the Inventory to avoid duplication.

 *
 * @author PhillipSanchez
 */
public class ModifyProductFormController implements Initializable {
   
    Stage stage;
    Parent scene;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField inventoryTxt;

    @FXML
    private TextField priceTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField minTxt;

    @FXML
    private TextField searchTxt;

    @FXML
    private TableView<Part> partTableView;;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInventoryCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableView<Part> asscTableView;

    @FXML
    private TableColumn<Part, Integer> asscIdCol;

    @FXML
    private TableColumn<Part, String> asscNameCol;

    @FXML
    private TableColumn<Part, Integer> asscInventoryCol;

    @FXML
    private TableColumn<Part, Double> asscPriceCol;

    private ObservableList<Part> bottomParts = FXCollections.observableArrayList();
    
    Product product1;
    
    @FXML
    private GridPane inhouseButton;
    
    /** Adds part to associated table. */
    @FXML
    void onActionAddPart(ActionEvent event) {
        Part part = partTableView.getSelectionModel().getSelectedItem();
        product1.addAssociatedPart(part);
        asscTableView.setItems(product1.getAllAssociatedParts());
    }
    
    /** Searches for parts, clears when text field is empty, and alerts user if part name not found. */
    @FXML
    void onActionSearchBtn(ActionEvent event) {

        String search = searchTxt.getText();
        
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
        }catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog.");
                alert.setContentText("Part name not found");
                alert.showAndWait();  
    }
        partTableView.setItems(parts);
        
    }
    
    /** Loops through part name. Returns if found in search text field or returns error message if not found-- see onActionSearchBtn. 
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
    /** Loops through part ID. Returns if found in search text field or returns error message if not found-- see onActionSearchBtn. 
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
    
    /** Returns user back to main form. */
    @FXML
    void onActionBackToMainForm(ActionEvent event) throws IOException {
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all textfield values, do you want to continue?");
       
       Optional<ButtonType> result = alert.showAndWait();
       if(result.isPresent() && result.get() == ButtonType.OK)
       {
       stage = (Stage)((Button)event.getSource()).getScene().getWindow();
       scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
       stage.setScene(new Scene(scene));
       stage.show();
       }
    }
    
    /** Allows the user to remove any of the product's associated items. */
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {
        Part part = asscTableView.getSelectionModel().getSelectedItem();
        product1.deleteAssociatedPart(part);
        asscTableView.setItems(product1.getAllAssociatedParts());
    }
    
    /** This saves the add products.
              Gets and saves the users input data.
              Checks if user selected InHouse or OutSourced.
              Checks for max > min.
              Checks for min < stock < max.
              Checks if forms filled correctly.
              If min > max or min > stock > max user notified if done incorrectly.
              Saves any modification product and its associated items. Directed back to the Main Form once complete.
    */
    @FXML
    void onActionSaveAddProducts(ActionEvent event) throws IOException {

        try {
                int id = Integer.parseInt(idTxt.getText());
                String name = nameTxt.getText();
                double price = Double.parseDouble(priceTxt.getText());
                int stock = Integer.parseInt(inventoryTxt.getText());
                int min = Integer.parseInt(minTxt.getText());
                int max = Integer.parseInt(maxTxt.getText());
            
            if(name.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING, "Name cannot be empty.");

                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK)
                {
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/ModifyProductController.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
                }
            }    
            if(min > max){  
                Alert alert = new Alert(Alert.AlertType.WARNING, "Min cannot be greater than max.");

                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK)
                {
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/ModifyProductController.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
                }
            }
            else if(min > stock) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Min cannot be greater than stock.");

                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK)
                {
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/ModifyProductController.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
                }
            }
            else if(stock > max){
                Alert alert = new Alert(Alert.AlertType.WARNING, "Stock cannot be greater than max.");

                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK)
                {
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/ModifyProductController.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
                }
                } else { 

                    bottomParts = asscTableView.getItems();
                    Product newProduct = new Product(id, name, price, stock, min, max);
                    for (int i = 0; i < bottomParts.size(); i++) {
                        newProduct.addAssociatedPart(bottomParts.get(i)); 
                        }

                        Inventory.getAllProducts().add(newProduct);
                        Inventory.getAllProducts().remove(product1);
                }
            } catch(NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog.");
                alert.setContentText("Invalid - Add Part failed due to incorrect values.");
                alert.showAndWait();
        }
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
    }
        /** Transfers data from previous screens. 
         *  @param product Passes product through the method
         */
        public void transferProduct(Product product){
        
        this.product1 = product;
       
        idTxt.setText(String.valueOf(product1.getId()));
        nameTxt.setText(product1.getName());
        priceTxt.setText(String.valueOf(product1.getPrice()));
        inventoryTxt.setText(String.valueOf(product1.getStock()));
        maxTxt.setText(String.valueOf(product1.getMax()));
        minTxt.setText(String.valueOf(product1.getMin()));  
        asscTableView.setItems(product1.getAllAssociatedParts());

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
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("inventory"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        /** Sets associated data to associated table. */
        asscIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        asscNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        asscInventoryCol.setCellValueFactory(new PropertyValueFactory<>("inventory"));
        asscPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
    }    
    
}
