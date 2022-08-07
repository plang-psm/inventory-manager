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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

/**
 * FXML Controller class - Add part controller class. Allows user to add parts.
 *
 * @author PhillipSanchez
 */
public class AddPartFormController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    @FXML
    private GridPane inhouseButton;

    @FXML
    private RadioButton inHouSrc;

    @FXML
    private ToggleGroup srcTG;

    @FXML
    private RadioButton outSrc;

    @FXML
    private Label ioNameLbl;

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
    private TextField ioTxt;

    @FXML
    private TextField minTxt;

   
    /** Sends user to the main form. */
    @FXML
    void onActionBackToMainForm(ActionEvent event) throws IOException 
    {
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
    
    /** Changes Machine Label to Machine ID if selected. */
    @FXML
    void onActionInHouSrc(ActionEvent event) {  
        if(inHouSrc.isSelected()){
            ioNameLbl.setText("Machine ID");
        }
    }

    /** Changes Machine Label to Company Name if selected. */
    @FXML
    void onActionOutSrc(ActionEvent event) {
        if(outSrc.isSelected()){
            ioNameLbl.setText("Company Name");
        }
    }
    
    /** 
    *   Gets and saves the users input data, 
    *   checks if user selected InHouse or OutSoruced, 
    *   checks for max > min
    *   checks for min < stock < max
    *   checks if forms filled correctly
    *   If min > max or min > stock > max user notified if done incorrectly
    */
    @FXML
    void onActionSaveAddParts(ActionEvent event) throws IOException 
    {
        try{
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
           
            if(inHouSrc.isSelected()){
                    if(ioTxt.getText().isEmpty()){
                        Alert alert = new Alert(Alert.AlertType.WARNING, "The Machine ID or Comapny Name cannot be left empty.");

                        Optional<ButtonType> result = alert.showAndWait();
                    if(result.isPresent() && result.get() == ButtonType.OK)
                        {
                        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/view/ModifyProductController.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();
                        }
                    } else if(!ioTxt.getText().isEmpty()){
                        int machineId = Integer.parseInt(ioTxt.getText());
                        Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId)); 
                    }
            }
            }
            
            if(outSrc.isSelected()) {
                    if(ioTxt.getText().isEmpty()){
                        Alert alert = new Alert(Alert.AlertType.WARNING, "The Machine ID or Comapny Name cannot be left empty.");

                        Optional<ButtonType> result = alert.showAndWait();
                    if(result.isPresent() && result.get() == ButtonType.OK)
                        {
                        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/view/ModifyProductController.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();
                        }
                    } else if(!ioTxt.getText().isEmpty()){
                        String companyName = ioTxt.getText();
                        Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
                } 
        }
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
                
        } catch(NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog.");
                alert.setContentText("Invalid - Add Part failed due to incorrect values.");
                alert.showAndWait();
                }
    }

    /**
     * Initializes the controller class.
     * @param url Passes URL.
     * @param rb Passes RB.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        /** Generates a user for parts ID. */
        idTxt.setText(String.valueOf(Inventory.getAllParts().size()+1));
    }    


    
}
