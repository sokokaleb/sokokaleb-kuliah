package main;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

@SuppressWarnings("serial")
public class Ball extends Ellipse2D.Double
{
	public static final double			GRAVITATION		= -9.81;
	public static final Point2D.Double	DRAWING_POINT	= new Point2D.Double(0.0, 0.0);

	private final double				WIDTH			= 20;

	private Point						initPos;
	private Point						initSpeed;
	private long						initDrawingTime;
	private Color						color;

	public Ball()
	{
		initPos = new Point(0, 0);
	}

	public Ball(Point initSpeed, long timeMillis, Color color)
	{
		this.initPos = new Point(0, 0);
		this.initSpeed = initSpeed;
		this.color = color;
		initDrawingTime = timeMillis;
	}

	public double getX(long timeMillis)
	{
		double time = 1.0 * timeMillis / 1000;
		return initPos.getX() + initSpeed.getX() * time;
	}

	public double getY(long timeMillis)
	{
		double time = 1.0 * timeMillis / 1000;
		return initPos.getX() + initSpeed.getX() * time + 0.5 * GRAVITATION * time * time;
	}

	public void setInitPos(Point initPos)
	{
		this.initPos = initPos;
	}

	public void setDrawingX(int x)
	{
		this.x = x;
	}

	public void setDrawingY(int y)
	{
		this.y = y;
	}

	public void setSpeedX(double x)
	{
		initSpeed.setLocation(x, initSpeed.y);
	}

	public void setSpeedY(double y)
	{
		initSpeed.setLocation(initSpeed.x, y);
	}

	public void setInitDrawingTime(long time)
	{
		initDrawingTime = time;
	}

	public long getInitDrawingTime()
	{
		return initDrawingTime;
	}

	public Color getColor()
	{
		return color;
	}
}
