package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class SidePanel extends JPanel
{
	private Border	panelBorder;

	public SidePanel()
	{
		panelBorder = BorderFactory.createTitledBorder(new EtchedBorder(), "Just Some Title", TitledBorder.CENTER, TitledBorder.TOP, new Font("Consolas", Font.PLAIN, 16), Color.BLACK); 
		setBorder(panelBorder);
		// setBorder(new TitledBorder(new EtchedBorder(), "OI OI"));
		setPreferredSize(new Dimension(200, 0));
		setBackground(Color.LIGHT_GRAY);
	}
}
