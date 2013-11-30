package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 * Kelas untuk membuat frame utama dari game. Memiliki kelas main.
 * 
 * @author Pusaka Kaleb Setyabudi - 1306398945
 * 
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame
{
	public static final String DEFAULT_FONT_FAMILY = (System.getProperty("os.name").charAt(0) == 'L') ? "Ubuntu" : "Candara";
	public static final int FRAME_PER_SECOND = 100;
	public static final int UPDATE_FQ = 1000 / FRAME_PER_SECOND;

	public static int sidePanelWidth;

	public static MainFrame parentFrame;

	private GameArea gameComp;
	private GameExecutor gameExecutor;
	private JPanel sidePanelBottom;
	private SidePanel sidePanelComp;

	/**
	 * Method main dari program
	 * 
	 * @param args
	 *            Not used
	 */
	public static void main(String[] args)
	{
		System.setProperty("awt.useSystemAAFontSettings", "on");
		System.setProperty("swing.aatext", "true");

		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				parentFrame = new MainFrame(); // Let the constructor do the job
			}
		});
	}

	/**
	 * Constructor dari MainFrame
	 */
	public MainFrame()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setExtendedState(MAXIMIZED_BOTH);
		setMinimumSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize());
		setMaximumSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize());
		
		setUndecorated(true);

		setVisible(true);

		makeSidePanel();
		makeGameArea();

		pack();

		makeGameExecutor();
	}

	/**
	 * Method untuk membuat komponen yang berguna untuk menggambar bentuk-bentuk
	 * dan segala macamnya yang akan digunakan dalam game
	 */
	private void makeGameArea()
	{
		gameComp = new GameArea();
		add(gameComp, BorderLayout.CENTER);
	}

	/**
	 * Method untuk membuat panel yang akan diletakkan di bagian kiri/kanan dari
	 * frame. Panel berisi informasi dari game dan interface game lainnya
	 */
	private void makeSidePanel()
	{
		sidePanelComp = new SidePanel();
		sidePanelComp.initialize();

		sidePanelBottom = new JPanel();
		sidePanelBottom.setLayout(new BorderLayout());
		sidePanelBottom.setBorder(new EmptyBorder(10, 10, 10, 10));
		sidePanelBottom.add(sidePanelComp);
		sidePanelBottom.setBackground(Color.LIGHT_GRAY);

		add(sidePanelBottom, BorderLayout.WEST);
	}

	/**
	 * Method untuk membuat suatu objek GameExecutor, yang mengatur jalannya
	 * game. Selain itu, method ini juga memanggil gameExecutor.start()
	 */
	private void makeGameExecutor()
	{
		gameExecutor = new GameExecutor(gameComp, sidePanelComp);
		gameExecutor.start();
	}
}
