package main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import object.Ball;
import object.TargetObject;

/**
 * Kelas yang berguna untuk mengeksekusi dan mengatur mekanisme game.
 * 
 * @author Pusaka Kaleb Setyabudi - 1306398945
 * 
 */
public class GameExecutor
{
	public final static int GAME_STATE_LEVEL_INTRO = 0;
	public final static int GAME_STATE_NORMAL_STATE = 1;
	public final static int GAME_STATE_GAME_OVER = 2;
	public final static int GAME_STATE_LEVEL_ADVANCE = 3;
	public final static int CLICK_AREA_RADIUS_SQUARED = 200 * 200 + 250 * 250;
	public final static int CLICK_AREA_RADIUS = (int) Math.sqrt(CLICK_AREA_RADIUS_SQUARED);
	public final static int DEFAULT_TIME_VISIBLE_LIMIT = 2000;

	private final static int SHORTEST_BALL_LIFETIME = 1000;
	private final static int LONGEST_BALL_LIFETIME = 7000;
	private final static int SHOOT_TIME_INTERVAL = 1;
	private final static int SHOOT_INTERVAL_DURATION = 205;
	private final static int BALL_EXTRA_SCORE = 20;

	private static int currentBallTimeVisibleLimit = 2000;

	private int bonusScore;
	private int ballLeft;
	private int currentLevel;
	private int currentScore;
	private int minimumScore;
	private int shootTimeCounter = SHOOT_INTERVAL_DURATION;
	private int totalScore;
	private boolean isShootable = true;
	private boolean targetListFilled;

	private List<Ball> ballList;
	private List<TargetObject> targetList;

	private int gameState = GAME_STATE_NORMAL_STATE;

	private GameArea gameArea;
	private Random rGen;
	private SidePanel sidePanel;

	/**
	 * Objek runnable yang mengatur mengenai mekanisme bola dan target
	 * penembakan, serta men-trigger penggambaran. Pemanggilannya akan diatur
	 * dalam suatu thread tersendiri.
	 */
	Runnable graphicsUpdate = new Runnable()
	{
		@Override
		public void run()
		{
			checkGameState();
			updateBallsAndTargets();
			gameArea.setDrawState(gameState);
			gameArea.repaint();
		}
	};

	/**
	 * Objek runnable yang mengatur mengenai mekanisme update informasi di
	 * SidePanel. Pemanggilannya akan diatur dalam suatu thread tersendiri.
	 */
	Runnable sideUpdate = new Runnable()
	{
		@Override
		public void run()
		{
			updateShootTimer();
			sidePanel.updateCurrentBallLeft(ballLeft);
			sidePanel.setGlobalAndCurrentScore(totalScore, currentScore, minimumScore);
			sidePanel.updateShootableText(shootTimeCounter, SHOOT_INTERVAL_DURATION);
		}
	};

	/**
	 * Constructor dari GameConstructor, mem-pass objek GameArea dan SidePanel
	 * yang digunakan dalam game.
	 * 
	 * @param gameArea
	 *            Objek GameArea yang digunakan
	 * @param sidePanel
	 *            Objek SidePanel yang digunakan
	 */
	public GameExecutor(GameArea gameArea, SidePanel sidePanel)
	{
		this.gameArea = gameArea;
		this.sidePanel = sidePanel;

		rGen = new Random();

		gameArea.initialize();
		gameArea.setGameExecutor(this);

		ballList = new ArrayList<Ball>();
		targetList = new ArrayList<TargetObject>();
	}

	/**
	 * Method yang digunakan untuk memulai jalannya game.
	 */
	public void start()
	{
		gameState = GAME_STATE_LEVEL_INTRO;

		/*
		 * Membuat suatu thread yang mengatur jalannya penggambaran
		 * bentuk-bentuk di game
		 */
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

		/*
		 * Membuat suatu thread yang mengatur jalannya update-update elemen dan
		 * objek
		 */
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

		/*
		 * Menjalankan semua thread secara bersamaan
		 */
		graphicThread.start();
		sideThread.start();
	}

