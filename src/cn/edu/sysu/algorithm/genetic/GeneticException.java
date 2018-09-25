package cn.edu.sysu.algorithm.genetic;

public class GeneticException extends Exception {

	private static final long serialVersionUID = -1701955573527713247L;
	
	public GeneticException(String message) {
		super("Genetic exception: ".concat(message));
	}
	
}
