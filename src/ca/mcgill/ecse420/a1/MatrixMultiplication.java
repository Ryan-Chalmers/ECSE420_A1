package ca.mcgill.ecse420.a1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MatrixMultiplication {
	
	private static final int NUMBER_THREADS = 1;
	private static final int MATRIX_SIZE = 2000;
	private static final int THREAD_COUNT = 3;

        public static void main(String[] args) {
		
		// Generate two random matrices, same size
		double[][] a = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
		double[][] b = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE);
//		sequentialMultiplyMatrix(a, b);
 		parallelMultiplyMatrix(a, b);

//		double[][] a = {{1.0, 2.0, 3.0},{1.0, 2.0, 3.0},{1.0, 2.0, 3.0}};
//		double[][] b = {{1.0, 2.0, 3.0},{1.0, 2.0, 3.0},{1.0, 2.0, 3.0}};
//
//		double[][] result = sequentialMultiplyMatrix(a, b);
//		for(int i = 0; i < result.length; i++){
//			for(int j = 0; j < result[0].length; j++){
//				System.out.print(result[i][j] + " ");
//			}
//			System.out.println("");
//		}

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
        //Initialize the instance of the counter object to have value -1
        Counter counter = new Counter(-1);

        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            Runnable worker = new WorkerThread("" + i);
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");

        return null;
    }

    public static class workerThread implements Runnable {

        public void run(){
            while(true) {
                System.out.println("MyRunnable running " + "__thread id here__");
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
