package cn.edu.sysu.algorithm.genetic.function;

import java.text.DecimalFormat;
import java.util.function.BinaryOperator;

import cn.edu.sysu.algorithm.RandomUtils;

public class CrossFunction {

	/**
	 * 随机权重均值交叉(float)
	 */
	public static final BinaryOperator<Object> MEAN_FLOAT_CROSS = (gene1, gene2) -> {
		float power1 = RandomUtils.nextInt(100) / 100.0f;
		float power2 = 1.0f - power1;
		float g1 = Float.parseFloat(gene1.toString());
		float g2 = Float.parseFloat(gene2.toString());
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(g1 * power1 + g2 * power2);
	};

	/**
	 * 随机权重均值交叉(int)
	 */
	public static final BinaryOperator<Object> MEAN_INT_CROSS = (gene1, gene2) -> {
		float power1 = RandomUtils.nextInt(100) / 100.0f;
		float power2 = 1.0f - power1;
		int g1 = Integer.parseInt(gene1.toString());
		int g2 = Integer.parseInt(gene2.toString());
		return Integer.toString((int) (g1 * power1 + g2 * power2));
	};

	/**
	 * 二进制交叉编码, 返回数组含2个后代
	 */
	public static final BinaryOperator<Object> BINARY_CROSS = (gene1, gene2) -> {
		if (gene1.toString().length() == gene2.toString().length()) {
			int length = gene1.toString().length();
			int start = RandomUtils.nextInt(length);
			int end = RandomUtils.nextInt(length);
			if (start > end) {
				// 交换双方值
				start += end;
				end = start - end;
				start = start - end;
			}
			StringBuffer child1 = new StringBuffer();
			StringBuffer child2 = new StringBuffer();
			child1.append(gene1.toString().substring(0, start)).append(gene2.toString().substring(start, end))
					.append(gene1.toString().substring(end, length - 1));
			child2.append(gene2.toString().substring(0, start)).append(gene1.toString().substring(start, end))
					.append(gene2.toString().substring(end, length - 1));
			return new String[] { child1.toString(), child2.toString() };
		} else {
			return null;
		}
	};
	
}
