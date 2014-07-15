package org.javafx.login;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * 列表面板
 * @author wangwei
 *
 */
public class TablePane extends AnchorPane {
	
	public TablePane() {
		//列表的列
		TableColumn lshColumn = new TableColumn();
		//列名
		lshColumn.setText("流水号");
		//列宽
		lshColumn.setMinWidth(100);
		//该列绑定的数据
		lshColumn.setCellValueFactory(new PropertyValueFactory("lsh"));
		
		TableColumn ksrqColumn = new TableColumn();
		ksrqColumn.setText("考试日期");
		ksrqColumn.setMinWidth(100);
		ksrqColumn.setCellValueFactory(new PropertyValueFactory("ksrq"));
		
		TableColumn kscjColumn = new TableColumn();
		kscjColumn.setText("考试成绩");
		kscjColumn.setMinWidth(100);
		kscjColumn.setCellValueFactory(new PropertyValueFactory("score"));
		
		//创建一个列表
		TableView tableView = new TableView();
		//在列表中加入列
		tableView.getColumns().addAll(lshColumn,ksrqColumn,kscjColumn);
		//表格的数据源
//		ObservableList<scoreInfo> data = FXCollections.observableArrayList();
//		//列表赋值
//		data.add(new scoreInfo("1", "2", "3"));
		//为列表绑定数据
//		tableView.setItems(data);
		//把列表加到面板中
		this.getChildren().add(tableView);
	}
}
