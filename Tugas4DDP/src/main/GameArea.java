package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;

import object.Ball;
import object.TargetObject;

/**
 * Kelas yang meng-extend JComponent, berguna sebagai kelas yang mengendalikan
 * penggambaran benda-benda yang ada di area game
 * 
 * @author Pusaka Kaleb Setyabudi - 1306398945
 * 
 */
@SuppressWarnings("serial")
public class GameArea extends JComponent implements MouseInputListener, MouseWheelListener
{
	private final static Color BALL_CLIP_COLOR = new Color(255, 100, 100, 255);
	private final static Color SKY_COLOR = new Color(200, 200, 255, 255);
	private final static Color TEXT_CLIP_COLOR = new Color(255, 210, 210, 255);
	private final static Color TRANSLUCENT_BLACK = new Color(0, 0, 0, 100);
	private final static Color TRANSLUCENT_WHITE = new Color(255, 255, 255, 100);
	private final static Font CLICK_TO_CONTINUE_FONT = new Font(MainFrame.DEFAULT_FONT_FAMILY, Font.BOLD, 20);
	private final static Font DEFAULT_FONT = new Font(MainFrame.DEFAULT_FONT_FAMILY, Font.BOLD, 30);
	private final static Font LEVEL_INTRO_FONT = new Font(MainFrame.DEFAULT_FONT_FAMILY, Font.BOLD, 80);
	private final static Font LEVEL_ACHIEVEMENT_FONT = new Font(MainFrame.DEFAULT_FONT_FAMILY, Font.BOLD, 45);
	private final static String CLICK_TO_CONTINUE_STRING = "click anywhere to continue";

	private int gameState = GameExecutor.GAME_STATE_NORMAL_STATE;

	private Dimension dimen;
	private Ellipse2D.Double ballAreaClip = new Ellipse2D.Double(0, 0, GameExecutor.CLICK_AREA_RADIUS * 2, GameExecutor.CLICK_AREA_RADIUS * 2);
	private GameExecutor gameExecutor;

	/**
	 * Constructor utama dari kelas GameArea. Pemanggilan constructor ini akan
	 * mengembalikan sebuah objek GameArea, yang lebarnya menyesuaikan dengan
	 * objek dari kelas SidePanel
	 */
	public GameArea()
	{
		Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		setPreferredSize(new Dimension(bounds.width - SidePanel.TOTAL_WIDTH, bounds.height));
		setOpaque(true);

		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
	}

