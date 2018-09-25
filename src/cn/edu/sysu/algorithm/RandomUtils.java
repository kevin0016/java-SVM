package cn.edu.sysu.algorithm;

import java.util.List;
import java.util.Random;

/**
 * 随机数字辅助类
 * @author leafxt
 *
 */

public class RandomUtils {
	
	private static Random random = new Random();
	
	/**
	 * 随机抽签函数
	 * @param probability   概率
	 * @param max			最大值
	 * @return
	 */
	public static boolean draw(int probability, int max) {
		int randomNumber = random.nextInt(max);
		return randomNumber < probability;
	}

	public static boolean drawByMil(int probability) {
		return draw(probability, 1000000);
	}
	
	public static boolean drawByTenthousand(int probability) {
		return draw(probability, 10000);
	}
	
	public static boolean drawByThousand(int probability) {
		return draw(probability, 1000);
	}
	
	public static boolean drawByHundred(int probability) {
		return draw(probability, 100);
	}
	
	public static int nextInt() {
		return random.nextInt();
	}
	
	public static int nextInt(int bound) {
		return random.nextInt(bound);
	}
	
	/**
	 * 轮盘, 需确保正数
	 * @param powers
	 * @return
	 */
	public static int turn(List<Integer> powers) {
		int total = 0;
		for (int p : powers) {
			total += p;
		}
		int point = random.nextInt(total);
		int power = 0;
		for (int i = 0; i < powers.size(); i++) {
			power += powers.get(i);
			if (point < power) {
				return i;
			}
		}
		return 0;
	}
	
}
