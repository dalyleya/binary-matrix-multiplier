package matrix.entity;

import java.util.Arrays;


public class Matrix {
    private int n;// dimension
    private boolean[][] matrix;

    public Matrix(int n, int[][] intMatrix) {
        this.n = n;
        this.matrix = MatrixHelper.transformToBoolMatrix(intMatrix);
    }

    /**
     * Generates random matrix with a given size
     * @param n size of both matrix dimensions
     */
    public Matrix(int n) {
        this.n = n;
        this.matrix = MatrixHelper.generateRandomSquareMatrix(n);
    }

    public int getN() {
        return n;
    }

    public boolean[][] getBoolMatrix() {
        return matrix;
    }

    public int[][] getIntMatrix() {
        return MatrixHelper.transformToDigitMatrix(matrix);
    }

    @Override
    public String toString() {
        return "Matrix{" +
                "n=" + n +
                ", matrix=" + Arrays.deepToString(matrix) +
                '}';
    }

}
