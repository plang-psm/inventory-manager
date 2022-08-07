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
 * FXML Controller class - Add product controller class. Allows user to add products.
 *
 * @author PhillipSanchez
 */
    public class AddProductFormController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private GridPane inhouseButton;

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
    private TextField partSearchTxt;

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
    
    Product product1 = new Product(0,"",0.0,0,0,0);
    
    /** Searches for parts, clears when text field is empty, and alerts user if part name not found. */
    @FXML
    void onActionSearchBtn(ActionEvent event) {

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
        }catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog.");
                alert.setContentText("Part name not found");
                alert.showAndWait();  
    }
        partTableView.setItems(parts);
        
    }
    /** Loops through part name. Returns if found in search text field or returns error message if not found. 
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
    /** Loops through part ID. Returns if found in search text field or returns error message if not found.
     * @param id Passes part ID.
     * @return part Returns id parts related to part id.
     */
    private Part getPartWithId(int id){
        ObservableList<Part> allParts = Inventory.getAllParts();
 
        for(Part part : allParts){
            if(part.getId() == id)
                return part;
        }
        return null;
    }
    
    /** Adds part to product creating an association. */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        
        Part part = partTableView.getSelectionModel().getSelectedItem();
        bottomParts.add(part);
        asscTableView.setItems(bottomParts);
    }
    
    /** Returns user back to main form. */
    @FXML
    void onActionBackToMainForm(ActionEvent event) throws IOException {
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to continue? All data will be erased if not saved.");
       
       Optional<ButtonType> result = alert.showAndWait();
       if(result.isPresent() && result.get() == ButtonType.OK)
       {
       stage = (Stage)((Button)event.getSource()).getScene().getWindow();
       scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
       stage.setScene(new Scene(scene));
       stage.show();
       }
    }
    
    /** Removes part association from product. */
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {
        Part part = asscTableView.getSelectionModel().getSelectedItem();
        
        bottomParts.remove(part);
        asscTableView.setItems(bottomParts);
    }

    /**
    Gets and saves the users input data. 
    checks if user selected InHouse or OutSourced.
    checks for max > min.
    checks for min < stock < max.
    checks if forms filled correctly.
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
            
            if(min > max){  
            Alert alert = new Alert(Alert.AlertType.WARNING, "Min cannot be greater than max");

                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK)
                {
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/ModifyProductController.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
            } else if(min > stock) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Min cannot be greater than stock");

                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK)
                {
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/ModifyProductController.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
            }else if(stock > max){
                Alert alert = new Alert(Alert.AlertType.WARNING, "Stock cannot be greater than max");

                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK)
                {
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/ModifyProductController.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
            } else { 

                product1 = new Product(id, name, price, stock, min, max);
                for (int i = 0; i < bottomParts.size(); i++) {
                    product1.addAssociatedPart(bottomParts.get(i));
                }
                Inventory.addProduct(product1);
            }
            } catch(NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog.");
                alert.setContentText("Invalid - Add Product failed due to incorrect values.");
                alert.showAndWait();
        }
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
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
        
        /** Generates a user for products ID. */
        idTxt.setText(String.valueOf(Inventory.getAllProducts().size()+1));
        
        /** Sets data into the parts table. */
        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        /** Sets associated data to associated table. */
        asscTableView.setItems(product1.getAllAssociatedParts());
        asscIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        asscNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        asscInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        asscPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    
    }    
}
