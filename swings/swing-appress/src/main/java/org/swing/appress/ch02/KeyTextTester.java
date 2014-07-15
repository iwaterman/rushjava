package org.swing.appress.ch02;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 监控键盘按键事件。
 * 
 */
public class KeyTextTester {
	public static void main(String args[]) {
		Runnable runner = new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Key Text Sample");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				KeyTextComponent keyTextComponent = new KeyTextComponent();
				final JTextField textField = new JTextField();
				ActionListener actionListener = new ActionListener() {
					public void actionPerformed(ActionEvent actionEvent) {
						String keyText = actionEvent.getActionCommand();
						textField.setText(keyText);
					}
				};
				keyTextComponent.addActionListener(actionListener);
				frame.add(keyTextComponent, BorderLayout.CENTER);
				frame.add(textField, BorderLayout.SOUTH);
				frame.setSize(300, 200);
				frame.setVisible(true);
			}
		};
		EventQueue.invokeLater(runner);
	}
}