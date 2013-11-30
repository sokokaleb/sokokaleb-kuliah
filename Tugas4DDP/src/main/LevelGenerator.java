package main;

import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Collections;

import object.TargetObject;

public class LevelGenerator
{
	public static final int HGAP = 10;
	public static final int VGAP = 10;
	private final int ROW = 6;
	private final int COLUMN = 4;
	private ArrayList<TargetObject> arr;
	private int currentLevel = 0;

	public LevelGenerator()
	{

	}

	public int getCurrentLevel()
	{
		return currentLevel;
	}

	public ArrayList<TargetObject> generateTargets(int difficulty)
	{
		arr.clear();
		for (int i = 0; i < difficulty; ++i)
			arr.add(new TargetObject(TargetObject.BAD_GUY));
		for (int i = difficulty; i < ROW * COLUMN; ++i)
			arr.add(new TargetObject(TargetObject.GOOD_GUY));
		for (int i = 0; i < 5; ++i)
			Collections.shuffle(arr);
		for (int i = 0; i < ROW; ++i)
			for (int j = 0; j < COLUMN; ++j)
				arr.get(i * COLUMN + j).setPos(i * (TargetObject.TARGET_HW + VGAP), j * (TargetObject.TARGET_HW + HGAP));
		return arr;
	}
}
