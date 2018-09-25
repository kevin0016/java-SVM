package cn.edu.sysu.opt;

import cn.edu.sysu.algorithm.genetic.Genetic;
import cn.edu.sysu.algorithm.genetic.function.CodingFunction;
import cn.edu.sysu.algorithm.genetic.function.CrossFunction;
import cn.edu.sysu.algorithm.genetic.function.VariationFunction;

public class RBFGenetic extends Genetic {
	
	@Override
	public void init(int groupCount) {
		setCross(CrossFunction.BINARY_CROSS);
		setVariation(VariationFunction.BINARY_VARIATION);
		setEncode(CodingFunction.INT_GRAY_ENCODE);
		setDecode(CodingFunction.INT_GRAY_DECODE);
		super.init(groupCount);
	}
}
