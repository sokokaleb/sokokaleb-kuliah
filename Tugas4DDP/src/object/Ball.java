package object;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import main.MainFrame;

/**
 * Kelas yang merepresentasikan bola-bola penembak
 * 
 * @author Pusaka Kaleb Setyabudi - 1306398945
 * 
 */
@SuppressWarnings("serial")
public class Ball extends Ellipse2D.Double
{
	public static final double GRAVITATION = -9.81;
	public static final double WIDTH = 30;

	public static final Point2D.Double DRAWING_POINT = new Point2D.Double(0.0, 0.0);

	private static final double ALPHA_DECRASING_FACTOR = 0.9;
	private static final double RESTITUTION_C = 0.8;
	private static final double SPEED_MANIP = 0.5;
	private static final double TIME_INTERVAL = 0.06;
	private static final int BARELY_VISIBLE_ALPHA_THRESHOLD = 50;

	private boolean isVisible = true;
	private double currentTime;
	private int timeVisibleLimit;
	private int visibleDuration;

	private Color color, strokeColor;
	private Dimension parentContainerDimen;
	private Point initPos;
	private Point initSpeed;

	/**
	 * Blank Constructor dari kelas Ball. Men-set posisi gambar menjadi (0,0)
	 * secara default.
	 */
	public Ball()
	{
		initPos = new Point(0, 0);
		width = height = WIDTH;
	}

	/**
	 * Constructor dari kelas Ball yang disertai parameter kecepatan awal,
	 * batasan waktu visibilitas dari bola, warna bola, serta kontainer/panel
	 * tempat bola digambar.
	 * 
	 * @param initSpeed
	 *            Kecepatan awal bola
	 * @param timeVisibleLimit
	 *            Waktu batasan visibilitas bola
	 * @param color
	 *            Warna dari bola
	 * @param container
	 *            Kontainer parent dari bola
	 */
	public Ball(Point initSpeed, int timeVisibleLimit, Color color, Container container)
	{
		this.initPos = new Point(0, 0);
		this.initSpeed = initSpeed;
		this.timeVisibleLimit = timeVisibleLimit;
		this.color = color;
		strokeColor = Color.BLACK;
		width = height = WIDTH;
		parentContainerDimen = container.getSize();

		// Manipulating speed, make the speed not too fast >_<"
		this.initSpeed.x *= SPEED_MANIP;
		this.initSpeed.y *= SPEED_MANIP;
	}

	/**
	 * Method untuk meng-update posisi bola berdasarkan waktu.
	 */
	public synchronized void update()
	{
		if (isVisible)
		{
			currentTime += TIME_INTERVAL;
			visibleDuration += MainFrame.UPDATE_FQ;
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

			if (visibleDuration > timeVisibleLimit && color.getAlpha() > 0)
			{
				color = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (color.getAlpha() * ALPHA_DECRASING_FACTOR));
				strokeColor = new Color(strokeColor.getRed(), strokeColor.getGreen(), strokeColor.getBlue(), (int) (strokeColor.getAlpha() * ALPHA_DECRASING_FACTOR));
			}

			if (color.getAlpha() == 0 || newDrawingX > parentContainerDimen.width)
				isVisible = false;
		}
	}

	/**
	 * Men-set posisi awal dari bola.
	 * 
	 * @param initPos
	 *            Posisi awal bola
	 */
	public void setInitPos(Point initPos)
	{
		this.initPos = initPos;
	}

	/**
	 * Men-set posisi koordinat x gambar bola.
	 * 
	 * @param x
	 *            Koordinat x penggambaran bola
	 */
	public void setDrawingX(int x)
	{
		this.x = x;
	}

	/**
	 * Men-set posisi koordinat y gambar bola.
	 * 
	 * @param y
	 *            Koordinat y penggambaran bola
	 */
	public void setDrawingY(int y)
	{
		this.y = y - WIDTH;
	}

	/**
	 * Mendapatkan warna dari bola.
	 * 
	 * @return Warna dari bola
	 */
	public Color getColor()
	{
		return color;
	}

	/**
	 * Mendapatkan warna border dari bola.
	 * 
	 * @return Warna border
	 */
	public Color getStrokeColor()
	{
		return strokeColor;
	}

	/**
	 * Mendapatkan state mengenai visibilitas bola.
	 * 
	 * @return State visibilitas bola
	 */
	public boolean isVisible()
	{
		return isVisible;
	}

	/**
	 * Mendapatkan state mengenai apakah bola dapat terlihat jelas oleh mata.
	 * Threshold alpha 50.
	 * 
	 * @return State visibilitas bola oleh mata
	 */
	public boolean hasVisibleAlpha()
	{
		return color.getAlpha() >= BARELY_VISIBLE_ALPHA_THRESHOLD;
	}

	/**
	 * Mendapatkan durasi visibilitas bola.
	 * 
	 * @return Durasi visibilitas bola
	 */
	public int getVisibleDuration()
	{
		return visibleDuration;
	}

	/**
	 * Mendapatkan sisa durasi visibilitas bola.
	 * 
	 * @return Sisa durasi visibilitas bola
	 */
	public int getRemainingVisibleDuration()
	{
		return timeVisibleLimit - visibleDuration;
	}
}
