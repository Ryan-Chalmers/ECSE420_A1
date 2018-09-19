package ca.mcgill.ecse420.a1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MatrixMultiplication {
	
	private static final int NUMBER_THREADS = 1;
	private static final int MATRIX_SIZE = 2000;

        public static void main(String[] args) {
		
		// Generate two random matrices, same size
		double[][] a = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
		double[][] b = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
		sequentialMultiplyMatrix(a, b);
//		parallelMultiplyMatrix(a, b);
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
		double result[][] = new double[MATRIX_SIZE][MATRIX_SIZE];

		//iterate through rows of matrix a and columns of matrix b
		for(int row_i = 0; row_i < MATRIX_SIZE; row_i++){
			for(int col_i=0; col_i < MATRIX_SIZE; col_i++){
				//retrieve the column array for col_i of matrix b
				double[] column = getColumnArray(col_i, b);

				//retrieve the row matrix a we are multiplyin
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
		return null;
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
