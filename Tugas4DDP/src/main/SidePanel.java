package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * Kelas pengatur dekorasi, string, dan utilitas lainnya untuk panel pinggir
 * 
 * @author Pusaka Kaleb Setyabudi - 1306398945
 * 
 */
@SuppressWarnings("serial")
public class SidePanel extends JPanel
{
	public final static int TOTAL_WIDTH = 220;

	private final static Color DARK_RED = new Color(100, 0, 0);
	private final static Color DARK_GREEN = new Color(0, 100, 0);

	private JPanel scoringPanel;
	private JLabel globalScoreText, globalScoreNumText;
	private JLabel currentScoreText, currentScoreNumText;

	private JPanel shootableIndicatorPanel;
	private JLabel shootableText, shootablePercentage;

	private JPanel ballInfoIndicatorPanel;
	private JLabel currentBallDuration, currentBallDurationText;
	private JLabel currentBallLeft, currentBallLeftText;

	private JPanel etcPanel;
	private JButton helpButton, exitButton;

	/**
	 * Constructor untuk kelas SidePanel
	 */
	public SidePanel()
	{
		setPreferredSize(new Dimension(200, 0));
		setBackground(Color.LIGHT_GRAY);
		setBorder(new TitledBorder(new EtchedBorder(), "Game Sidebar", TitledBorder.CENTER, TitledBorder.TOP, new Font("Consolas", Font.PLAIN, 16), Color.BLACK));
		setBorder(new CompoundBorder(getBorder(), new EmptyBorder(10, 10, 10, 10)));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	/**
	 * Method untuk menyiapkan area panel pinggir
	 */
	public void initialize()
	{
		add(Box.createVerticalGlue());
		makeScoringPanel();
		add(Box.createVerticalGlue());
		add(new JSeparator(SwingConstants.HORIZONTAL));
		add(Box.createVerticalGlue());
		add(Box.createVerticalStrut(20));
		makeShootableIndicatorPanel();
		add(Box.createVerticalGlue());
		add(new JSeparator(SwingConstants.HORIZONTAL));
		add(Box.createVerticalGlue());
		add(Box.createVerticalStrut(20));
		makeBallInfoIndicatorPanel();
		add(Box.createVerticalGlue());
		add(new JSeparator(SwingConstants.HORIZONTAL));
		add(Box.createVerticalGlue());
		add(Box.createVerticalStrut(20));
		makeEtcPanel();
		add(Box.createVerticalGlue());
	}

	/**
	 * Membuat panel skor dari panel pinggir
	 */
	private void makeScoringPanel()
	{
		scoringPanel = new JPanel();
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

		currentScoreText = new JLabel("Current Level Score");
		currentScoreText.setAlignmentX(Component.CENTER_ALIGNMENT);
		currentScoreText.setFont(new Font(MainFrame.DEFAULT_FONT_FAMILY, Font.PLAIN, 18));
		scoringPanel.add(currentScoreText);

		currentScoreNumText = new JLabel("0 / " + 100);
		currentScoreNumText.setAlignmentX(Component.CENTER_ALIGNMENT);
		currentScoreNumText.setFont(new Font(MainFrame.DEFAULT_FONT_FAMILY, Font.BOLD, 28));
		scoringPanel.add(currentScoreNumText);
	}

	/**
	 * Membuat panel indikator panel untuk panel pinggiran
	 */
	private void makeShootableIndicatorPanel()
	{
		shootableIndicatorPanel = new JPanel();
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

	/**
	 * Membuat panel indikator info bola untuk panel pinggir
	 */
	private void makeBallInfoIndicatorPanel()
	{
		ballInfoIndicatorPanel = new JPanel();
		ballInfoIndicatorPanel.setBackground(Color.LIGHT_GRAY);
		ballInfoIndicatorPanel.setLayout(new BoxLayout(ballInfoIndicatorPanel, BoxLayout.PAGE_AXIS));
		add(ballInfoIndicatorPanel);

		currentBallDurationText = new JLabel("Ball Lifetime");
		currentBallDurationText.setForeground(Color.BLACK);
		currentBallDurationText.setAlignmentX(Component.CENTER_ALIGNMENT);
		currentBallDurationText.setFont(new Font(MainFrame.DEFAULT_FONT_FAMILY, Font.BOLD, 25));
		ballInfoIndicatorPanel.add(currentBallDurationText);

		currentBallDuration = new JLabel(String.format("%.1f sec", 0.001 * GameExecutor.DEFAULT_TIME_VISIBLE_LIMIT));
		currentBallDuration.setForeground(Color.BLACK);
		currentBallDuration.setAlignmentX(Component.CENTER_ALIGNMENT);
		currentBallDuration.setFont(new Font(MainFrame.DEFAULT_FONT_FAMILY, Font.BOLD, 45));
		ballInfoIndicatorPanel.add(currentBallDuration);

		ballInfoIndicatorPanel.add(Box.createVerticalStrut(10));

		currentBallLeftText = new JLabel("Ball(s) left");
		currentBallLeftText.setForeground(Color.BLACK);
		currentBallLeftText.setAlignmentX(Component.CENTER_ALIGNMENT);
		currentBallLeftText.setFont(new Font(MainFrame.DEFAULT_FONT_FAMILY, Font.BOLD, 25));
		ballInfoIndicatorPanel.add(currentBallLeftText);

		currentBallLeft = new JLabel("" + LevelGenerator.getBallLimit(0));
		currentBallLeft.setForeground(Color.BLACK);
		currentBallLeft.setAlignmentX(Component.CENTER_ALIGNMENT);
		currentBallLeft.setFont(new Font(MainFrame.DEFAULT_FONT_FAMILY, Font.BOLD, 40));
		ballInfoIndicatorPanel.add(currentBallLeft);
	}

	/**
	 * Membuat panel untuk tombol help dan exit dari panel pinggiran
	 */
	private void makeEtcPanel()
	{
		etcPanel = new JPanel();
		etcPanel.setPreferredSize(new Dimension(1000, 100));
		etcPanel.setLayout(new GridLayout(2, 1));
		add(etcPanel);

		helpButton = new JButton("(?) Help");
		helpButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				JTextArea helpText = new JTextArea();
				helpText.setMargin(new Insets(10, 10, 10, 10));
				helpText.setEditable(false);
				helpText.setFocusable(false);

				helpText.setText("Win each level by shooting ball(s) to destroy block(s).\n");
				helpText.append("You must achieve a level's certain minimal score in order to win a level.\n");
				helpText.append("\nGreen blocks adds +10 to current score.");
				helpText.append("\nBrown blocks adds -50 to current score.");
				helpText.append("\nEach unused given ball adds +20 to total score at the end of each level.");
				helpText.append("\n\n");
				helpText.append("You can adjust ball lifetime by 0.1 second by scrolling your mouse wheel up/down.\n");
				helpText.append("\nUse your strategy carefully to gain score optimally, and win the game!");
				helpText.append("\n\n\n\n\nSadly, actually you can never win this game...");

				JOptionPane.showMessageDialog(null, helpText, "Not-so-helping-dialog-box", JOptionPane.PLAIN_MESSAGE, null);
			}
		});
		helpButton.setPreferredSize(new Dimension(150, 40));
		helpButton.setFocusPainted(false);
		etcPanel.add(helpButton, BorderLayout.CENTER);

