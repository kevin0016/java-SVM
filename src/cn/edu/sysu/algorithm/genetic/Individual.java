package cn.edu.sysu.algorithm.genetic;

/**
 * 物种个体基类
 * @author leafxt
 *
 */

public class Individual {
	
	/**
	 * 个体数值
	 */
	private Object data;
	
	/**
	 * 适应度
	 */
	private Object adaptability;
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getAdaptability() {
		return adaptability;
	}

	public void setAdaptability(Object adaptability) {
		this.adaptability = adaptability;
	}
	
	public boolean equals(Individual individual) {
		if (data != null && individual.getData() != null) {
			return data.toString().equals(individual.getData().toString());
		} else {
			return false;
		}
	}
	
}
