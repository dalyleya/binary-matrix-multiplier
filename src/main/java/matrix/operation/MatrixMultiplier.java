package matrix.operation;

import matrix.entity.Matrix;
import matrix.entity.MatrixHelper;
import org.apache.log4j.Logger;

public class MatrixMultiplier {

    private final static Logger logger = Logger.getLogger(MatrixMultiplier.class);

    public int[][] multiplyAndTrace(Matrix a, Matrix b) {
        long time = System.currentTimeMillis();
        int[][] resultMatrix = multiplyMatricesSequentially(a, b);
        time = System.currentTimeMillis() - time;
        logger.info(String.format("Sequential multiplication took %d milliseconds..\n", time));
        return resultMatrix;
    }

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
