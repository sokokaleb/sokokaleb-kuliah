package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class SidePanel extends JPanel
{
	private DrawingArea	mainPanel;

	private JLabel		globalScoreText, globalScoreNumText;
	private JLabel		currentScoreText, currentScoreNumText;
	private int			globalScore	= 0, currentScore = 0;

	private JPanel		shootableIndicatorPanel, scoringPanel;
	private JSeparator	defaultSeparator;

	public SidePanel()
	{
		initialize();
		makeScoringPanel();
	}

	private void initialize()
	{

		setPreferredSize(new Dimension(200, 0));
		setBackground(Color.LIGHT_GRAY);
		setBorder(BorderFactory.createTitledBorder(new EtchedBorder(), "Game Sidebar", TitledBorder.CENTER, TitledBorder.TOP, new Font("Consolas", Font.PLAIN, 16), Color.BLACK));
		setBorder(BorderFactory.createCompoundBorder(getBorder(), new EmptyBorder(10, 10, 10, 10)));
		// setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// shootableIndicatorPanel = new JPanel();
		// shootableIndicatorPanel.setPreferredSize(new Dimension(200, 0));
		// shootableIndicatorPanel.setBackground(Color.BLACK);
		// add(shootableIndicatorPanel);
	}

	private void makeScoringPanel()
	{
		scoringPanel = new JPanel();
		scoringPanel.setPreferredSize(new Dimension(200, 0));
		scoringPanel.setMaximumSize(new Dimension(1000, 150));
		scoringPanel.setBackground(Color.LIGHT_GRAY);
		scoringPanel.setLayout(new BoxLayout(scoringPanel, BoxLayout.PAGE_AXIS));
		System.out.println(scoringPanel.getLayout().getClass());
		add(scoringPanel);

		globalScoreText = new JLabel("Total Score", JLabel.CENTER);
		globalScoreText.setAlignmentX(Component.CENTER_ALIGNMENT);
		globalScoreText.setFont(new Font(MainFrame.DEFAULT_FONT_FAMILY, Font.PLAIN, 25));
		scoringPanel.add(globalScoreText);

		globalScoreNumText = new JLabel("0");
		globalScoreNumText.setAlignmentX(Component.CENTER_ALIGNMENT);
		globalScoreNumText.setFont(new Font(MainFrame.DEFAULT_FONT_FAMILY, Font.BOLD, 40));
		scoringPanel.add(globalScoreNumText);

		scoringPanel.add(Box.createVerticalStrut(8));
		scoringPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

		currentScoreText = new JLabel("Current Score");
		currentScoreText.setAlignmentX(Component.CENTER_ALIGNMENT);
		currentScoreText.setFont(new Font(MainFrame.DEFAULT_FONT_FAMILY, Font.PLAIN, 18));
		scoringPanel.add(currentScoreText);

		currentScoreNumText = new JLabel("0");
		currentScoreNumText.setAlignmentX(Component.CENTER_ALIGNMENT);
		currentScoreNumText.setFont(new Font(MainFrame.DEFAULT_FONT_FAMILY, Font.BOLD, 28));
		scoringPanel.add(currentScoreNumText);
	}

	public void addScore(int value)
	{
		globalScore += value;
		currentScore += value;
		globalScoreNumText.setText(""+globalScore);
		currentScoreNumText.setText(""+currentScore);
	}
	
	public void resetCurrentScore()
	{
		currentScore = 0;
		currentScoreNumText.setText("0");
	}

	public JLabel getTotalScoreText()
	{
		return globalScoreNumText;
	}

	public void setMainPanel(DrawingArea mainPanel)
	{
		this.mainPanel = mainPanel;
	}
}
