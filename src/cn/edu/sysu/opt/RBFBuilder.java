package cn.edu.sysu.opt;

import cn.edu.sysu.algorithm.RandomUtils;
import cn.edu.sysu.algorithm.genetic.Individual;
import cn.edu.sysu.algorithm.genetic.IndividualBuilder;

public class RBFBuilder extends IndividualBuilder {

	@Override
	public Individual build() {
		Individual individual = new Individual();
		individual.setData(RandomUtils.nextInt(10000000));
		return individual;
	}
}
