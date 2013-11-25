package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

@SuppressWarnings("serial")
public class DrawingArea extends JComponent implements MouseInputListener
{
	private long		currentTime, timeAtZero;
	private Container	area;
	private Dimension	dimen;
	List<Ball>			ballList;

	private Random		rGen;

	public DrawingArea(Container area)
	{
		rGen = new Random();
		this.area = area;
		ballList = new ArrayList<Ball>();
		timeAtZero = System.currentTimeMillis();
		addMouseListener(this);
		addMouseMotionListener(this);

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

		for (Ball i : ballList)
		{
			g2.setColor(i.getColor());
			currentTime = System.currentTimeMillis();

			double drawingX = i.getX(currentTime - i.getInitDrawingTime());
			double drawingY = i.getY(currentTime - i.getInitDrawingTime());
			i.setDrawingX((int) drawingX);
			i.setDrawingY((int) drawingY);

			g2.fill(i);
		}
	}

	public void updateBalls()
	{
		List<Ball> tempList = new ArrayList<Ball>();
		
		for (Ball i : ballList)
		{
			updateCurrentTime();
		}
		
		ballList = tempList;
	}
	
	public void updateCurrentTime()
	{
		currentTime = System.currentTimeMillis();
	}

	public void updateDimen()
	{
		dimen = area.getSize();
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		Point mousePos = arg0.getPoint();

		mousePos.setLocation(mousePos.x, dimen.height - mousePos.y);
		ballList.add(new Ball(mousePos, System.currentTimeMillis(), new Color(rGen.nextInt(256), rGen.nextInt(256), rGen.nextInt(256))));
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
