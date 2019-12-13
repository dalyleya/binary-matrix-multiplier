package matrix.entity;

import java.util.Random;

public final class MatrixHelper {

    public static int[][] transformToDigitMatrix(boolean[][] boolMatrix) {
        int size = boolMatrix.length;
        int[][] intMatrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                intMatrix[i][j] = (boolMatrix[i][j]) ? 1 : 0;
            }
        }
        return intMatrix;
    }

    public static boolean[][] transformToBoolMatrix(int[][] matrix) {
        int size = matrix.length;
        boolean[][] boolMatrix = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boolMatrix[i][j] = matrix[i][j] == 1;
            }
        }
        return boolMatrix;
    }

    public static boolean[][] generateRandomSquareMatrix(int size) {
        boolean[][] randomMatrix = new boolean[size][size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                randomMatrix[i][j] = random.nextBoolean();
            }
        }
        return randomMatrix;
    }
}
