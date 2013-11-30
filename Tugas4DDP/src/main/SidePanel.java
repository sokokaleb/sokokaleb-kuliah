package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class SidePanel extends JPanel
{
	private final static Color DARK_RED = new Color(100, 0, 0);
	private final static Color DARK_GREEN = new Color(0, 100, 0);

	private JLabel globalScoreText, globalScoreNumText;
	private JLabel currentScoreText, currentScoreNumText;
	private int globalScore = 0, currentScore = 0;

	private JLabel shootableText, shootablePercentage;

	private JPanel shootableIndicatorPanel, scoringPanel;

	public SidePanel()
	{
		initialize();
		makeScoringPanel();
		add(Box.createVerticalStrut(60));
		makeShootableIndicatorPanel();
	}

	private void initialize()
	{

		setPreferredSize(new Dimension(200, 0));
		setBackground(Color.LIGHT_GRAY);
		setBorder(new TitledBorder(new EtchedBorder(), "Game Sidebar", TitledBorder.CENTER, TitledBorder.TOP, new Font("Consolas", Font.PLAIN, 16), Color.BLACK));
		setBorder(new CompoundBorder(getBorder(), new EmptyBorder(10, 10, 10, 10)));
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

	private void makeShootableIndicatorPanel()
	{
		shootableIndicatorPanel = new JPanel();
		shootableIndicatorPanel.setPreferredSize(new Dimension(200, 0));
		shootableIndicatorPanel.setMaximumSize(new Dimension(1000, 100));
		shootableIndicatorPanel.setBackground(Color.LIGHT_GRAY);
		shootableIndicatorPanel.setLayout(new BoxLayout(shootableIndicatorPanel, BoxLayout.PAGE_AXIS));
		add(shootableIndicatorPanel);

		shootableText = new JLabel("Shoot now!");
		shootableText.setForeground(new Color(0, 170, 0));
		shootableText.setAlignmentX(Component.CENTER_ALIGNMENT);
		shootableText.setFont(new Font(MainFrame.DEFAULT_FONT_FAMILY, Font.BOLD, 30));
		shootableIndicatorPanel.add(shootableText);

		shootablePercentage = new JLabel("100.00%");
		shootablePercentage.setForeground(new Color(0, 100, 0));
		shootablePercentage.setAlignmentX(Component.CENTER_ALIGNMENT);
		shootablePercentage.setFont(new Font(MainFrame.DEFAULT_FONT_FAMILY, Font.BOLD, 40));
		shootableIndicatorPanel.add(shootablePercentage);

	}

	public void addScore(int value)
	{
		globalScore += value;
		currentScore += value;
		globalScoreNumText.setText("" + globalScore);
		currentScoreNumText.setText("" + currentScore);
	}

	public void resetCurrentScore()
	{
		currentScore = 0;
		currentScoreNumText.setText("0");
	}

	public void updateShootableText(int currentCount, int fullCount)
	{
		double percentage = 1.0 * currentCount / fullCount;
		String percentageString = String.format("%.2f%%", percentage * 100);
		// if (true) return;
		if (currentCount < fullCount)
		{
			if ((int) ((currentCount / 8) & 1) == 1)
				shootableText.setForeground(DARK_RED);
			else shootableText.setForeground(getBackground());
			shootablePercentage.setForeground(DARK_RED);

			shootableText.setText("Reloading");
			shootablePercentage.setText(percentageString);
		}
		else
		{
			shootableText.setForeground(DARK_GREEN);
			shootablePercentage.setForeground(DARK_GREEN);
			shootableText.setText("Shoot now!");
			shootablePercentage.setText("100.00%");
		}
	}

	public JLabel getTotalScoreText()
	{
		return globalScoreNumText;
	}
}
