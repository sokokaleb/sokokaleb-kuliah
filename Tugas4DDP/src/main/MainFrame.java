package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class MainFrame extends JFrame
{
	// private final Dimension MINIMUM_SIZE = new Dimension(800, 600);
	// private final Dimension MAXIMUM_SIZE = new Dimension(1366, 768);
	// private final Dimension SCREEN_DIMENSION =
	// Toolkit.getDefaultToolkit().getScreenSize();

	private DrawingArea	drawingComp;
	private SidePanel	sidePanelComp;
	private JPanel		sidePanelBottom;

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				new MainFrame(); // Let the constructor do the job
			}
		});
	}

	public MainFrame()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setExtendedState(MAXIMIZED_BOTH);
		setUndecorated(true);

		setVisible(true);

		makeSidePanel();
		makeDrawingArea();

		synchronize();
	}

	public void makeDrawingArea()
	{
		drawingComp = new DrawingArea();
		add(drawingComp, BorderLayout.CENTER);
	}

	public void makeSidePanel()
	{
		sidePanelComp = new SidePanel();

		makeSidePanelBottom();

		add(sidePanelBottom, BorderLayout.EAST);
	}

	private void makeSidePanelBottom()
	{
		sidePanelBottom = new JPanel();
		sidePanelBottom.setLayout(new BorderLayout());
		sidePanelBottom.setBorder(new EmptyBorder(10, 10, 10, 10));
		sidePanelBottom.add(sidePanelComp);
		sidePanelBottom.setBackground(Color.LIGHT_GRAY);
	}

	private void synchronize()
	{
		sidePanelComp.setMainPanel(drawingComp);
		drawingComp.setSidePanel(sidePanelComp);
	}
}
