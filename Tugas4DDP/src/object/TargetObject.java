package object;

import java.awt.Color;
import java.awt.geom.RoundRectangle2D;

/**
 * Kelas yang merepresentasikan benda yang bisa ditembak oleh bola-bola. Target
 * berbentuk persegi panjang dengan ujung bundar (Rounded Rectangle). Benda
 * target dibedakan sifat/value-nya berdasarkan warnanya. Warna hijau adalah
 * goodguy (value +10), dan warna coklat adalah badguy (value -50).
 * 
 * @author Pusaka Kaleb Setyabudi - 1306398945
 * 
 */
@SuppressWarnings("serial")
public class TargetObject extends RoundRectangle2D.Double
{
	public static Color BAD_GUY = new Color(200, 100, 0);
	public static Color GOOD_GUY = new Color(0, 200, 100);
	public static int TARGET_HW = 60;

	private boolean isAlive = true;
	private Color color, strokeColor;
	private int value;
	private int xPos, yPos;

	/**
	 * Constructor default dari kelas TargetObject, dengan parameter berupa
	 * warna penentu sifat dari target objek.
	 * 
	 * @param color
	 */
	public TargetObject(Color color)
	{
		this.color = color;
		strokeColor = Color.BLACK;
		width = height = TARGET_HW;

		determineValue(color);
	}

	/**
	 * Method untuk meng-update kondisi/state dari sebuah objek target
	 */
	public synchronized void update()
	{
		if (!getAliveState())
		{
			int newColorAlpha = color.getAlpha();
			if (newColorAlpha > 0)
			{
				newColorAlpha *= 0.8;
				color = new Color(color.getRed(), color.getGreen(), color.getBlue(), newColorAlpha);
			}

			newColorAlpha = strokeColor.getAlpha();
			if (newColorAlpha > 0)
			{
				newColorAlpha *= 0.8;
				strokeColor = new Color(strokeColor.getRed(), strokeColor.getGreen(), strokeColor.getBlue(), newColorAlpha);
			}
		}
	}

	/**
	 * Mengembalikan nilai skor yang akan diberikan apabila objek target ini
	 * tertembak.
	 * 
	 * @return Skor dari suatu objek target
	 */
	public int getValue()
	{
		return value;
	}

	/**
	 * Mengembalikan status apakah benda target masih bisa menjadi target
	 * tembakan atau tidak.
	 * 
	 * @return Status 'kehidupan' suatu objek target
	 */
	public boolean getAliveState()
	{
		return isAlive;
	}

	/**
	 * Men-set status apakah benda target masih bisa menjadi target tembakan
	 * atau tidak.
	 * 
	 * @param isAlive
	 *            status 'kehidupan' objek target
	 */
	public void setAliveState(boolean isAlive)
	{
		this.isAlive = isAlive;
		if (!isAlive)
			value = 0;
		else
			determineValue(color);
	}

	/**
	 * Method untuk menentukan sifat (value) dari objek target berdasarkan
	 * warna.
	 * 
	 * @param color
	 *            Warna dari objek target
	 */
	private void determineValue(Color color)
	{
		if (color.equals(GOOD_GUY))
			value = 10;
		else
			value = -20;
	}

	/**
	 * Method untuk men-set posisi dari objek target.
	 * 
	 * @param xPos
	 *            Koordinat x
	 * @param yPos
	 *            Koordinat y
	 */
	public void setPos(int xPos, int yPos)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		x = (int) xPos;
		y = (int) yPos;
	}

	/**
	 * Mengembalikan koordinat x dari objek target.
	 * 
	 * @return Koordinat x objek
	 */
	public int getPosX()
	{
		return xPos;
	}

	/**
	 * Mengembalikan koordinat y dari objek target.
	 * 
	 * @return Koordinat y objek
	 */
	public int getPosY()
	{
		return yPos;
	}

	/**
	 * Mengembalikan warna dari objek.
	 * 
	 * @return Warna objek
	 */
	public Color getColor()
	{
		return color;
	}

	/**
	 * Mengembalikan warna 'border'/stroke dari objek.
	 * 
	 * @return Warna border objek
	 */
	public Color getStrokeColor()
	{
		return strokeColor;
	}
}
