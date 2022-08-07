package finalproject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Product;

/**
 * This class creates an inventory management system.
 * @author PhillipSanchez
 */
public class FinalProject extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This class add part data.
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
                 // int id, String name, double price, int stock, int min, int max
        InHouse part1 = new InHouse(1, "Brakes", 100.00, 25, 1, 123, 20);
        InHouse part2 = new InHouse(2, "Tires",10.00, 20, 1, 30, 20);
        InHouse part3 = new InHouse(3, "Rims", 45.00, 15, 1, 25, 20);
        InHouse part4 = new InHouse(4, "Seats",6.00, 11, 1, 25, 20);
        InHouse part5 = new InHouse(5, "Handles", 30.00, 10, 1, 25, 20);
       
        
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);

        
        launch(args);
    }
    
}
