package object;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Timer;
import java.util.TimerTask;

import main.MainFrame;

@SuppressWarnings("serial")
public class Ball extends Ellipse2D.Double
{
	public static final double			GRAVITATION		= -9.81;
	public static final Point2D.Double	DRAWING_POINT	= new Point2D.Double(0.0, 0.0);
	public static final double			WIDTH			= 20;

	private static final double			SPEED_MANIP		= 0.5;
	private static final double			RESTITUTION_C	= 0.8;
	private static final double			TIME_INTERVAL	= 0.05;

	private Point						initPos;
	private Point						initSpeed;
	private double						currentTime;
	private Color						color;
	private Dimension					parentContainerDimen;

	private Timer						ballTimer;
	private boolean						isVisible		= true;

	public Ball()
	{
		initPos = new Point(0, 0);
		width = height = WIDTH;
	}

	public Ball(Point initSpeed, Color color, Container container)
	{
		this.initPos = new Point(0, 0);
		this.initSpeed = initSpeed;
		this.color = color;
		width = height = WIDTH;
		parentContainerDimen = container.getSize();

		// Manipulating speed, so the speed is not too fast >_<"
		this.initSpeed.x *= SPEED_MANIP;
		this.initSpeed.y *= SPEED_MANIP;

		ballTimer = new Timer();
		ballTimer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				updateBallPosition();
				if (x > parentContainerDimen.width)
				{
					isVisible = false;
					this.cancel();
				}
			}
		}, 0, MainFrame.UPDATE_FQ);
	}

	public void updateBallPosition()
	{
		if (isVisible)
		{
			currentTime += TIME_INTERVAL;
			double newDrawingX = initPos.x + initSpeed.x * currentTime;
			double newDrawingY = initPos.y + initSpeed.y * currentTime + 0.5 * GRAVITATION * currentTime * currentTime;

			if (newDrawingY <= 1e-14)
			{
				initSpeed.y *= RESTITUTION_C;

				if (initSpeed.y <= 1 + 1e-14)
				{
					initSpeed.y = 0;
					newDrawingY = -1;
				}

				setInitPos(new Point((int) newDrawingX, (int) -newDrawingY));
				setDrawingX((int) newDrawingX);
				setDrawingY((int) (parentContainerDimen.height + newDrawingY));
				currentTime = 0;
			}
			else
			{
				setDrawingX((int) newDrawingX);
				setDrawingY((int) (parentContainerDimen.height - newDrawingY));
			}
		}
	}

	public double getX(long timeMillis)
	{
		double time = 1.0 * timeMillis / 1000;
		return initPos.getX() + initSpeed.getX() * time;
	}

	public double getY(long timeMillis)
	{
		double time = 1.0 * timeMillis / 1000;
		return initPos.getY() + initSpeed.getY() * time + 0.5 * GRAVITATION * time * time;
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
		this.y = y - WIDTH;
	}

	public void setSpeedX(double x)
	{
		initSpeed.setLocation(x, initSpeed.y);
	}

	public void setSpeedY(double y)
	{
		initSpeed.setLocation(initSpeed.x, y);
	}

	public Color getColor()
	{
		return color;
	}
}