		exitButton = new JButton("(X) Exit");
		exitButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		exitButton.setPreferredSize(new Dimension(150, 40));
		exitButton.setFocusPainted(false);
		etcPanel.add(exitButton, BorderLayout.SOUTH);
	}

	/**
	 * Men-set tampilan teks skor global, skor sekarang, dan skor minimal
	 * kelulusan.
	 * 
	 * @param globalScore
	 *            Skor global yang akan di-set
	 * @param currentScore
	 *            Skor sekarang yang akan di-set
	 * @param minimumScore
	 *            Skor minimum yang akan di-set
	 */
	public void setGlobalAndCurrentScore(int globalScore, int currentScore, int minimumScore)
	{
		globalScoreNumText.setText("" + globalScore);
		currentScoreNumText.setText(currentScore + " / " + minimumScore);
	}

	/**
	 * Men-set tampilan teks shooting.
	 * 
	 * @param currentCount
	 *            Hitungan sekarang
	 * @param fullCount
	 *            Hitungan penuh untuk dapat menembak
	 */
	public void updateShootableText(int currentCount, int fullCount)
	{
		double percentage = 1.0 * currentCount / fullCount;
		String percentageString = String.format("%.2f%%", percentage * 100);

		if (currentCount < fullCount)
		{
			if ((int) ((currentCount / 8) & 1) == 1)
				shootableText.setForeground(DARK_RED);
			else
				shootableText.setForeground(getBackground());
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

	/**
	 * Meng-update durasi/lifetime bola saat ini.
	 * 
	 * @param ms
	 *            Durasi/lifetime bola saat ini
	 */
	public void updateCurrentBallDuration(int ms)
	{
		currentBallDuration.setText(String.format("%.1f sec", 0.001 * ms));
	}

	/**
	 * Mendapatkan mengenai informasi banyak-nya bola yang masih bisa digunakan
	 * dalam suatu level tertentu
	 * 
	 * @param ballLeft
	 *            Sisa bola yang dapat digunakan
	 */
	public void updateCurrentBallLeft(int ballLeft)
	{
		currentBallLeft.setText("" + ballLeft);
	}

	/**
	 * Meng-update tampilan teks minimum score yang harus dicapai di suatu level
	 * tertentu.
	 * 
	 * @param currentLevel
	 *            Level tertentu
	 */
	public void updateMinimumScore(int currentLevel)
	{
		currentScoreNumText = new JLabel(0 + " / " + LevelGenerator.getMinimumAchievedScore(currentLevel));
	}
}