	/**
	 * Method untuk memulai menjalankan game dengan level berdasarkan
	 * <em>currentLevel</em>.
	 */
	public synchronized void startLevel()
	{
		startLevel(currentLevel);
		gameState = GAME_STATE_NORMAL_STATE;
		setTargetListFilled(false);
	}

	/**
	 * Method untuk memulai menjalankan game dengan level berdasarkan
	 * <em>currentLevel</em>.
	 * 
	 * @param level
	 *            Level sekarang di game
	 */
	private synchronized void startLevel(int level)
	{
		bonusScore = 0;
		currentScore = 0;
		minimumScore = LevelGenerator.getMinimumAchievedScore(level);
		ballLeft = LevelGenerator.getBallLimit(level);
		targetList = LevelGenerator.generateTargets(level);
	}

	/**
	 * Method untuk meng-advance level game.
	 */
	public synchronized void advanceLevel()
	{
		++currentLevel;
		totalScore += bonusScore;
		gameState = GAME_STATE_LEVEL_INTRO;
	}

	/**
	 * Method yang berfungsi untuk pengecekan state game serta mengganti state
	 * game berdasarkan keadaan game sekarang.
	 */
	public synchronized void checkGameState()
	{
		if (gameState == GAME_STATE_LEVEL_INTRO)
			;
		else if (gameState == GAME_STATE_NORMAL_STATE)
		{
			if (currentScore >= minimumScore)
			{
				if (ballList.size() == 0)
				{
					bonusScore = ballLeft * BALL_EXTRA_SCORE;
					gameState = GAME_STATE_LEVEL_ADVANCE;
				}
			}
			else
			{
				if (ballLeft == 0 && ballList.size() == 0)
				{
					gameState = GAME_STATE_GAME_OVER;
				}
			}
		}
	}

	/**
	 * Method yang berguna untuk mengupdate keadaan bola dan target sasaran.
	 */
	private synchronized void updateBallsAndTargets()
	{
		int backQueue = 0;
		for (int i = 0; i < ballList.size(); ++i)
		{
			Ball ball = ballList.get(i);
			ball.update();
			if (!ball.isVisible() || ball.x > gameArea.getSize().width)
				; // deletes ball
			else if (ball.hasVisibleAlpha())
			{
				for (int j = 0; j < targetList.size(); ++j)
				{
					if (targetList.get(j).getAliveState())
					{
						if (ball.getBounds().intersects(targetList.get(j).getBounds()))
						{
							addScore(targetList.get(j).getValue());
							targetList.get(j).setAliveState(false);
						}
					}
				}
				ballList.set(backQueue++, ball);
			}

		}

		for (int j = 0; j < targetList.size(); ++j)
			targetList.get(j).update();

		for (; ballList.size() > backQueue;)
			ballList.remove(ballList.get(ballList.size() - 1));

		if (!isTargetListFilled())
		{
			targetList = LevelGenerator.generateTargets(currentLevel);
			setTargetListFilled(true);
		}
	}

	/**
	 * Method yang berfungsi untuk mengupdate timer penembakan.
	 */
	private synchronized void updateShootTimer()
	{
		if (!shootable())
		{
			if (shootTimeCounter >= SHOOT_INTERVAL_DURATION)
				setShootable(true);
			else
				shootTimeCounter += SHOOT_TIME_INTERVAL;
		}
	}

	/**
	 * Method yang berfungsi untuk me-reset timer penembakan.
	 */
	private synchronized void resetShootTimer()
	{
		setShootable(false);
		shootTimeCounter = 0;
	}

	/**
	 * Mendapatkan keadaan apakah list target terisi apa tidak.
	 * 
	 * @return Keadaan apakah targetList terisi apa tidak.
	 */
	private synchronized boolean isTargetListFilled()
	{
		return targetListFilled;
	}

