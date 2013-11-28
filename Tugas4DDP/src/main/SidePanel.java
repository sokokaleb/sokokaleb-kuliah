package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class SidePanel extends JPanel
{
	private final String	fontFamily	= (System.getProperty("os.name").charAt(0) == 'L') ? "Ubuntu" : "Segoe UI";

	private DrawingArea		mainPanel;
	private JLabel			scoreText, totalScoreText;

	public SidePanel()
	{
		initialize();
	}

	private void initialize()
	{
		setPreferredSize(new Dimension(200, 0));
		setBackground(Color.LIGHT_GRAY);
		setBorder(BorderFactory.createTitledBorder(new EtchedBorder(), "Game Sidebar", TitledBorder.CENTER, TitledBorder.TOP, new Font("Consolas", Font.PLAIN, 16), Color.BLACK));
		// setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		scoreText = new JLabel("Score:");
		scoreText.setBorder(new EmptyBorder(10, 10, 0, 10));
		scoreText.setFont(new Font(fontFamily, Font.PLAIN, 20));
		add(scoreText);

		totalScoreText = new JLabel("0");
		totalScoreText.setBorder(new EmptyBorder(5, 10, 10, 10));
		totalScoreText.setFont(new Font(fontFamily, Font.BOLD, 35));
		add(totalScoreText);
	}

	public JLabel getTotalScoreText()
	{
		return totalScoreText;
	}

	public void setMainPanel(DrawingArea mainPanel)
	{
		this.mainPanel = mainPanel;
	}
}
