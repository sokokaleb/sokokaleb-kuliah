package object;

import java.awt.Color;
import java.awt.geom.RoundRectangle2D;

public class TargetObject extends RoundRectangle2D.Double
{
	public static Color BAD_GUY = new Color(200, 100, 0);
	public static Color GOOD_GUY = new Color(0, 200, 100);
	public static int TARGET_HW = 30;

	private boolean isAlive = true;
	private Color color;
	private int value;
	private int xPos, yPos;

	public TargetObject(Color color)
	{
		this.color = color;
		determineValue(color);
		width = height = TARGET_HW;
	}

	public int getValue()
	{
		return value;
	}

	public boolean getAliveState()
	{
		return isAlive;
	}

	public void setAliveState(boolean isAlive)
	{
		this.isAlive = isAlive;
		if (!isAlive)
			value = 0;
		else determineValue(color);
	}

	private void determineValue(Color color)
	{
		if (color.equals(GOOD_GUY))
		{
			value = 10;
		}
		else
		{
			value = -20;
		}
	}

	public void setPos(int xPos, int yPos)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		x = (int) xPos;
		y = (int) yPos;
	}

	public int getPosX()
	{
		return xPos;
	}

	public int getPosY()
	{
		return yPos;
	}
}
