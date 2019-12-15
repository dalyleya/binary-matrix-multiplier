package matrix;

import matrix.entity.Matrix;
import matrix.entity.NotSuitableMatrixSizeException;
import matrix.internal.ReadWriteHelper;
import matrix.operation.MatrixMultiplier;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Scanner;

public class Runner {

    private final static Logger logger = Logger.getLogger(Runner.class);

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose action? Get data from file(f)/Generate Random data and write to file(r)");
        String methodInput = scanner.next();

        int size = 0;
        try {

            if (methodInput.equalsIgnoreCase("r")) {

                System.out.println("Insert path to file");
                //Example: src\\main\\resources\\input\\input.txt
                String pathName = scanner.next();

                System.out.println("Matrix size [1..10000]");
                size = scanner.nextInt();
                checkSize(size);

                generateTwoMatricesAndWriteToFile(size, pathName);

            } else if (methodInput.equalsIgnoreCase("f")) {

                System.out.println("Insert path to input file");
//              Example "src\\main\\resources\\matrix\\input\\input4.txt";
                String pathName = scanner.next();
                System.out.println("Insert path output to file for sequential multiplication");
//              Example "src\\main\\resources\\matrix\\output\\output4_1.txt";
                String sOutputPath = scanner.next();

                System.out.println("Insert path output to file for parallel multiplication");
//              Example "src\\main\\resources\\matrix\\output\\output4_2.txt";
                String pOutputPath = scanner.next();

                File file = new File(pathName);
                Scanner fileScanner = new Scanner(file);

                size = fileScanner.nextInt();
                checkSize(size);

                Matrix matrixA = ReadWriteHelper.readMatrix(fileScanner, size);
                Matrix matrixB = ReadWriteHelper.readMatrix(fileScanner, size);

                MatrixMultiplier matrixMultiplier = new MatrixMultiplier();

                multiplyAndTrace(sOutputPath, matrixA, matrixB, matrixMultiplier, false);
                multiplyAndTrace(pOutputPath, matrixA, matrixB, matrixMultiplier, true);

            }
        } catch (NotSuitableMatrixSizeException e) {
            logger.error(String.format("Wrong matrix size %d. Should be above 0. \n", size));
        } catch (FileNotFoundException e) {
            logger.error("Wrong filename");
        }
    }

    private static void multiplyAndTrace(String outputPath, Matrix matrixA, Matrix matrixB,
                                         MatrixMultiplier matrixMultiplier, boolean isParallel) {
        long time = System.currentTimeMillis();
        int[][] result;
        result = (isParallel) ?
                matrixMultiplier.multiplyMatricesInParallel(matrixA, matrixB) :
                matrixMultiplier.multiplyMatricesSequentially(matrixA, matrixB);

        time = System.currentTimeMillis() - time;
        String multType = (isParallel) ? "Parallel" : "Sequential";
        logger.info(String.format("%s multiplication took %d milliseconds..\n", multType, time));
        ReadWriteHelper.writeOutputMatrixToFile(outputPath, matrixA.getN(), result);
        logger.info(String.format("%s multiplication was written to file %s \n", multType, outputPath));
    }

    private static void checkSize(int size) throws NotSuitableMatrixSizeException {
        if (size < 1) throw new NotSuitableMatrixSizeException();
    }

    private static void generateTwoMatricesAndWriteToFile(int size, String pathName) {
        Matrix matrixA = new Matrix(size);
        Matrix matrixB = new Matrix(size);
        ReadWriteHelper.writeInputMatricesToFile(pathName, size, matrixA, matrixB);
    }
}
