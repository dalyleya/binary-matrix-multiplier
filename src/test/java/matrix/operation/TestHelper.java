package matrix.operation;

import matrix.entity.Matrix;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TestHelper {


    private final static Logger logger = Logger.getLogger(TestHelper.class);

    public static void generateTwoMatricesAndWriteToFile(int size, String pathName) {
        Matrix matrixA = new Matrix(size);
        Matrix matrixB = new Matrix(size);
        writeInputMatricesToFile(pathName, size, matrixA, matrixB);
    }
    public static void writeInputMatricesToFile(String fileName, int size, Matrix matrixA, Matrix matrixB) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, false))) {
            int[][] a = matrixA.getIntMatrix();
            int[][] b = matrixB.getIntMatrix();
            writer.println(size);
            printMatrixToWriter(writer, a);
            writer.println();
            printMatrixToWriter(writer, b);
        } catch (IOException e) {
            logger.error(String.format("Wrong file location %s \n", fileName));
        }
    }

    private static void printMatrixToWriter(PrintWriter writer, int[][] a) {
        for (int i = 0; i < a.length; i++) {
            writer.println(Arrays.stream(a[i])
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(" ")));
            writer.flush();
        }
    }

    public static void writeOutputMatrixToFile(String fileName, int size, int[][] matrix) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, false))) {
            writer.println(size);
            printMatrixToWriter(writer, matrix);
        } catch (IOException e) {
            logger.error(String.format("Wrong file location %s \n", fileName));
        }
    }

    public static void appendMatrixToFile(String fileName, int[][] matrix) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, false))) {
            printMatrixToWriter(writer, matrix);
        } catch (IOException e) {
            logger.error(String.format("Wrong file location %s \n", fileName));
        }
    }

    public static Matrix readMatrix(Scanner scanner, int size) {
        int[][] digits = readDigitMatrix(scanner, size);
        return new Matrix(size, digits);
    }

    public static int[][] readDigitMatrix(Scanner scanner, int size) {
        int[][] digits = new int[size][];
        int lineCount = 0;
        while (scanner.hasNextLine() &&
                lineCount < size) {
            String strLine = scanner.nextLine();
            if (StringUtils.isNotBlank(strLine)) {
                String[] strGidits = strLine.split(" ");
                if (strGidits.length != size) {
                    throw new IllegalArgumentException();
                }
                int[] array = Arrays.stream(strGidits)
                        .mapToInt(Integer::parseInt)
                        .toArray();
                digits[lineCount] = array;

                lineCount++;
            }
        }
        return digits;
    }

}
