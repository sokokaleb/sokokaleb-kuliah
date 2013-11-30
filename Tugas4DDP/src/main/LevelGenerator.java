package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import object.TargetObject;

/**
 * Kelas statik yang berfungsi untuk meng-generate level dari game ini :3
 * 
 * @author Pusaka Kaleb Setyabudi - 1306398945
 * 
 */
public class LevelGenerator
{
	private static final int[] goodGuyCount =
	{ 24, 24, 24, 22, 22, 22, 16, 16, 16, 14, 14, 12, 0 };

	private static final int[] minimumScoreToAchieve =
	{ 100, 200, 200, 100, 140, 140, 10, 20, 30, 10, 20, 30, 1 };

	private static final int[] ballLimit =
	{ 8, 8, 4, 8, 8, 4, 8, 8, 8, 2, 4, 6, 100 };

	private static final int TOTAL_LEVEL = ballLimit.length;
	private static final int HGAP = 40;
	private static final int VGAP = 60;
	private static final int XPAD = 740;
	private static final int YPAD = 54;
	private static final int ROW = 6;
	private static final int COLUMN = 4;

	private static List<TargetObject> arr = new ArrayList<TargetObject>();

	/**
	 * Method yang berguna untuk menggenerate objek-objek target dari level
	 * <em>level</em>.
	 * 
	 * @param level
	 *            Level yang objek-objek-nya akan di-generate
	 * @return List berisi objek-objek target untuk level <em>level</em>
	 */
	public static synchronized List<TargetObject> generateTargets(int level)
	{
		arr.clear();
		for (int i = 0; i < goodGuyCount[level]; ++i)
			arr.add(new TargetObject(TargetObject.GOOD_GUY));
		for (int i = goodGuyCount[level]; i < ROW * COLUMN; ++i)
			arr.add(new TargetObject(TargetObject.BAD_GUY));
		for (int i = 0; i < 5; ++i)
			Collections.shuffle(arr);
		for (int i = 0; i < ROW; ++i)
			for (int j = 0; j < COLUMN; ++j)
				arr.get(i * COLUMN + j).setPos(XPAD + j * (TargetObject.TARGET_HW + HGAP), YPAD + i * (TargetObject.TARGET_HW + VGAP));
		return arr;
	}

	/**
	 * Mendapatkan batasan jumlah bola dari level <em>level</em>.
	 * 
	 * @param level
	 *            Level yang limit bola-nya akan diambil
	 * @return Limit bola dari level <em>level</em>
	 */
	public static int getBallLimit(int level)
	{
		return ballLimit[level];
	}

	/**
	 * Mendapatkan skor minimal yang harus dicapai di level <em>level</em>.
	 * 
	 * @param level
	 *            Level yang skor minimalnya ingin didapat
	 * @return Skor minimal dari level <em>level</em>
	 */
	public static int getMinimumAchievedScore(int level)
	{
		return minimumScoreToAchieve[level];
	}

	/**
	 * Mendapatkan lebar dari seluruh objek-objek target yang digambar.
	 * 
	 * @return Lebar seluruh 'pasukan' objek target
	 */
	public static int getWidth()
	{
		return COLUMN * TargetObject.TARGET_HW + COLUMN * VGAP - VGAP;
	}

	/**
	 * Mendapatkan tinggi dari seluruh objek-objek target yang digambar.
	 * 
	 * @return Tinggi seluruh 'pasukan' objek target
	 */
	public static int getHeight()
	{
		return ROW * TargetObject.TARGET_HW + ROW * HGAP - HGAP;
	}

	/**
	 * Mendapatkan total banyaknya level yang ada di game ini.
	 * 
	 * @return Total level yang ada untuk game ini
	 */
	public static int getTotalLevel()
	{
		return TOTAL_LEVEL;
	}
}
