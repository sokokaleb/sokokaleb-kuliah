package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

import object.Ball;

@SuppressWarnings("serial")
public class DrawingArea extends JComponent implements MouseInputListener
{
	private final static int	CLICK_AREA_RADIUS_SQUARED	= 200 * 200 + 250 * 250;
	private final static int	CLICK_AREA_RADIUS			= (int) Math.sqrt(CLICK_AREA_RADIUS_SQUARED);
	private final static Color	BALL_CLIP_COLOR				= new Color(255, 100, 100, 255);
	private final static Color	TEXT_CLIP_COLOR				= new Color(255, 210, 210, 255);
	private final static Font	DEFAULT_FONT				= new Font(MainFrame.DEFAULT_FONT_FAMILY, Font.BOLD, 30);

	private Dimension			dimen;
	private List<Ball>			ballList;
	private Ellipse2D.Double	ballAreaClip				= new Ellipse2D.Double(0, 0, CLICK_AREA_RADIUS * 2, CLICK_AREA_RADIUS * 2);
	private boolean				isShootable					= false;

	private Random				rGen;

	/*
	 * Game Mechanism variables
	 */

	private final int			SHOOT_INTERVAL_DURATION_MS	= 1500;
	private Timer				shootTimer;
	private int					timeCounter;
	private SidePanel			sidePanel;

	public DrawingArea(SidePanel sidePanel)
	{
		rGen = new Random();
		ballList = new ArrayList<Ball>();

		this.sidePanel = sidePanel;

		Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		setPreferredSize(new Dimension(bounds.width - 200, bounds.height));
		setDoubleBuffered(true);
		setOpaque(false);

		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void start()
	{
		// Rectangle bounds =
		// GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		// dimen = new Dimension(bounds.width, bounds.height);
		// ballAreaClip.x = -CLICK_AREA_RADIUS;
		// ballAreaClip.y = dimen.getHeight() - CLICK_AREA_RADIUS;
		dimen = getSize();
		System.out.println(dimen);
		ballAreaClip.x = -CLICK_AREA_RADIUS;
		ballAreaClip.y = dimen.getHeight() - CLICK_AREA_RADIUS;

		// Timer t = new Timer(MainFrame.UPDATE_FQ, new ActionListener()
		// {
		// @Override
		// public void actionPerformed(ActionEvent arg0)
		// {
		// updateBalls();
		// repaint();
		// }
		// });
		// t.start();

		Thread t = new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				while (true)
				{
					updateBalls();
					try
					{
						Thread.sleep(17);
					}
					catch (InterruptedException e)
					{
					}
					repaint();
				}
			}
		});
		t.start();
	}

	public void paintComponent(Graphics g)
	{
		paintComponent((Graphics2D) g);
	}

	private GradientPaint	alay	= new GradientPaint(0, 500, new Color(220, 220, 255, 255), 0, 768, Color.WHITE);

	private void paintComponent(Graphics2D g)
	{
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(new Color(220, 220, 255, 255));
		// g.setPaint(alay);
		g.fillRect(0, 0, dimen.width, dimen.height);
		g.setClip(ballAreaClip.getBounds());
		g.setColor(BALL_CLIP_COLOR);
		g.fill(ballAreaClip);

		g.setFont(DEFAULT_FONT);
		g.setColor(TEXT_CLIP_COLOR);
		g.drawString("Clickable Area", 20, (int) dimen.getHeight() - 20);

		g.setClip(0, 0, dimen.width, dimen.height);
		for (Ball i : ballList)
		{
			g.setColor(i.getColor());
			g.setClip(i.getBounds());
			g.fill(i);
		}
	}

	private void updateBalls()
	{
		// List<Ball> tempList = new ArrayList<Ball>();

		int backQueue = 0;
		for (int i = 0; i < ballList.size(); ++i)
		{
			Ball ball = ballList.get(i);

			ball.updateBallPosition();
			if (!ball.isVisible() || ball.x > dimen.width - Ball.WIDTH)
				; // deletes ball
			else ballList.set(backQueue++, ball);
		}

		for (; ballList.size() > backQueue;)
			ballList.remove(ballList.get(ballList.size() - 1));
	}

	private void resetShootTimer()
	{
		// sidePanel.resetShootTimer();
		setShootable(false);
	}

	public void setShootable(boolean state)
	{
		isShootable = state;
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		// if (!isShootable)
		// return;
		if (SwingUtilities.isLeftMouseButton(arg0))
		{
			Point mousePos = arg0.getPoint();

			mousePos.setLocation(mousePos.x, dimen.height - mousePos.y);
			if (mousePos.x * mousePos.x + mousePos.y * mousePos.y <= CLICK_AREA_RADIUS_SQUARED)
			{
				ballList.add(new Ball(mousePos, new Color(rGen.nextInt(128), rGen.nextInt(128), rGen.nextInt(128)), this));
				ballList.get(ballList.size() - 1).setDrawingY(dimen.height);
			}
		}
		else if (SwingUtilities.isMiddleMouseButton(arg0))
		{
			sidePanel.resetCurrentScore();
		}
		else if (SwingUtilities.isRightMouseButton(arg0))
		{
			sidePanel.addScore(10);
		}

		resetShootTimer();
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	public void setSidePanel(SidePanel sidePanel)
	{
		this.sidePanel = sidePanel;
	}
}
