package org.swing.appress.ch02;

import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionFocusMover implements ActionListener {
	public void actionPerformed(ActionEvent actionEvent) {
		KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		manager.focusNextComponent();
	}
}