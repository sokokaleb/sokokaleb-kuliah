package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

import object.Ball;

@SuppressWarnings("serial")
public class GameArea extends JComponent implements MouseInputListener
{
	private final static Color BALL_CLIP_COLOR = new Color(255, 100, 100, 255);
	private final static Color TEXT_CLIP_COLOR = new Color(255, 210, 210, 255);
	private final static Font DEFAULT_FONT = new Font(MainFrame.DEFAULT_FONT_FAMILY, Font.BOLD, 30);
	private final static Color SKY_COLOR = new Color(200, 200, 255, 255);

	private Dimension dimen;
	private Ellipse2D.Double ballAreaClip = new Ellipse2D.Double(0, 0, GameExecutor.CLICK_AREA_RADIUS * 2, GameExecutor.CLICK_AREA_RADIUS * 2);
	private GameExecutor gameExecutor;

	public GameArea(SidePanel sidePanel)
	{
		Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		// Rectangle bounds = new Rectangle(800, 600);
		setPreferredSize(new Dimension(bounds.width - 200, bounds.height));
		setOpaque(true);
	}

	public void initialize()
	{
		dimen = getSize();
		ballAreaClip.x = -GameExecutor.CLICK_AREA_RADIUS;
		ballAreaClip.y = dimen.getHeight() - GameExecutor.CLICK_AREA_RADIUS;
		System.out.println(dimen);

		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void paint(Graphics g)
	{
		paintComponent(g);
	}

	protected void paintComponent(Graphics g)
	{
		paintComponent((Graphics2D) g);
	}

	private synchronized void paintComponent(Graphics2D g)
	{
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(SKY_COLOR);
		g.fill(new Rectangle(0, 0, dimen.width, dimen.height));

		g.setClip(ballAreaClip.getBounds());
		g.setColor(BALL_CLIP_COLOR);
		g.fill(ballAreaClip);

		g.setFont(DEFAULT_FONT);
		g.setColor(TEXT_CLIP_COLOR);
		g.drawString("Clickable Area", 40, (int) dimen.getHeight() - 20);

		g.setClip(0, 0, dimen.width, dimen.height);
		for (Ball i : gameExecutor.getBallList())
		{
			g.setColor(i.getColor());
			g.fill(i);
		}
		g.dispose();
	}

	public void setGameExecutor(GameExecutor gameExecutor)
	{
		this.gameExecutor = gameExecutor;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
	}

	@Override
	public synchronized void mousePressed(MouseEvent e)
	{
		// gameExecutor.dispatchEvent(e);
		if (SwingUtilities.isLeftMouseButton(e))
		{
			Point mousePos = e.getPoint();
			mousePos.setLocation(mousePos.x, dimen.height - mousePos.y);
			if (mousePos.x * mousePos.x + mousePos.y * mousePos.y <= GameExecutor.CLICK_AREA_RADIUS_SQUARED)
				gameExecutor.addBall(mousePos);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
	}
}
