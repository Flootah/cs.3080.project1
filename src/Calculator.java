import java.text.DecimalFormat;
import java.util.Arrays;

public class Calculator {
	private int[][] matrix;		// original matrix
	private float[][] fmatrix;	// float version of input matrix
	private int stage;			// number of times an iteration was completed.
	private int curPivot;		// index of current pivot row
	private float[] curRatios;	// array of current set of ratios
	private int[] answer;		

	public Calculator(int[][] x) {
		matrix = x;
		curPivot = 0;
		stage = 0;
		curRatios = new float[]{0,0,0};
		fmatrix = matrixIntToFloat(matrix);
	}
	
	public float[] calculate() {
		/*
		 * determine pivot row and swap if necessary.
		 */
		while(stage < fmatrix.length-1) {
			curPivot = findPivot(fmatrix, stage);
			if(curPivot != stage) {
				swapRows(fmatrix, curPivot, stage);
				curPivot = stage;
			}
			/*
			 * Pivot row now at the top for current stage
			 */
			for(int i = stage+1; i < fmatrix.length; i++) {							
				float divFactor = fmatrix[i][stage]/fmatrix[stage][stage];
				for(int j = 0; j < fmatrix[0].length; j++) {
					fmatrix[i][j] = fmatrix[i][j] - fmatrix[stage][j]*divFactor;
				}
			}
			printMatrix(fmatrix, curPivot);
			stage++;
		}
		float[] answer = systemSolver(fmatrix);
		return answer;
	}
	
	private float[] systemSolver(float[][] system) {
		float[] result = new float[system.length];
		int count = 0;
		for(int i = system.length-1; i >= 0; i--) {
			result[count] = system[i][system[i].length-1];
			
			for(int j = system[i].length-2; j >= 0; j--) {
				if(system[i][j] == 0) break;
				if(i == j) {
					result[count] = result[count]/system[i][j];
					break;
				} else {
					result[count] -= result[result.length-1-j]*system[i][j];
				}
			}
			count++;
		}
		return result;
	}

	/*
	 * returns an fmatrix given an imatrix.
	 */
	private float[][] matrixIntToFloat(int[][] x) {
		float[][] y = new float[x.length][x[0].length];
		
		for (int i = 0; i < x.length; i++) {
			for (int j = 0; j < x[0].length; j++) {
				y[i][j] = (float) x[i][j];
			}
		}
		return y;
	}
	/*
	 * Finds which row to use as a pivot, given a matrix and stage number.
	 * returns index of row which should be used as the pivot.
	 */
	private int findPivot(float[][] x, int s) {
		DecimalFormat df = new DecimalFormat("##.##");
		float[] ratios = new float[x[0].length-1];	// float array with all ratios
		
		System.out.print("ratios: ");
		System.out.print("[ ");
		for(int i = s; i < x.length; i++) {		// loop through rows, finding ratios and storing in ratios[]
			float r = findRatio(x[i], stage);
			ratios[i] = Math.abs(r);
			System.out.printf("%4s", df.format(Math.abs(r)));
			if (i != x.length-1) System.out.print(",");
		}
		System.out.print("]");
		System.out.println();
		System.out.println();
		System.out.println();
		int maxIndex = 0;
		float max = ratios[0];
		for(int i = 0; i < ratios.length; i++) {	// find largest element in ratios[], set maxIndex as that element's index
			if(ratios[i] > max) {
				max = ratios[i];
				maxIndex = i;
			}
		}
		return maxIndex;
	}
	/*
	 * given a matrix row, returns the scaled ratio of that row.
	 */
	private float findRatio(float[] x, int s) {
		float max = x[s];
		for(int i = s; i < x.length-1; i++) {
			if(Math.abs(x[i]) > max) {
				max = Math.abs(x[i]);
			}
		}
		return x[s]/max;
	}
	/*
	 * Exchanges rows within a fmatrix, given the following parameters:
	 * x, the matrix itself
	 * index1, index of a row to swap
	 * index2, index of other row to swap
	 * 
	 * returns an fmatrix with the rows swapped.
	 */
	private float[][] swapRows(float[][] x, int index1, int index2) {
		float[] tempRow = x[index1];
		x[index1] = x[index2];
		x[index2] = tempRow;
		return x;
	}
	
	private void printMatrix(float[][] x, int pivot) {
		DecimalFormat df = new DecimalFormat("##.##");
        // Loop through all rows 
        for (int i = 0; i < x.length; i++) {
            // Loop through all elements of current row 
        	if(i == pivot) {
        		System.out.printf("%6s", "->["); 
        	} else {
        		System.out.printf("%6s", "["); 
        	}
            for (int j = 0; j < x[0].length; j++) {
            	System.out.printf("%5s", df.format(x[i][j]));
            	if(j != x.length) System.out.print(" ");
            }
            System.out.println(" ]");
        }
	}
}
