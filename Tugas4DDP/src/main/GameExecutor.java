package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import object.Ball;

public class GameExecutor
{
	protected final static int CLICK_AREA_RADIUS_SQUARED = 200 * 200 + 250 * 250;
	protected final static int CLICK_AREA_RADIUS = (int) Math.sqrt(CLICK_AREA_RADIUS_SQUARED);

	private final static int SHOOT_TIME_INTERVAL = 1;
	private final int SHOOT_INTERVAL_DURATION_MS = 107;
	private int shootTimeCounter;
	private List<Ball> ballList;
	private boolean isShootable = true;

	private GameArea gameArea;
	private SidePanel sidePanel;

	private Random rGen;

	Runnable graphicsUpdate = new Runnable()
	{
		@Override
		public void run()
		{
			updateBalls();
			gameArea.repaint();
		}
	};

	Runnable sideUpdate = new Runnable()
	{

		@Override
		public void run()
		{
			updateShootTimer();
		}
	};

	public GameExecutor(GameArea gameArea, SidePanel sidePanel)
	{
		this.gameArea = gameArea;
		this.sidePanel = sidePanel;
		rGen = new Random();
		// addMouseListener(this);
		// addMouseMotionListener(this);

		gameArea.initialize();
		gameArea.setGameExecutor(this);

		ballList = new ArrayList<Ball>();
		System.out.println(ballList);
	}

	public void start()
	{
		Thread graphicThread = new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				try
				{
					while (true)
					{
						if (EventQueue.isDispatchThread())
							graphicsUpdate.run();
						else
						{
							try
							{
								EventQueue.invokeAndWait(graphicsUpdate);
							}
							catch (InvocationTargetException | InterruptedException e)
							{
							}
						}
						Thread.sleep(MainFrame.UPDATE_FQ);
					}
				}
				catch (InterruptedException e)
				{
				}
			}
		});
		Thread sideThread = new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				try
				{
					while (true)
					{
						if (EventQueue.isDispatchThread())
							sideUpdate.run();
						else
						{
							try
							{
								EventQueue.invokeAndWait(sideUpdate);
							}
							catch (InvocationTargetException | InterruptedException e)
							{
							}
						}
						Thread.sleep(MainFrame.UPDATE_FQ);
					}
				}
				catch (InterruptedException e)
				{
				}
			}
		});
		graphicThread.start();
		sideThread.start();
	}

	protected synchronized void updateBalls()
	{
		// List<Ball> tempList = new ArrayList<Ball>();

		int backQueue = 0;
		for (int i = 0; i < ballList.size(); ++i)
		{
			Ball ball = ballList.get(i);
			ball.updateBallPosition();
			if (!ball.isVisible() || ball.x > gameArea.getSize().width)
				; // deletes ball
			else ballList.set(backQueue++, ball);
		}

		for (; ballList.size() > backQueue;)
			ballList.remove(ballList.get(ballList.size() - 1));
	}

	private synchronized void updateShootTimer()
	{
		if (!shootable())
		{
			if (shootTimeCounter >= SHOOT_INTERVAL_DURATION_MS)
				setShootable(true);
			else shootTimeCounter += SHOOT_TIME_INTERVAL;
			sidePanel.updateShootableText(shootTimeCounter, SHOOT_INTERVAL_DURATION_MS);
		}
	}

	private synchronized void resetShootTimer()
	{
		setShootable(false);
		shootTimeCounter = 0;
	}

	public void addBall(Point mousePos)
	{
		if (shootable())
		{
			ballList.add(new Ball(mousePos, new Color(rGen.nextInt(128), rGen.nextInt(128), rGen.nextInt(128)), gameArea));
			ballList.get(ballList.size() - 1).setDrawingY(gameArea.getSize().height);
			resetShootTimer();
		}
	}

	public synchronized List<Ball> getBallList()
	{
		return ballList;
	}

	private synchronized void setShootable(boolean state)
	{
		isShootable = state;
	}

	private synchronized boolean shootable()
	{
		return isShootable;
	}
}
