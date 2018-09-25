package cn.edu.sysu.algorithm.genetic.function;

import java.text.DecimalFormat;
import java.util.function.UnaryOperator;

import cn.edu.sysu.algorithm.RandomUtils;

public class VariationFunction {
	
	/**
	 * 浮点数变异
	 */
	public static final UnaryOperator<String> FLOAT_VARIATION = gene -> {
		float g = Float.parseFloat(gene);
		// 变异因子
		float variation = g * 0.5f / (RandomUtils.nextInt(10) + 1);
		if (RandomUtils.drawByHundred(50)) {
			variation *= -1.0f;
		}
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(g + variation);
	};
	
	/**
	 * 直接整数变异
	 */
	public static final UnaryOperator<String> INT_VARIATION = gene -> {
		int g = Integer.parseInt(gene);
		// 变异因子
		int variation = (int) (g * 0.5 / (RandomUtils.nextInt(10) + 1));
		if (RandomUtils.drawByHundred(50)) {
			variation *= -1.0f;
		}
		return Integer.toString(g + variation);
	};
	
	/**
	 * 二进制变异
	 */
	public static final UnaryOperator<String> BINARY_VARIATION = gene -> {
		char geneArray[] = gene.toCharArray();
		int length = gene.length();
		int bit = RandomUtils.nextInt(1) + 1;
		for (int i = 0; i < bit; i++) {
			int variation = RandomUtils.nextInt(length);
			if (geneArray[variation] == '1') {
				geneArray[variation] = '0';
			} else {
				geneArray[variation] = '1';
			}
		}
		return new String(geneArray);
	};

}
