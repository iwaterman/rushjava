package org.swing.appress.ch02;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseEnterFocusMover extends MouseAdapter {
	public void mouseEntered(MouseEvent mouseEvent) {
		Component component = mouseEvent.getComponent();
		if (!component.hasFocus()) {
			component.requestFocusInWindow();
		}
	}
}