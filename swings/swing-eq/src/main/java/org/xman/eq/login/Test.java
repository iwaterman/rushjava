package org.xman.eq.login;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.awt.AWTUtilities;

public class Test {
	private static JFrame frame = new JFrame();

	private MouseAdapter moveWindowListener = new MouseAdapter() {

		private Point lastPoint = null;

		@Override
		public void mousePressed(MouseEvent e) {
			lastPoint = e.getLocationOnScreen();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			Point point = e.getLocationOnScreen();
			int offsetX = point.x - lastPoint.x;
			int offsetY = point.y - lastPoint.y;
			Rectangle bounds = frame.getBounds();
			bounds.x += offsetX;
			bounds.y += offsetY;
			frame.setBounds(bounds);
			lastPoint = point;
		}
	};

	public void doTask() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setBounds(500, 500, 300, 300);
		AWTUtilities.setWindowOpaque(frame, false);

		JPanel pane = new JPanel() {

			@Override
			public void paint(Graphics g) {
				super.paint(g);

				g.setColor(Color.red);
				g.fill3DRect(10, 10, 100, 100, true);
			}
		};
		pane.addMouseListener(moveWindowListener);

		frame.setContentPane(pane);

		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new Test().doTask();
	}
}