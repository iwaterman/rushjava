package org.javafx.hello;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
 
public class HelloLogin extends Application {
    
    @Override
    public void start(Stage primaryStage) {
    	GridPane grid = new GridPane();
    	grid.setAlignment(Pos.CENTER);
    	// 设置组件之间的水平间隔
    	grid.setHgap(10);
    	// 设置组件之间的垂直间隔
    	grid.setVgap(10);
    	// 设置容器的边界
    	grid.setPadding(new Insets(25, 25, 25, 25));
    	 
    	Scene scene = new Scene(grid, 300, 275);
    	primaryStage.setScene(scene);
    	
    	Text scenetitle = new Text("Welcome");
    	scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
    	grid.add(scenetitle, 0, 0, 2, 1);
    	 
    	Label userName = new Label("User Name");
    	grid.add(userName, 0, 1);
    	 
    	final TextField userTextField = new TextField();
    	grid.add(userTextField, 1, 1);
    	 
    	Label pw = new Label("Password");
    	grid.add(pw, 0, 2);
    	 
    	final PasswordField passwordField = new PasswordField();
    	grid.add(passwordField, 1, 2);
    	
    	Button btn = new Button("Sign in");
    	HBox hbBtn = new HBox(10);
    	hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
    	hbBtn.getChildren().add(btn);
    	grid.add(hbBtn, 1, 4);
    	 
    	final Text actiontarget = new Text();
    	grid.add(actiontarget, 1, 6);
    	
    	btn.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override
    	    public void handle(ActionEvent e) {
    	        String userNameValue = userTextField.getText();
    	        String passwordValue = passwordField.getText();
    	 
    	        String result = "Incorrect!";
    	 
    	        if (userNameValue.equals("java") && passwordValue.equals("fx2") ) {
    	            result = "Welcome!";
    	        }
    	 
    	        actiontarget.setFill(Color.FIREBRICK);
    	        actiontarget.setText(result);
    	    }
    	});
    	
    	
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
