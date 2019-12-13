package matrix.operation;

import matrix.entity.Matrix;
import matrix.entity.MatrixHelper;

public class MatrixMultiplier {

    public int[][] multiplyAndTrace(Matrix a, Matrix b, boolean isParallel) {
        long time = System.currentTimeMillis();
        int[][] resultMatrix = multiplyMatrices(a, b, isParallel);
        time = System.currentTimeMillis() - time;
        System.out.printf("Multiplication operation took %d milliseconds..\n", time);
        return resultMatrix;
    }

    public int[][] multiplyMatrices(Matrix a, Matrix b, boolean isParallel) {
        int size = a.getN();
        boolean[][] resultMatrix = new boolean[size][size];
        if (isParallel) {

        }
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

        return MatrixHelper.transformToDigitMatrix(resultMatrix);
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
