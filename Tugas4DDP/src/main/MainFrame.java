package main;

import java.awt.Dimension;

import javax.swing.JFrame;

public class MainFrame extends JFrame
{
	private final Dimension	MINIMUM_SIZE	= new Dimension(800, 600);
	private final Dimension	MAXIMUM_SIZE	= new Dimension(1366, 768);

	public static void main(String[] args)
	{
		new MainFrame();
	}

	public MainFrame()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DrawingArea comp = new DrawingArea(getContentPane());

		setMinimumSize(MINIMUM_SIZE);
		setMaximumSize(MAXIMUM_SIZE);

		add(comp);
		setVisible(true);
	}

}
