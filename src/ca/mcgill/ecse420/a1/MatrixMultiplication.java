package ca.mcgill.ecse420.a1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MatrixMultiplication {
	
	private static final int NUMBER_THREADS = 2;
	private static final int MATRIX_SIZE = 10;

        public static void main(String[] args) {
		
		// Generate two random matrices, same size
		double[][] a = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
		double[][] b = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);

 		double[][] result = parallelMultiplyMatrix(a, b);

 		System.out.println("Matrix A:");
		printMatrix(b);
		System.out.println("Matrix A:");
		printMatrix(b);
		System.out.println("Matrix C:");
 		printMatrix(result);

	}

	// This method prints a matrix
	public static void printMatrix(double[][] m){
		for(int i = 0; i < m.length; i++){
			for(int j = 0; j < m.length; j++) {
				System.out.print(m[i][j] + " ");
			}
			System.out.println("");
		}
	}
	
	/**
	 * Returns the result of a sequential matrix multiplication
	 * The two matrices are randomly generated
	 * @param a is the first matrix
	 * @param b is the second matrix
	 * @return the result of the multiplication
	 * */
	public static double[][] sequentialMultiplyMatrix(double[][] a, double[][] b) {
		//Define output matrix
		double result[][] = new double[a.length][b.length];         //assuming square !!!

		//iterate through rows of matrix a and columns of matrix b
		for(int row_i = 0; row_i < a.length; row_i++){
			for(int col_i=0; col_i < b.length; col_i++){            //assuming square !!!
				//retrieve the column array for col_i of matrix b
				double[] column = getColumnArray(col_i, b);

				//retrieve the row matrix a we are multiplying
				double[] row = a[row_i];

				result[row_i][col_i] = calculateCell(row, column);
			}
		}

		return result;
	}
	
	/**
	 * Returns the result of a concurrent matrix multiplication
	 * The two matrices are randomly generated
	 * @param a is the first matrix
	 * @param b is the second matrix
	 * @return the result of the multiplication
	 * */
    public static double[][] parallelMultiplyMatrix(double[][] a, double[][] b) {
		// 1. Define the result and the thread-pool
        double result[][] = new double[a.length][b[0].length];
        ExecutorService executor = Executors.newFixedThreadPool(NUMBER_THREADS);

        int MATRIX_CELL_COUNT = (a.length)*(b[0].length);

        // 2. Launch all the threads and ___
        for(int i = 0; i < NUMBER_THREADS; i++){
        	// If you're on the remainder thread, just
        	if(MATRIX_CELL_COUNT % NUMBER_THREADS != 0 && i == NUMBER_THREADS - 1){
        		executor.execute(new ParallelMultiplication(result, i*(MATRIX_CELL_COUNT/NUMBER_THREADS), MATRIX_CELL_COUNT, a, b));
			}else{
				executor.execute(new ParallelMultiplication(result, i*(MATRIX_CELL_COUNT/NUMBER_THREADS), (i+1)*(MATRIX_CELL_COUNT/NUMBER_THREADS), a, b));
			}
		}

		// 3. Shutdown each thread from pool
		executor.shutdown();

		// 4. Wait until the pool is down
        while(!executor.isTerminated()){ }

		// 5. Return the result
        return result;
    }

    public static class ParallelMultiplication implements Runnable{
    	private double[][] result;
    	private int startRowA;
		private int endRowA;
		private double[][] a;
		private double[][] b;

		public ParallelMultiplication(double[][] result, int startRowA, int endRowA, double[][] a, double[][] b){
			this.result = result;
			this.startRowA = startRowA;
			this.endRowA = endRowA;
			this.a = a;
			this.b = b;
		}

		// Each thread takes a chunk of cells and calculates their value
		@Override
		public void run() {
			// For each cell
			for(int iter = startRowA; iter < endRowA; iter++){
				// Get the row
				int i = iter / result[0].length;
				double[] row = a[i]; // this should be matrix a not result

				// Get the column
				int j = iter % result[0].length;
				double[] col = getColumnArray(j, b); // this should be matrix b not result

				// Calculate and set the cell
				result[i][j] = calculateCell(row, col);
			}
		}
	}

    /**
     * Populates a matrix of given size with randomly generated integers between 0-10.
     * @param numRows number of rows
     * @param numCols number of cols
     * @return matrix
     */
    private static double[][] generateRandomMatrix (int numRows, int numCols) {
        double matrix[][] = new double[numRows][numCols];
        for (int row = 0 ; row < numRows ; row++ ) {
            for (int col = 0 ; col < numCols ; col++ ) {
                matrix[row][col] = (double) ((int) (Math.random() * 10.0));
            }
        }
        return matrix;
    }

	/* This method takes a column index (j) and matrix then return the column as an array */
	// 1. Define an output array equal to the number of rows
	// 2. Iterate through every row - populating array
	// 3. Return the array
    private static double[] getColumnArray(int j, double[][] matrix){
		// 1. Define an output array equal to the number of rows
		double column[] = new double[matrix.length];

		// 2. Iterate through every row - populating array
		for(int iter = 0; iter < matrix.length; iter++){
			column[iter] = matrix[iter][j];
		}

		// 3. Return the array
        return column;
	}

    /* This method takes a row array and a column array and returns a cell post matrix multiplication */
	// 1. Set a sum to zero
	// 2. Iterate through the length of row/column
	// 3. Return the final answer
    private static double calculateCell (double[] row, double[] column) {
		// 1. Set a sum to zero
		double cell = 0.0;

		// 2. Iterate through the length of row/column
		for(int iter = 0; iter < row.length; iter++){
			cell += row[iter] * column[iter];
		}

		// 3. Return the final answer
		return cell;
	}

}
