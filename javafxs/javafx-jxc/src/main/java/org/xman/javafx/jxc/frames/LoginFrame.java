package org.xman.javafx.jxc.frames;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
 
public class LoginFrame extends Application {
    
    @Override
    public void start(final Stage stage) {
    	GridPane imgPane = new GridPane();
        imgPane.setId("img-pane");
        imgPane.setPrefSize(560, 360);
        imgPane.setLayoutX(230);
        imgPane.setLayoutY(200);
        
        imgPane.setAlignment(Pos.CENTER);
    	// 设置组件之间的水平间隔
        imgPane.setHgap(10);
    	// 设置组件之间的垂直间隔
        imgPane.setVgap(10);
    	// 设置容器的边界
        imgPane.setPadding(new Insets(25, 25, 25, 225));
        
        Text scenetitle = new Text("Welcome");
    	scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
    	scenetitle.setId("welcome-text");
    	imgPane.add(scenetitle, 0, 0, 2, 1);
    	 
    	Label namelbl = new Label("用户名：");
    	imgPane.add(namelbl, 0, 1);
    	 
    	final TextField nameTf = new TextField();
    	nameTf.setPrefHeight(27);
    	imgPane.add(nameTf, 1, 1);
    	 
    	Label pw = new Label("授权码：");
    	imgPane.add(pw, 0, 2);
    	 
    	final PasswordField pswdPf = new PasswordField();
    	pswdPf.setPrefHeight(27);
    	imgPane.add(pswdPf, 1, 2);
    	
    	Button loginBtn = new Button("登 陆");
    	loginBtn.setPrefHeight(27);
    	HBox hbBtn = new HBox(10);
    	hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
    	hbBtn.getChildren().add(loginBtn);
    	imgPane.add(hbBtn, 1, 4);
    	 
    	final Text actiontarget = new Text();
    	actiontarget.setId("actiontarget");
    	imgPane.add(actiontarget, 1, 6);
    	
    	
    	loginBtn.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override
    	    public void handle(ActionEvent e) {
    	    	IndexFrame index = new IndexFrame();
    	    	index.start(stage);
    	    }
    	});
    	
    	Button cancelBtn = new Button("取 消");
    	cancelBtn.setPrefHeight(27);
    	hbBtn.getChildren().add(cancelBtn);
    	cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override
    	    public void handle(ActionEvent e) {
    	    	stage.close();
    	    }
    	});
        
        AnchorPane root = new AnchorPane();
        root.getChildren().add(imgPane);
        root.setId("root");


        Scene scene = new Scene(root, 1020, 760);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.centerOnScreen();
        
        scene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
