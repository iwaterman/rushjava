package org.javafx.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * 登陆首页面
 * @author wangwei
 *
 */
public class LoginWidthCss extends Application {

	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	
	private 	GridPane grid;
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(final Stage primaryStage) throws Exception {
		//窗口标题
		primaryStage.setTitle("JavaFX 登陆");
		//设置面板及布局
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25));
		//文本框
		Text scenetitle = new Text("Welcome");
		scenetitle.setId("welcome-text");
		grid.add(scenetitle, 0, 0, 2, 1);
		//标签
		Label userName = new Label("用户名:");
		grid.add(userName, 0, 1);
		//文本输入框
		final TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);
		
		Label passwd = new Label("密码");
		grid.add(passwd, 0, 2);
		//密码输入框
		final PasswordField passwdField = new PasswordField();
		grid.add(passwdField, 1, 2);
		//按钮及按钮布局
		Button btn = new Button("登陆");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 4);
		
		//点击登陆按钮显示的提示文字
		final Text actiontarget = new Text();
		actiontarget.setId("actiontarget");
		grid.add(actiontarget, 1, 6);

		//登陆按钮单击事件
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				actiontarget.setText("登陆成功");
				grid.setVisible(false);
				//表格面板
				TablePane tp = new TablePane();
				//加到场景中
				Scene tpScene = new Scene(tp,500,500);
				//切换舞台场景为表格面板
				primaryStage.setScene(tpScene);
				
			/*	//获取用户名，密码的值
				String userName = userTextField.getText();
				String passWord = passwdField.getText();
				//建立连接查询数据库
				conn = JdbcUtil.getConn();
				String sql = "select t.*, t.rowid from itcsys_user t where t.usercode='"+userName+"' and t.username='"+passWord+"'";
				try {
					ps = conn.prepareStatement(sql);
					rs = ps.executeQuery();
					if(rs.next()){
						actiontarget.setText("登陆成功");
						grid.setVisible(false);
						//表格面板
						TablePane tp = new TablePane();
						//加到场景中
						Scene tpScene = new Scene(tp,500,500);
						//切换舞台场景为表格面板
						primaryStage.setScene(tpScene);
					}else{
						actiontarget.setText("登陆失败");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					JdbcUtil.close(rs,ps,conn);
				}*/
			}
		});
		Scene scene = new Scene(grid,300,275);
		//为场景加入CSS样式
		scene.getStylesheets().add(LoginWidthCss.class.getResource("LoginWidthCss.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
}
