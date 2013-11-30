package main;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class MainFrame extends JFrame
{
	public static final String DEFAULT_FONT_FAMILY = (System.getProperty("os.name").charAt(0) == 'L') ? "Ubuntu" : "Segoe UI";
	public static final int FRAME_PER_SECOND = 100;
	public static final int UPDATE_FQ = 1000 / FRAME_PER_SECOND;
	// private final Dimension MINIMUM_SIZE = new Dimension(800, 600);
	// private final Dimension MAXIMUM_SIZE = new Dimension(1366, 768);
	// private final Dimension SCREEN_DIMENSION =
	// Toolkit.getDefaultToolkit().getScreenSize();
	private GameArea gameComp;
	private SidePanel sidePanelComp;
	private JPanel sidePanelBottom;
	private GameExecutor gameExecutor;

	public static void main(String[] args)
	{
		System.setProperty("awt.useSystemAAFontSettings", "on");
		System.setProperty("swing.aatext", "true");
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
		// setSize(800, 600);
		setUndecorated(true);

		setVisible(true);

		makeSidePanel();
		makeGameArea();

		pack();

		makeGameExecutor();
	}

	private void makeGameArea()
	{
		gameComp = new GameArea(sidePanelComp);
		add(gameComp, BorderLayout.CENTER);
	}

	private void makeSidePanel()
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

	private void makeGameExecutor()
	{
		gameExecutor = new GameExecutor(gameComp, sidePanelComp);
		gameExecutor.start();
	}
}
