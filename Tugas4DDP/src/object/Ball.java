package object;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

@SuppressWarnings("serial")
public class Ball extends Ellipse2D.Double
{
	public static final double GRAVITATION = -9.81;
	public static final Point2D.Double DRAWING_POINT = new Point2D.Double(0.0, 0.0);
	public static final double WIDTH = 30;

	private static final double SPEED_MANIP = 0.5;
	private static final double RESTITUTION_C = 0.8;
	private static final double TIME_INTERVAL = 0.06;
	private static final double TIME_ALPHA_LIMIT_MULTIPLIER = 230;

	private Point initPos;
	private Point initSpeed;
	private double currentTime, visibleDuration;
	private Color color;
	private Dimension parentContainerDimen;

	// private Timer ballTimer;
	private boolean isVisible = true;

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

		// Manipulating speed, make the speed not too fast >_<"
		this.initSpeed.x *= SPEED_MANIP;
		this.initSpeed.y *= SPEED_MANIP;

		// ballTimer = new Timer();
		// ballTimer.schedule(new TimerTask()
		// {
		// @Override
		// public void run()
		// {
		// updateBallPosition();
		// if (x > parentContainerDimen.width)
		// {
		// isVisible = false;
		// this.cancel();
		// }
		// }
		// }, 0, MainFrame.UPDATE_FQ);

		// Thread ballThread = new Thread(new Runnable()
		// {
		// @Override
		// public void run()
		// {
		// while (isVisible)
		// {
		// updateBallPosition();
		// try
		// {
		// Thread.sleep(17);
		// }
		// catch (InterruptedException e)
		// {
		// }
		// }
		// }
		// });
		// ballThread.start();
	}

	public synchronized void updateBallPosition()
	{
		if (isVisible)
		{
			currentTime += TIME_INTERVAL;
			visibleDuration += TIME_INTERVAL;
			double newDrawingX = initPos.x + initSpeed.x * currentTime;
			double newDrawingY = initPos.y + initSpeed.y * currentTime + 0.5 * GRAVITATION * currentTime * currentTime;

			if (newDrawingY <= 1e-14)
			{
				initSpeed.y *= RESTITUTION_C;
				if (initSpeed.y <= 1e-14)
					initSpeed.y = -initSpeed.y;

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
			else if (newDrawingX >= parentContainerDimen.width - WIDTH)
			{
				initSpeed.x *= -1;

				setInitPos(new Point((int) (2 * (parentContainerDimen.width - WIDTH) - initPos.x), initPos.y));
				newDrawingX = initPos.x + initSpeed.x * currentTime;
				newDrawingY = initPos.y + initSpeed.y * currentTime + 0.5 * GRAVITATION * currentTime * currentTime;

				setDrawingX((int) newDrawingX);
				setDrawingY((int) (parentContainerDimen.height - newDrawingY));
			}
			else
			{
				setDrawingX((int) newDrawingX);
				setDrawingY((int) (parentContainerDimen.height - newDrawingY));
			}

			if (visibleDuration > TIME_INTERVAL * TIME_ALPHA_LIMIT_MULTIPLIER && color.getAlpha() > 0)
				color = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() - 5);

			if (color.getAlpha() == 0 || newDrawingX > parentContainerDimen.width)
				isVisible = false;
		}
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

	public Color getColor()
	{
		return color;
	}

	public boolean isVisible()
	{
		return isVisible;
	}
}
