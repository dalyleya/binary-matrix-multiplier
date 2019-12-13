package matrix.helper;

import matrix.entity.Matrix;
import matrix.entity.WrongInputDataException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReadWriteHelper {

    private final static Logger logger = Logger.getLogger(ReadWriteHelper.class);

    public static void writeInputMatricesToFile(String fileName, int size, Matrix matrixA, Matrix matrixB) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, false))) {
            int[][] a = matrixA.getIntMatrix();
            int[][] b = matrixB.getIntMatrix();
            writer.println(size);
            printMatrixToWriter(size, writer, a);
            writer.println();
            printMatrixToWriter(size, writer, b);
        } catch (IOException e) {
            logger.error(String.format("Wrong file location %s \n", fileName));
        }
    }

    public static void writeOutputMatrixToFile(String fileName, int size, int[][] matrix) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, false))) {
            writer.println(size);
            printMatrixToWriter(size, writer, matrix);
        } catch (IOException e) {
            logger.error(String.format("Wrong file location %s \n", fileName));
        }
    }

    private static void printMatrixToWriter(int size, PrintWriter writer, int[][] a) {
        for (int i = 0; i < size; i++) {
            writer.println(Arrays.stream(a[i])
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(" ")));
            writer.flush();
        }
    }

    public static Matrix readMatrix(Scanner scanner, int size) throws WrongInputDataException {
        int[][] digits = new int[size][];
        int lineCount = 0;
        while (scanner.hasNextLine() &&
                lineCount < size) {
            String strLine = scanner.nextLine();
            if (StringUtils.isNotBlank(strLine)) {
                String[] strGidits = strLine.split(" ");
                if (strGidits.length != size) {
                    throw new WrongInputDataException();
                }
                int[] array = Arrays.stream(strGidits)
                        .mapToInt(Integer::parseInt)
                        .toArray();
                digits[lineCount] = array;

                lineCount++;
            }
        }
        return new Matrix(size, digits);
    }

}
