package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
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
	private final static int	CLICK_AREA_RADIUS	= 194 * 194 + 242 * 242;
	private final static long	TIME_MULTIPLIER		= 5;

	private long				currentTime;
	private Container			sidePanel;
	private Dimension			dimen;
	private List<Ball>			ballList;
	private Ellipse2D.Double	ballAreaClip		= new Ellipse2D.Double(-CLICK_AREA_RADIUS / 2, CLICK_AREA_RADIUS / 2, CLICK_AREA_RADIUS, CLICK_AREA_RADIUS);

	private Random				rGen;

	public DrawingArea(Container sidePanel)
	{
		rGen = new Random();

		this.sidePanel = sidePanel;
		ballList = new ArrayList<Ball>();
		addMouseListener(this);
		addMouseMotionListener(this);
		setOpaque(false);

		start();
	}

	public void start()
	{
		Timer t = new Timer(1, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				updateDimen();
				updateBalls();
				repaint();
			}
		});
		t.start();
	}

	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.fill(ballAreaClip);

		for (Ball i : ballList)
		{
			g2.setColor(i.getColor());
			g2.fill(i);
		}
	}

	public void updateBalls()
	{
		List<Ball> tempList = new ArrayList<Ball>();

		for (Ball i : ballList)
		{
			updateCurrentTime();

			double drawingX = i.getX(currentTime - i.getInitDrawingTime());
			double drawingY = i.getY(currentTime - i.getInitDrawingTime());

			if (drawingX > dimen.width + Ball.WIDTH)
			{
				// deletes ball
			}
			else
			{
				if (drawingY < 1e-14)
				{
					i.setInitPos(new Point((int) drawingX, (int) -drawingY));
					i.setDrawingX((int) drawingX);
					i.setDrawingY((int) (dimen.height + drawingY));
					i.setInitDrawingTime(currentTime);
				}
				else
				{
					i.setDrawingX((int) drawingX);
					i.setDrawingY((int) (dimen.height - drawingY));
				}

				tempList.add(i);
			}
		}

		ballList = tempList;
	}

	public void updateCurrentTime()
	{
		currentTime = System.currentTimeMillis() * TIME_MULTIPLIER;
	}

	public void updateDimen()
	{
		dimen = getSize();
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
		System.out.println("ASEU");
		if (SwingUtilities.isLeftMouseButton(arg0))
		{
			Point mousePos = arg0.getPoint();
			updateCurrentTime();

			mousePos.setLocation(mousePos.x, dimen.height - mousePos.y);
			System.out.println(mousePos + " = " + (mousePos.x * mousePos.x + mousePos.y * mousePos.y) + " " + CLICK_AREA_RADIUS);
			if (mousePos.x * mousePos.x + mousePos.y * mousePos.y <= CLICK_AREA_RADIUS)
			{
				ballList.add(new Ball(mousePos, currentTime, new Color(rGen.nextInt(128), rGen.nextInt(128), rGen.nextInt(128))));
				ballList.get(ballList.size() - 1).setDrawingY(dimen.height);
				System.out.println(mousePos);
			}
		}
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
}