	/**
	 * Berfungsi untuk menginisialisasikan beberapa instance variable dari kelas
	 * ini. Dipanggil HANYA SETELAH frame luar telah visible dan memiliki
	 * ukuran.
	 */
	public void initialize()
	{
		dimen = getSize();
		ballAreaClip.x = -GameExecutor.CLICK_AREA_RADIUS;
		ballAreaClip.y = dimen.getHeight() - GameExecutor.CLICK_AREA_RADIUS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g)
	{
		paintComponent(g);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	protected void paintComponent(Graphics g)
	{
		paintComponent((Graphics2D) g);
	}

	/**
	 * Method tambahan untuk menggambar, sama dengan paintComponent yang
	 * dimiliki JComponent, namun memiliki parameter bertipe Graphics2D. Method
	 * ini mengatur seluruh penggambaran untuk setiap state game yang berbeda.s
	 * 
	 * @param g
	 *            Objek pengontrol penggambaran
	 */
	private synchronized void paintComponent(Graphics2D g)
	{
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setClip(0, 0, dimen.width, dimen.height);

		g.setColor(SKY_COLOR);
		g.fill(new Rectangle(0, 0, dimen.width, dimen.height));

		if (gameState != GameExecutor.GAME_STATE_LEVEL_INTRO)
		{
			g.setColor(BALL_CLIP_COLOR);
			g.fill(ballAreaClip);

			if (gameState != GameExecutor.GAME_STATE_NORMAL_STATE)
			{
				g.setColor(TRANSLUCENT_WHITE);
				g.fill(new Rectangle(0, 0, dimen.width, dimen.height));
			}
		}

		switch (gameState)
		{
			case GameExecutor.GAME_STATE_NORMAL_STATE:
			{
				String message = String.format("Level %d", gameExecutor.getCurrentLevel() + 1);

				g.setColor(TRANSLUCENT_BLACK);
				FontMetrics fm;
				int stringWidth, stringAccent;
				int xCoor, yCoor;

				g.setFont(DEFAULT_FONT);
				fm = g.getFontMetrics(DEFAULT_FONT);

				stringWidth = fm.stringWidth(message);
				stringAccent = fm.getAscent();

				xCoor = 20;
				yCoor = stringAccent + 20;

				g.drawString(message, xCoor, yCoor);

				g.setFont(DEFAULT_FONT);
				g.setColor(TEXT_CLIP_COLOR);
				g.drawString("Clickable Area", 40, (int) dimen.getHeight() - 20);

				for (Ball i : gameExecutor.getBallList())
				{
					g.setColor(i.getColor());
					g.fill(i);

					g.setStroke(new BasicStroke(2));
					g.setColor(i.getStrokeColor());
					g.draw(i);

				}

				for (TargetObject i : gameExecutor.getTargetList())
				{
					g.setColor(i.getColor());
					g.fill(i);

					g.setStroke(new BasicStroke(1.5f));
					g.setColor(i.getStrokeColor());
					g.draw(i);
				}
				break;
			}
			case GameExecutor.GAME_STATE_LEVEL_INTRO:
			{
				String message = String.format("Level %d", gameExecutor.getCurrentLevel() + 1);

				g.setColor(Color.BLACK);
				FontMetrics fm;
				int stringWidth, stringAccent;
				int xCoor, yCoor;

				g.setFont(LEVEL_INTRO_FONT);
				fm = g.getFontMetrics(LEVEL_INTRO_FONT);

				stringWidth = fm.stringWidth(message);
				stringAccent = fm.getAscent();

				xCoor = dimen.width / 2 - stringWidth / 2;
				yCoor = dimen.height / 2 - stringAccent / 5;

				g.drawString(message, xCoor, yCoor);

				message = String.format("target score: %d  |  ball given: %d", LevelGenerator.getMinimumAchievedScore(gameExecutor.getCurrentLevel()), LevelGenerator.getBallLimit(gameExecutor.getCurrentLevel()));

				g.setFont(LEVEL_ACHIEVEMENT_FONT);
				fm = g.getFontMetrics(LEVEL_ACHIEVEMENT_FONT);

				stringWidth = fm.stringWidth(message);
				stringAccent = fm.getAscent();

				xCoor = dimen.width / 2 - stringWidth / 2;
				yCoor = stringAccent + yCoor + 20;

				g.drawString(message, xCoor, yCoor);

				g.setColor(Color.DARK_GRAY);

				g.setFont(CLICK_TO_CONTINUE_FONT);
				fm = g.getFontMetrics(CLICK_TO_CONTINUE_FONT);

				stringWidth = fm.stringWidth(CLICK_TO_CONTINUE_STRING);
				stringAccent = fm.getAscent();

				xCoor = dimen.width / 2 - stringWidth / 2;
				yCoor = stringAccent + yCoor + 20;

				g.drawString(CLICK_TO_CONTINUE_STRING, xCoor, yCoor);
				break;
			}
			case GameExecutor.GAME_STATE_LEVEL_ADVANCE:
			{
				String message = String.format("Level %d cleared!", gameExecutor.getCurrentLevel() + 1);

				g.setColor(Color.BLACK);
				FontMetrics fm;
				int stringWidth, stringAccent;
				int xCoor, yCoor;

				g.setFont(LEVEL_INTRO_FONT);
				fm = g.getFontMetrics(LEVEL_INTRO_FONT);

				stringWidth = fm.stringWidth(message);
				stringAccent = fm.getAscent();

				xCoor = dimen.width / 2 - stringWidth / 2;
				yCoor = dimen.height / 2 - stringAccent / 2;

				g.drawString(message, xCoor, yCoor);

				message = String.format("level score: %d / %d  |  bonus: 20 \u00d7 %d bola", gameExecutor.getCurrentScore(), LevelGenerator.getMinimumAchievedScore(gameExecutor.getCurrentLevel()), gameExecutor.getBallLeft());

				g.setFont(LEVEL_ACHIEVEMENT_FONT);
				fm = g.getFontMetrics(LEVEL_ACHIEVEMENT_FONT);

				stringWidth = fm.stringWidth(message);
				stringAccent = fm.getAscent();

				xCoor = dimen.width / 2 - stringWidth / 2;
				yCoor = stringAccent + yCoor + 20;

				g.drawString(message, xCoor, yCoor);

				message = String.format("total score: %d", gameExecutor.getTotalScore() + gameExecutor.getBonusScore());

				g.setFont(LEVEL_ACHIEVEMENT_FONT);
				fm = g.getFontMetrics(LEVEL_ACHIEVEMENT_FONT);

				stringWidth = fm.stringWidth(message);
				stringAccent = fm.getAscent();

				xCoor = dimen.width / 2 - stringWidth / 2;
				yCoor = stringAccent + yCoor + 20;

				g.drawString(message, xCoor, yCoor);

				break;
			}
			case GameExecutor.GAME_STATE_GAME_OVER:
			{
				String message = "GAME OVER";

				g.setColor(Color.BLACK);
				FontMetrics fm;
				int stringWidth, stringAccent;
				int xCoor, yCoor;

				g.setFont(LEVEL_INTRO_FONT);
				fm = g.getFontMetrics(LEVEL_INTRO_FONT);

				stringWidth = fm.stringWidth(message);
				stringAccent = fm.getAscent();

				xCoor = dimen.width / 2 - stringWidth / 2;
				yCoor = dimen.height / 2;

				g.drawString(message, xCoor, yCoor);

				message = String.format("Your total score: %d", gameExecutor.getTotalScore());

				g.setFont(LEVEL_INTRO_FONT);
				fm = g.getFontMetrics(LEVEL_INTRO_FONT);

				stringWidth = fm.stringWidth(message);
				stringAccent = fm.getAscent();

				xCoor = dimen.width / 2 - stringWidth / 2;
				yCoor = stringAccent + yCoor + 20;

				g.drawString(message, xCoor, yCoor);

				break;
			}
			default:
			{
				break;
			}
		}
		g.dispose();
	}

	/**
	 * Method untuk mengatur state dari penggambaran, berdasarkan state yang
	 * diberikan.
	 * 
	 * @param gameState
	 */
	public void setDrawState(int gameState)
	{
		this.gameState = gameState;
	}

	/**
	 * Method untuk mem-pass suatu objek GameExecutor, dan disimpan sebagai
	 * instance variable dari objek kelas ini.
	 * 
	 * @param gameExecutor
	 */
	public void setGameExecutor(GameExecutor gameExecutor)
	{
		this.gameExecutor = gameExecutor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (SwingUtilities.isLeftMouseButton(e))
		{
			switch (gameState)
			{
				case GameExecutor.GAME_STATE_NORMAL_STATE:
				{
					Point mousePos = e.getPoint();
					mousePos.setLocation(mousePos.x, dimen.height - mousePos.y);
					if (mousePos.x * mousePos.x + mousePos.y * mousePos.y <= GameExecutor.CLICK_AREA_RADIUS_SQUARED)
						gameExecutor.addBall(mousePos);
					break;
				}
				case GameExecutor.GAME_STATE_LEVEL_INTRO:
				{
					gameExecutor.startLevel();
					break;
				}
				case GameExecutor.GAME_STATE_LEVEL_ADVANCE:
				{
					gameExecutor.advanceLevel();
					break;
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public synchronized void mousePressed(MouseEvent e)
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e)
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e)
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent
	 * )
	 */
	@Override
	public void mouseDragged(MouseEvent e)
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e)
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseWheelListener#mouseWheelMoved(java.awt.event.
	 * MouseWheelEvent)
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		switch (gameState)
		{
			case GameExecutor.GAME_STATE_NORMAL_STATE:
			{
				boolean upDirection = (e.getWheelRotation() < 0);
				if (upDirection)
					gameExecutor.addBallTimeVisibleLimit(100);
				else
					gameExecutor.addBallTimeVisibleLimit(-100);
			}
		}
	}
}
