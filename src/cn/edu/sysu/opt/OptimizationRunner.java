package cn.edu.sysu.opt;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import cn.edu.sysu.algorithm.genetic.Individual;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LibSVM;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

/**
 * 运行参数优化
 * @author leafxt
 *
 */

public class OptimizationRunner {
	
	private static Instances dataSet;
	
	public static void init(String arff) throws IOException {
		File inputFile = new File(arff);
		ArffLoader arffLoader = new ArffLoader();
		arffLoader.setFile(inputFile);
		dataSet = arffLoader.getDataSet();
		dataSet.setClassIndex(dataSet.numAttributes() - 1);
	}

	public static Evaluation study(double gamma, int crossCount) {
		try {
			LibSVM libSvm = new LibSVM();
			libSvm.buildClassifier(dataSet);
			libSvm.setGamma(gamma);
			Evaluation evaluation = new Evaluation(dataSet);
			evaluation.crossValidateModel(libSvm, dataSet, crossCount, new Random(1));
			return evaluation;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) throws Exception {
		init("E:\\doc\\thesis\\data\\video2.arff");
		RBFGenetic rbfGenetic = new RBFGenetic();
		rbfGenetic.setDebug(false);
		rbfGenetic.setEvaluation(individual -> {
			Evaluation evaluation = study(Double.valueOf(individual.getData().toString()) / 1000000.0, 10);
			System.out.println("Study completed...");
			if (evaluation != null) {
				int correctRate = (int) evaluation.pctCorrect();
				int tp = (int) (evaluation.confusionMatrix()[0][0] * 100
						/ (evaluation.confusionMatrix()[0][0] + evaluation.confusionMatrix()[0][1]));
				int tn = (int) (evaluation.confusionMatrix()[1][1] * 100
						/ (evaluation.confusionMatrix()[1][0] + evaluation.confusionMatrix()[1][1]));
				//int score = (int) (5 * tp + 3 * tn + correctRate);
				//tp *= 2;
				int score = tp + tn;
				return score;
			} else {
				return 0;
			}
		});
		rbfGenetic.setIndividualBuilder(new RBFBuilder());
		rbfGenetic.setTerminationCondition(100);
		rbfGenetic.setTerminationTypeCount(4);
		rbfGenetic.setVariationRate(10);
		rbfGenetic.setSelectCount(20);
		rbfGenetic.init(100);
		Individual individual = rbfGenetic.evolve();
		System.out.println(individual.getData());
	}
	
}
