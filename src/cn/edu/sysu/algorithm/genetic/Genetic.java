package cn.edu.sysu.algorithm.genetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import cn.edu.sysu.algorithm.RandomUtils;

/**
 * 遗传算法框架
 * @author leafxt
 *
 */

public abstract class Genetic {
	
	private boolean isDebug;
	
	/**
	 * 变异概率(百万分之概率)
	 */
	private int variationRate = 1;
	
	
	/**
	 * 选择次数, -1表示物种个数, 生成的个体将完全代替上一代
	 */
	private int selectCount;
	
	/**
	 * 终止次数(条件)
	 */
	private long terminationCondition = 1000;
	
	/**
	 * 物种稳定的介绍条件
	 */
	private long terminationTypeCount = 4;
	
	/**
	 * 群种
	 */
	private List<Individual> groups;
	
	/**
	 * 评价函数
	 */
	private Function<Individual, Integer> evaluation;
	
	/**
	 * 交叉操作
	 */
	private BinaryOperator<Object> cross;
	
	/**
	 * 变异操作
	 */
	private UnaryOperator<String> variation;
	
	/**
	 * 编码操作
	 */
	private Function<Individual, String> encode;
	
	/**
	 * 解码操作
	 */
	private Function<String, Individual> decode;
	
	/**
	 * 物种比较
	 */
	private Comparator<Individual> individualComparator;
	
	private IndividualBuilder individualBuilder;
	
	public boolean isDebug() {
		return isDebug;
	}

	public void setDebug(boolean isDebug) {
		this.isDebug = isDebug;
	}

	public int getVariationRate() {
		return variationRate;
	}

	public void setVariationRate(int variationRate) {
		this.variationRate = variationRate;
	}

	public int getSelectCount() {
		return selectCount;
	}

	public void setSelectCount(int selectCount) {
		this.selectCount = selectCount;
	}

	public long getTerminationCondition() {
		return terminationCondition;
	}

	public void setTerminationCondition(long terminationCondition) {
		this.terminationCondition = terminationCondition;
	}

	public Function<Individual, Integer> getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Function<Individual, Integer> evaluation) {
		this.evaluation = evaluation;
	}

	public BinaryOperator<Object> getCross() {
		return cross;
	}

	public void setCross(BinaryOperator<Object> cross) {
		this.cross = cross;
	}

	public UnaryOperator<String> getVariation() {
		return variation;
	}

	public void setVariation(UnaryOperator<String> variation) {
		this.variation = variation;
	}

	public Function<Individual, String> getEncode() {
		return encode;
	}

	public void setEncode(Function<Individual, String> encode) {
		this.encode = encode;
	}

	public Function<String, Individual> getDecode() {
		return decode;
	}

	public void setDecode(Function<String, Individual> decode) {
		this.decode = decode;
	}

	public IndividualBuilder getIndividualBuilder() {
		return individualBuilder;
	}

	public void setIndividualBuilder(IndividualBuilder individualBuilder) {
		this.individualBuilder = individualBuilder;
	}

	public List<Individual> getGroups() {
		return groups;
	}

	public long getTerminationTypeCount() {
		return terminationTypeCount;
	}

	public void setTerminationTypeCount(long terminationTypeCount) {
		this.terminationTypeCount = terminationTypeCount;
	}

	/**
	 * 初始化群组等参数
	 */
	public void init(int groupCount) {
		individualComparator = (ind1, ind2) -> {
			int diff = evaluate(ind1) - evaluate(ind2);
			if (diff > 0.0f) {
				return 1;
			} else if (diff == 0.0f) {
				return 0;
			} else {
				return -1;
			}
		};
		groups = new ArrayList<>();
		for(int i=0; i<groupCount; i++) {
			Individual individual = individualBuilder.build();
			groups.add(individual);
		}
	}
	
	public int evaluate(Individual individual) {
		Integer score = (Integer) individual.getAdaptability();
		if (score == null) {
			score = evaluation.apply(individual);
			individual.setAdaptability(score);
		}
		return score;
	}
	
	/**
	 * 选择
	 */
	public Individual[] select() {
		List<Integer> scores = new ArrayList<>(); // 物种对应的生存分数
		Map<Individual, Integer> mapScore = new HashMap<>();
		groups.parallelStream().forEach(individual -> {
			Integer score = evaluate(individual);
			synchronized(this) {
				mapScore.put(individual, score);
			}
		});
		groups.forEach(individual -> scores.add(mapScore.get(individual)));
		int selection[] = { 0, 0 };
		for (int i = 0; i < 2; i++) {
			selection[i] = RandomUtils.turn(scores);
			if (i == 1 && selection[1] == selection[0]) {
				i = 0;
				continue;
			}
		}
		return new Individual[] { groups.get(selection[0]), groups.get(selection[1]) };
	}
	
	/**
	 * 进化进程, 返回最优物种
	 */
	public Individual evolve() throws GeneticException {
		if (groups.size() == 0 || evaluation == null || cross == null || variation == null
				|| encode == null || decode == null || individualComparator == null
				|| individualBuilder == null) {
			throw new GeneticException("evaluation was not initial parameters");
		}

		for (long i = 0; i < terminationCondition; i++) {
			System.out.println("The " + i + " times process...");
			List<Individual> children = new ArrayList<>();
			// 交叉产生后代
			for (int j = 0; j < selectCount; j++) {
				Individual individual[] = select();
				String gene1 = encode.apply(individual[0]);
				String gene2 = encode.apply(individual[1]);
				Object childGene = cross.apply(gene1, gene2);
				List<String> genes = new ArrayList<>();
				if(childGene.getClass().isArray()) {
					genes.addAll(Arrays.asList((String[])childGene));
				} else {
					genes.add((String)childGene);
				}
				for(String gene : genes) {
					// 变异
					if (RandomUtils.drawByMil(variationRate)) {
						childGene = variation.apply(gene);
					}
					children.add(decode.apply(gene));
				}
			}
			groups = groups.stream().sorted(individualComparator).collect(Collectors.toList());
			if(isDebug) {
				groups.forEach(g -> System.out.print(g.getData() + " "));
				System.out.print("\r\n");
				children = children.stream().sorted(individualComparator).collect(Collectors.toList());
				children.forEach(c -> System.out.print(c.getData() + "  "));
				System.out.print("\r\n");
			}
			if (children.size() >= groups.size()) {
				groups.clear();
			} else {
				for (int j = 0; j < children.size(); j++) {
					groups.remove(0);
				}
			}
			children.forEach(c -> groups.add(c));
			
			long typeCount = groups.stream().distinct().count();
			System.out.println("remind type count: " + typeCount);
			if(typeCount <= terminationTypeCount) {
				break;
			}
		}
		groups = groups.stream().sorted(individualComparator).collect(Collectors.toList());
		return groups.stream().max(individualComparator).get();
	}
	
	
	
}
