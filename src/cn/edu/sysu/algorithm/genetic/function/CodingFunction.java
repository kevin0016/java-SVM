package cn.edu.sysu.algorithm.genetic.function;

import java.util.function.Function;

import cn.edu.sysu.algorithm.genetic.Individual;

public class CodingFunction {

	/** 
	 * 编码 ---------------------------------
	 */
	
	/**
	 * 二进制编码 int
	 */
	public static final Function<Individual, String> INT_BINARY_ENCODE = individual -> {
		return makeUpZero(Integer.toBinaryString((Integer) individual.getData()));
	};
	
	/**
	 * 二进制编码 long
	 */
	public static final Function<Individual, String> LONG_BINARY_ENCODE = individual -> {
		return makeUpZero(Long.toBinaryString((Long) individual.getData()));
	};
	
	/**
	 * 直接编码
	 */
	public static final Function<Individual, String> DIRECT_ENCODE = individual -> {
		return individual.getData().toString();
	};
	
	/**
	 * 格雷编码 int
	 */
	public static final Function<Individual, String> INT_GRAY_ENCODE = individual -> {
		String binary = INT_BINARY_ENCODE.apply(individual);
		return bin2Gray(binary);
	};
	
	/**
	 * 格雷编码 long
	 */
	public static final Function<Individual, String> LONG_GRAY_ENCODE = individual -> {
		String binary = LONG_BINARY_ENCODE.apply(individual);
		return bin2Gray(binary);
	};
	
	private static String bin2Gray(String binary) {
		char binaryArray[] = binary.toCharArray();
		char grayArray[] = new char[binaryArray.length];
		for (int i = 0; i < binaryArray.length; i++) {
			if (i == 0) {
				grayArray[i] = binaryArray[i];
			} else {
				grayArray[i] = (char) ((int) binaryArray[i - 1] ^ (int) binaryArray[i]);
			}
		}
		return new String(grayArray);
	}
	
	private static String makeUpZero(String binary) {
		int makeNum = 32 - binary.length();
		for (int i = 0; i < makeNum; i++) {
			binary = "0".concat(binary);
		}
		return binary;
	}

	/**
	 * 解码 -----------------------------------
	 */
	
	/**
	 * 二进制解码 int
	 */
	public static final Function<String, Individual> INT_BINARY_DECODE = code -> {
		Integer data = Integer.valueOf(code, 2);
		Individual individual = new Individual();
		individual.setData(data);
		return individual;
	};
	
	/**
	 * 二进制解码 long
	 */
	public static final Function<String, Individual> LONG_BINARY_DECODE = code -> {
		Long data = Long.valueOf(code, 2);
		Individual individual = new Individual();
		individual.setData(data);
		return individual;
	};
	
	/**
	 * 单个整数直接解码
	 */
	public static final Function<String, Individual> INT_DECODE = code -> {
		Integer data = Integer.parseInt(code);
		Individual individual = new Individual();
		individual.setData(data);
		return individual;
	};
	
	/**
	 * 单个浮点数解码
	 */
	public static final Function<String, Individual> FLOAT_DECODE = code -> {
		Float data = Float.parseFloat(code);
		Individual individual = new Individual();
		individual.setData(data);
		return individual;
	};
	
	/**
	 * 格雷解码 int
	 */
	public static final Function<String, Individual> INT_GRAY_DECODE = code -> {
		String binary = gray2Bin(code);
		return INT_BINARY_DECODE.apply(binary);
	};
	
	/**
	 * 格雷解码 long
	 */
	public static final Function<String, Individual> LONG_GRAY_DECODE = code -> {
		String binary = gray2Bin(code);
		return LONG_BINARY_DECODE.apply(binary);

	};
	
	private static String gray2Bin(String gray) {
		char grayArray[] = gray.toCharArray();
		char binaryArray[] = new char[grayArray.length];
		for (int i = 0; i < grayArray.length; i++) {
			if (i == 0) {
				binaryArray[0] = grayArray[0];
			} else {
				binaryArray[i] = (char) ((int) binaryArray[i - 1] ^ (int) grayArray[i]);
			}
		}
		return new String(binaryArray);
	}
	
}
