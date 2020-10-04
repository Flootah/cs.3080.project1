import java.text.DecimalFormat;

public class IterativeSolver {
	private int[][] matrix;
	private double[] solutions;
	private double stopError; 
	
	public IterativeSolver(int[][] x, double[] s, double e) {
		matrix = x;
		solutions = s;
		stopError = e;
	}
	
	/*
	 * 
	 */
	public void j_calculate() {
		int iterations = 0;
		double curError;
		double[] j_solutions = solutions.clone();
		double[] tempsolutions = solutions.clone();					// copy of solutions[] for use in calculations
		do {
			for(int i = 0; i < matrix.length; i++) {			// for each row						
				int rowMax = maximum(matrix[i]);				// get maximum coefficient in row		
				int bval = matrix[i][matrix[i].length-1]; 		// value of last column in row 
				double result = (double) bval/rowMax;
				for(int j = 0; j < matrix[0].length-1; j++) {	// for each item in row
					if(i == j) {								// skip diagonals
						//continue;				
					} else {
						double a = (double) matrix[i][j] / rowMax;
						double b = (double) tempsolutions[j];
						double term = (double) a * b;
						result -= term;
					}
				}
				j_solutions[i] = result;

			}
			iterations++;

			curError = Math.abs((l2(j_solutions) - l2(tempsolutions)) / l2(j_solutions));

			System.out.println("Iteration " + iterations + " :");
			System.out.print("[ ");
			for(double i : j_solutions) {
				System.out.printf("%16s", new DecimalFormat("##.#########").format(i));
			}
			System.out.print(" ]");
			System.out.println();
			System.out.print("error: ");
			System.out.println(curError);
			System.out.println();
			if(iterations > 49) {
				System.out.println("Maximum iterations! Goal error not reached.");
				break;
			}
			tempsolutions = j_solutions.clone();
		} while(curError > stopError);
		System.out.println("complete!");
	}
	
	public void gs_calculate() {
		int iterations = 0;
		double curError;
		double[] gs_solutions = solutions.clone();
		double[] lastsolutions = solutions.clone();					// copy of solutions[] for use in calculations
		do {
			for(int i = 0; i < matrix.length; i++) {			// for each row						
				int rowMax = maximum(matrix[i]);				// get maximum coefficient in row		
				int bval = matrix[i][matrix[i].length-1]; 		// value of last column in row 
				double result = (double) bval/rowMax;
				for(int j = 0; j < matrix[0].length-1; j++) {	// for each item in row
					if(i == j) {								// skip diagonals
						//continue;				
					} else {
						double a = (double) matrix[i][j] / rowMax;
						double b = (double) gs_solutions[j];
						double term = (double) a * b;
						result -= term;
					}
				}
				gs_solutions[i] = result;

			}
			iterations++;

			curError = Math.abs((l2(gs_solutions) - l2(lastsolutions)) / l2(gs_solutions));

			System.out.println("Iteration " + iterations + " :");
			System.out.print("[ ");
			for(double i : gs_solutions) {
				System.out.printf("%16s", new DecimalFormat("##.#########").format(i));
			}
			System.out.print(" ]");
			System.out.println();
			System.out.print("error: ");
			System.out.println(curError);
			System.out.println();
			if(iterations > 49) {
				System.out.println("Maximum iterations! Goal error not reached.");
				break;
			}
			lastsolutions = gs_solutions.clone();
		} while(curError > stopError);
		System.out.println("complete!");
	}
	
	private double l2(double[] a) {
		double result = 0;
		for (double x : a) {
			result += x*x;
		}
		return Math.sqrt(result);
	}
	
	private int maximum(int[] a) {
		int max = 0;
		for(int i = 0; i < a.length-1; i++) {
			if (Math.abs(a[i]) > max) max = Math.abs(a[i]);
		}
		return max;
	}
	
}
