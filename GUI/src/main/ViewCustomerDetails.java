package main;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewCustomerDetails extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Button insertButton = new Button("Switch to insert mode");
        Button deleteButton = new Button("Switch to deletion mode");
        Button displayButton = new Button("Switch to display mode");
        Button changeButton = new Button("Switch to update mode");
        Button persistantButton = new Button("Switch to persistence mode");
        Button quitButton  = new Button("Quit");




        VBox vbox = new VBox(10); // 10 pixels spacing
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(insertButton,deleteButton,displayButton,changeButton,persistantButton,quitButton);

        // Create a scene with the VBox as the root node
        Scene scene = new Scene(vbox, 300, 200);


        // Set the scene and show the stage
        primaryStage.setTitle("Warehouse Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