	/**
	 * Method untuk meng-set keadaan list target, kosong apa tidak.
	 * 
	 * @param state
	 *            Keadaan list target
	 */
	private synchronized void setTargetListFilled(boolean state)
	{
		targetListFilled = state;
	}

	/**
	 * Method untuk menambahkan bola ke dalam list bola yang ada, berdasarkan
	 * koordinat yang diberikan.
	 * 
	 * @param mousePos
	 *            Koordinat peng-klik-an mouse
	 */
	public synchronized void addBall(Point mousePos)
	{
		if (shootable())
		{
			--ballLeft;
			ballList.add(new Ball(mousePos, currentBallTimeVisibleLimit, new Color(rGen.nextInt(200), rGen.nextInt(200), rGen.nextInt(200)), gameArea));
			ballList.get(ballList.size() - 1).setDrawingY(gameArea.getSize().height);
			resetShootTimer();
		}
	}

	/**
	 * Menambahkan/mengurangkan durasi visibilitas bola saat ditembakkan.
	 * 
	 * @param ms
	 *            Waktu penambahan/pengurangan visibilitas
	 */
	public synchronized void addBallTimeVisibleLimit(int ms)
	{
		currentBallTimeVisibleLimit += ms;
		currentBallTimeVisibleLimit = Math.max(SHORTEST_BALL_LIFETIME, currentBallTimeVisibleLimit);
		currentBallTimeVisibleLimit = Math.min(LONGEST_BALL_LIFETIME, currentBallTimeVisibleLimit);
		sidePanel.updateCurrentBallDuration(currentBallTimeVisibleLimit);
	}

	/**
	 * Menambahkan <em>value</em> ke skor sementara.
	 * 
	 * @param value
	 *            Nilai yang akan ditambah/kurangkan.
	 */
	public void addScore(int value)
	{
		totalScore += value;
		currentScore += value;
		sidePanel.setGlobalAndCurrentScore(totalScore, currentScore, minimumScore);
	}

	/**
	 * Mengembalikan reference ke list bola-bola yang ditembakkan.
	 * 
	 * @return List bola-bola yang ditembakkan
	 */
	public synchronized List<Ball> getBallList()
	{
		return ballList;
	}

	/**
	 * Mengembalikan reference ke list objek-objek sasaran.
	 * 
	 * @return List ke objek-objek sasaran
	 */
	public synchronized List<TargetObject> getTargetList()
	{
		return targetList;
	}

	/**
	 * Menset state penembakan.
	 * 
	 * @param state
	 *            State penembakan
	 */
	private synchronized void setShootable(boolean state)
	{
		isShootable = state;
	}

	/**
	 * Mengembalikan apakah boleh menembak apa tidak.
	 * 
	 * @return State penembakan
	 */
	private synchronized boolean shootable()
	{
		return isShootable && (ballLeft > 0);
	}

	/**
	 * Mengembalikan batas durasi bola visibel.
	 * 
	 * @return Durasi visibilitas bola
	 */
	public int getBallVisibleTimeLimit()
	{
		return currentBallTimeVisibleLimit;
	}

	/**
	 * Mengembalikan level yang sedang dijalankan.
	 * 
	 * @return Current level
	 */
	public int getCurrentLevel()
	{
		return currentLevel;
	}

	/**
	 * Mengembalikan skor sementara (skor level)
	 * 
	 * @return Skor sementara level
	 */
	public int getCurrentScore()
	{
		return currentScore;
	}

	/**
	 * Mengembalikan skor total
	 * 
	 * @return Skor total
	 */
	public int getTotalScore()
	{
		return totalScore;
	}

	/**
	 * Mengembalikan skor bonus dari sisa bola.
	 * 
	 * @return Skor bonus
	 */
	public int getBonusScore()
	{
		return bonusScore;
	}

	/**
	 * Mengembalikan sisa bola yang dapat ditembakkan.
	 * 
	 * @return Sisa bola
	 */
	public int getBallLeft()
	{
		return ballLeft;
	}
}
