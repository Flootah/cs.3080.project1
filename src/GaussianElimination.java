/**
 * Gaussian Elimination
 * Solves a system of linear equations (up to 10) using the Gaussian
 * elimination method with Scaled Partial Pivoting method.
 * User can either enter number of equations and then give coefficients
 * from command line or can specify a text file with a coefficient matrix.
 * 
 * example input:
 * 2 3 0 8
 * -1 2 -1 0
 * 3 0 2 9
 * 
 * output:
 * x=1
 * y=2
 * z=3
 * 
 * 
 * @author Eduardo
 *
 */
public class GaussianElimination {
	private static int[][] matrix;

	public GaussianElimination() {
		
	}
	
	public static void main(String[] args) {
		UserInterface ui = new UserInterface();		
		ui.start();
	}
}
