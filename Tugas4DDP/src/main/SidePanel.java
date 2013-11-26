package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class SidePanel extends JPanel
{
	private DrawingArea	mainPanel;
	private JLabel		scoreText;

	public SidePanel()
	{
		initialize();
		// setBorder(new TitledBorder(new EtchedBorder(), "OI OI"));
	}

	private void initialize()
	{
		setPreferredSize(new Dimension(200, 0));
		setBackground(Color.LIGHT_GRAY);
		setBorder(BorderFactory.createTitledBorder(new EtchedBorder(), "Just Some Title", TitledBorder.CENTER, TitledBorder.TOP, new Font("Consolas", Font.PLAIN, 16), Color.BLACK));
		setLayout(new BorderLayout());

		scoreText = new JLabel("Score:");
		scoreText.setBorder(new EmptyBorder(10, 10, 10, 10));
		scoreText.setFont(Font.getFont("Ubuntu", Font.getFont("Segoe UI")));
		add(scoreText, BorderLayout.NORTH);

	}

	public void setMainPanel(DrawingArea mainPanel)
	{
		this.mainPanel = mainPanel;
	}
}
