package matrix.operation;

import matrix.entity.Matrix;
import matrix.internal.MatrixHelper;
import org.apache.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MatrixMultiplier {

    private final static Logger logger = Logger.getLogger(MatrixMultiplier.class);

    /**
     * Multiplies two matrices in several threads and return result
     * @param a first matrix to multiply
     * @param b first matrix to multiply
     * @return Matrix of ints with 0 or 1 value
     */
    public int[][] multiplyMatricesInParallel(Matrix a, Matrix b) {
        int size = a.getN();
        boolean[][] resultMatrix = multiplyInSeveralThreads(a, b, size);
        return MatrixHelper.transformToDigitMatrix(resultMatrix);
    }

    private boolean[][] multiplyInSeveralThreads(Matrix a, Matrix b, int size) {
        boolean[][] resultMatrix = new boolean[size][size];
        boolean[][] matrixA = a.getBoolMatrix();
        boolean[][] matrixB = b.getBoolMatrix();
        int poolSize = (size > 10) ? 10 : size;
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                int finalJ = j;
                int finalI = i;
                executor.submit(() -> {
                    int k = 0;
                    resultMatrix[finalI][finalJ] = multiplyTwoCells(matrixA[finalI][k], matrixB[k][finalJ]);
                    if (size > 1) {
                        for (k = 1; k < size; k++) {
                            resultMatrix[finalI][finalJ] = addTwoCells(resultMatrix[finalI][finalJ],
                                    multiplyTwoCells(matrixA[finalI][k], matrixB[k][finalJ]));
                        }
                    }
                });

            }
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(3, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
        return resultMatrix;
    }

    /**
     * Multiplies two matrices sequentially and return result
     * @param a first matrix to multiply
     * @param b first matrix to multiply
     * @return Matrix of ints with 0 or 1 value
     */
    public int[][] multiplyMatricesSequentially(Matrix a, Matrix b) {
        int size = a.getN();
        boolean[][] resultMatrix = multiplyInOneThread(a, b, size);
        return MatrixHelper.transformToDigitMatrix(resultMatrix);
    }

    private boolean[][] multiplyInOneThread(Matrix a, Matrix b, int size) {
        boolean[][] resultMatrix = new boolean[size][size];
        boolean[][] matrixA = a.getBoolMatrix();
        boolean[][] matrixB = b.getBoolMatrix();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int k = 0;
                resultMatrix[i][j] = multiplyTwoCells(matrixA[i][k], matrixB[k][j]);
                if (size > 1) {
                    for (k = 1; k < size; k++) {
                        resultMatrix[i][j] = addTwoCells(resultMatrix[i][j],
                                multiplyTwoCells(matrixA[i][k], matrixB[k][j]));

                    }
                }
            }
        }
        return resultMatrix;
    }

    // 1^0 = 1
    // 1^1 = 0
    // 0^1 = 1
    // 0^0 = 0
    private boolean addTwoCells(boolean aCell, boolean bCell) {
        return aCell ^ bCell; // XOR
    }

    private boolean multiplyTwoCells(boolean aCell, boolean bCell) {
        return aCell && bCell;
    }
}
