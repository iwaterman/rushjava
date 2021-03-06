package org.swing.appress.ch02;

import java.awt.event.*;
import javax.swing.*;

public class PrintHelloAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private static final Icon printIcon = new ImageIcon("Print.gif");

	PrintHelloAction() {
		super("Print", printIcon);
		putValue(Action.SHORT_DESCRIPTION, "Hello, World");
	}

	public void actionPerformed(ActionEvent actionEvent) {
		System.out.println("Hello, World");
	}
}