package matrix;

import matrix.entity.Matrix;
import matrix.entity.NotSuitableMatrixSizeException;
import matrix.entity.WrongInputDataException;
import matrix.helper.ReadWriteHelper;
import matrix.operation.MatrixMultiplier;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

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
                //Example: C:\\Users\\user\\IdeaProjects\\MatrixOperations\\src\\main\\resources\\input.txt
                String pathName = scanner.next();

                System.out.println("Matrix size [1..10000]");
                size = scanner.nextInt();
                checkSize(size);

                generateTwoMatricesAndWriteToFile(size, pathName);

            } else if (methodInput.equalsIgnoreCase("f")) {
                System.out.println("Insert path to input file");
                //Example: C:\\Users\\user\\IdeaProjects\\MatrixOperations\\src\\main\\resources\\input.txt
                String pathName = scanner.next();
                System.out.println("Insert path output to file");
                //Example: C:\\Users\\user\\IdeaProjects\\MatrixOperations\\src\\main\\resources\\output.txt
                String outputPathName = scanner.next();

                File file = new File(pathName);
                Scanner fileScanner = new Scanner(file);

                size = fileScanner.nextInt();
                checkSize(size);

                Matrix matrixA = ReadWriteHelper.readMatrix(fileScanner, size);
                Matrix matrixB = ReadWriteHelper.readMatrix(fileScanner, size);

                MatrixMultiplier matrixMultiplier = new MatrixMultiplier();
                int[][] result = matrixMultiplier.multiplyAndTrace(matrixA, matrixB);

                ReadWriteHelper.writeOutputMatrixToFile(outputPathName, size, result);
            }
        } catch (NotSuitableMatrixSizeException e) {
            logger.error(String.format("Wrong matrix size %d. Should be above 0. \n", size));
        } catch (FileNotFoundException e) {
            logger.error("Wrong filename");
        } catch (WrongInputDataException e) {
            logger.error("Wrong input data");
        }
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
